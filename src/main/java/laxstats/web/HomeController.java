package laxstats.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

   @RequestMapping(value = "/", method = RequestMethod.GET)
   public String index(Model model, HttpServletRequest request) {
      model.addAttribute("title", "Laxstats");
      model.addAttribute("back", request.getHeader("Referer"));
      return "home/ngindex";
   }

   @RequestMapping(value = "/home", method = RequestMethod.GET)
   public String home(Model model, HttpServletRequest request) {
      model.addAttribute("title", "Laxstats");
      model.addAttribute("back", request.getHeader("Referer"));
      return "redirect:home/ngindex";
   }

   @RequestMapping(value = "/admin/office", method = RequestMethod.GET)
   public String office(Model model) {
      model.addAttribute("title", "The Office");
      return "home/office";
   }

   @RequestMapping(value = "/sitemap", method = RequestMethod.GET)
   public String siteMap() {
      return "home/siteMap";
   }

   @RequestMapping(value = "/terms", method = RequestMethod.GET)
   public String termsOfUse() {
      return "home/termsOfUse";
   }

   @RequestMapping(value = "/privacy", method = RequestMethod.GET)
   public String privacyPolicy() {
      return "home/privacyPolicy";
   }

   @RequestMapping(value = "/subscribe", method = RequestMethod.GET)
   public String subscribe() {
      return "home/subscribe";
   }
}
