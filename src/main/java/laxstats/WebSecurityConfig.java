package laxstats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService loginService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(loginService).passwordEncoder(
				new BCryptPasswordEncoder());

		// For testing
		// auth.inMemoryAuthentication().withUser("admin@example.com")
		// .password("admin").roles("ADMIN", "USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/", "/home", "/events", "/teams", "/people")
				.permitAll().antMatchers("/fonts/**").permitAll()
				.antMatchers("/admin/**").hasAnyRole("ADMIN").anyRequest()
				.authenticated();
		http.formLogin().failureUrl("/login?error")
				.defaultSuccessUrl("/home/office").loginPage("/login")
				.permitAll();
		http.logout().permitAll();
	}
}
