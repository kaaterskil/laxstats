package laxstats.web.events;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import laxstats.query.events.AttendeeEntry;

public abstract class AbstractPlayForm {
	private String teamSeasonId;
	private int period;
	private String comment;
	private Map<String, String> teams = new HashMap<String, String>();
	private Map<String, String> participants = new HashMap<String, String>();
	private Map<String, List<AttendeeEntry>> attendees = new HashMap<>();

	public String getTeamSeasonId() {
		return teamSeasonId;
	}

	public void setTeamSeasonId(String teamSeasonId) {
		this.teamSeasonId = teamSeasonId;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Map<String, String> getTeams() {
		return teams;
	}

	public void setTeams(Map<String, String> teams) {
		this.teams = teams;
	}

	public Map<String, String> getParticipants() {
		return participants;
	}

	public void setParticipants(Map<String, String> participants) {
		this.participants = participants;
	}

	public Map<String, List<AttendeeEntry>> getAttendees() {
		return attendees;
	}

	public void setAttendees(Map<String, List<AttendeeEntry>> attendees) {
		this.attendees = attendees;
	}
}
