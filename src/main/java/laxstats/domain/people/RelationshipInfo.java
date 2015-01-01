package laxstats.domain.people;

import laxstats.api.relationships.RelationshipType;

public class RelationshipInfo {
	private final String id;
	private final String parentId;
	private final String parentName;
	private final String childId;
	private final String childName;
	private final RelationshipType type;

	public RelationshipInfo(String id, String parentId, String parentName,
			String childId, String childName, RelationshipType type) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.parentName = parentName;
		this.childId = childId;
		this.childName = childName;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getParentId() {
		return parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public String getChildId() {
		return childId;
	}

	public String getChildName() {
		return childName;
	}

	public RelationshipType getType() {
		return type;
	}
}
