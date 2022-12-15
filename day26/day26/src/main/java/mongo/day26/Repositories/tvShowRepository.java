package mongo.day26.Repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class tvShowRepository {
 
    public static final String C_TV_SHOWS = "tvshows";
    @Autowired
    private MongoTemplate mongoTemplate;

    // db.tvshows.find( { language: "English"} )

    public List<Document> findTVShowByLanguage(String language) {

        // Create a criteria/predicate (WHERE condition)
        Criteria c = Criteria.where("language").is(language);
        
        // Query to use the criteria
        Query q = Query.query(c);
    
        List<Document> results = mongoTemplate.find(q, Document.class, C_TV_SHOWS);
        
        return results;
    }

    /* db.tvshows.find({
     'rating.average': {$gte: 6},
     year: {$gte: 1990}
    })
    */
    public List<Document> findTVShowByRating(Float f, Integer y) {
      Criteria c = Criteria
      .where("rating.average").gte(f).andOperator(
      Criteria.where("language").is("English")); 
      
      Query q = Query.query(c);

      return mongoTemplate.find(q, Document.class, "tvshows");
    }

    public List<String> getGenres(){
      List<String> results = mongoTemplate.findDistinct(new Query(), "genres", C_TV_SHOWS, String.class);
      return results;
    }

    public List<Document> getAGenre(String genre) {
      // Create a criteria/predicate (WHERE condition)
      Criteria c = Criteria.where("genres").is(genre);
        
      // Query to use the criteria
      Query q = Query.query(c);
      q.fields().include("name", "summary", "image.original", "rating.average").exclude("_id");
      q.skip(0).limit(10);
      List<Document> results = mongoTemplate.find(q, Document.class, C_TV_SHOWS);
      return results;
    }

}