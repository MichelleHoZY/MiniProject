package vttp2022.paf.Project.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.paf.Project.model.DatabaseResult;
import vttp2022.paf.Project.model.SearchResult;
import vttp2022.paf.Project.repository.MainRepository;

import static vttp2022.paf.Project.model.ConversionUtils.*;

@Service
public class MainService {

    Logger logger = LoggerFactory.getLogger(MainService.class);

    @Autowired
    private MainRepository mainRepo;

    @Value("${rapid.api.key}")
    private String rapidApiKey;

    @Value("${om.api.key}")
    private String omApiKey;

    public static final String baseURL = "https://streaming-availability.p.rapidapi.com/get/basic?";

    public static final String omdbBaseURL = "http://www.omdbapi.com/";

    public List<DatabaseResult> searchDatabaseForInput(String input) throws IOException {
        List<DatabaseResult> databaseResultList = new LinkedList<>();
        databaseResultList = mainRepo.searchExistingTitle(input);

        return databaseResultList;
    }   

    public Optional<SearchResult> ratingsApiRequest(String imdbId) throws IOException {
        
        String url = UriComponentsBuilder
            .fromUriString(omdbBaseURL)
            .queryParam("i", imdbId)
            .queryParam("apikey", omApiKey)
            .toUriString();

            System.out.println(">>>>> OM URL: " + url);

            RequestEntity<Void> req = RequestEntity
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();

            RestTemplate template = new RestTemplate();

            ResponseEntity<String> resp = template.exchange(req, String.class);

            try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
                JsonReader reader = Json.createReader(is);
                JsonObject object = reader.readObject();
                String jsonResponse = object.toString();

                System.out.println(">>>>> OM JSON Response: " + jsonResponse);

                String response = object.getString("Response");

                if (response.equals("False")) {
                    return Optional.empty();
                }

                String type = object.getString("Type");

                SearchResult result = convertSearchResult(object, imdbId);

                if (type.equals("series")) {
                    result.setTotalSeasons(mainRepo.getNumberOfSeasons(imdbId));
                    result.setTotalEpisodes(mainRepo.getNumberOfEpisodes(imdbId));
                }

                System.out.println(">>>>> Search result details: " + result);

                List<String> streamingList = streamingApiRequest(imdbId);
                result.setStreaming(streamingList);

                return Optional.of(result);

                }
            }

    public List<String> streamingApiRequest(String imdbId) throws IOException {

        String url = UriComponentsBuilder
            .fromUriString(baseURL)
            .queryParam("country", "sg")
            .queryParam("imdb_id", imdbId)
            .toUriString();

        System.out.println(">>>>> URL: " + url);

        RequestEntity<Void> req = RequestEntity
            .get(url)
            .header("X-RapidAPI-Host", "streaming-availability.p.rapidapi.com")
            .header("X-RapidAPI-Key", rapidApiKey)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject object = reader.readObject();
            String jsonResponse = object.toString();

            System.out.println(">>>>> JSON Response: " + jsonResponse);
            
            JsonObject streamingInfo = object.getJsonObject("streamingInfo");

            Set<String> streamingSiteKeys = streamingInfo.keySet();
            List<String> list = new LinkedList<>();
            for (String value : streamingSiteKeys) {
                String site = value;
                list.add(site);
            }
            
            return list;
        }
    }

}
