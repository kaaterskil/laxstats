package laxstats.web.violations;

import javax.validation.constraints.NotNull;

import laxstats.api.violations.PenaltyCategory;
import laxstats.api.violations.PenaltyLength;

public class ViolationForm {

	@NotNull
	private String name;
	private String description;

	@NotNull
	private PenaltyCategory category;

	@NotNull
	private PenaltyLength penaltyLength;
	private boolean releasable = true;

	// ---------- Getter/Setters ----------//

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PenaltyCategory getCategory() {
		return category;
	}

	public void setCategory(PenaltyCategory category) {
		this.category = category;
	}

	public PenaltyLength getPenaltyLength() {
		return penaltyLength;
	}

	public void setPenaltyLength(PenaltyLength penaltyLength) {
		this.penaltyLength = penaltyLength;
	}

	public boolean isReleasable() {
		return releasable;
	}

	public void setReleasable(boolean releasable) {
		this.releasable = releasable;
	}
}
