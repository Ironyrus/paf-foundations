package mongo.day26.Controllers;

import java.util.List;

import org.apache.tomcat.util.http.parser.MediaType;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import mongo.day26.Repositories.tvShowRepository;

@RestController
@RequestMapping(path="/api")
public class day26RestController {
    
    @Autowired
    tvShowRepository tvShowRepo;

    @GetMapping(path="/genres")
    public ResponseEntity<String> getGenres() {

        List<String> results = tvShowRepo.getGenres();
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (String item : results) {
            builder.add(item);
        }    

        JsonArray j  = builder.build();

        return ResponseEntity.ok().body(j.toString());
    }

    @GetMapping(path="tvshow/{genre}")
    public ResponseEntity<String> getAGenre(
        @PathVariable("genre") String genre){
        List<Document> results = tvShowRepo.getAGenre(genre);
        
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder arrbuilder = Json.createArrayBuilder();
        for (Document item : results) {
            builder.add("name", item.getString("name"));
            builder.add("summary", item.getString("summary"));

            Document image = item.get("image", Document.class);
            builder.add("image", image.getString("original"));

            Document rating = item.get("rating", Document.class);
            Object r = rating.get("average");
            if(r instanceof Integer)
                builder.add("rating", ((Integer)r).doubleValue());
            else
                builder.add("rating", (Double)r);

            JsonObject toBuild = builder.build();
            System.out.println(toBuild.toString());
            arrbuilder.add(toBuild);
        }    
        JsonArray j  = arrbuilder.build();

        return ResponseEntity.ok().body(j.toString());
    }
}