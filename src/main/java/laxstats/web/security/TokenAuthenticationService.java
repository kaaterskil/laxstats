package laxstats.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class TokenAuthenticationService {
   public static String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

   private final TokenHandler tokenHandler;

   @Autowired
   public TokenAuthenticationService(@Value("${app.admin.token.secret}") String secret,
      UserDetailsService loginService) {
      tokenHandler = new TokenHandler(secret, loginService);
   }

   public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
      final UserDetails user = (UserDetails)authentication.getDetails();
      response.addHeader(AUTH_HEADER_NAME, tokenHandler.createTokenForUser(user));
   }

   public Authentication getAuthentication(HttpServletRequest request) {
      final String token = request.getHeader(AUTH_HEADER_NAME);
      if (token != null) {
         final UserDetails user = tokenHandler.parseUserFromToken(token);
         if (user != null) {
            return new UserAuthentication(user);
         }
      }
      return null;
   }
}
