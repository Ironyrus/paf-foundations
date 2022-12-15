package workshop.day27workshop.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.annotation.JsonValue;
import com.mongodb.client.result.UpdateResult;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import workshop.day27workshop.Repositories.day27Repo;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

@RestController
// Apparently need @EnableWebMvc so that PutMapping and @RequestBody can work
// https://stackoverflow.com/questions/41872261/put-request-required-request-body-is-missing
@EnableWebMvc
public class day27Controller {
    
    @Autowired
    day27Repo repo;

    @PostMapping(path="review", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postReview(
        @RequestBody MultiValueMap<String, String> form
    ) {

        Document doc = new Document();
        try {
            doc = repo.addReview(form.getFirst("name"), Integer.parseInt(form.getFirst("rating")), form.getFirst("comment"), Integer.parseInt(form.getFirst("game_id")));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Error");
        }


        if(doc.getString("name").equals("Game Name Not Found"))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game Name Not Found!");

        System.out.println("Id granted: " + doc.toString());
        return ResponseEntity.ok().body("doc");
    }

    @PutMapping(path="review/{review_id}")
    public ResponseEntity<String> updateReview(
        @PathVariable("review_id") String review_id,
        @RequestBody MultiValueMap<String, String> form
    ) {
        
        UpdateResult result = repo.updateReview(
            review_id,
             Integer.parseInt(form.getFirst("rating")),
              form.getFirst("comment"));

        System.out.println("Documents updated: " + result.getModifiedCount());

        return ResponseEntity.ok().body("Documents updated: " + result.getModifiedCount());
    }

    @GetMapping(path="review/{review_id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getLatestCommentAndRatings(@PathVariable("review_id") String id) {

        Document results = repo.getLatestComment(id);

        JsonObject out= Json.createObjectBuilder()
            .add("user", results.getString("user"))
            .add("rating", results.getInteger("rating"))
            .add("comment", results.getString("comment"))
            .add("ID", results.getInteger("ID"))
            .add("posted", results.getDate("posted").toString())
            .add("name", results.getString("name"))
            .add("edited", results.getBoolean("edited"))
            .add("timestamp", results.getDate("timestamp").toString())
            .build();

        return ResponseEntity.status(HttpStatus.OK).body(out.toString());
    }

    @GetMapping(path="review/{review_id}/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHistory(@PathVariable("review_id") String id) {

        Document results = repo.getHistory(id);

        //Can also add JsonObject j to out.
        JsonObject j = Json.createObjectBuilder().add("test1", "hello").add("test2", "is").build();
        
        JsonObject out= Json.createObjectBuilder()
            .add("user", results.getString("user"))
            .add("rating", results.getInteger("rating"))
            .add("comment", results.getString("comment"))
            .add("ID", results.getInteger("ID"))
            .add("posted", results.getDate("posted").toString())
            .add("name", results.getString("name"))
            .add("edited", results.get("edited") .toString())
            // .add("edited", j)
            .add("timestamp", results.getDate("timestamp").toString())
            .build();

        return ResponseEntity.status(HttpStatus.OK).body(out.toString());

    }
}