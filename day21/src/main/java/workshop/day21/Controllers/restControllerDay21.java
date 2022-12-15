package workshop.day21.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import workshop.day21.Models.customers;
import workshop.day21.Models.orders;
import workshop.day21.Repository.northwindRepository;

@RestController
@RequestMapping(path="/api", produces="application/json")
public class restControllerDay21 {
    
    @Autowired
    northwindRepository nwRepo;
    
    @GetMapping(path="/customers")
    public ResponseEntity<String> getAllCustomers(
        @RequestParam("offset") String offset,
        @RequestParam("limit") String limit) {
        
        if(limit.equals(""))
            limit = "5";

        if(offset.equals(""))
            offset = "0";
        
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder builderArr = Json.createArrayBuilder();
        List<customers> customers = nwRepo.getAllCustomers(Integer.parseInt(limit), Integer.parseInt(offset));
        int count = 0;
        for (customers customer : customers) {
            builder.add("id", customer.getId())
            .add("company", customer.getCompany())
            .add("first_name", customer.getFirst_name())
            .add("last_name", customer.getLast_name())
            .add("job_title", customer.getJob_title())
            .add("business_phone", customer.getBusiness_phone())
            .add("fax_number", customer.getFax_number())
            .add("address", customer.getAddress());
            JsonObject jj = builder.build();
            builderArr.add(count, jj);
            count++;
        }       

        JsonArray j = builderArr.build();
        return ResponseEntity.ok(j.toString());
    }

    @GetMapping(path="/customer/{customer_id}")
    public ResponseEntity<String> getCustomerById(@PathVariable("customer_id") int customer_id) {
        customers customer = new customers(null, null, null, null, null, null, null, null);
        try {
            customer = nwRepo.getCustomerById(customer_id);
            if(customer.getId().equals(null))
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
        JsonObject customerJson = Json.createObjectBuilder()
        .add("id", customer.getId())
        .add("company", customer.getCompany())
        .add("first_name", customer.getFirst_name())
        .add("last_name", customer.getLast_name())
        .add("job_title", customer.getJob_title())
        .add("business_phone", customer.getBusiness_phone())
        .add("fax_number", customer.getFax_number())
        .add("address", customer.getAddress())
        .build();
        return ResponseEntity.ok(customerJson.toString());
    }

    @GetMapping(path="/customer/{customer_id}/orders")
    public ResponseEntity<String> getOrderByCustomerId(@PathVariable("customer_id") int customer_id) {
        List<orders> order = new ArrayList<>();
        try {
            order = nwRepo.getOrdersByCustomerId(customer_id);
            if(customer_id > 29)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // TODO: handle exception
        }
        int count = 0;
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder builderArr = Json.createArrayBuilder();
        try {
            for (orders each : order) {
            builder.add("id", each.getId())
            .add("employee_id", each.getEmployee_id())
            .add("order_date", each.getOrder_date())
            .add("shipped_date", each.getShipped_date())
            .add("shipper_id", each.getShipper_id())
            .add("ship_name", each.getShip_name());
            JsonObject temp = builder.build();
            builderArr.add(count, temp);
            count++;
        }
        } catch (JsonException je) {
            je.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        JsonArray jsonArray = builderArr.build();
        return ResponseEntity.ok(jsonArray.toString());
    }

    
    @GetMapping("/home")
    public ResponseEntity<String> getHome() {
        
        nwRepo.hello();

        /*
        {
            "name":"muz"

        }
        

         */

         //Step 1 - Create builder
        JsonObjectBuilder builder = Json.createObjectBuilder();
        // Step 2 - add key and value to builder
        builder.add("name", "muz");
        // Step 3 - Build the Json
        JsonObject jsonObject = builder.build();


        return ResponseEntity.ok().body(jsonObject.toString());
    }



}