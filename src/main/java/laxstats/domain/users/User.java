package laxstats.domain.users;

import laxstats.api.users.UserCreatedEvent;
import laxstats.api.users.UserId;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class User extends AbstractAnnotatedAggregateRoot<UserId> {
	private static final long serialVersionUID = -2440058181713894132L;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@AggregateIdentifier
	private UserId userId;
	
	@SuppressWarnings("unused")
	private String encryptedPassword;

	protected User() {
	}

	public User(UserId userId, String email, String password, String firstName,
			String lastName, String role, String teamId, String ipAddress) {
		apply(new UserCreatedEvent(userId, email, encode(password), firstName,
				lastName, role, teamId, ipAddress));
	}

	@Override
	public UserId getIdentifier() {
		return userId;
	}
	
	@EventHandler
	public void onUserCreated(UserCreatedEvent event) {
		this.userId = event.getUserId();
		this.encryptedPassword = event.getPassword();
	}

	private String encode(String rawPassword) {
		CharSequence cs = rawPassword;
		return passwordEncoder.encode(cs);
	}
}
