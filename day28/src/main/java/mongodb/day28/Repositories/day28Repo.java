package mongodb.day28.Repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class day28Repo {
    
    @Autowired 
    MongoTemplate mongoTemplate; 

    public List<Document> findGameByName(String name) {
        
        name = name.toLowerCase().substring(0, 1).toUpperCase() + name.toLowerCase().substring(1);
        System.out.println(name);
        Criteria c = Criteria.where("name").is(name);
        Query q = Query.query(c);

        List<Document> result = mongoTemplate.find(q, Document.class, "comments");

        return result;
    }

    public List<Document> getAllGames(){
        Criteria c = Criteria.where("name").regex("");

        Query q = Query.query(c).limit(100);

        List<Document> result = mongoTemplate.find(q, Document.class, "comments");

        return result;
    }
}
