package laxstats.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ScoreboardController {

   @RequestMapping(value = "/scoreboard", method = RequestMethod.GET)
   public String index() {
      return "scoreboard/index";
   }
}
