package laxstats.web.teamSeasons;

import java.util.Map;

import javax.validation.constraints.NotNull;

import laxstats.api.teamSeasons.TeamStatus;

import org.joda.time.LocalDate;

public class TeamSeasonForm {

	@NotNull
	private String seasonId;

	@NotNull
	private LocalDate startsOn;

	private LocalDate endsOn;
	private TeamStatus status;
	private Map<String, String> teams;
	private Map<String, String> seasons;

	public String getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(String seasonId) {
		this.seasonId = seasonId;
	}

	public LocalDate getStartsOn() {
		return startsOn;
	}

	public void setStartsOn(LocalDate startsOn) {
		this.startsOn = startsOn;
	}

	public LocalDate getEndsOn() {
		return endsOn;
	}

	public void setEndsOn(LocalDate endsOn) {
		this.endsOn = endsOn;
	}

	public TeamStatus getStatus() {
		return status;
	}

	public void setStatus(TeamStatus status) {
		this.status = status;
	}

	public Map<String, String> getTeams() {
		return teams;
	}

	public void setTeams(Map<String, String> teams) {
		this.teams = teams;
	}

	public Map<String, String> getSeasons() {
		return seasons;
	}

	public void setSeasons(Map<String, String> seasons) {
		this.seasons = seasons;
	}
}
