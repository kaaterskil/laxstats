package laxstats.web.events;

import laxstats.api.events.Alignment;
import laxstats.api.events.Conditions;
import laxstats.api.events.Schedule;
import laxstats.api.events.Status;
import laxstats.query.sites.SiteEntry;

import org.joda.time.LocalDateTime;

public class EventForm {
	private SiteEntry site;
	private Alignment alignment;
	private LocalDateTime startsAt;
	private Schedule schedule;
	private Status status;
	private Conditions conditions;
	private String description;

	public SiteEntry getSite() {
		return site;
	}

	public void setSite(SiteEntry site) {
		this.site = site;
	}

	public Alignment getAlignment() {
		return alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

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

	public Conditions getConditions() {
		return conditions;
	}

	public void setConditions(Conditions conditions) {
		this.conditions = conditions;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
