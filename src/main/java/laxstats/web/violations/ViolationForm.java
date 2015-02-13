package laxstats.web.violations;

import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.violations.PenaltyCategory;
import laxstats.api.violations.PenaltyLength;

public class ViolationForm {

	@NotNull
	@Size(min = 3, max = 50)
	private String name;
	private String description;

	@NotNull
	private PenaltyCategory category;

	private PenaltyLength penaltyLength;
	private boolean releasable = true;
	private List<PenaltyLength> penaltyLengths;
	private List<PenaltyCategory> categories;

	/*---------- Getter/Setters ----------*/

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

	/*---------- Select element options ----------*/

	public List<PenaltyLength> getPenaltyLengths() {
		if (penaltyLengths == null) {
			penaltyLengths = Arrays.asList(PenaltyLength.values());
		}
		return penaltyLengths;
	}

	public List<PenaltyCategory> getCategories() {
		if (categories == null) {
			categories = Arrays.asList(PenaltyCategory.values());
		}
		return categories;
	}
}
