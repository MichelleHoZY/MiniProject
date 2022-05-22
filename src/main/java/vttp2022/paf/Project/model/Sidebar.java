package vttp2022.paf.Project.model;

public class Sidebar {
    private Float totalRatings;
    private Float average;
    
    public Float getTotalRatings() {
        return totalRatings;
    }
    public void setTotalRatings(Float totalRatings) {
        this.totalRatings = totalRatings;
    }
    public Float getAverage() {
        return average;
    }
    public void setAverage(Float average) {
        this.average = average;
    }

    @Override
    public String toString() {
        return "Sidebar [average=" + average + ", totalRatings=" + totalRatings + "]";
    }
    
}
