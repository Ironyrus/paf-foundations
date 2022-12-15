package workshop.day23;

public class Queries {
    public static String SQL_GET_ORDER_DETAILS = "select * from orders where id = ?";
    public static String SQL_GET_TOTAL_PRICE = "select sum(quantity * unit_price) as total from order_details where order_id = ? group by order_id";
    public static String SQL_GET_COST_PRICE = "select p.id as product_id, p.standard_cost, od.order_id, od.quantity, sum(od.quantity * p.standard_cost) as costs from products p join order_details od on od.product_id = p.id where od.order_id = ? group by od.order_id";
}