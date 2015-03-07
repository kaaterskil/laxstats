package laxstats.web.teamSeasons;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;

import laxstats.api.teamSeasons.TeamStatus;
import laxstats.query.leagues.LeagueEntry;
import laxstats.query.seasons.SeasonEntry;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class TeamSeasonForm {
	private String id;

	@NotNull
	private String team;

	@NotNull
	private String season;

	private String affiliation;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startsOn;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endsOn;

	private String name;

	@NotNull
	private TeamStatus status;

	private String teamTitle;
	private List<SeasonEntry> seasons;
	private List<LeagueEntry> leagues;
	private List<TeamStatus> statuses;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getLeague() {
		return affiliation;
	}

	public void setLeague(String affiliation) {
		this.affiliation = affiliation;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TeamStatus getStatus() {
		return status;
	}

	public void setStatus(TeamStatus status) {
		this.status = status;
	}

	public String getTeamTitle() {
		return teamTitle;
	}

	public void setTeamTitle(String teamTitle) {
		this.teamTitle = teamTitle;
	}

	public List<SeasonEntry> getSeasons() {
		return seasons;
	}

	public void setSeasons(List<SeasonEntry> seasons) {
		this.seasons = seasons;
	}

	public List<LeagueEntry> getLeagues() {
		return leagues;
	}

	public void setLeagues(List<LeagueEntry> leagues) {
		this.leagues = leagues;
	}

	public List<TeamStatus> getStatuses() {
		if (statuses == null) {
			statuses = Arrays.asList(TeamStatus.values());
		}
		return statuses;
	}
}
