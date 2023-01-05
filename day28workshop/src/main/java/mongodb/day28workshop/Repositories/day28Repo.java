package mongodb.day28workshop.Repositories;

import java.util.List;

import org.attoparser.dom.DocType;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
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
    

    /*
use bggnew;
db.game.aggregate([

    {
        $lookup: {
        from: 'reviews',
        foreignField: 'ID',
        localField: 'gid',
        as: 'reviews'
        }
    },
    {
        $unwind: "$reviews"
    }
    ,
    {
        $group: {
            _id: "$gid",
            ratings: {
                $push: "$reviews.rating"
            },
            max: {$max: "$reviews.rating"},
            name: {$first: "$name"},
            user: {$first: "$reviews.user"},
            comment: {$first: "$reviews.comment"}
        }
        
    }
]);
     */

    //https://stackoverflow.com/questions/16662405/mongo-group-query-how-to-keep-fields
    public List<Document> listGamesByMaxOrMin(String minOrMax){

        //Currently not used, since we want to return ALL the games
        MatchOperation matchTitle = Aggregation.match(Criteria.where("name").is("Die Macher"));

        // To create the relationship between two collections
        LookupOperation lookupComments = Aggregation.lookup(
        "reviews",
        "gid",
        "ID",
        "reviews");

        //Need to unwind so that we are able to use the "max" or "min" operation
        AggregationOperation unwindGenres = Aggregation.unwind("reviews");

        SortOperation sortedArr = null;
        GroupOperation groupByGameId = null;

        if(minOrMax.equals("high")){
            groupByGameId = Aggregation
            .group("name").max("reviews.rating").as("Highest/Lowest Rating")
            .first("gid").as("game id")
            .first("reviews.user").as("user")
            .first("reviews.comment").as("comment")
            .first("reviews._id").as("review id");
            // Sort by Highest First, so that the highest rating information is chosen.
            sortedArr = Aggregation.sort(Sort.by(Direction.DESC, "reviews.rating"));
        } else if (minOrMax.equals("low")) {
            groupByGameId = Aggregation
            .group("name").min("reviews.rating").as("Highest/Lowest Rating")
            .first("gid").as("game id")
            .first("reviews.user").as("user")
            .first("reviews.comment").as("comment")
            .first("reviews._id").as("review id");
            // Sort by Lowest First, so that the lowest rating information is chosen.
            sortedArr = Aggregation.sort(Sort.by(Direction.ASC, "reviews.rating"));
        }
        
        // Consolidate all the operations
        Aggregation pipeline = Aggregation.newAggregation(lookupComments, unwindGenres, sortedArr, groupByGameId);

        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, "game", Document.class);

        // To convert results into a List of Document(s)
        List<Document> results2 = results.getMappedResults();

        for (Document document : results2) {
            System.out.println(document.toString());
        }

        return results2;
    }
}