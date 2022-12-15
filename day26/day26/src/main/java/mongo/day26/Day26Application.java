package mongo.day26;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import mongo.day26.Repositories.tvShowRepository;

@SpringBootApplication
public class Day26Application implements CommandLineRunner {

	@Autowired
	private tvShowRepository repo;
	
	
	@Override
	public void run(String... args) {
		List<Document> result = repo.findTVShowByRating(6.5f, 1990);
		for (Document d : result) {
			Document ratingDoc = d.get("rating", Document.class);
			System.out.printf("Name: %s\nSummary: %s\nRating: %s\n ", d.getString("name"), d.getString("summary"), ratingDoc.get("average"));
			// System.out.println(d.toJson());
		}
		// System.exit(0);
	}
	
	public static void main(String[] args) {

		SpringApplication.run(Day26Application.class, args);
	}

}
