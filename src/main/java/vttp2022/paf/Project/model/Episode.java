package vttp2022.paf.Project.model;

public class Episode {
    private String title;
    private Integer seasonNumber;
    private Integer episodeNumber;
    private Integer year;
    private Integer runtime;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getSeasonNumber() {
        return seasonNumber;
    }
    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }
    public Integer getEpisodeNumber() {
        return episodeNumber;
    }
    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
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
    
    @Override
    public String toString() {
        return "Episode [episodeNumber=" + episodeNumber + ", runtime=" + runtime + ", seasonNumber=" + seasonNumber
                + ", title=" + title + ", year=" + year + "]";
    }

}
