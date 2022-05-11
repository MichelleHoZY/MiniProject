package vttp2022.paf.Project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.paf.Project.model.DatabaseResult;

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
    
}
