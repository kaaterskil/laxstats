package laxstats.api.users;

import org.springframework.security.core.userdetails.User;

import laxstats.query.users.UserAuthority;

/**
 * {@code UserRole} enumerates the role-based authorization of this application.
 */
public enum UserRole {

   /**
    * A team member
    */
   MEMBER("Member"),

   /**
    * A team-based user.
    */
   MANAGER("Manager"),

   /**
    * A team-based user with the highest level of privileges.
    */
   COACH("Coach"),

   /**
    * An application administrator.
    */
   ADMIN("Administrator"),

   /**
    * An application administrator with the highest level of privileges.
    */
   SUPERADMIN("Super Admin");

   /**
    * Return the UserRole for the given authority.
    *
    * @param authority
    * @return
    */
   public static UserRole valueOf(UserAuthority authority) {
      switch (authority.getAuthority()) {
         case "ROLE_MEMBER":
            return MEMBER;
         case "ROLE_MANAGER":
            return MANAGER;
         case "ROLE_COACH":
            return COACH;
         case "ROLE_ADMIN":
            return ADMIN;
         case "ROLE_SUPERADMIN":
            return SUPERADMIN;
      }
      throw new IllegalArgumentException(
         "No role defined for authority: " + authority.getAuthority());
   }

   private String label;

   /**
    * Creates a {@code UserRole} with the given pretty name.
    *
    * @param label
    */
   private UserRole(String label) {
      assert label != null;
      this.label = label;
   }

   /**
    * Returns the pretty name of a {@code UserRole} for use in a drop-down menu.
    *
    * @return
    */
   public String getLabel() {
      return label;
   }

   public UserAuthority asAuthorityFor(User user) {
      final UserAuthority authority = new UserAuthority();
      authority.setAuthority("ROLE_" + toString());
      authority.setUser(user);
      return authority;
   }
}
