package laxstats.api.people;

import laxstats.query.people.PersonEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

public class ContactDTO {
	private final String id;
	private final PersonEntry person;
	private final ContactMethod method;
	private final String value;
	private final boolean isPrimary;
	private final boolean doNotUse;
	private final LocalDateTime createdAt;
	private final UserEntry createdBy;
	private final LocalDateTime modifiedAt;
	private final UserEntry modifiedBy;

	public ContactDTO(String id, PersonEntry person, ContactMethod method,
			String value, boolean isPrimary, boolean doNotUse,
			LocalDateTime createdAt, UserEntry createdBy,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this.id = id;
		this.person = person;
		this.method = method;
		this.value = value;
		this.isPrimary = isPrimary;
		this.doNotUse = doNotUse;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
	}

	public ContactDTO(String id, PersonEntry person, ContactMethod method,
			String value, boolean isPrimary, boolean doNotUse,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this(id, person, method, value, isPrimary, doNotUse, null, null,
				modifiedAt, modifiedBy);
	}

	public String getId() {
		return id;
	}

	public PersonEntry getPerson() {
		return person;
	}

	public ContactMethod getMethod() {
		return method;
	}

	public String getValue() {
		return value;
	}

	public boolean isPrimary() {
		return isPrimary;
	}

	public boolean isDoNotUse() {
		return doNotUse;
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
