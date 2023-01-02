package mongodb.day28workshop.Repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class day28Repo {
    
    @Autowired
    MongoTemplate mongoTemplate;

    public List<Document> getBoardGameReviews(int game_id){

        // Aggregation and relationship between collections: day28 slide 37-38
        // Match where game_id is the one we want
        // NOTE: game_id type has to match the type in mongodb. For instance, here needs to be int. If String, does NOT work.
        MatchOperation matchTitle = Aggregation.match(
            Criteria.where("gid").is(game_id));

        // Foreign field: reviews (collection), ID (column name)    Local field: game, gid
        // reviews: New column to be embedded, data retrieved from reviews collection
        LookupOperation lookupReviews = Aggregation.lookup(
        "reviews",
        "gid",
        "ID",
        "reviews");


        // Arguments from two variables above.
        Aggregation pipeline = Aggregation.newAggregation(
            matchTitle, lookupReviews);


        // Parent/Local collection
        AggregationResults<Document> results = mongoTemplate.aggregate(
            pipeline, "game", Document.class);

        List<Document> results2 = results.getMappedResults(); 

        return results2;
    }
    

}
