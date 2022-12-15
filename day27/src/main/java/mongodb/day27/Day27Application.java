package mongodb.day27;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaExtensionsKt;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.client.result.DeleteResult;

import jakarta.json.Json;
import jakarta.json.JsonObject;

// To import JSON into mongo
// mongoimport -hlocalhost --port=27017 -dnetflix -ctvshows --jsonArray C:\Users\vans_\VISA NUS-ISS VTTP\paf-foundations\day26\tv-shows.json
// mongoimport --uri "mongodb+srv://MongoRidhwan2:pa55w0rd1luvM0ng0@cluster0.xcra51i.mongodb.net/?retryWrites=true&w=majority" --db=netflix -ctvshows  --jsonArray "C:\Users\vans_\VISA NUS-ISS VTTP\paf-foundations\day26\tv-shows.json"
@SpringBootApplication
public class Day27Application implements CommandLineRunner{

	@Autowired
	private MongoTemplate template;

	@Override
	public void run(String... args) {

		// insertNewDoc();
		// insertFromJsonObject();
		// System.exit(0);
	}

	public void insertNewDoc() {
		//Create a document to be inserted
		Document doc = new Document();
		doc.put("title", "Third blog");
		doc.put("date", new Date());
		doc.put("summary", "The meaning of life");

		Document comment = new Document();
		comment.put("user", "fred");
		comment.put("comment", "i love your blog");

		//Create a list to hold the comments
		List<Document> comments = new LinkedList<>();
		comments.add(comment);

		doc.put("comments", comments);
		doc.put("tags", List.of("one", "two", "three"));

		// Insert into the connection name
		Document inserted = template.insert(doc, "blogs");
		System.out.println(inserted.toJson());
	}

	public void insertFromJsonObject() {
		JsonObject json = Json.createObjectBuilder()
			.add("title", "Concerning Json-P")
			.add("date", (new Date().toString()))
			.add("summary", "Convert Json-P to String 333")
			.build();
		
		Document blog = Document.parse(json.toString());
		Document inserted = template.insert(blog, "posts");
		
		System.out.println(inserted);
	}

	public void deleteJson() {
		//create a criteria
		Criteria c = Criteria.where("title").regex(".*json.*", "i");
		DeleteResult result = template.remove(c, "posts");
		System.out.println(result);
		
	}

	public static void main(String[] args) {
		SpringApplication.run(Day27Application.class, args);
	}

}