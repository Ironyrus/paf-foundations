package paf.assessment.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import paf.assessment.queries;
import paf.assessment.Models.task;
import paf.assessment.Models.User;

@Service
public class TodoService {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserService userService;

    // day 24
    // @Transactional(rollbackFor = TodoSvcException.class)
    public void upsertTask(String username_to_check, List<task> taskArr) throws TodoSvcException {
        // https://chartio.com/resources/tutorials/how-to-insert-if-row-does-not-exist-upsert-in-mysql/
        // INSERT IGNORE into user
        // (user_id, username, name)
        // values
        // ("0293f138", "biatchies", "Billy2");

        // select * from user;

        // Checking if user exists:
        String user_id = "empty. please check.";
        final SqlRowSet query = jdbcTemplate.queryForRowSet(queries.GET_ALL_USERS);
        while(query.next()){
            if(username_to_check.equals(query.getString("username"))){
                // assume that user_id is unique
                user_id = query.getString("user_id");
                System.out.println("[TO DO SVC]: User exists.");
                break;
            }
        }

        final String id = user_id;

        if(user_id.equals("empty. please check.")){
            // to-do: create user, then insert tasks
            User user = new User();
            user.create(username_to_check, "New User");
            //Insert the new user in the User table, if not we won't be able to update task table due to constraints
            userService.insertUser(user);
            System.out.println("New user " + username_to_check + " created.");
            final String newId = user.getUser_id();
            // day22 slide 17 - batch updates
            List<Object[]> params = taskArr.stream()
                                            .map(task -> new Object[] {task.getDescription(), task.getPriority(), task.getDue_date(), newId})
                                            .collect(Collectors.toList());
            int added[];
            try {
                added = jdbcTemplate.batchUpdate(queries.INSERT_TASK, params);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // to-do: insert tasks
            // day22 slide 17 - batch updates
            List<Object[]> params = taskArr.stream()
            .map(task -> new Object[] {task.getDescription(), task.getPriority(), task.getDue_date(), id})
            .collect(Collectors.toList());
            int added[];
            try {
                added = jdbcTemplate.batchUpdate(queries.INSERT_TASK, params);

            } catch (Exception e) {
                throw new TodoSvcException();
            }

        }
        
        // while(query.next()){
        //     checkUser.add(query.getString("user_id"));
        // }
        // if(checkUser.contains(user_id_to_check)) {
        //     System.out.println("user id exists");
        //     // to-do: insert tasks
        // } else {
        //     // to-do: create user, then insert tasks
        //     User user = new User();
        //     user.create("dxx01", "Mandy");
        //     // day 22 slide 17 of 23: batch updates
        // }


    }

    
}