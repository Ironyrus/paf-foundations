package workshop.day28marvelworkshop.Repositories;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;


import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonObject;
import workshop.day28marvelworkshop.Models.marvelModel;
import workshop.day28marvelworkshop.Services.AppConfig;

@Repository
public class marvelRepository {
    //MarvelCache

    @Autowired
    private RedisTemplate<String, String> redisTemplate;   

    public void cache(String key, List<marvelModel> values) {

        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

        //We have a toJson method in marvelModel
        values.stream().forEach(c -> {
            arrBuilder.add(c.toJson());
        });
        JsonArray j = arrBuilder.build();
        ops.set(key, j.toString());
    }

    public Optional<List<marvelModel>> get(String name) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        String value = ops.get(name);
        
        // If Redis does not have the data, get from another source. 
        if(null==value)
                return Optional.empty();
        
        // Convert from String to Json
        JsonReader reader = Json.createReader(new StringReader(value));
        JsonArray results = reader.readArray();
        List<marvelModel> heroes = results.stream()
        .map(v -> (JsonObject)v)
        .map(v -> marvelModel.fromCache(v))
        .toList();

        // Else if Redis does have the data, return the redis data.
        return Optional.of(heroes);

    }

}