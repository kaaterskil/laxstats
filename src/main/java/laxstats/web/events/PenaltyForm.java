package laxstats.web.events;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalTime;

public class PenaltyForm extends AbstractPlayForm {
	private LocalTime elapsedTime;
	private String committedById;
	private String committedAgainstId;
	private String violationId;
	private int duration;
	private Map<String, String> violationData = new HashMap<>();

	public LocalTime getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(LocalTime elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public String getCommittedById() {
		return committedById;
	}

	public void setCommittedById(String committedById) {
		this.committedById = committedById;
	}

	public String getCommittedAgainstId() {
		return committedAgainstId;
	}

	public void setCommittedAgainstId(String committedAgainstId) {
		this.committedAgainstId = committedAgainstId;
	}

	public String getViolationId() {
		return violationId;
	}

	public void setViolationId(String violationId) {
		this.violationId = violationId;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Map<String, String> getViolationData() {
		return violationData;
	}

	public void setViolationData(Map<String, String> violationData) {
		this.violationData = violationData;
	}

}
