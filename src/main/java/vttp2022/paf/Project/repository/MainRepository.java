package vttp2022.paf.Project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.paf.Project.model.DatabaseResult;
import vttp2022.paf.Project.model.Episode;
import vttp2022.paf.Project.model.Sidebar;
import vttp2022.paf.Project.model.UserRatings;

import static vttp2022.paf.Project.repository.Queries.*;

import java.util.LinkedList;
import java.util.List;

import static vttp2022.paf.Project.model.ConversionUtils.*;

@Repository
public class MainRepository {

    @Autowired
    private JdbcTemplate template;

    public List<DatabaseResult> searchExistingTitle(String input) {
        SqlRowSet rs = template.queryForRowSet(
            SQL_SEARCH_MEDIA_BY_TITLE, input
        );

        List<DatabaseResult> databaseResultList = new LinkedList<>();

        while (rs.next()) {
            DatabaseResult result = new DatabaseResult();
            result = convertDatabaseResult(rs);
            databaseResultList.add(result);
        }
        
        return databaseResultList;
    }

    public Integer getNumberOfEpisodes(String imdbId) {
        SqlRowSet rs = template.queryForRowSet(
            SQL_GET_NUMBER_OF_EPISODES, imdbId
        );

        if (!rs.next()) {
            return 0;
        }

        return rs.getInt("totalEpisodes");
    }

    public Integer getNumberOfSeasons(String imdbId) {
        SqlRowSet rs = template.queryForRowSet(
            SQL_GET_NUMBER_OF_SEASONS, imdbId
        );

        if (!rs.next()) {
            return 0;
        }

        return rs.getInt("totalSeasons");
    }

    public List<Episode> getSeasonEpisodes(String imdbId) {
        SqlRowSet rs = template.queryForRowSet(
            SQL_GET_SEASON_EPISODES, imdbId
        );

        List<Episode> episodeList = new LinkedList<>();

        while (rs.next()) {
            Episode episode = new Episode();
            episode = convertEpisode(rs);
            episodeList.add(episode);
        }

        return episodeList;
    }

    public boolean searchUsername(String username) {
        SqlRowSet rs = template.queryForRowSet(
            SQL_SEARCH_USERNAME, username
        );

        if (!rs.next())
            return false;

        return true;
    }

    public boolean verifyUser(String username, String password) {
        SqlRowSet rs = template.queryForRowSet(
            SQL_VERIFY_USERNAME_PASSWORD, username, password
        );

        if (!rs.next())
            return false;
        
        return true;
    }

    public boolean insertNewUser(String username, String password) {
        int count = template.update(
            SQL_INSERT_NEW_USER, username, password
        );

        return 1 == count;
    }

    public int searchUserId(String username) {
        SqlRowSet rs = template.queryForRowSet(
            SQL_SEARCH_USER_ID, username
        );   

        if (!rs.next())
            return 0;

        return rs.getInt("user_id");
    }

    public boolean insertNewUserRating(Integer userId, String imdbId, Integer rating) {
        int count = template.update(
            SQL_INSERT_NEW_USER_RATING, userId, imdbId, rating
        );

        return count ==1;
    }

    public Integer checkUserRating(Integer userId, String imdbId) {
        SqlRowSet rs = template.queryForRowSet(
            SQL_CHECK_USER_RATING_EXIST, userId, imdbId
        );

        if (!rs.next()) {
            return 0;
        }

        return rs.getInt("rating");
    }

    public boolean updateUserRating(Integer userId, String imdbId, Integer rating) {
        int count = template.update(
            SQL_UPDATE_USER_RATING, rating, userId, imdbId
        );

        return count == 1;
    }

    public List<UserRatings> recentRatings(Integer userId) {
        List<UserRatings> list = new LinkedList<>();

        SqlRowSet rs = template.queryForRowSet(
            SQL_GET_TEN_RECENT_RATINGS, userId
        );

        while (rs.next()) {
            UserRatings rating = new UserRatings();
            rating = convertUserRating(rs);
            list.add(rating);
        }

        return list;
    }

    public Sidebar sidebarInfo(Integer userId) {
        Sidebar sidebar = new Sidebar();
        Float totalRatings = 0f;
        Float total = 0f;

        SqlRowSet rs1 = template.queryForRowSet(
            SQL_COUNT_ALL_USER_RATINGS, userId
        );

        if (rs1.next()) {
            totalRatings = rs1.getFloat("total");
            sidebar.setTotalRatings(totalRatings);
        }

        SqlRowSet rs2 = template.queryForRowSet(
            SQL_SUM_ALL_USER_RATINGS, userId
        );

        if (rs2.next()) {
            total = rs2.getFloat("total");
        }

        Float average = total/totalRatings;
        sidebar.setAverage(average);

        return sidebar;
    }

    public List<UserRatings> allRatings(Integer userId) {
        List<UserRatings> list = new LinkedList<>();

        SqlRowSet rs = template.queryForRowSet(
            SQL_GET_ALL_RATINGS, userId
        );

        while (rs.next()) {
            UserRatings rating = new UserRatings();
            rating = convertAllUserRating(rs);
            list.add(rating);
        }

        return list;
    }

    public Boolean deleteUser(Integer userId) {
        int count = template.update(
            SQL_DELETE_USER, userId
        );

        return count == 1;
    }
    
}
