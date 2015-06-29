package laxstats.query.users;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * {@code UserAuthority} encapsulates data for application-level authentication.
 */
public class UserAuthority implements GrantedAuthority {
   private static final long serialVersionUID = -7859908366978143461L;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY)
   @JsonIgnore
   @Id
   private User user;

   @NotNull
   @Id
   private String authority;

   /**
    * Returns the user.
    *
    * @return
    */
   public User getUser() {
      return user;
   }

   /**
    * Sets the user
    *
    * @param user
    */
   public void setUser(User user) {
      this.user = user;
   }

   /**
    * Returns the user's security level. {@inheritDoc}
    */
   @Override
   public String getAuthority() {
      return authority;
   }

   /**
    * Sets the security level for this user.
    *
    * @param authority
    */
   public void setAuthority(String authority) {
      this.authority = authority;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof UserAuthority)) {
         return false;
      }

      final UserAuthority that = (UserAuthority)obj;
      return that.getAuthority() == getAuthority() || that.getAuthority().equals(getAuthority());
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return getAuthority() == null ? 0 : getAuthority().hashCode();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return getClass().getSimpleName() + ": " + getAuthority();
   }
}
