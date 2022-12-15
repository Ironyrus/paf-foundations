package workshop.day22.Controllers;

import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import workshop.day22.Models.rsvp;
import workshop.day22.Repositories.day22Repo;

@RestController
@RequestMapping(path="/api", produces="application/json")
public class day22RestController {
    
    @Autowired
    day22Repo repo;

    @GetMapping(path="/rsvps")
    public ResponseEntity<String> getAllRsvps() {
        List<rsvp> rsvps = repo.getAllRsvps();
        JsonArrayBuilder builderArr = Json.createArrayBuilder();
        JsonObjectBuilder builder = Json.createObjectBuilder();
        int count = 0;
        for (rsvp rsvp : rsvps) {
            System.out.println(rsvp.toString());
            builder.add("name", rsvp.getName())
            .add("email", rsvp.getEmail())
            .add("phone", rsvp.getPhone())
            .add("confirmation_date", rsvp.getConfirmation_date().toString())
            .add("comments", rsvp.getComments());
            JsonObject addToArray = builder.build();
            builderArr.add(count, addToArray);
            count++;
        }
        
        JsonArray out = builderArr.build();
        
        return ResponseEntity.ok(out.toString());
    }

    @GetMapping(path="/rsvp")
    public ResponseEntity<String> getRsvpByName(
            @RequestParam("q") String name) {
        rsvp rsvp = repo.getRsvpByName(name);
        JsonObjectBuilder builder = Json.createObjectBuilder();

        try {
            builder
            .add("name", rsvp.getName())
            .add("email", rsvp.getEmail())
            .add("phone", rsvp.getPhone())
            .add("confirmation_date", rsvp.getConfirmation_date().toString())
            .add("comments", rsvp.getComments());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 Error: " + e.toString());
        }
        
        JsonObject out = builder.build();
        return ResponseEntity.ok(out.toString());
    }

    @PostMapping(path = "rsvp")
    public ResponseEntity<String> addNewRsvp(
        @RequestBody MultiValueMap<String, String> form) {
        
        rsvp checkRsvp = repo.getRsvpByName(form.getFirst("name"));
        // If rsvp exists, update.
        // Else, add new one
        if(checkRsvp.getName() != null){
            int result = repo.updateExistingRsvp(
                form.getFirst("name"),
                form.getFirst("phone"),
                form.getFirst("confirmation_date"),
                form.getFirst("comments"),
                form.getFirst("email"));
            return ResponseEntity.status(HttpStatus.CREATED).body("Updated!");
        } else  {
            repo.addNewRsvp(
                form.getFirst("name"),
                form.getFirst("email"),
                form.getFirst("phone"),
                form.getFirst("confirmation_date"),
                form.getFirst("comments"));
            return ResponseEntity.status(HttpStatus.CREATED).body("Added!");

        } 
    }

    // Note: request must be in raw, json format eg.
    // {
    //     "name":"Fredders",
    //     "email":"fred@Fred",
    //     "phone":"55555",
    //     "confirmation_date":"2021-13-22",
    //     "comments":"nana"
    // }
    @PutMapping(path="/rsvp/{email}")
    public ResponseEntity<String> updateRsvp(
        @PathVariable("email") String email,
        @RequestBody Map form) {
            try {
                int result = repo.updateExistingRsvp(
                    form.get("name").toString(),
                    form.get("phone").toString(),
                    form.get("confirmation_date").toString(),
                    form.get("comments").toString(),
                    form.get("email").toString());
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Created successfully");
    }

    @GetMapping(path = "rsvps/count")
    public ResponseEntity<String> getCount() {
        int count = repo.getCount();
        return ResponseEntity.status(HttpStatus.CREATED).body("" + count);
    }
}
