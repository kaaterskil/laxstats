package laxstats.domain.events;

import laxstats.api.events.AthleteStatus;
import laxstats.api.events.AttendeeDTO;
import laxstats.api.events.AttendeeUpdatedEvent;
import laxstats.api.players.Role;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;

public class Attendee extends AbstractAnnotatedEntity {
	private String id;
	private String playerId;
	private String teamSeasonId;
	private String name;
	private String jerseyNumber;
	private Role role;
	private AthleteStatus status;

	public Attendee(String id, String playerId, String teamSeasonId,
			String name, String jerseyNumber, Role role, AthleteStatus status) {
		super();
		this.id = id;
		this.playerId = playerId;
		this.teamSeasonId = teamSeasonId;
		this.name = name;
		this.jerseyNumber = jerseyNumber;
		this.role = role;
		this.status = status;
	}

	protected Attendee() {
	}

	/*---------- Event handlers ----------*/

	@EventHandler
	protected void handle(AttendeeUpdatedEvent event) {
		if (!this.id.equals(event.getAttendeeDTO().getId())) {
			return;
		}
		final AttendeeDTO dto = event.getAttendeeDTO();
		name = dto.getName();
		jerseyNumber = dto.getJerseyNumber();
		role = dto.getRole();
		status = dto.getStatus();
	}

	/*---------- Getters ----------*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getTeamSeasonId() {
		return teamSeasonId;
	}

	public void setTeamSeasonId(String teamSeasonId) {
		this.teamSeasonId = teamSeasonId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJerseyNumber() {
		return jerseyNumber;
	}

	public void setJerseyNumber(String jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public AthleteStatus getStatus() {
		return status;
	}

	public void setStatus(AthleteStatus status) {
		this.status = status;
	}

}
