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
public class HttpSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService loginService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(loginService).passwordEncoder(
				new BCryptPasswordEncoder());
		auth.inMemoryAuthentication().withUser("sa").password("admin")
				.roles("SUPERADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.authorizeRequests()
		// .antMatchers("/", "/home", "/events", "/teams", "/players",
		// "/sitemap", "/terms", "/privacy", "/subscribe")
		// .permitAll()
		// .antMatchers("/resources/**", "/fonts/**", "/images/**")
		// .permitAll().antMatchers("/admin/**", "/api/**")
		// .hasAnyRole("MANAGER", "COACH", "ADMIN", "SUPERADMIN")
		// .antMatchers("/super/**").hasAnyRole("ADMIN", "SUPERADMIN")
		// .anyRequest().authenticated();
		//
		// http.formLogin().defaultSuccessUrl("/admin/office")
		// .failureUrl("/login?error").loginPage("/login").permitAll();
		//
		// http.logout().permitAll();
	}
}
