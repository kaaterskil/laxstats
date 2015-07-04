package laxstats.web.users;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.users.UserRole;
import laxstats.api.utils.Constants;

import org.hibernate.validator.constraints.Email;

/**
 * {@code UserResource} represents a user resource for remote clients.
 */
public class UserResource {

   private String id;
   private String teamId;

   @NotNull
   @Email
   private String email;

   @NotNull
   @Size(min = 6)
   private String password;

   private boolean enabled = true;

   @Size(max = Constants.MAX_LENGTH_FIRST_OR_MIDDLE_NAME)
   private String firstName;

   @NotNull
   @Size(min = Constants.MIN_LENGTH_STRING, max = Constants.MAX_LENGTH_LAST_NAME)
   private String lastName;

   @NotNull
   private UserRole role;

   /**
    * Creates a {@code UserResource} with the given information.
    *
    * @param id
    * @param teamId
    * @param email
    * @param password
    * @param enabled
    * @param firstName
    * @param lastName
    * @param role
    */
   public UserResource(String id, String teamId, String email, String password, boolean enabled,
      String firstName, String lastName, UserRole role) {
      this.id = id;
      this.teamId = teamId;
      this.email = email;
      this.password = password;
      this.enabled = enabled;
      this.firstName = firstName;
      this.lastName = lastName;
      this.role = role;
   }

   /**
    * Creates an empty {@code UserResource} for internal use.
    */
   public UserResource() {
   }

   /**
    * Returns the user's primary key, or null if the user has not been persisted.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the user's primary key. Use null if the user has not been persisted.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the identifier of the user's team association, or null if none.
    *
    * @return
    */
   public String getTeamId() {
      return teamId;
   }

   /**
    * Sets the identifier of the user's team association. Use null for none.
    *
    * @param teamId
    */
   public void setTeamId(String teamId) {
      this.teamId = teamId;
   }

   /**
    * Returns the user's email address. Never null.
    *
    * @return
    */
   public String getEmail() {
      return email;
   }

   /**
    * Sets the user's email address. Must not be null.
    *
    * @param email
    */
   public void setEmail(String email) {
      assert email != null;
      this.email = email;
   }

   /**
    * Returns the user's password. Never null.
    *
    * @return
    */
   public String getPassword() {
      return password;
   }

   /**
    * Sets the user's password. Must not be null.
    *
    * @param password
    */
   public void setPassword(String password) {
      assert password != null;
      this.password = password;
   }

   /**
    * Returns true if the user is enabled to user this application, false otherwise.
    *
    * @return
    */
   public boolean isEnabled() {
      return enabled;
   }

   /**
    * Sets a flag to determine if the user is enabled to use this application. Defaults to true.
    *
    * @param enabled
    */
   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   /**
    * Returns the user's first name, or null if not provided.
    *
    * @return
    */
   public String getFirstName() {
      return firstName;
   }

   /**
    * Sets the user's first name. Use null for none or unknown.
    *
    * @param firstName
    */
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   /**
    * Returns the user's last name. Never null.
    *
    * @return
    */
   public String getLastName() {
      return lastName;
   }

   /**
    * Sets the user's last name. Must not be null.
    *
    * @param lastName
    */
   public void setLastName(String lastName) {
      assert lastName != null;
      this.lastName = lastName;
   }

   /**
    * Returns the user's role. Never null.
    *
    * @return
    */
   public UserRole getRole() {
      return role;
   }

   /**
    * Sets the user's role. Must not be null.
    *
    * @param role
    */
   public void setRole(UserRole role) {
      assert role != null;
      this.role = role;
   }
}
