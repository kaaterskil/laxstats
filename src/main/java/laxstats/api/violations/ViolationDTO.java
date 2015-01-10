package laxstats.api.violations;

import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

public class ViolationDTO {

	private String id;
	private String name;
	private String description;
	private PenaltyCategory category;
	private PenaltyLength penaltyLength;
	private boolean releasable;
	private LocalDateTime createdAt;
	private UserEntry createdBy;
	private LocalDateTime modifiedAt;
	private UserEntry modifiedBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public UserEntry getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserEntry createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public UserEntry getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(UserEntry modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
