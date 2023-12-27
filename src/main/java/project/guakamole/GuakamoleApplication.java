package project.guakamole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class GuakamoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuakamoleApplication.class, args);
	}
}
