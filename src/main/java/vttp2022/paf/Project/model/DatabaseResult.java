package vttp2022.paf.Project.model;

public class DatabaseResult {
    private String primaryTitle;
    private String originalTitle;
    private String imdbId;
    private Integer year;
    private String genre;
    private String type;
    
    public String getPrimaryTitle() {
        return primaryTitle;
    }
    public void setPrimaryTitle(String primaryTitle) {
        this.primaryTitle = primaryTitle;
    }
    public String getOriginalTitle() {
        return originalTitle;
    }
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
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
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DatabaseResult [genre=" + genre + ", imdbId=" + imdbId + ", originalTitle=" + originalTitle
                + ", primaryTitle=" + primaryTitle + ", type=" + type + ", year=" + year + "]";
    }
}
