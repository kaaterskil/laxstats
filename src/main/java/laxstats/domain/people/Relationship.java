package laxstats.domain.people;

import java.io.Serializable;

public class Relationship {

	public enum Type {
		FAMILY, COUNSELOR
	}

	public static class Id implements Serializable {
		private static final long serialVersionUID = -3172351527214214709L;
		private String parentId;
		private String childId;

		public Id() {
		}

		public Id(String parentId, String childId) {
			this.parentId = parentId;
			this.childId = childId;
		}

		public boolean equals(Object o) {
			if (o != null && o instanceof Relationship.Id) {
				Id that = (Id) o;
				return this.parentId.equals(that.parentId)
						&& this.childId.equals(that.childId);
			}
			return false;
		}

		public int hashCode() {
			return parentId.hashCode() + childId.hashCode();
		}
	}

	private Relationship.Id id = new Id();
	private Person parent;
	private Person child;
	private Relationship.Type type;
}
