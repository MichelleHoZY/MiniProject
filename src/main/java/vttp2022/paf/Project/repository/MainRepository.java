package vttp2022.paf.Project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.paf.Project.model.Media;
import vttp2022.paf.Project.model.StreamingSite;

import static vttp2022.paf.Project.repository.Queries.*;

@Repository
public class MainRepository {

    @Autowired
    private JdbcTemplate template;

    public boolean insertMedia(Media media) {
        int count = template.update(
          SQL_INSERT_NEW_MEDIA, media.getTitle(), media.getOriginalTitle(), media.getRating(), media.getOverview(),
            media.getImdbId(), media.getYear(), media.getRuntime(), media.getLanguage(),
            media.getLeaving(), media.getPosterPath() 
        );

        return count == 1;        
    }

    public boolean insertStreamingSite(StreamingSite site) {
        int count = template.update(
            SQL_INSERT_NEW_STREAMING_SITE, site.getTitle(), site.getStreamingSite()
        );

        return count == 1;
    }

    public boolean searchExistingTitle(Media media) {
        SqlRowSet rs = template.queryForRowSet(
            SQL_SEARCH_MEDIA_BY_TITLE, media.getTitle()
        );

        if (!rs.next()) {
            return false;
        }

        return true;
    }

    public boolean searchExistingStreamingSite(StreamingSite site) {
        SqlRowSet rs = template.queryForRowSet(
            SQL_SEARCH_STREAMING_SITE, site.getTitle()
        );

        if (!rs.next()) {
            return false;
        }

        return true;
    }
    
}
