package laxstats.web.security;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

   @RequestMapping(value = "/user")
   public Principal user(Principal user) {
      return user;
   }

   @RequestMapping(value = "/token")
   public Map<String, String> token(HttpSession session) {
      return Collections.singletonMap("token", session.getId());
   }
}
