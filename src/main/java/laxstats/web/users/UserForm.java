package laxstats.web.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.Region;
import laxstats.api.users.UserRole;
import laxstats.query.teams.TeamEntry;

import org.hibernate.validator.constraints.Email;

/**
 * {@code UserForm} contains user-defined information to create and update a user.
 */
public class UserForm {

   private String id;
   private String teamId;

   @NotNull
   @Email
   private String email;

   @Size(min = 6)
   private String password;

   private boolean enabled;

   @Size(max = 20)
   private String firstName;

   @NotNull
   @Size(min = 3, max = 30)
   private String lastName;

   @NotNull
   private UserRole role;

   private List<UserRole> roles;
   private Map<Region, List<TeamEntry>> teams;

   public List<UserRole> getUserRoles() {
      final List<UserRole> list = new ArrayList<>();
      list.add(UserRole.MEMBER);
      list.add(UserRole.COACH);
      list.add(UserRole.MANAGER);

      return list;
   }

   /**
    * Returns the user's primary key, or null if a new user.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the user's primary key.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the user's team association, or null if none.
    *
    * @return
    */
   public String getTeamId() {
      return teamId;
   }

   /**
    * Sets the user's team association.
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
    * Sets the user's email address.
    *
    * @param email
    */
   public void setEmail(String email) {
      this.email = email;
   }

   /**
    * Returns the user's password (in plaintext). Never null.
    *
    * @return
    */
   public String getPassword() {
      return password;
   }

   /**
    * Sets the user's password (in plaintext).
    *
    * @param password
    */
   public void setPassword(String password) {
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
    * Sets a flag to determine if the user is enabled to use this application.
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
    * Sets the user's first name.
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
    * Sets the user's last name.
    *
    * @param lastName
    */
   public void setLastName(String lastName) {
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
    * Sets the user's role.
    *
    * @param role
    */
   public void setRole(UserRole role) {
      this.role = role;
   }

   /*---------- Select element options ----------*/

   public List<UserRole> getRoles() {
      if (roles == null) {
         roles = Arrays.asList(UserRole.values());
      }
      return roles;
   }

   public Map<Region, List<TeamEntry>> getTeams() {
      return teams;
   }

   public void setTeams(Map<Region, List<TeamEntry>> teams) {
      this.teams = teams;
   }
}
