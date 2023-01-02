package paf.assessment.Models;

import java.util.UUID;

public class User {
    private String user_id;
    private String username;
    private String name;
    
    public User(){
    };

    public User(String user_id, String username, String name) {
        this.user_id = user_id;
        this.username = username;
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void create(String username, String name){
        this.user_id = UUID.randomUUID().toString().substring(0, 8);
        this.username = username;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User [user_id=" + user_id + ", username=" + username + ", name=" + name + "]";
    }
       
}