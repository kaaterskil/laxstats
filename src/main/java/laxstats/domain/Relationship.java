package laxstats.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(indexes = {@Index(name = "relationship_idx1", columnList = "type")})
public class Relationship {
	
	static Relationship create(Person parent, Person child, Relationship.Type type) {
		if(!canCreate(parent, child, type)) {
			throw new IllegalArgumentException("Invalid relationship");
		}
		return new Relationship(parent, child, type);
	}
	
	static boolean canCreate(Person parent, Person child, Relationship.Type type) {
		if(parent.equals(child)) {
			return false;
		}
		if(parent.ancestorsInclude(child, type)) {
			return false;
		}
		return true;
	}
	
	public enum Type {
		FAMILY, COUNSELOR;
	}
	
	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = -3172351527214214709L;

		@Column(name = "parent_id")
		private UUID parentId;
		
		@Column(name = "child_id")
		private UUID childId;
		
		public Id(){}
		
		public Id(UUID parentId, UUID childId) {
			this.parentId = parentId;
			this.childId = childId;
		}
		
		public boolean equals(Object o) {
			if(o != null && o instanceof Relationship.Id) {
				Id that = (Id) o;
				return this.parentId.equals(that.parentId) && this.childId.equals(that.childId);
			}
			return false;
		}
		
		public int hashCode() {
			return parentId.hashCode() + childId.hashCode();
		}
	}

	@javax.persistence.Id
	@Embedded
	private Relationship.Id id = new Id();

	@ManyToOne
	@JoinColumn(name = "parent_id", insertable = false, updatable = false)
	private Person parent;
	
	@ManyToOne
	@JoinColumn(name = "child_id", insertable = false, updatable = false)
	private Person child;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Relationship.Type type;
	
	private Relationship(Person parent, Person child, Relationship.Type type) {
		this.parent = parent;
		this.child = child;
		this.type = type;
		
		this.id.parentId = parent.getId();
		this.id.childId = child.getId();
		
		parent.addChildRelationship(this);
		child.addParentRelationship(this);
	}

	public Person getParent() {
		return parent;
	}

	public void setParent(Person parent) {
		this.parent = parent;
	}

	public Person getChild() {
		return child;
	}

	public void setChild(Person child) {
		this.child = child;
	}

	public Relationship.Type getType() {
		return type;
	}

	public void setType(Relationship.Type type) {
		this.type = type;
	}
}
