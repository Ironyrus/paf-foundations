package mongodb.day27.Models;

public class day27Model {
    String id;
    String c_id;
    String user;
    Integer rating;
    String c_text;
    String gid;
    Double textScore;

    public day27Model(String id, String c_id, String user, Integer rating, String c_text, String gid, Double textScore) {
        this.id = id;
        this.c_id = c_id;
        this.user = user;
        this.rating = rating;
        this.c_text = c_text;
        this.gid = gid;
        this.textScore = textScore;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getC_id() {
        return c_id;
    }
    public void setC_id(String c_id) {
        this.c_id = c_id;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getC_text() {
        return c_text;
    }
    public void setC_text(String c_text) {
        this.c_text = c_text;
    }
    public String getGid() {
        return gid;
    }
    public void setGid(String gid) {
        this.gid = gid;
    }

    

    @Override
    public String toString() {
        return "day27Model [id=" + id + ", c_id=" + c_id + ", user=" + user + ", rating=" + rating + ", c_text="
                + c_text + ", gid=" + gid + "]";
    }
    public Double getTextScore() {
        return textScore;
    }
    public void setTextScore(Double textScore) {
        this.textScore = textScore;
    }

        
}
