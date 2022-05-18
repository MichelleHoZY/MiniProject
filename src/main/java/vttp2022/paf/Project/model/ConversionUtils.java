package vttp2022.paf.Project.model;

import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

public class ConversionUtils {

    public static SearchResult convertSearchResult(JsonObject object, String imdbId) {
        SearchResult result = new SearchResult();

        String title = object.getString("Title");
        result.setTitle(title);

        String year = object.getString("Year");
        result.setYear(year);

        String rated = object.getString("Rated");
        result.setRated(rated);

        String released = object.getString("Released");
        result.setReleased(released);

        String runtime = object.getString("Runtime");
        result.setRuntime(runtime);

        String director = object.getString("Director");
        result.setDirector(director);

        String awards = object.getString("Awards");
        result.setAwards(awards);

        String poster = object.getString("Poster");
        result.setPoster(poster);

        String actors = object.getString("Actors");
        result.setActors(actors);

        String genre = object.getString("Genre");
        result.setGenre(genre);

        String language = object.getString("Language");
        result.setLanguage(language);

        List<Ratings> ratingsList = new LinkedList<>();

        JsonArray arr = object.getJsonArray("Ratings");
        for (int i = 0; i < arr.size(); i++) {
            Ratings ratings = new Ratings();
            JsonObject ratingsObj = arr.getJsonObject(i);

            String source = ratingsObj.getString("Source");
            ratings.setSource(source);

            String rating = ratingsObj.getString("Value");
            ratings.setRating(rating);

            ratingsList.add(ratings);
        }

        result.setRatings(ratingsList);

        String type = object.getString("Type");
        result.setType(type);

        String country = object.getString("Country");
        result.setCountry(country);

        result.setImdbId(imdbId);

        return result;
    }

    public static DatabaseResult convertDatabaseResult(SqlRowSet rs) {
        DatabaseResult result = new DatabaseResult();

        result.setPrimaryTitle(rs.getString("primaryTitle"));
        result.setYear(rs.getInt("startYear"));
        result.setGenre(rs.getString("genres"));
        result.setImdbId(rs.getString("tconst"));
        
        String titleType = rs.getString("titleType");

        if (titleType.equals("movie")) {
            result.setType("Movie");
        } else if (titleType.equals("tvSeries")) {
            result.setType("TV Series");
        } else if (titleType.equals("tvMiniSeries")) {
            result.setType("Mini Series");
        } else {
            result.setType("TV Movie");
        }

        return result;
    }

    public static Episode convertEpisode(SqlRowSet rs) {
        Episode episode = new Episode();

        episode.setTitle(rs.getString("title"));
        episode.setSeasonNumber(rs.getInt("season"));
        episode.setEpisodeNumber(rs.getInt("episode"));
        episode.setYear(rs.getInt("year"));
        episode.setRuntime(rs.getInt("runtime"));

        return episode;
    }
    
}
