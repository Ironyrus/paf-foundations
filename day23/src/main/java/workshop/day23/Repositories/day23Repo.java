package workshop.day23.Repositories;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import workshop.day23.Queries;
import workshop.day23.orderModel;

@Repository
public class day23Repo {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void getOrderDetails(String orderid) {
        
        final SqlRowSet queryForOrder = jdbcTemplate.queryForRowSet(Queries.SQL_GET_ORDER_DETAILS, orderid);
        final SqlRowSet queryForTotal = jdbcTemplate.queryForRowSet(Queries.SQL_GET_TOTAL_PRICE, orderid);
        final SqlRowSet queryForCost = jdbcTemplate.queryForRowSet(Queries.SQL_GET_COST_PRICE, orderid);

        orderModel order = new orderModel();

        while(queryForOrder.next()) {
            order.setOrder_id(queryForOrder.getString("id"));
            order.setOrder_date(queryForOrder.getString("order_date"));
            order.setCustomer_id(queryForOrder.getString("customer_id"));
        }
        while(queryForTotal.next()) {
            order.setTotal(queryForTotal.getDouble("total"));
        }
        while(queryForCost.next()) {
            order.setCost_price(queryForCost.getDouble("costs"));
        }

        System.out.println(order);
    }
}
