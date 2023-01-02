package mongodb.day28workshop.Controllers;

import java.util.Date;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import mongodb.day28workshop.Repositories.day28Repo;

// To import JSON into mongo
// mongoimport -hlocalhost --port=27017 -dnetflix -ctvshows --jsonArray C:\Users\vans_\VISA NUS-ISS VTTP\paf-foundations\day26\tv-shows.json
// mongoimport --uri "mongodb+srv://MongoRidhwan2:pa55w0rd1luvM0ng0@cluster0.xcra51i.mongodb.net/?retryWrites=true&w=majority" --db=netflix -ctvshows  --jsonArray "C:\Users\vans_\VISA NUS-ISS VTTP\paf-foundations\day26\tv-shows.json"
/*
PS C:\Users\vans_\sdf-workshop1> 
git add . (add ALL content of cart to github)
git commit -m "While Loop"                  (add comment while committing)
git push origin main                        (push to main branch)
*/

@Controller
public class day28RestController {
    
    @Autowired
    day28Repo repo;

    @GetMapping(path="game/{game_id}/reviews", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getReviewsById(@PathVariable("game_id") int game_id) {

        List<Document> results = repo.getBoardGameReviews(game_id);

        Date currDateAndTime = new Date();

        List<Document> reviews = results.get(0).getList("reviews", Document.class);

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        JsonObjectBuilder builder = Json.createObjectBuilder();

        for (Document item : reviews) {
            JsonObject tempBuilder = builder
            .add("id", item.getObjectId("_id").toString())
            .add("name", item.getString("name"))
            .add("user", item.getString("user"))
            .add("rating", item.getInteger("rating"))
            .add("comment", item.getString("comment"))
            .add("posted", item.getDate("posted").toString())
            .build();

            arrBuilder.add(tempBuilder);
        }

        JsonArray j = arrBuilder.build();

        JsonObject out = Json.createObjectBuilder()
            .add("game_id", results.get(0).getInteger("gid"))
            .add("name", results.get(0).getString("name"))
            .add("year", results.get(0).getInteger("year"))
            .add("rank", results.get(0).getInteger("ranking"))
            .add("average", results.get(0).getInteger("gid"))
            .add("users_rated", results.get(0).getInteger("users_rated"))
            .add("url", results.get(0).getString("url"))
            .add("thumbnail", results.get(0).getString("image"))
            .add("reviews", j)
            .add("timestamp", currDateAndTime.toString())
            .build();

        return ResponseEntity.ok().body(out.toString());
    }
}
