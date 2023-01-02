package paf.assessment.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import paf.assessment.queries;
import paf.assessment.Models.task;
import paf.assessment.Models.User;

@Repository
public class TaskRepository {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserService userService;

    public int insertTask(task task, String username_to_check) {
        // insert into task (description, priority, due_date) values("he-ho", 1, "2022-01-01");
        
        // Checking if user exists:
        String user_id = "empty. please check.";
        final SqlRowSet query = jdbcTemplate.queryForRowSet(queries.GET_ALL_USERS);
        while(query.next()){
            if(username_to_check.equals(query.getString("username"))){
                user_id = query.getString("user_id");
                System.out.println("[TASK REPO]: User exists.");
            }
        }
        // If user id does not exist, create a new user.
        if (user_id.equals("empty. please check.")){
            User user = new User();
            user.create(username_to_check, "New User");
            //Insert the new user in the User table, if not we won't be able to update task table due to constraints
            userService.insertUser(user);

            user_id = user.getUser_id();

            System.out.println("New user " + username_to_check + " created. Saving into database...");
        }

        int check = jdbcTemplate.update(queries.INSERT_TASK, task.getDescription(), task.getPriority(), task.getDue_date(), user_id);
        return check;
    }

}