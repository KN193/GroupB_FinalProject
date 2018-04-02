package uow.finalproject.webapp;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select username,password,verified from User where username=?")
														.authoritiesByUsernameQuery("select username, usertype from User where username=? ");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/","/recommendation.html","/index.html","/index_login.html", "/register.html",
											"/register_firstStep" ,"/member_person", "/registerNewUser"
							).permitAll()
							.antMatchers("/*").hasAnyRole("user", "admin").anyRequest().authenticated().and().formLogin().loginPage("/index_login").permitAll()
							.defaultSuccessUrl("/member_index").and().logout()
							.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
							.logoutSuccessUrl("/");
//		http.exceptionHandling().accessDeniedPage("/403");
	}

}
