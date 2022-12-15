package workshop.day26workshop.Controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import workshop.day26workshop.Repositories.day26Repo;

@RestController
public class day26RestController {
    
    @Autowired 
    day26Repo repo;

    // db.game.find();
    @GetMapping(path="games", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGames(
        // Pass value through Query Params for @RequestParam
        @RequestParam("limit") Optional<Integer> limit,
        @RequestParam("offset") Optional<Integer> offset,
        // Pass value through x-www-form-urlencoded if for @RequestBody
        @RequestBody Optional<MultiValueMap<String, String>> form
        // Can either use @RequestParam of @RequestBody
        ) {
            if(!limit.isPresent())
                limit = limit.of(25);
            
            if(!offset.isPresent())
                offset = offset.of(0);
          
            Date date = new Date();
            Timestamp ts = new Timestamp(date.getTime());
            
            List<Document> games = repo.getGames(limit.get(), offset.get());

            JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

            JsonObjectBuilder builder = Json.createObjectBuilder();
            for (Document game : games) {
                JsonObject builder2 = builder.add("game_id", game.getInteger("gid"))
                        .add("name", game.getString("name")).build();
                arrBuilder.add(builder2);
            }

            JsonArray j = arrBuilder.build();
            
            JsonObject out= Json.createObjectBuilder()
            .add("games", j)
            .add("offset", offset.get())
            .add("limit", limit.get())
            .add("total", games.size())
            .add("timestamp", ts.toString())
            .build();
            
            return ResponseEntity.ok().body(out.toString());
    }

    // db.game.find().sort({ranking: 1});
    @GetMapping(path="games/rank", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGamesByRank(
    // Pass value through Query Params for @RequestParam
    @RequestParam("limit") Optional<Integer> limit,
    @RequestParam("offset") Optional<Integer> offset,
    // Pass value through x-www-form-urlencoded if for @RequestBody
    @RequestBody Optional<MultiValueMap<String, String>> form
    // Can either use @RequestParam of @RequestBody
    ) {
        if(!limit.isPresent())
                limit = limit.of(25);
            
        if(!offset.isPresent())
                offset = offset.of(0);
          
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
            
        List<Document> games = repo.getGamesByRanking(limit.get(), offset.get());

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        JsonObjectBuilder builder = Json.createObjectBuilder();
        for (Document game : games) {
            JsonObject builder2 = builder.add("game_id", game.getInteger("gid"))
                    .add("name", game.getString("name"))
                    .add("ranking", game.getInteger("ranking"))
                    .build();
            arrBuilder.add(builder2);
        }

        JsonArray j = arrBuilder.build();
        
        JsonObject out= Json.createObjectBuilder()
        .add("games", j)
        .add("offset", offset.get())
        .add("limit", limit.get())
        .add("total", games.size())
        .add("timestamp", ts.toString())
        .build();

        return ResponseEntity.ok().body(out.toString());
    }

    // db.game.find({gid: 1});
    @GetMapping(path="game/{gameId}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getGameDetails(@PathVariable("gameId") Integer gameId){
        
        List<Document> results = repo.getGamesById(gameId);

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        JsonObjectBuilder builder = Json.createObjectBuilder();
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());

        for (Document item : results) {
            JsonObject builder2 = builder.add("game_id", item.getInteger("gid"))
            .add("name", item.getString("name"))
            .add("year", item.getInteger("year"))
            .add("ranking", item.getInteger("ranking"))
            // .add("average", item.getDouble("average"))
            .add("users_rated", item.getInteger("users_rated"))
            .add("url", item.getString("url"))
            .add("image", item.getString("image"))
            .add("timestamp", ts.toString()).build();

            arrBuilder.add(builder2);
        }
        JsonArray out = arrBuilder.build();
        return ResponseEntity.ok().body(out.toString());
    }

}