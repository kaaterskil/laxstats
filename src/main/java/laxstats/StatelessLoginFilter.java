package laxstats;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import laxstats.web.security.Credentials;
import laxstats.web.security.TokenAuthenticationService;
import laxstats.web.security.UserAuthentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

   private final TokenAuthenticationService tokenAuthenticationService;
   private final UserDetailsService userDetailsService;

   public StatelessLoginFilter(String urlMapping,
      TokenAuthenticationService tokenAuthenticationService, UserDetailsService userDetailsService,
      AuthenticationManager authManager) {

      super(new AntPathRequestMatcher(urlMapping));
      this.userDetailsService = userDetailsService;
      this.tokenAuthenticationService = tokenAuthenticationService;
      setAuthenticationManager(authManager);
   }

   @Override
   public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException, IOException, ServletException
   {
      final ObjectMapper mapper = new ObjectMapper();
      final Credentials user = mapper.readValue(request.getInputStream(), Credentials.class);
      final UsernamePasswordAuthenticationToken loginToken =
         new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
      return getAuthenticationManager().authenticate(loginToken);
   }

   @Override
   protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) throws IOException, ServletException
   {
      // Look up the complete User object from the database and create an Authentication for it.
      final UserDetails authenticatedUser =
         userDetailsService.loadUserByUsername(authResult.getName());
      final UserAuthentication userAuthentication = new UserAuthentication(authenticatedUser);

      // Add the custom token as HTTP header to the response.
      tokenAuthenticationService.addAuthentication(response, userAuthentication);

      // Add the authentication to the Security context.
      SecurityContextHolder.getContext().setAuthentication(userAuthentication);
   }
}
