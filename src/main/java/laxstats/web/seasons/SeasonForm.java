package laxstats.web.seasons;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.joda.time.LocalDate;

public class SeasonForm {
	@NotNull
	@Size(min = 3)
	private String description;
	
	@NotNull
	private LocalDate startsOn;
	private LocalDate endsOn;
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
}
