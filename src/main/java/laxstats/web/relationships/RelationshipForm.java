package laxstats.web.relationships;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import laxstats.api.relationships.RelationshipType;

public class RelationshipForm {
	@NotNull
	private String parentId;
	@NotNull
	private String childId;
	private RelationshipType type;
	private Map<String, String> parents = new HashMap<>();
	private Map<String, String> children = new HashMap<>();

	public RelationshipForm(Map<String, String> parents,
			Map<String, String> children) {
		this.parents = parents;
		this.children = children;
	}

	/*---------- Getter/Setters----------*/

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getChildId() {
		return childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}

	public RelationshipType getType() {
		return type;
	}

	public void setType(RelationshipType type) {
		this.type = type;
	}

	public Map<String, String> getParents() {
		return parents;
	}

	public Map<String, String> getChildren() {
		return children;
	}
}
