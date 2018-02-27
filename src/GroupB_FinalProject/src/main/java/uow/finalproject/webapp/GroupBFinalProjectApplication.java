package uow.finalproject.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import uow.finalproject.webapp.utility.EmailService;

@SpringBootApplication
//@EnableConfigurationProperties(EmailService.class)
public class GroupBFinalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroupBFinalProjectApplication.class, args);
	}
}
