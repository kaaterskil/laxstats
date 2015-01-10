package laxstats.web.events;

import java.util.HashMap;
import java.util.Map;

public class PenaltyForm extends AbstractPlayForm {
	private String violationId;
	private int duration;
	private Map<String, String> violationData = new HashMap<>();

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
