package workshop.day26workshop.Repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class day26Repo {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Document> getGames(Integer limit, Integer offset) {

        // Database bggnew, collection game

        Criteria c = Criteria.where("name").exists(true);
        Query q = Query.query(c).limit(limit).skip(offset);

        List<Document> results = mongoTemplate
        .find(new Query(), Document.class, "game");

        return results;
    }

    public List<Document> getGamesByRanking(Integer limit, Integer offset) {

        Criteria c = Criteria.where("name").exists(true);
        Query q = Query.query(c).with(Sort.by(Sort.Direction.ASC, "ranking")).limit(limit).skip(offset);

        List<Document> results = mongoTemplate
        .find(q, Document.class, "game");

        return results;
    }

    public List<Document> getGamesById(Integer gameId) {

        Criteria c = Criteria.where("gid").is(gameId);
        Query q = Query.query(c);

        List<Document> results = mongoTemplate
        .find(q, Document.class, "game");

        return results;
    }

}
