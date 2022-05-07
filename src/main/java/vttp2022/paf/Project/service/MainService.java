package vttp2022.paf.Project.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.paf.Project.model.Media;
import vttp2022.paf.Project.model.StreamingSite;
import vttp2022.paf.Project.repository.MainRepository;

import static vttp2022.paf.Project.model.ConversionUtils.*;

@Service
public class MainService {

    @Autowired
    private MainRepository mainRepo;

    @Value("${rapid.api.key}")
    private String apiKey;

    public static final String baseURL = "https://streaming-availability.p.rapidapi.com/search/basic?";

    public Integer makingRequest(String input, String country, String type, String service) throws IOException {
        Integer pages = 1;

        String url = UriComponentsBuilder
            .fromUriString(baseURL)
            .queryParam("country", country)
            .queryParam("service", service)
            .queryParam("type", type)
            .queryParam("keyword", input)
            .queryParam("page", pages)
            .toUriString();

        System.out.println(">>>>> URL: " + url);

        RequestEntity<Void> req = RequestEntity
            .get(url)
            .header("X-RapidAPI-Host", "streaming-availability.p.rapidapi.com")
            .header("X-RapidAPI-Key", apiKey)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req, String.class);

        List<Media> resultsList = new LinkedList<>();

        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject object = reader.readObject();
            String jsonResponse = object.toString();
            pages = object.getInt("total_pages");

            System.out.println(">>>>> JSON Response: " + jsonResponse);
            
            JsonArray resultsArr = object.getJsonArray("results");

            for (int i = 0; i < resultsArr.size(); i++) {
                JsonObject resultsObj = resultsArr.getJsonObject(i);

                Media media = convertJSON(resultsObj, service, country);
                
                if (!mainRepo.searchExistingTitle(media)) { // if it's not inside, add it
                    resultsList.add(media);
                } 
            }
        }

        System.out.println(">>>>> Results list: " + resultsList);

        Integer total = 0;

        for (Media media : resultsList) {
            if (mainRepo.insertMedia(media)) {
                for (StreamingSite site : media.getStreamList()) {
                    if (mainRepo.insertStreamingSite(site)) {
                        total = total + 1;
                    }
                }
            }
        }

        System.out.println(">>>>> Total added into database if one page: " + total);
        
        if (pages > 1) {
            for (int y = 2; y <= pages; y++) {
                total = total + multipleCalls(y, country, service, type, input);
            }
        }

        System.out.println(">>>>> Total added into database if multiple pages: " + total);

        return total;
    }

    public Integer multipleCalls(Integer pages, String country, String service, String type, String input) throws IOException {
        String url = UriComponentsBuilder
            .fromUriString(baseURL)
            .queryParam("country", country)
            .queryParam("service", service)
            .queryParam("type", type)
            .queryParam("keyword", input)
            .queryParam("page", pages)
            .toUriString();

        System.out.println(">>>>> URL: " + url);

        RequestEntity<Void> req = RequestEntity
            .get(url)
            .header("X-RapidAPI-Host", "streaming-availability.p.rapidapi.com")
            .header("X-RapidAPI-Key", apiKey)
            .accept(MediaType.APPLICATION_JSON)
            .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req, String.class);

        List<Media> resultsList = new LinkedList<>();

        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject object = reader.readObject();
            String jsonResponse = object.toString();

            System.out.println(">>>>> JSON Response: " + jsonResponse);
            
            JsonArray resultsArr = object.getJsonArray("results");

            for (int i = 0; i < resultsArr.size(); i++) {
                JsonObject resultsObj = resultsArr.getJsonObject(i);

                Media media = convertJSON(resultsObj, service, country);
                
                if (!mainRepo.searchExistingTitle(media)) { // if it's not inside, add it
                    resultsList.add(media);
                } 
            }
        }

        System.out.println(">>>>> Results list: " + resultsList);

        Integer total = 0;

        for (Media media : resultsList) {
            if (mainRepo.insertMedia(media)) {
                for (StreamingSite site : media.getStreamList()) {
                    if (mainRepo.insertStreamingSite(site)) {
                        total = total + 1;
                    }
                }
            }
        }

        System.out.println(">>>>> Total added into database: " + total);
    
        return total;        
    }
}
