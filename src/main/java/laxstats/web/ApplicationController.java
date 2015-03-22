package laxstats.web;

import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;

import org.axonframework.commandhandling.CommandBus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

abstract public class ApplicationController {
   protected final UserQueryRepository userRepository;
   protected final CommandBus commandBus;
   private UserEntry currentUser;

   protected ApplicationController(UserQueryRepository userRepository, CommandBus commandBus) {
      this.userRepository = userRepository;
      this.commandBus = commandBus;
   }

   protected UserEntry getCurrentUser() {
      if (currentUser == null) {
         final SecurityContext ctx = SecurityContextHolder.getContext();
         final String email = (String)ctx.getAuthentication().getPrincipal();
         currentUser = userRepository.findByEmail(email);
      }
      return currentUser;
   }
}
