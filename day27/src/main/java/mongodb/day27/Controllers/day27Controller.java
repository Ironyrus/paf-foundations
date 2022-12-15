package mongodb.day27.Controllers;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mongodb.day27.Models.day27Model;
import mongodb.day27.Repositories.day27Repo;

@Controller
@RequestMapping(path="")
public class day27Controller {

    @Autowired
    day27Repo repo;

    @PostMapping("results")
    public String getResults(
        @RequestParam("someValue") String someValue,
        @RequestParam("someOption") String someOption,
        Model model){

            model.addAttribute("someOption", someOption);
            model.addAttribute("someValue", someValue);
            List<day27Model> results = repo.findCommentsByTextAndRating(someValue, someOption);

            model.addAttribute("results", results);

            return "results";
    }

}