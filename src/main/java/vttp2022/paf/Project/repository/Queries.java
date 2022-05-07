package vttp2022.paf.Project.repository;

public class Queries {

    public static final String SQL_SEARCH_MEDIA_BY_TITLE = 
        "select * from media where title like concat('%', ?, '%')";

    public static final String SQL_INSERT_NEW_MEDIA =   
        "insert into media(title, original_title, rating, overview, imdb_id, year, runtime, language, leaving, poster_path, search_date) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now())";

    public static final String SQL_UPDATE_EXISTING_MEDIA =  
        "update media set rating = ?, overview = ? where title = ?";

    public static final String SQL_INSERT_NEW_STREAMING_SITE = 
        "insert into streaming_sites(title, streaming_site) values (?, ?)";

    public static final String SQL_SEARCH_STREAMING_SITE =
        "select * from streaming_sites where streaming_site like concat('%', ?, '%')";
    
}
