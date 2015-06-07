package laxstats;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import laxstats.web.security.TokenAuthenticationService;

public class StatelessAuthenticationFilter extends GenericFilterBean {

   private final TokenAuthenticationService authenticationService;

   public StatelessAuthenticationFilter(TokenAuthenticationService authenticationService) {
      this.authenticationService = authenticationService;
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException
   {
      final HttpServletRequest httpRequest = (HttpServletRequest)request;
      SecurityContextHolder.getContext()
         .setAuthentication(authenticationService.getAuthentication(httpRequest));
      chain.doFilter(request, response);
   }

}
