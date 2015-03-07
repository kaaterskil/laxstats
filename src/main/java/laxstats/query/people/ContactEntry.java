package laxstats.query.people;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.api.people.ContactMethod;
import laxstats.api.people.Contactable;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "contacts", indexes = {
		@Index(name = "contacts_idx1", columnList = "method"),
		@Index(name = "contacts_idx2", columnList = "isPrimary"),
		@Index(name = "contacts_idx3", columnList = "doNotUse") }, uniqueConstraints = { @UniqueConstraint(name = "contacts_uk1", columnNames = {
		"person_id", "isPrimary" }) })
public class ContactEntry implements Contactable, Serializable {
	private static final long serialVersionUID = -572352451858382338L;

	@Id
	@Column(length = 36)
	private String id;

	@ManyToOne
	@JoinColumn(name = "person_id")
	private PersonEntry person;

	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private ContactMethod method;

	@Column(nullable = false)
	private String value;

	private boolean isPrimary = false;

	private boolean doNotUse = false;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne
	private UserEntry createdBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime modifiedAt;

	@ManyToOne
	private UserEntry modifiedBy;

	/*---------- Methods ----------*/

	public String getHtml() {
		final StringBuilder sb = new StringBuilder();
		if (method.equals(ContactMethod.EMAIL)) {
			sb.append("<a href='mailto:").append(value).append("'>");
			sb.append(value);
			sb.append("</a>");
		} else {
			sb.append(value);
		}
		return sb.toString();
	}

	/*---------- Getter/Setters ----------*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public ContactMethod getMethod() {
		return method;
	}

	public void setMethod(ContactMethod method) {
		this.method = method;
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

	public PersonEntry getPerson() {
		return person;
	}

	public void setPerson(PersonEntry person) {
		this.person = person;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean isDoNotUse() {
		return doNotUse;
	}

	@Override
	public void setDoNotUse(boolean doNotUse) {
		this.doNotUse = doNotUse;
	}

	@Override
	public boolean isPrimary() {
		return isPrimary;
	}

	@Override
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
}
