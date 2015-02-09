package laxstats.api.users;

/**
 * For authorization
 */
public enum UserRole {
	MANAGER("Manager"), COACH("Coach"), ADMIN("Administrator"), SUPERADMIN(
			"Super Admin");
	private String label;

	private UserRole(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
