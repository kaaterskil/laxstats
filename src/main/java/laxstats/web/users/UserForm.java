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
public class UserForm implements UserResource {

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
