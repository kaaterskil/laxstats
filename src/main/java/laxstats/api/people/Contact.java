package laxstats.api.people;

import org.joda.time.LocalDateTime;

public class Contact {

	private final String id;
	private final String personId;
	private final ContactMethod method;
	private final String value;
	private final boolean isPrimary;
	private final boolean doNotUse;
	private final LocalDateTime createdAt;
	private final String createdBy;
	private final LocalDateTime modifiedAt;
	private final String modifiedBy;

	public Contact(String id, String personId, ContactMethod method,
			String value, boolean isPrimary, boolean doNotUse,
			LocalDateTime createdAt, String createdBy,
			LocalDateTime modifiedAt, String modifiedBy) {
		this.id = id;
		this.personId = personId;
		this.method = method;
		this.value = value;
		this.isPrimary = isPrimary;
		this.doNotUse = doNotUse;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public String getId() {
		return id;
	}

	public ContactMethod getMethod() {
		return method;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public String getPersonId() {
		return personId;
	}

	public String getValue() {
		return value;
	}

	public boolean isDoNotUse() {
		return doNotUse;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

}
