package laxstats.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class CustomSecurityMockMvcRequestPostProcessors {

   public static RequestPostProcessor superadmin(String username, String password) {
      return user(username).password(password).roles("SUPERADMIN");
   }
}
