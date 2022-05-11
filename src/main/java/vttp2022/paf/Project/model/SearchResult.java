package vttp2022.paf.Project.model;

import java.util.LinkedList;
import java.util.List;

public class SearchResult {

    private String title;
    private String year;
    private String rated;
    private String runtime;
    private String plot;
    private String director;
    private String language;
    private String awards;
    private String actors;
    private List<Ratings> ratings = new LinkedList<>();
    private String poster;
    private String released;
    private String type;
    private String genre;
    private String country;
    private String imdbId;
    private Integer totalSeasons;
    private Integer totalEpisodes;
    private List<String> streaming = new LinkedList<>();
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getRated() {
        return rated;
    }
    public void setRated(String rated) {
        this.rated = rated;
    }
    public String getRuntime() {
        return runtime;
    }
    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }
    public String getPlot() {
        return plot;
    }
    public void setPlot(String plot) {
        this.plot = plot;
    }
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getAwards() {
        return awards;
    }
    public void setAwards(String awards) {
        this.awards = awards;
    }
    public String getActors() {
        return actors;
    }
    public void setActors(String actors) {
        this.actors = actors;
    }
    public List<Ratings> getRatings() {
        return ratings;
    }
    public void setRatings(List<Ratings> ratings) {
        this.ratings = ratings;
    }
    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }
    public String getReleased() {
        return released;
    }
    public void setReleased(String released) {
        this.released = released;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getImdbId() {
        return imdbId;
    }
    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
    public List<String> getStreaming() {
        return streaming;
    }
    public void setStreaming(List<String> streaming) {
        this.streaming = streaming;
    }
    public Integer getTotalSeasons() {
        return totalSeasons;
    }
    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }
    public Integer getTotalEpisodes() {
        return totalEpisodes;
    }
    public void setTotalEpisodes(Integer totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }
    @Override
    public String toString() {
        return "SearchResult [actors=" + actors + ", awards=" + awards + ", country=" + country + ", director="
                + director + ", genre=" + genre + ", imdbId=" + imdbId + ", language=" + language + ", plot=" + plot
                + ", poster=" + poster + ", rated=" + rated + ", ratings=" + ratings + ", released=" + released
                + ", runtime=" + runtime + ", streaming=" + streaming + ", title=" + title + ", totalEpisodes="
                + totalEpisodes + ", totalSeasons=" + totalSeasons + ", type=" + type + ", year=" + year + "]";
    }
}
