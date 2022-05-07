package vttp2022.paf.Project.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import jakarta.json.JsonObject;

public class ConversionUtils {

    public static Media convertJSON(JsonObject object, String service, String country) {

        Media media = new Media();

            String originalTitle = object.getString("originalTitle");
            media.setOriginalTitle(originalTitle);
            System.out.println(">>>>> Original title: " + originalTitle);

            String title = object.getString("title");
            media.setTitle(title);
            System.out.println(">>>>> Title: " + title);

            Integer runtime = object.getInt("runtime");
            media.setRuntime(runtime);
            String runtimeString = String.valueOf(runtime);
            System.out.println(">>>>> Runtime: " + runtime);

            String posterPath = object.getString("posterPath");
            media.setPosterPath(posterPath);
            System.out.println(">>>>> Poster path: " + posterPath);

            Integer year = object.getInt("year");
            media.setYear(year);
            String yearString = String.valueOf(year);
            System.out.println(">>>>> Year: " + year);

            Integer ratingInt = object.getInt("imdbRating");
            Double ratingDecimal = ratingInt * 0.1;
            String ratingString = String.valueOf(ratingDecimal);
            media.setRating(Float.parseFloat(ratingString));
            System.out.println(">>>>> Rating: " + ratingString);

            String overview = object.getString("overview");
            media.setOverview(overview);
            System.out.println(">>>>> Overview: " + overview);

            String language = object.getString("originalLanguage");
            media.setLanguage(language);
            System.out.println(">>>>> Language: " + language);

            String imdbId = object.getString("imdbID");
            media.setImdbId(imdbId);
            System.out.println(">>>>> Imdb ID: " + imdbId);

            JsonObject streamingInfo = object.getJsonObject("streamingInfo");

            Set<String> streamingSiteKeys = streamingInfo.keySet();
            String streamingSiteKeysString = streamingSiteKeys.toString();
            List<StreamingSite> list = new LinkedList<>();
            for (String value : streamingSiteKeys) {
                StreamingSite site = new StreamingSite();
                site.setStreamingSite(value);
                site.setTitle(title);
                list.add(site);
            }
            media.setStreamList(list);
            System.out.println(">>>>> Stream list: " + list);
            // media.setStreamingSite(streamingSiteKeysString);
            // System.out.println(">>>>> Streaming site keys: " + streamingSiteKeys);

            JsonObject streamingSite = streamingInfo.getJsonObject(service);
            Set<String> countryKeys = streamingSite.keySet();
            String countryKeysString = countryKeys.toString();
            System.out.println(">>>>> Keys: " + countryKeys);
            System.out.println(">>>>> Streaming site: " + streamingSite);

            JsonObject countryStreamingInfo = streamingSite.getJsonObject(country);
            String streamingLink = countryStreamingInfo.getString("link");
            media.setStreamingLink(streamingLink);
            System.out.println(">>>>> Streaming link: " + streamingLink);
            
            Integer leaving = countryStreamingInfo.getInt("leaving");
            String leavingString = String.valueOf(leaving);
            media.setLeaving(leaving);
            System.out.println(">>>>> Leaving: " + leaving);

            // resultsList.add(originalTitle);
            // resultsList.add(title);
            // resultsList.add(ratingString);
            // resultsList.add(runtimeString);
            // resultsList.add(posterPath);
            // resultsList.add(yearString);
            // resultsList.add(streamingSiteKeysString);
            // resultsList.add(countryKeysString);
            // resultsList.add(streamingLink);
            // resultsList.add(leavingString);
            // resultsList.add(overview);

            // System.out.println(">>>>> List item: " + resultsList);
            System.out.println(">>>>> Media: " + media);
        
        return media;
    }
    
}
