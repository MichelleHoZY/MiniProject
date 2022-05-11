package vttp2022.paf.Project.model;

public class Ratings {
    
    private String source;
    private String rating;
    
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    
    @Override
    public String toString() {
        return "Ratings [rating=" + rating + ", source=" + source + "]";
    }
    
}
