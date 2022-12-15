package workshop.day21;

public class queries {
    public static String SQL_SELECT_ALL_CUSTOMERS = "select * from customers limit ? offset ?";

    public static String SQL_SELECT_CUSTOMER_BY_ID = "select * from customers where id = ?";

    public static String SQL_SELECT_ORDERS_BY_CUSTOMER_ID = 
    "select c.id, c.first_name, c.last_name, o.* from orders o" +
    " join customers c" + 
    " on o.customer_id = c.id" + 
    " where o.customer_id = ?";
}
