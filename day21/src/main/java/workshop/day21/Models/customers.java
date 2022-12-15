package workshop.day21.Models;

public class customers {
    
    private String id;
    private String company;
    private String first_name;
    private String last_name;
    private String job_title;
    private String business_phone;
    private String fax_number;
    private String address;

    

    public customers(String id, String company, String first_name, String last_name, String job_title,
            String business_phone, String fax_number, String address) {
        this.id = id;
        this.company = company;
        this.first_name = first_name;
        this.last_name = last_name;
        this.job_title = job_title;
        this.business_phone = business_phone;
        this.fax_number = fax_number;
        this.address = address;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public String getJob_title() {
        return job_title;
    }
    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }
    public String getBusiness_phone() {
        return business_phone;
    }
    public void setBusiness_phone(String business_phone) {
        this.business_phone = business_phone;
    }
    public String getFax_number() {
        return fax_number;
    }
    public void setFax_number(String fax_number) {
        this.fax_number = fax_number;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    @Override
    public String toString() {
        return "customers [id=" + id + ", company=" + company + ", first_name=" + first_name + ", last_name="
                + last_name + ", job_title=" + job_title + ", business_phone=" + business_phone + ", fax_number="
                + fax_number + ", address=" + address + "]";
    }

    

}
