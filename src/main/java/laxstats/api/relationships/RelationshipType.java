package laxstats.api.relationships;

public enum RelationshipType {
	PARENT("Parent"), GUARDIAN("Guardian"), COUNSELOR("Counselor"), TEACHER(
			"Teacher"), FAMILY("Family member");
	private String label;

	private RelationshipType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
