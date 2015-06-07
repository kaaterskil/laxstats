package laxstats.web.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;

@Service
public class LoginService implements UserDetailsService {

   @Autowired
   private UserQueryRepository repository;

   private final AccountStatusUserDetailsChecker detailsChecker =
      new AccountStatusUserDetailsChecker();

   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      final UserEntry user = repository.findByEmail(email);
      if (user == null) {
         throw new UsernameNotFoundException("User not found");
      }

      final Set<SimpleGrantedAuthority> authorities = new HashSet<>();
      authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
      user.setAuthorities(authorities);

      detailsChecker.check(user);

      return user;
   }

}
