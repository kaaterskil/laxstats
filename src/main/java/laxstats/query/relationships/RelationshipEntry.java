package laxstats.query.relationships;

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

import laxstats.api.relationships.RelationshipType;
import laxstats.query.people.PersonEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "relationships", indexes = {
		@Index(name = "relationships_idx1", columnList = "type"),
		@Index(name = "relationships_idx2", columnList = "child") }, uniqueConstraints = { @UniqueConstraint(name = "relationships_uk1", columnNames = {
		"id", "parent", "child" }) })
public class RelationshipEntry {
	@Id
	@Column(length = 36)
	private String id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private PersonEntry parent;

	@ManyToOne
	@JoinColumn(nullable = false)
	private PersonEntry child;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private RelationshipType type;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime createdAt;

	@ManyToOne
	private UserEntry createdBy;

	/*---------- Constructors ----------*/

	public RelationshipEntry(String id, PersonEntry parent, PersonEntry child,
			RelationshipType type) {
		this.id = id;
		this.parent = parent;
		this.child = child;
		this.type = type;

		parent.addChildRelationship(this);
		child.addParentRelationship(this);
	}

	protected RelationshipEntry() {
	}

	/*---------- Getter/Setters ----------*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PersonEntry getParent() {
		return parent;
	}

	public void setParent(PersonEntry parent) {
		this.parent = parent;
	}

	public PersonEntry getChild() {
		return child;
	}

	public void setChild(PersonEntry child) {
		this.child = child;
	}

	public RelationshipType getType() {
		return type;
	}

	public void setType(RelationshipType type) {
		this.type = type;
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
}
