package vttp2022.paf.Project.model;

public class Streaming {

    private String site;
    private String link;

    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    @Override
    public String toString() {
        return "Streaming [link=" + link + ", site=" + site + "]";
    }
    
}
