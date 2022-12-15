package workshop.day21.Repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.type.ArrayType;

import workshop.day21.boy;
import workshop.day21.queries;
import workshop.day21.Models.customers;
import workshop.day21.Models.orders;

@Repository
public class northwindRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public List<customers> getAllCustomers( int limit, int offset) {
        
        List<customers> customers = new ArrayList<>();
        final SqlRowSet query = jdbcTemplate.queryForRowSet(queries.SQL_SELECT_ALL_CUSTOMERS, limit, offset);
        while(query.next()){
            customers.add(new customers(
                query.getString("id"), 
                query.getString("company"), 
                query.getString("first_name"), 
                query.getString("last_name"), 
                query.getString("job_title"), 
                query.getString("business_phone"), 
                query.getString("fax_number"), 
                query.getString("address")));
            
        }

        return customers;
    }

    public customers getCustomerById(int customer_id) {
        customers customer = new customers(null, null, null, null, null, null, null, null);
        final SqlRowSet query = jdbcTemplate.queryForRowSet(queries.SQL_SELECT_CUSTOMER_BY_ID, customer_id);
        while(query.next()){
            customer = new customers(
                query.getString("id"), 
                query.getString("company"), 
                query.getString("first_name"), 
                query.getString("last_name"), 
                query.getString("job_title"), 
                query.getString("business_phone"), 
                query.getString("fax_number"), 
                query.getString("address"));
            
        }
        return customer;
    }

    public List<orders> getOrdersByCustomerId(int customer_id) {
        List<orders> orders = new ArrayList<>();
        final SqlRowSet query = jdbcTemplate.queryForRowSet(queries.SQL_SELECT_ORDERS_BY_CUSTOMER_ID, customer_id);
        while(query.next()){
            orders.add(new orders(
                query.getString("id"),
                query.getString("employee_id"),
                query.getString("order_date"),
                query.getString("shipped_date"),
                query.getString("shipper_id"),
                query.getString("ship_name")));
        }
        
        return orders;
    } 

    public void hello() {
        String name = "John";
        Integer age = 34;

        boy boy10 = new boy("Chuk", 50, "chuk@google.com");
       
        boy boy3 = new boy("Kenneth", 35, "kenken@nus.com");

        boy boy1 = new boy("Ridhwan", 30, "ridhwan@r.com");
       
        List<boy> arrayBoys = new ArrayList<>();
        arrayBoys.add(boy10);
        arrayBoys.add(boy3);
        arrayBoys.add(boy1);
        arrayBoys.add(boy1);
        arrayBoys.add(boy1);


        for (int i = 0; i < arrayBoys.size(); i++) {
            System.out.println(arrayBoys.get(i).getName());

        }

    }
}