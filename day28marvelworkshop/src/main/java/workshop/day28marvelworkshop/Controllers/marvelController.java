package workshop.day28marvelworkshop.Controllers;

/*
--- Railway Deployment ---
NOTE: pom.xml java version must be 11

Railway login
Railway link
Railway up -d
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import workshop.day28marvelworkshop.Models.marvelModel;
import workshop.day28marvelworkshop.Repositories.marvelRepository;
import workshop.day28marvelworkshop.Services.MarvelService;

@Controller
@RequestMapping("")
public class marvelController {
    
    @Autowired
    MarvelService service;

    @Autowired
    marvelRepository repo;

    @PostMapping("/results")
    public String getResults(Model model, @RequestParam("search") String name) {
        
        List<marvelModel> arr = null;
        
        Optional<List<marvelModel>> opt = repo.get(name);
        if(opt.isEmpty()){
            //If Redis does not have data, make API call to Marvel, and cache the data
            arr = service.search(name);
            repo.cache(name, arr);
        } else {
            arr = opt.get();
            System.out.println(">>>FROM CACHE\n");
        }

        model.addAttribute("heroes", arr);
        return "results";
    }

}