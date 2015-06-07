package laxstats.web.security;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

   @RequestMapping(value = "/api/user/current", method = RequestMethod.GET)
   public @ResponseBody Map<String, Object> showPrincipal(HttpSession session) {
      final Principal user = SecurityContextHolder.getContext().getAuthentication();
      if (user != null) {
         final Map<String, Object> model = new HashMap<String, Object>();
         model.put("principal", user);
         return model;
      }
      return null;
   }

   @RequestMapping(value = "/admin/home")
   public @ResponseBody Map<String, Object> office() {
      final Map<String, Object> model = new HashMap<String, Object>();
      model.put("id", UUID.randomUUID().toString());
      model.put("message", "This is a test of dynamic data");
      return model;
   }
}
