package laxstats.query.people;

import java.io.Serializable;

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
	
	static Relationship create(PersonEntry parent, PersonEntry child, Relationship.Type type) {
		if(!canCreate(parent, child, type)) {
			throw new IllegalArgumentException("Invalid relationship");
		}
		return new Relationship(parent, child, type);
	}
	
	static boolean canCreate(PersonEntry parent, PersonEntry child, Relationship.Type type) {
		if(parent.equals(child) || parent.ancestorsInclude(child, type)) {
			return false;
		}
		return true;
	}
	
	public enum Type {
		FAMILY, COUNSELOR
	}
	
	@Embeddable
	public static class Id implements Serializable {
		private static final long serialVersionUID = -3172351527214214709L;
		@Column(length = 36)
		private String parentId;
		
		@Column(length = 36)
		private String childId;
		
		public Id(){}
		
		public Id(String parentId, String childId) {
			this.parentId = parentId;
			this.childId = childId;
		}
		
		public boolean equals(Object o) {
			if(o != null && o instanceof Relationship.Id) {
				Id that = (Id) o;
				return this.parentId.equals(that.parentId) && 
						this.childId.equals(that.childId);
			}
			return false;
		}
		
		public int hashCode() {
			return parentId.hashCode() + childId.hashCode();
		}
	}

	@javax.persistence.Id
	@Embedded
	private final Relationship.Id id = new Id();

	@ManyToOne
	@JoinColumn(name = "parentId", insertable = false, updatable = false)
	private PersonEntry parent;
	
	@ManyToOne
	@JoinColumn(name = "childId", insertable = false, updatable = false)
	private PersonEntry child;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Relationship.Type type;
	
	private Relationship(PersonEntry parent, PersonEntry child, Relationship.Type type) {
		this.parent = parent;
		this.child = child;
		this.type = type;
		
		this.id.parentId = parent.getId();
		this.id.childId = child.getId();
		
		parent.addChildRelationship(this);
		child.addParentRelationship(this);
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

	public Relationship.Type getType() {
		return type;
	}

	public void setType(Relationship.Type type) {
		this.type = type;
	}
}
