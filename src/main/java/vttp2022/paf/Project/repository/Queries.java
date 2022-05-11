package vttp2022.paf.Project.repository;

public class Queries {

    public static final String SQL_SEARCH_MEDIA_BY_TITLE = 
        "select * from imdb_data where match(primaryTitle, originalTitle) against (concat('\"', ?, '\"')) and titleType not in ('video', 'videoGame', 'tvEpisode','tvSpecial', 'tvShort', 'short')";

    public static final String SQL_GET_NUMBER_OF_EPISODES = 
        "select count(*) as totalEpisodes from episodes where parentTconst = ?";

    public static final String SQL_GET_NUMBER_OF_SEASONS = 
        "select max(seasonNumber) as totalSeasons from episodes where parentTconst = ?";
    }
