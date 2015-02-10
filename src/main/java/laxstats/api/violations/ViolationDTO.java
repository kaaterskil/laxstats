package laxstats.api.violations;

import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

public class ViolationDTO {

	private final String id;
	private final String name;
	private final String description;
	private final PenaltyCategory category;
	private final PenaltyLength penaltyLength;
	private final boolean releasable;
	private final LocalDateTime createdAt;
	private final UserEntry createdBy;
	private final LocalDateTime modifiedAt;
	private final UserEntry modifiedBy;

	public ViolationDTO(String id, String name, String description,
			PenaltyCategory category, PenaltyLength penaltyLength,
			boolean releasable, LocalDateTime createdAt, UserEntry createdBy,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.penaltyLength = penaltyLength;
		this.releasable = releasable;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
	}

	public ViolationDTO(String id, String name, String description,
			PenaltyCategory category, PenaltyLength penaltyLength,
			boolean releasable, LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this(id, name, description, category, penaltyLength, releasable, null,
				null, modifiedAt, modifiedBy);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public PenaltyCategory getCategory() {
		return category;
	}

	public PenaltyLength getPenaltyLength() {
		return penaltyLength;
	}

	public boolean isReleasable() {
		return releasable;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public UserEntry getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public UserEntry getModifiedBy() {
		return modifiedBy;
	}
}
