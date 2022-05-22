package vttp2022.paf.Project.model;

public class UserRatings {
    private String title;
    private Integer rating;
    private Integer year;
    private String type;
    private String imdbId;
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getImdbId() {
        return imdbId;
    }
    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
    
    @Override
    public String toString() {
        return "UserRatings [imdbId=" + imdbId + ", rating=" + rating + ", title=" + title + ", type=" + type
                + ", year=" + year + "]";
    }


}
