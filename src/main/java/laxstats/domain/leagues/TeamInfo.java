package laxstats.domain.leagues;

import laxstats.query.users.UserEntry;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class TeamInfo {
	private String teamId;
	private String name;
	private LocalDate startingOn;
	private LocalDate endingOn;
	private LocalDateTime createdAt;
	private UserEntry createdBy;
	private LocalDateTime modifiedAt;
	private UserEntry modifiedBy;

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getStartingOn() {
		return startingOn;
	}

	public void setStartingOn(LocalDate startingOn) {
		this.startingOn = startingOn;
	}

	public LocalDate getEndingOn() {
		return endingOn;
	}

	public void setEndingOn(LocalDate endingOn) {
		this.endingOn = endingOn;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public UserEntry getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserEntry createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public UserEntry getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(UserEntry modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof TeamInfo) {
			final TeamInfo that = (TeamInfo) o;
			return this.teamId.equals(that.teamId);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return teamId.hashCode();
	}
}
