package sshukla.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"sshukla.learning"})
public class SpringbootDynamoDbExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDynamoDbExampleApplication.class, args);
	}

}
