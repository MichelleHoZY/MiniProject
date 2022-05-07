package vttp2022.paf.Project.model;

import java.util.Date;
import java.util.List;

public class Media {
    private String title;
    private String originalTitle;
    private Float rating;
    private String overview;
    private String imdbId;
    private Integer year;
    private Integer runtime;
    private String language;
    private Integer leaving;
    private String posterPath;
    private Date searchDate;
    private String streamingLink;
    private List<StreamingSite> streamList;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getOriginalTitle() {
        return originalTitle;
    }
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }
    public Float getRating() {
        return rating;
    }
    public void setRating(Float rating) {
        this.rating = rating;
    }
    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }
    public String getImdbId() {
        return imdbId;
    }
    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getRuntime() {
        return runtime;
    }
    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public Integer getLeaving() {
        return leaving;
    }
    public void setLeaving(Integer leaving) {
        this.leaving = leaving;
    }
    public String getPosterPath() {
        return posterPath;
    }
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
    public Date getSearchDate() {
        return searchDate;
    }
    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }

    public String getStreamingLink() {
        return streamingLink;
    }
    public void setStreamingLink(String streamingLink) {
        this.streamingLink = streamingLink;
    }
    public List<StreamingSite> getStreamList() {
        return streamList;
    }
    public void setStreamList(List<StreamingSite> streamList) {
        this.streamList = streamList;
    }
    public void addStreamList(StreamingSite streamList) {
        this.streamList.add(streamList);
    }

    @Override
    public String toString() {
        return "Media [imdbId=" + imdbId + ", language=" + language + ", leaving=" + leaving + ", originalTitle="
                + originalTitle + ", overview=" + overview + ", posterPath=" + posterPath + ", rating=" + rating
                + ", runtime=" + runtime + ", searchDate=" + searchDate + ", streamList=" + streamList
                + ", streamingLink=" + streamingLink + ", title=" + title + ", year=" + year + "]";
    }


    
}
