package uow.finalproject.webapp.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UtilityService {

	private static BCryptPasswordEncoder passwordEncoder = null;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		if (passwordEncoder == null)
			passwordEncoder = new BCryptPasswordEncoder();
		
		return passwordEncoder;
	}
}
