package vttp2022.paf.Project.model;

public class StreamingSite {

    private Integer id;
    private String title;
    private String streamingSite;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getStreamingSite() {
        return streamingSite;
    }
    public void setStreamingSite(String streamingSite) {
        this.streamingSite = streamingSite;
    }

    @Override
    public String toString() {
        return "StreamingSite [id=" + id + ", streamingSite=" + streamingSite + ", title=" + title + "]";
    }
    
}
