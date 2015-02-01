package laxstats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Use @SpringBootApplication: same as using the combined
// @Configuration, @EnableAutoConfiguration and @ComponentScan annotations
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
