package mongodb.day27.Repositories;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import mongodb.day27.Models.day27Model;

@Repository
public class day27Repo {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<day27Model> findCommentsByTextAndRating(String text, String rating) {
        TextCriteria c = TextCriteria.forDefaultLanguage().matchingPhrase(text);
        TextQuery query = TextQuery.queryText(c).sortByScore();
        query.setScoreFieldName("textScore");
        List<Document> docs = mongoTemplate.find(query, Document.class, "comments2");
        List<day27Model> results = new ArrayList<>();
        Double ratings = Double.parseDouble(rating);
        for (Document eachDoc : docs) {
            if(eachDoc.getDouble("textScore") > ratings){
                results.add(new day27Model(
                            eachDoc.getString("id"), 
                            eachDoc.getString("c_id"), 
                            eachDoc.getString("user"), 
                            eachDoc.getInteger("rating"), 
                            eachDoc.getString("c_text"),
                            eachDoc.getString("g_id"),
                            eachDoc.getDouble("textScore")
                            ));
            };
        }

        return results;
    }

}
