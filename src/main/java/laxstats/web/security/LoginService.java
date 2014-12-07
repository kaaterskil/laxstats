package laxstats.web.security;

import java.util.Arrays;
import java.util.List;

import laxstats.query.users.UserEntry;
import laxstats.query.users.UserQueryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

	private final UserQueryRepository repository;

	@Autowired
	public LoginService(UserQueryRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		final UserEntry user = repository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		final List<SimpleGrantedAuthority> authorities = Arrays
				.asList(new SimpleGrantedAuthority(user.getRole().toString()));
		return new User(user.getEmail(), user.getEncodedPassword(), authorities);
	}

}
