package workshop.day21.Models;

public class orders {
    private String id;
    private String employee_id;
    private String order_date;
    private String shipped_date;
    private String shipper_id;
    private String ship_name;

    public orders(String id, String employee_id, String order_date, String shipped_date, String shipper_id,
            String ship_name) {
        this.id = id;
        this.employee_id = employee_id;
        this.order_date = order_date;
        this.shipped_date = shipped_date;
        this.shipper_id = shipper_id;
        this.ship_name = ship_name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEmployee_id() {
        return employee_id;
    }
    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }
    public String getOrder_date() {
        return order_date;
    }
    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }
    public String getShipped_date() {
        if (this.shipped_date == null)
            this.shipped_date = "";
        return shipped_date;
    }
    public void setShipped_date(String shipped_date) {
        this.shipped_date = shipped_date;
    }
    public String getShipper_id() {
        if (this.shipper_id == null)
            this.shipper_id = "";
        return shipper_id;
    }
    public void setShipper_id(String shipper_id) {
        this.shipper_id = shipper_id;
    }
    public String getShip_name() {
        return ship_name;
    }
    public void setShip_name(String ship_name) {
        this.ship_name = ship_name;
    }
    @Override
    public String toString() {
        return "orders [id=" + id + ", employee_id=" + employee_id + ", order_date=" + order_date + ", shipped_date="
                + shipped_date + ", shipper_id=" + shipper_id + ", ship_name=" + ship_name + "]";
    }

    
}
