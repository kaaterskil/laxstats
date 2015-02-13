package laxstats.api.users;

/**
 * For authorization
 */
public enum UserRole {
	ROLE_MANAGER("Manager"), ROLE_COACH("Coach"), ROLE_ADMIN("Administrator"), ROLE_SUPERADMIN(
			"Super Admin");
	private String label;

	private UserRole(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
