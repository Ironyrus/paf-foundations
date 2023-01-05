package workshop.day27workshop.Repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.print.Doc;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

@Repository
public class day27Repo {
    
    @Autowired
    MongoTemplate mongoTemplate;

    public Document addReview(String name, Integer rating, String comment, int game_id) {
        String gameName = "Game Name Not Found";

        Criteria c = Criteria.where("name").exists(true);
        Query q = Query.query(c);

        List<Document> results = mongoTemplate
        .find(new Query(), Document.class, "game");

        for (Document item : results) {

            if(item.getInteger("gid") != game_id){
                // System.out.println("Game ID Not Found!");
            } else {
                gameName = item.getString("name");
            }

            if (game_id == item.getInteger("gid")){
                System.out.println(game_id + " vs: " + item.getInteger("gid"));           
                System.out.println("***");
                System.out.println("Found!!!");
                System.out.println("***");
                break;
            }
        }
        
        Document insert = new Document();
        Document doc = new Document();
        insert.put("user", name);
        insert.put("rating", rating);
        insert.put("comment", comment);
        insert.put("ID", game_id);
        insert.put("posted", new Date());
        insert.put("name", gameName); 
        if(!gameName.equals("Game Name Not Found"))
            doc = mongoTemplate.insert(insert, "reviews");  
        else
            doc = insert;
        return doc;
    }

    public UpdateResult updateReview(String id, int rating, String comment) {        

        Query q = Query.query(Criteria.where("_id").is(id));
        // Make a new column "edited" if there isn't one (line 62) and add new data
        Document insert = new Document();
        insert.put("comment", comment);
        insert.put("rating", rating);
        insert.put("posted", new Date());
        // set = update, push = add to column
        Update updateOps = new Update().set("rating", rating).set("comment", comment).push("edited", insert);
        UpdateResult updateResult = mongoTemplate.updateFirst(q, updateOps, Document.class, "reviews");

        return updateResult;
    }

    public Document getLatestComment(String id) {
        Criteria c = Criteria.where("_id").is(id);
        Query q = Query.query(c);
        Boolean edited = true;

        List<Document> results = mongoTemplate.find(q, Document.class, "reviews");

        List<Document> editedComments = (List<Document>)results.get(0).get("edited");
        if(editedComments == null){
            editedComments = results;
            edited = false;
        }

        Date temp = editedComments.get(0).getDate("posted");
        for (Document item : editedComments) {
            // Date latest = new Date();
            if(item.getDate("posted").compareTo(temp) > 0){
                System.out.println("Comparing...");
                System.out.println(item.getDate("posted"));
                System.out.println(temp);
                System.out.println("Comparing ends...");
                temp = item.getDate("posted");
            }
            System.out.println("Latest date should be:");
            System.out.println(temp);
        }

        Document latestComment = new Document();
        for (Document comment : editedComments) {
            if(comment.getDate("posted").compareTo(temp) == 0) {
                latestComment = comment;
            }
        }

        if (latestComment.isEmpty()) {
            edited = false;
        }

        Document result = new Document();
        result.put("user", results.get(0).getString("user"));
        result.put("rating", latestComment.getInteger("rating"));
        result.put("comment", latestComment.getString("comment"));
        result.put("ID", results.get(0).getInteger("ID"));
        result.put("posted", latestComment.getDate("posted"));
        result.put("name", results.get(0).getString("name"));
        result.put("edited", edited);
        result.put("timestamp", new Date());

        return result;
    }

    public Document getHistory(String id) {
        
        Criteria c = Criteria.where("_id").is(id);
        Query q = Query.query(c);
        Boolean edited = true;

        List<Document> results = mongoTemplate.find(q, Document.class, "reviews");
        List<Document> editedComments = (List<Document>)results.get(0).get("edited");
        if(editedComments == null){
            editedComments = results;
            edited = false;
        }

        Date temp = editedComments.get(0).getDate("posted");
        for (Document item : editedComments) {
            // Date latest = new Date();
            if(item.getDate("posted").compareTo(temp) > 0){
                System.out.println("Comparing...");
                System.out.println(item.getDate("posted"));
                System.out.println(temp);
                System.out.println("Comparing ends...");
                temp = item.getDate("posted");
            }
            System.out.println("Latest date should be:");
            System.out.println(temp);
        }

        Document latestComment = new Document();
        for (Document comment : editedComments) {
            if(comment.getDate("posted").compareTo(temp) == 0) {
                latestComment = comment;
            }
        }

        if (latestComment.isEmpty()) {
            edited = false;
        }

        Document result = new Document();
        result.put("user", results.get(0).getString("user"));
        result.put("rating", latestComment.getInteger("rating"));
        result.put("comment", latestComment.getString("comment"));
        result.put("ID", results.get(0).getInteger("ID"));
        result.put("posted", latestComment.getDate("posted"));
        result.put("name", results.get(0).getString("name"));

        if(results.get(0).get("edited") == null)
            result.put("edited", "Empty!");
        else
            result.put("edited", results.get(0).get("edited"));
        result.put("timestamp", new Date());    
        
        return result;
    }

}