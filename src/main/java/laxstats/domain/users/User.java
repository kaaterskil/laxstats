package laxstats.domain.users;

import laxstats.api.users.UserCreatedEvent;
import laxstats.api.users.UserDTO;
import laxstats.api.users.UserId;
import laxstats.api.users.UserRole;
import laxstats.api.users.UserUpdatedEvent;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

public class User extends AbstractAnnotatedAggregateRoot<UserId> {
	private static final long serialVersionUID = -2440058181713894132L;

	@AggregateIdentifier
	private UserId userId;
	private String teamId;
	private String email;
	private String encodedPassword;
	private boolean enabled;
	private String firstName;
	private String lastName;
	private String ipAddress;
	private UserRole role;

	public User(UserId userId, UserDTO userDTO) {
		apply(new UserCreatedEvent(userId, userDTO));
	}

	protected User() {
	}

	/*---------- Methods ----------*/

	public void update(UserId userId, UserDTO userDTO) {
		apply(new UserUpdatedEvent(userId, userDTO));
	}

	/*---------- Event handlers ----------*/

	@EventSourcingHandler
	protected void handle(UserCreatedEvent event) {
		final UserDTO dto = event.getUserDTO();
		this.userId = event.getUserId();

		if (dto.getTeam() != null) {
			teamId = dto.getTeam().getId().toString();
		}
		email = dto.getEmail();
		encodedPassword = dto.getEncodedPassword();
		enabled = dto.isEnabled();
		firstName = dto.getFirstName();
		lastName = dto.getLastName();
		ipAddress = dto.getIpAddress();
		role = dto.getRole();
	}

	@EventSourcingHandler
	protected void handle(UserUpdatedEvent event) {
		final UserDTO dto = event.getUserDTO();
		if (dto.getTeam() != null) {
			teamId = dto.getTeam().getId().toString();
		}
		email = dto.getEmail();
		encodedPassword = dto.getEncodedPassword();
		enabled = dto.isEnabled();
		firstName = dto.getFirstName();
		lastName = dto.getLastName();
		ipAddress = dto.getIpAddress();
		role = dto.getRole();
	}

	/*---------- Getters ----------*/

	@Override
	public UserId getIdentifier() {
		return userId;
	}

	public String getTeamId() {
		return teamId;
	}

	public String getEmail() {
		return email;
	}

	public String getEncodedPassword() {
		return encodedPassword;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public UserRole getRole() {
		return role;
	}
}
