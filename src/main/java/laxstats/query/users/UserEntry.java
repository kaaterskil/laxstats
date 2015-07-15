package laxstats.query.users;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import laxstats.api.users.UserRole;
import laxstats.api.utils.Constants;
import laxstats.query.teams.TeamEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * {@code UserEntry} represents a query object model of an application user.
 */
@Entity
@Table(name = "users",
         indexes = { @Index(name = "users_idx1", columnList = "lastName") },
         uniqueConstraints = { @UniqueConstraint(name = "users_uk1", columnNames = { "email" }) })
public class UserEntry implements UserDetails {
   private static final long serialVersionUID = 4593458673816348289L;

   /**
    * Returns a set of granted authorities for use in authentication.
    *
    * @param authorities
    * @return
    */
   private static SortedSet<GrantedAuthority> sortAuthorities(
      Collection<? extends GrantedAuthority> authorities)
   {
      Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
      // Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract
      // and SEC-717)
      final SortedSet<GrantedAuthority> sortedAuthorities =
         new TreeSet<GrantedAuthority>(new AuthorityComparator());

      for (final GrantedAuthority grantedAuthority : authorities) {
         Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
         sortedAuthorities.add(grantedAuthority);
      }

      return sortedAuthorities;
   }

   private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
      private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

      @Override
      public int compare(GrantedAuthority g1, GrantedAuthority g2) {
         // Neither should ever be null as each entry is checked before adding it to the set.
         // If the authority is null, it is a custom authority and should precede others.
         if (g2.getAuthority() == null) {
            return -1;
         }

         if (g1.getAuthority() == null) {
            return 1;
         }

         return g1.getAuthority()
            .compareTo(g2.getAuthority());
      }
   }

   @Id
   @org.springframework.data.annotation.Id
   @Column(length = Constants.MAX_LENGTH_DATABASE_KEY)
   private String id;

   @ManyToOne
   private TeamEntry team;

   @Column(length = Constants.MAX_LENGTH_CONTACT_VALUE, nullable = false)
   private String email;

   @Column(length = 100, nullable = false)
   private String encodedPassword;

   @Column(nullable = false)
   private boolean enabled = false;

   @Column(length = Constants.MAX_LENGTH_FIRST_OR_MIDDLE_NAME)
   private String firstName;

   @Column(length = Constants.MAX_LENGTH_LAST_NAME, nullable = false)
   private String lastName;

   @Column(length = 15)
   private String ipAddress;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING)
   private UserRole role;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   private boolean accountNonExpired = true;
   private boolean accountNonLocked = true;
   private boolean credentialsNonExpired = true;

   @Transient
   private Set<? extends GrantedAuthority> authorities;

   /**
    * Creates a {@code UserEntry} with the given username and password.
    *
    * @param username
    * @param password
    */
   public UserEntry(String username, String password) {
      this(username, password, true, true, true, true, new HashSet<GrantedAuthority>());
   }

   /**
    * Creates a {@code UserEntry} with the given username, password and collection of granted
    * authorities.
    *
    * @param username
    * @param password
    * @param authorities
    */
   public UserEntry(String username, String password,
      Collection<? extends GrantedAuthority> authorities) {

      this(username, password, true, true, true, true, authorities);
   }

   /**
    * Creates a {@code UserEntry} with the given information.
    *
    * @param username
    * @param password
    * @param enabled
    * @param accountNonExpired
    * @param credentialsNonExpired
    * @param accountNonLocked
    * @param authorities
    */
   public UserEntry(String username, String password, boolean enabled, boolean accountNonExpired,
      boolean credentialsNonExpired, boolean accountNonLocked,
      Collection<? extends GrantedAuthority> authorities) {
      if ((username == null) || username.equals("") || (password == null)) {
         throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
      }
      email = username;
      encodedPassword = password;
      this.enabled = enabled;
      this.accountNonExpired = accountNonExpired;
      this.credentialsNonExpired = credentialsNonExpired;
      this.accountNonLocked = accountNonLocked;
      this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
   }

   /**
    * Creates a {@code UserEntry}. Internal use only.
    */
   public UserEntry() {
   }

   /**
    * Returns the user's concatenated full name.
    *
    * @return
    */
   public String getFullName() {
      final StringBuilder sb = new StringBuilder();
      boolean concat = false;
      if (firstName != null) {
         sb.append(firstName);
         concat = true;
      }
      if (lastName != null) {
         if (concat) {
            sb.append(" ");
         }
         sb.append(lastName);
      }
      return sb.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object obj) {
      if (obj instanceof UserEntry) {
         final UserEntry that = (UserEntry)obj;
         return getUsername().equals(that.getUsername());
      }
      return false;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return email.hashCode();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();
      sb.append(super.toString())
         .append(": ");
      sb.append("Username: ")
         .append(email)
         .append("; ");
      sb.append("Password: [PROTECTED]; ");
      sb.append("Enabled: ")
         .append(enabled)
         .append("; ");
      sb.append("AccountNonExpired: ")
         .append(accountNonExpired)
         .append("; ");
      sb.append("credentialsNonExpired: ")
         .append(credentialsNonExpired)
         .append("; ");
      sb.append("AccountNonLocked: ")
         .append(accountNonLocked)
         .append("; ");

      if (!authorities.isEmpty()) {
         sb.append("Granted Authorities: ");

         boolean first = true;
         for (final GrantedAuthority auth : authorities) {
            if (!first) {
               sb.append(",");
            }
            first = false;

            sb.append(auth);
         }
      }
      else {
         sb.append("Not granted any authorities");
      }

      return sb.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities;
   }

   /**
    * Sets the roles by which the user can be authenticated.
    *
    * @param authorities
    */
   public void setAuthorities(Set<? extends GrantedAuthority> authorities) {
      this.authorities = authorities;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getPassword() {
      return encodedPassword;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getUsername() {
      return email;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isAccountNonExpired() {
      return accountNonExpired;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isAccountNonLocked() {
      return accountNonLocked;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isCredentialsNonExpired() {
      return credentialsNonExpired;
   }

   /**
    * Clears the user's password.
    */
   public void eraseCredentials() {
      encodedPassword = null;
   }

   /**
    * Returns the user's primary key.
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
   public TeamEntry getTeam() {
      return team;
   }

   /**
    * Sets the user's team association, if any.
    *
    * @param team
    */
   public void setTeam(TeamEntry team) {
      this.team = team;
   }

   /**
    * Returns trus if this user is enabled to use this application. {@inheritDoc}
    */
   @Override
   public boolean isEnabled() {
      return enabled;
   }

   /**
    * Sets a flag to determine if the user is enabled to user this application.
    *
    * @param enabled
    */
   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   /**
    * Returns the user's email address.
    *
    * @return
    */
   public String getEmail() {
      return email;
   }

   /**
    * Sets the user's email address. Never null.
    *
    * @param email
    */
   public void setEmail(String email) {
      assert email != null;
      this.email = email;
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
    * Sets the user's encoded password. Never null.
    *
    * @param encodedPassword
    */
   public void setEncodedPassword(String encodedPassword) {
      assert encodedPassword != null;
      this.encodedPassword = encodedPassword;
   }

   /**
    * Returns the user's first name, or null if none.
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
    * Returns the user's last name.
    *
    * @return
    */
   public String getLastName() {
      return lastName;
   }

   /**
    * Sets the users's last name. Never null.
    *
    * @param lastName
    */
   public void setLastName(String lastName) {
      assert lastName != null;
      this.lastName = lastName;
   }

   /**
    * Returns hte user's IP address, or null if none.
    *
    * @return
    */
   public String getIpAddress() {
      return ipAddress;
   }

   /**
    * Sets the user's IP address.
    *
    * @param ipAddress
    */
   public void setIpAddress(String ipAddress) {
      this.ipAddress = ipAddress;
   }

   /**
    * Returns the user's role.
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

   /**
    * Returns the date and time this user wasfirst persisted.
    *
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Sets the date and time this user was first persisted.
    *
    * @param createdAt
    */
   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
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
    * Sets the user who first persisted this user.
    *
    * @param createdBy
    */
   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
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
    * Sets the date and time this user was last modified.
    *
    * @param modifiedAt
    */
   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   /**
    * Returns the user who last modified this user.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   /**
    * Sets the user who last modified this user.
    *
    * @param modifiedBy
    */
   public void setModifiedBy(UserEntry modifiedBy) {
      this.modifiedBy = modifiedBy;
   }
}
