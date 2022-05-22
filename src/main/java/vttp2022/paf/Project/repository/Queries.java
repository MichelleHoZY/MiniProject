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

    public static final String SQL_SEARCH_USERNAME = 
        "select * from users where username = ?";
    
    public static final String SQL_VERIFY_USERNAME_PASSWORD = 
        "select * from users where username = ? and password = sha1(?)";

    public static final String SQL_INSERT_NEW_USER = 
        "insert into users(username, password) values (?, sha1(?))";

    public static final String SQL_SEARCH_USER_ID = 
        "select user_id from users where username = ?";

    public static final String SQL_INSERT_NEW_USER_RATING = 
        "insert into user_ratings(user_id, tconst, rating) values (?, ?, ?)";

    public static final String SQL_CHECK_USER_RATING_EXIST =
        "select * from user_ratings where user_id = ? and tconst = ?";

    public static final String SQL_UPDATE_USER_RATING = 
        "update user_ratings set rating = ? where user_id = ? and tconst = ?";

    public static final String SQL_GET_TEN_RECENT_RATINGS = 
        "select A.rating as rating, B.primaryTitle as title, A.user_id as user_id from (select * from user_ratings where user_id = ? order by rating_id desc limit 10) A inner join imdb_data as B on A.tconst = B.tconst";
    
    public static final String SQL_COUNT_ALL_USER_RATINGS =
        "select count(*) as total from user_ratings where user_id = ?";

    public static final String SQL_SUM_ALL_USER_RATINGS = 
        "select sum(rating) as total from user_ratings where user_id = ?";

    public static final String SQL_GET_ALL_RATINGS = 
        "select A.rating as rating, B.primaryTitle as title, A.user_id as user_id, B.titleType as type, B.startYear as year, B.tconst as imdbId from (select * from user_ratings where user_id = ?) A inner join imdb_data as B on A.tconst = B.tconst order by rating desc";
    
    public static final String SQL_DELETE_USER = 
        "delete from users where user_id = ?";
    }
