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

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import laxstats.api.users.UserRole;
import laxstats.query.teams.TeamEntry;

@Entity
@Table(name = "users", indexes = { @Index(name = "users_idx1", columnList = "lastName") },
   uniqueConstraints = { @UniqueConstraint(name = "users_uk1", columnNames = { "email" }) })
public class UserEntry implements UserDetails {
   private static final long serialVersionUID = 1L;

   private static SortedSet<GrantedAuthority>
      sortAuthorities(Collection<? extends GrantedAuthority> authorities)
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

         return g1.getAuthority().compareTo(g2.getAuthority());
      }
   }

   /*---------- Instance properties ----------*/

   @Id
   @org.springframework.data.annotation.Id
   @Column(length = 36)
   private String id;

   @ManyToOne
   private TeamEntry team;

   @Column(nullable = false)
   private String email;

   @Column(length = 100, nullable = false)
   private String encodedPassword;

   @Column(nullable = false)
   private boolean enabled = false;

   @Column(length = 20)
   private String firstName;

   @Column(length = 30, nullable = false)
   private String lastName;

   @Column(length = 50)
   private String ipAddress;

   @Enumerated(EnumType.STRING)
   @Column(length = 20)
   private UserRole role;

   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   private boolean accountNonExpired;
   private boolean accountNonLocked;
   private boolean credentialsNonExpired;

   @Transient
   private Set<? extends GrantedAuthority> authorities;

   /*---------- Constructors ----------*/

   public UserEntry(String username, String password) {
      this(username, password, true, true, true, true, new HashSet<GrantedAuthority>());
   }

   public UserEntry(String username, String password,
      Collection<? extends GrantedAuthority> authorities) {

      this(username, password, true, true, true, true, authorities);
   }

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

   public UserEntry() {
   }

   /*---------- Methods ----------*/

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

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof UserEntry) {
         final UserEntry that = (UserEntry)obj;
         return getUsername().equals(that.getUsername());
      }
      return false;
   }

   @Override
   public int hashCode() {
      return email.hashCode();
   }

   @Override
   public String toString() {
      final StringBuilder sb = new StringBuilder();
      sb.append(super.toString()).append(": ");
      sb.append("Username: ").append(email).append("; ");
      sb.append("Password: [PROTECTED]; ");
      sb.append("Enabled: ").append(enabled).append("; ");
      sb.append("AccountNonExpired: ").append(accountNonExpired).append("; ");
      sb.append("credentialsNonExpired: ").append(credentialsNonExpired).append("; ");
      sb.append("AccountNonLocked: ").append(accountNonLocked).append("; ");

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

   /*---------- UserDetails ----------*/

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities;
   }

   public void setAuthorities(Set<? extends GrantedAuthority> authorities) {
      this.authorities = authorities;
   }

   @Override
   public String getPassword() {
      return encodedPassword;
   }

   @Override
   public String getUsername() {
      return email;
   }

   @Override
   public boolean isAccountNonExpired() {
      return accountNonExpired;
   }

   @Override
   public boolean isAccountNonLocked() {
      return accountNonLocked;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return credentialsNonExpired;
   }

   public void eraseCredentials() {
      encodedPassword = null;
   }

   /*---------- Getter/Setters ----------*/

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public TeamEntry getTeam() {
      return team;
   }

   public void setTeam(TeamEntry team) {
      this.team = team;
   }

   @Override
   public boolean isEnabled() {
      return enabled;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getEncodedPassword() {
      return encodedPassword;
   }

   public void setEncodedPassword(String encodedPassword) {
      this.encodedPassword = encodedPassword;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getIpAddress() {
      return ipAddress;
   }

   public void setIpAddress(String ipAddress) {
      this.ipAddress = ipAddress;
   }

   public UserRole getRole() {
      return role;
   }

   public void setRole(UserRole role) {
      this.role = role;
   }

   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   public UserEntry getCreatedBy() {
      return createdBy;
   }

   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
   }

   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   public void setModifiedBy(UserEntry modifiedBy) {
      this.modifiedBy = modifiedBy;
   }
}
