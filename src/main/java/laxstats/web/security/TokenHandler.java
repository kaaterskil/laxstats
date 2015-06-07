package laxstats.web.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenHandler {
   private final String secret;
   private final UserDetailsService loginService;

   public TokenHandler(String secret, UserDetailsService loginService) {
      this.secret = secret;
      this.loginService = loginService;
   }

   public UserDetails parseUserFromToken(String token) {
      final String username =
         Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
      return loginService.loadUserByUsername(username);
   }

   public String createTokenForUser(UserDetails user) {
      return Jwts.builder().setSubject(user.getUsername()).signWith(SignatureAlgorithm.HS512, secret)
         .compact();
   }
}
