package vttp2022.paf.Project.repository;

public class Queries {

    public static final String SQL_SEARCH_MEDIA_BY_TITLE = 
        "select * from imdb_data where match(primaryTitle, originalTitle) against (concat('\"', ?, '\"')) and titleType in ('movie', 'tvSeries', 'tvMovie', 'tvMiniSeries')";

    public static final String SQL_GET_NUMBER_OF_EPISODES = 
        "select count(*) as totalEpisodes from episodes where parentTconst = ?";

    public static final String SQL_GET_NUMBER_OF_SEASONS = 
        "select max(seasonNumber) as totalSeasons from episodes where parentTconst = ?";

    public static final String SQL_GET_SEASON_EPISODES = 
        "select A.seasonNumber as season, A.episodeNumber as episode, B.primaryTitle as title, B.startYear as year, B.runtimeMinutes as runtime from (select * from episodes where parentTconst = ?) A inner join imdb_data as B on A.tconst = B.tconst order by A.seasonNumber, A.episodeNumber";
    }
