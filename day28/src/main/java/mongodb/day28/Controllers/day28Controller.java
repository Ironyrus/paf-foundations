    package mongodb.day28.Controllers;

    import java.util.ArrayList;
    import java.util.LinkedList;
    import java.util.List;

    import org.bson.Document;
    import org.springframework.beans.factory.annotation.Autowired;

    // mongoimport --uri "mongodb://mongo:YluG28CJlogd441DDKBo@containers-us-west-130.railway.app:7524" --drop --authenticationDatabase admin --db=game -c=comments  --jsonArray "./game.json"
    /*
    --- Railway Deployment ---
    NOTE: pom.xml java version must be 11

    Railway login
    Railway link
    Railway up -d
    */
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RequestParam;

    import mongodb.day28.Repositories.day28Repo;

    @Controller
    @RequestMapping(path="/")
    public class day28Controller {

        @Autowired
        day28Repo repo;
        
        @PostMapping("results")
        public String getResults(Model model, @RequestParam("game") String game){
            List<Document> results = repo.findGameByName(game);
            // List<String> games = new LinkedList<>();

            // List<Document> demo = repo.getAllGames();
            // for (Document doc : demo) {
            //     games.add(doc.getString("name"));
            // }
            
            try {
                model.addAttribute("name", results.get(0).getString("name"));
                model.addAttribute("year", results.get(0).getInteger("year"));
                model.addAttribute("ranking", results.get(0).getInteger("ranking"));
                model.addAttribute("url", results.get(0).getString("url"));
                model.addAttribute("image", results.get(0).getString("image"));
                // model.addAttribute("games", games);
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
            
            return "results";
        }
    }
