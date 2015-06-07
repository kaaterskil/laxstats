package laxstats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import laxstats.web.security.TokenAuthenticationService;

@Configuration
@EnableWebMvcSecurity
@Order(1)
public class HttpSecurityConfiguration extends WebSecurityConfigurerAdapter {

   @Autowired
   private UserDetailsService loginService;

   @Autowired
   private TokenAuthenticationService tokenAuthenticationService;

   public HttpSecurityConfiguration() {
      super(true);
   }

   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(loginService).passwordEncoder(new BCryptPasswordEncoder());
      auth.inMemoryAuthentication().withUser("sa").password("admin").roles("SUPERADMIN");
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.exceptionHandling().and().anonymous().and().servletApi().and().headers().cacheControl()
         .and().authorizeRequests()

      // Allow anonymous resource requests
         .antMatchers("/", "/favicon.ico", "**/*.html", "**/*.css", "**/*.js").permitAll()

      // Allow anonymous logins
         .antMatchers(HttpMethod.POST, "/api/login").permitAll()

      // Allow anonymous GETs to API
         .antMatchers(HttpMethod.GET, "/api/**").permitAll()

      // All other requests need to be authenticated
         .anyRequest().authenticated().and()

      // Custom JSON based authentication by POST of {"username": "<email>", "password":
      // "<password>"} which sets the token upon authentication
         .addFilterBefore(new StatelessLoginFilter("/api/login", tokenAuthenticationService,
            loginService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)

      // Custom token based authentication based on the header previously given to the client
         .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService),
            UsernamePasswordAuthenticationFilter.class);
   }

   @Bean
   @Override
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   }
}
