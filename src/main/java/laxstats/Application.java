package laxstats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

// Use @SpringBootApplication: same as using the combined
// @Configuration, @EnableAutoConfiguration and @ComponentScan annotations
@SpringBootApplication
@EnableConfigurationProperties
public class Application {

   public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
   }

}
