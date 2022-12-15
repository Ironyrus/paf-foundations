package workshop.day23;

import java.util.Date;

public class orderModel {
    private String order_id;
    private String order_date;
    private String customer_id;
    private Double total;
    private Double cost_price;
    
    public String getOrder_id() {
        return order_id;
    }
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
    public String getOrder_date() {
        return order_date;
    }
    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }
    public String getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
    public Double getCost_price() {
        return cost_price;
    }
    public void setCost_price(Double cost_price) {
        this.cost_price = cost_price;
    }
    @Override
    public String toString() {
        return "orderModel [order_id=" + order_id + ", order_date=" + order_date + ", customer_id=" + customer_id
                + ", total=" + total + ", cost_price=" + cost_price + "]";
    }

    
}
