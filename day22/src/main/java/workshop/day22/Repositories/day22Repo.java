package workshop.day22.Repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import workshop.day22.Models.rsvp;
import workshop.day22.queries;

@Repository
public class day22Repo {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<rsvp> getAllRsvps() {
        List<rsvp> rsvps = new ArrayList<>();
        final SqlRowSet query = jdbcTemplate.queryForRowSet(queries.SQL_SELECT_ALL_FROM_RSVP);
        while(query.next()){
            rsvps.add(new rsvp(
                query.getString("name"), 
                query.getString("email"), 
                query.getString("phone"), 
                query.getDate("confirmation_date"), 
                query.getString("comments")));
        }

        return rsvps;
    }

    public rsvp getRsvpByName(String name) {
        rsvp rsvp = new rsvp(null, null, null, null, null);
        final SqlRowSet query = jdbcTemplate.queryForRowSet(queries.SQL_SELECT_BY_NAME, '%' + name + '%');
        while(query.next()){
            rsvp.setName(query.getString("name"));
            rsvp.setEmail(query.getString("email"));
            rsvp.setPhone(query.getString("phone"));
            rsvp.setConfirmation_date(query.getDate("confirmation_date"));
            rsvp.setComments(query.getString("comments"));
        }
        return rsvp;
    }

    public int updateExistingRsvp(String name, String phone, String confirmation_date, String comments, String email) {
        return jdbcTemplate.update(queries.SQL_UPDATE_RSVP_BY_EMAIL, name, phone, confirmation_date, comments, email);
    }

    public Integer addNewRsvp(String name, String email, String phone, String confirmation_date, String comments){
        return jdbcTemplate.update(queries.SQL_ADD_NEW_RSVP, name, email, phone, confirmation_date, comments);
    }

    public int getCount() {
        final SqlRowSet query = jdbcTemplate.queryForRowSet(queries.SQL_COUNT_ALL_RSVP);
        int count = 0;
        while(query.next()) {
            count = query.getInt("count(*)");
        }
        return count;
    }

}