package laxstats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebMvcSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class HttpSecurityConfiguration extends WebSecurityConfigurerAdapter {

   @Autowired
   private UserDetailsService loginService;

   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(loginService).passwordEncoder(new BCryptPasswordEncoder());
      auth.inMemoryAuthentication().withUser("sa").password("admin").roles("SUPERADMIN");
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.httpBasic().and().authorizeRequests().antMatchers("/", "/authenticate").permitAll()
         .anyRequest().authenticated().and().csrf().csrfTokenRepository(csrfTokenRepository()).and()
      .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
   }

   private CsrfTokenRepository csrfTokenRepository() {
      final HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
      repository.setHeaderName("X-XSRF-TOKEN");
      return repository;
   }
}
