package laxstats.web.events;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import laxstats.api.Region;
import laxstats.api.events.Conditions;
import laxstats.api.events.Schedule;
import laxstats.api.events.Status;
import laxstats.api.sites.SiteAlignment;
import laxstats.query.sites.SiteEntry;
import laxstats.query.teams.TeamEntry;

import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class EventForm {
	private String id;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@NotNull
	private LocalDateTime startsAt;

	@NotNull
	private Schedule schedule = Schedule.REGULAR;

	@NotNull
	private Status status;

	@NotNull
	private String site;

	private String teamOne;
	private String teamTwo;
	private boolean teamOneHome = false;
	private boolean teamTwoHome = false;
	private SiteAlignment alignment = SiteAlignment.HOME;
	private String description;
	private Conditions weather;

	private Map<Region, List<SiteEntry>> sites = new HashMap<>();
	private Map<Region, List<TeamEntry>> teams = new HashMap<>();
	private List<Schedule> schedules;
	private List<Status> statuses;
	private List<SiteAlignment> siteAlignments;
	private List<Conditions> conditions;

	/*---------- Getter/Setters ----------*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getStartsAt() {
		return startsAt;
	}

	public void setStartsAt(LocalDateTime startsAt) {
		this.startsAt = startsAt;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getTeamOne() {
		return teamOne;
	}

	public void setTeamOne(String teamOne) {
		this.teamOne = teamOne;
	}

	public String getTeamTwo() {
		return teamTwo;
	}

	public void setTeamTwo(String teamTwo) {
		this.teamTwo = teamTwo;
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

	public Conditions getWeather() {
		return weather;
	}

	public void setWeather(Conditions weather) {
		this.weather = weather;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	/*---------- Select element options ----------*/

	public Map<Region, List<SiteEntry>> getSites() {
		return sites;
	}

	public void setSites(Map<Region, List<SiteEntry>> sites) {
		this.sites = sites;
	}

	public Map<Region, List<TeamEntry>> getTeams() {
		return teams;
	}

	public void setTeams(Map<Region, List<TeamEntry>> teams) {
		this.teams = teams;
	}

	public List<Schedule> getSchedules() {
		if (schedules == null) {
			schedules = Arrays.asList(Schedule.values());
		}
		return schedules;
	}

	public List<Status> getStatuses() {
		if (statuses == null) {
			statuses = Arrays.asList(Status.values());
		}
		return statuses;
	}

	public List<SiteAlignment> getSiteAlignments() {
		if (siteAlignments == null) {
			siteAlignments = Arrays.asList(SiteAlignment.values());
		}
		return siteAlignments;
	}

	public List<Conditions> getConditions() {
		if (conditions == null) {
			conditions = Arrays.asList(Conditions.values());
		}
		return conditions;
	}

}
