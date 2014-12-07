package laxstats.domain.users;

import laxstats.api.users.UserCreatedEvent;
import laxstats.api.users.UserDTO;
import laxstats.api.users.UserId;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

public class User extends AbstractAnnotatedAggregateRoot<UserId> {
	private static final long serialVersionUID = -2440058181713894132L;

	@AggregateIdentifier
	private UserId userId;

	@SuppressWarnings("unused")
	private String encodedPassword;

	protected User() {
	}

	public User(UserId userId, UserDTO userDTO) {
		apply(new UserCreatedEvent(userId, userDTO));
	}

	@Override
	public UserId getIdentifier() {
		return userId;
	}

	@EventHandler
	public void handle(UserCreatedEvent event) {
		this.userId = event.getUserId();
		this.encodedPassword = event.getUserDTO().getEncodedPassword();
	}
}
