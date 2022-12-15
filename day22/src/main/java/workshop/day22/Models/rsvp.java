package workshop.day22.Models;

import java.util.Date;

public class rsvp {
    private String name;
    private String email;
    private String phone;
    private Date confirmation_date;
    private String comments;
    
    public rsvp(String name, String email, String phone, Date confirmation_date, String comments) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.confirmation_date = confirmation_date;
        this.comments = comments;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Date getConfirmation_date() {
        return confirmation_date;
    }
    public void setConfirmation_date(Date confirmation_date) {
        this.confirmation_date = confirmation_date;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "rsvp [name=" + name + ", email=" + email + ", phone=" + phone + ", confirmation_date="
                + confirmation_date + ", comments=" + comments + "]";
    }

    
}