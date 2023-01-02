package paf.assessment.Models;

public class task {
    private String description;
    private String priority;
    private String due_date;
    private String user_id;
    private String delete;
    
    public task(String description, String priority, String due_date) {
        this.description = description;
        this.priority = priority;
        this.due_date = due_date;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getPriority() {
        int priority2 = 0;
        if(priority.equals("high")){
            priority2 = 3;
        } else if(priority.equals("medium")){
            priority2 = 2;
        } else{
            priority2 = 1;
        }
        return priority2;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public String getDue_date() {
        return due_date;
    }
    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String isDelete() {
        return delete;
    }
    public void setDelete(String delete) {
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "Task [description=" + description + ", priority=" + priority + ", due_date=" + due_date + ", user_id="
                + user_id + ", delete=" + delete + "]";
    }

}