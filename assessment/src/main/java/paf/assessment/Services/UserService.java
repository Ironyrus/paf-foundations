package paf.assessment.Services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import paf.assessment.queries;
import paf.assessment.Models.User;

@Service
public class UserService {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Optional<User> findUserByUserId(String userId) {
        User user = new User();
        final SqlRowSet query = jdbcTemplate.queryForRowSet(queries.SELECT_USER_BY_USERID, userId);
        while(query.next()){
            user = new User(
                query.getString("user_id"),
                query.getString("username"),
                query.getString("name")
            );
        }

        Optional<User> toReturn = null;

        if(user.getName() != null){
            toReturn = toReturn.ofNullable(user);
            // Returns the user if not null
            return toReturn;
        } else {
            toReturn.ofNullable(user);
            // Returns null if query does not return anything
            return toReturn;
        }
    }

    public String insertUser(User user) {
        
        //String user_id = UUID.randomUUID().toString().substring(0, 8);

        int check = jdbcTemplate.update(queries.INSERT_USER, user.getUser_id(), user.getUsername(), user.getName());

        // If insert successful, return user id
        return user.getUser_id();
    }

}