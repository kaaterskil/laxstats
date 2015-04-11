package laxstats.api.users;

/**
 * {@code UserRole} enumerates the role-based authorization of this application.
 */
public enum UserRole {

   /**
    * A team-based user.
    */
   ROLE_MANAGER("Manager"),

   /**
    * A team-based user with the highest level of privileges.
    */
   ROLE_COACH("Coach"),

   /**
    * An application administrator.
    */
   ROLE_ADMIN("Administrator"),

   /**
    * An application administrator with the highest level of privileges.
    */
   ROLE_SUPERADMIN("Super Admin");

   /**
    * Returns the pretty name of a {@code UserRole} for use in a drop-down menu.
    * 
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates a {@code UserRole} with the given pretty name.
    * 
    * @param label
    */
   private UserRole(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
