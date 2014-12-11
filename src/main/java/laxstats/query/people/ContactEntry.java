package laxstats.query.people;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import laxstats.api.people.ContactMethod;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(indexes = { @Index(name = "contact_idx1", columnList = "method"),
		@Index(name = "contact_idx2", columnList = "isPrimary"),
		@Index(name = "contact_idx3", columnList = "doNotUse") })
public class ContactEntry {

	@Id
	@Column(length = 36)
	private String id;

	@ManyToOne
	private PersonEntry person;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private ContactMethod method;

	@NotNull
	@Column(nullable = false)
	private String value;

	private boolean isPrimary = false;

	private boolean doNotUse = false;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne(targetEntity = UserEntry.class)
	private String createdBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne(targetEntity = UserEntry.class)
	private String modifiedBy;

	// ---------- Getter/Setters ----------//

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

	public PersonEntry getPerson() {
		return person;
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

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setDoNotUse(boolean doNotUse) {
		this.doNotUse = doNotUse;
	}

	public void setMethod(ContactMethod method) {
		this.method = method;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setPerson(PersonEntry person) {
		this.person = person;
	}

	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
