package laxstats.web.events;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import laxstats.api.events.Conditions;
import laxstats.api.events.Schedule;
import laxstats.api.events.Status;
import laxstats.api.sites.SiteAlignment;
import laxstats.query.seasons.SeasonEntry;
import laxstats.query.sites.SiteEntry;

import org.joda.time.LocalDateTime;

public class EventForm {
	@NotNull
	private LocalDateTime startsAt;
	@NotNull
	private Schedule schedule;
	@NotNull
	private Status status;
	private String siteId;
	private String teamOneId;
	private String teamTwoId;
	private boolean teamOneHome;
	private boolean teamTwoHome;
	private SiteAlignment alignment;
	private String description;
	private Conditions conditions;
	private List<SiteEntry> sites = new ArrayList<>();
	private List<SeasonEntry> seasons = new ArrayList<>();
	private List<TeamSeasonSelect> teams = new ArrayList<>();

	public LocalDateTime getStartsAt() {
		return startsAt;
	}

	public void setStartsAt(LocalDateTime startsAt) {
		this.startsAt = startsAt;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getTeamOneId() {
		return teamOneId;
	}

	public void setTeamOneId(String teamOneId) {
		this.teamOneId = teamOneId;
	}

	public String getTeamTwoId() {
		return teamTwoId;
	}

	public void setTeamTwoId(String teamTwoId) {
		this.teamTwoId = teamTwoId;
	}

	public boolean isTeamOneHome() {
		return teamOneHome;
	}

	public void setTeamOneHome(boolean teamOneHome) {
		this.teamOneHome = teamOneHome;
	}

	public boolean isTeamTwoHome() {
		return teamTwoHome;
	}

	public void setTeamTwoHome(boolean teamTwoHome) {
		this.teamTwoHome = teamTwoHome;
	}

	public SiteAlignment getAlignment() {
		return alignment;
	}

	public void setAlignment(SiteAlignment alignment) {
		this.alignment = alignment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Conditions getConditions() {
		return conditions;
	}

	public void setConditions(Conditions conditions) {
		this.conditions = conditions;
	}

	public List<SiteEntry> getSites() {
		return sites;
	}

	public void setSites(List<SiteEntry> sites) {
		this.sites = sites;
	}

	public List<SeasonEntry> getSeasons() {
		return seasons;
	}

	public void setSeasons(List<SeasonEntry> seasons) {
		this.seasons = seasons;
	}

	public List<TeamSeasonSelect> getTeams() {
		return teams;
	}

	public void setTeams(List<TeamSeasonSelect> teams) {
		this.teams = teams;
	}

}
