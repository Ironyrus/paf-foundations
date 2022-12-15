package workshop.day28marvelworkshop.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.springframework.http.ResponseEntity;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

public class marvelModel {
    
    private int id;
    private String name;
    private String description;
    private String modified;
    private String thumbnail;

    public marvelModel() {
    }

    public marvelModel(int id, String name, String description, String modified, String thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.modified = modified;
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
        .add("id", id)
        .add("name", name)
        .add("description", description)
        .add("modified", modified)
        .add("thumbnail", thumbnail)
        .add("id", id)
        .build();
    }

    public static marvelModel fromCache(JsonObject v) {

        final marvelModel sh = new marvelModel();
        sh.setId(v.getInt("id"));
        sh.setName(v.getString("name"));
        sh.setDescription(v.getString("description"));
        sh.setThumbnail(v.getString("thumbnail"));
        sh.setModified(v.getString("modified"));
        return sh;
    }

    public ArrayList<marvelModel> create(JsonObject data){

        // Drill down to data JSON, then drill down to results JSON Array
        JsonArray heroArr= data.getJsonObject("data").getJsonArray("results");
        
        ArrayList<marvelModel> heroes = new ArrayList<>();
        for (int i = 0; i < heroArr.size(); i++) {
            //currently at results jsonobjects, drill down to thumbnail
            JsonObject image = heroArr.getJsonObject(i).getJsonObject("thumbnail");
            marvelModel hero = new marvelModel(
                heroArr.getJsonObject(i).getInt("id"),
                heroArr.getJsonObject(i).getString("name"),
                heroArr.getJsonObject(i).getString("description"),
                heroArr.getJsonObject(i).getString("modified"),
                image.getString("path") + "." + image.getString("extension")
            );

            heroes.add(hero);

        }

        return heroes;
    }

}