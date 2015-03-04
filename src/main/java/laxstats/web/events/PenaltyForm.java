package laxstats.web.events;

import java.util.HashMap;
import java.util.Map;

import laxstats.api.events.PlayKey;
import laxstats.api.events.PlayType;

import org.joda.time.Period;

public class PenaltyForm extends AbstractPlayForm {
	private Period elapsedTime;
	private String committedById;
	private String committedAgainstId;
	private String violationId;
	private Period duration;
	private Map<String, String> violationData = new HashMap<>();

	public PenaltyForm() {
		super(PlayType.PENALTY, PlayKey.PLAY);
	}

	/*---------- Getter/Setters ----------*/

	public Period getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Period elapsedTime) {
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

	public Period getDuration() {
		return duration;
	}

	public void setDuration(Period duration) {
		this.duration = duration;
	}

	public Map<String, String> getViolationData() {
		return violationData;
	}

	public void setViolationData(Map<String, String> violationData) {
		this.violationData = violationData;
	}

}
