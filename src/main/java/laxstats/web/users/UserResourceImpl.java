package laxstats.web.users;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.users.UserRole;
import laxstats.api.utils.Constants;

import org.hibernate.validator.constraints.Email;

/**
 * {@code UserResource} represents a user resource for remote clients.
 */
public class UserResourceImpl implements UserResource {

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
   public UserResourceImpl(String id, String teamId, String email, String password, boolean enabled,
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
   public UserResourceImpl() {
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getId() {
      return id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setId(String id) {
      this.id = id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getTeamId() {
      return teamId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setTeamId(String teamId) {
      this.teamId = teamId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getEmail() {
      return email;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setEmail(String email) {
      assert email != null;
      this.email = email;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getPassword() {
      return password;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setPassword(String password) {
      assert password != null;
      this.password = password;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isEnabled() {
      return enabled;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getFirstName() {
      return firstName;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getLastName() {
      return lastName;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setLastName(String lastName) {
      assert lastName != null;
      this.lastName = lastName;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public UserRole getRole() {
      return role;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setRole(UserRole role) {
      assert role != null;
      this.role = role;
   }
}
