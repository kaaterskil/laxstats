package laxstats.web.leagues;

import java.util.Map;

import org.joda.time.LocalDate;

public class TeamAffiliationForm {
	private String teamId;
	private LocalDate startsOn;
	private LocalDate endsOn;
	private Map<String, String> teams;

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
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

	public Map<String, String> getTeams() {
		return teams;
	}

	public void setTeams(Map<String, String> teams) {
		this.teams = teams;
	}

}
