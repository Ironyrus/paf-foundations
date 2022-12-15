package workshop.day28marvelworkshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.ReactiveRedisConnection.Command;

import workshop.day28marvelworkshop.Services.MarvelService;

@SpringBootApplication
public class Day28marvelworkshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(Day28marvelworkshopApplication.class, args);
	}

}
