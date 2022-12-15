package workshop.day23.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import workshop.day23.Repositories.day23Repo;

@Controller
@RequestMapping(path="/order")
public class day23Controller {
    
    @Autowired
    day23Repo repo;

    @GetMapping(path="total/{orderid}")
    public String getHome(
            @PathVariable("orderid") String orderid) {
        repo.getOrderDetails(orderid);
        return "index";
    }
}