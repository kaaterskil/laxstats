package laxstats.api.users;

import java.io.Serializable;

import laxstats.query.teams.TeamEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

/**
 * {@code UserDTO} transfers data about a user between the presentation and domain layers.
 */
public class UserDTO implements Serializable {
   private static final long serialVersionUID = -189331604621547915L;

   private final String userId;
   private final String email;
   private final String encodedPassword;
   private final String firstName;
   private final String lastName;
   private final TeamEntry team;
   private final String ipAddress;
   private final boolean enabled;
   private final UserRole role;
   private final LocalDateTime createdAt;
   private final UserEntry createdBy;
   private final LocalDateTime modifiedAt;
   private final UserEntry modifiedBy;

   /**
    * Creates a {@code UserDTO} with the given information.
    * 
    * @param userId
    * @param email
    * @param encodedPassword
    * @param firstName
    * @param lastName
    * @param team
    * @param ipAddress
    * @param enabled
    * @param role
    * @param createdAt
    * @param createdBy
    * @param modifiedAt
    * @param modifiedBy
    */
   public UserDTO(String userId, String email, String encodedPassword, String firstName,
      String lastName, TeamEntry team, String ipAddress, boolean enabled, UserRole role,
      LocalDateTime createdAt, UserEntry createdBy, LocalDateTime modifiedAt, UserEntry modifiedBy) {
      this.userId = userId;
      this.email = email;
      this.encodedPassword = encodedPassword;
      this.firstName = firstName;
      this.lastName = lastName;
      this.team = team;
      this.ipAddress = ipAddress;
      this.enabled = enabled;
      this.role = role;
      this.createdAt = createdAt;
      this.createdBy = createdBy;
      this.modifiedAt = modifiedAt;
      this.modifiedBy = modifiedBy;
   }

   /**
    * Creates a {@code UserDTO} with the given information.
    * 
    * @param userId
    * @param email
    * @param encodedPassword
    * @param firstName
    * @param lastName
    * @param team
    * @param ipAddress
    * @param enabled
    * @param role
    * @param modifiedAt
    * @param modifiedBy
    */
   public UserDTO(String userId, String email, String encodedPassword, String firstName,
      String lastName, TeamEntry team, String ipAddress, boolean enabled, UserRole role,
      LocalDateTime modifiedAt, UserEntry modifiedBy) {
      this(userId, email, encodedPassword, firstName, lastName, team, ipAddress, enabled, role,
         null, null, modifiedAt, modifiedBy);
   }

   /**
    * Returns the user's primary key.
    * 
    * @return
    */
   public String getUserId() {
      return userId;
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
    * Returns the user's encoded password.
    * 
    * @return
    */
   public String getEncodedPassword() {
      return encodedPassword;
   }

   /**
    * Returns the user's first name.
    * 
    * @return
    */
   public String getFirstName() {
      return firstName;
   }

   /**
    * Returns the user's last name.
    * 
    * @return
    */
   public String getLastName() {
      return lastName;
   }

   /**
    * Returns this user's team, or null if none.
    * 
    * @return
    */
   public TeamEntry getTeam() {
      return team;
   }

   /**
    * Returns the user's IP address, or null if none or not known.
    * 
    * @return
    */
   public String getIpAddress() {
      return ipAddress;
   }

   /**
    * Returns true if this user is enabled to operate this application, false otherwise.
    * 
    * @return
    */
   public boolean isEnabled() {
      return enabled;
   }

   /**
    * Returns the user role. Never null.
    * 
    * @return
    */
   public UserRole getRole() {
      return role;
   }

   /**
    * Returns the date and time this user was first persisted.
    * 
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Returns the user who first persisted this user.
    * 
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Returns the date and time this user was last modified.
    * 
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Returns the user who last modified this user.
    * 
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

}
