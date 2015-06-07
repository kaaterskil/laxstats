package laxstats.query.users;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserAuthority implements GrantedAuthority {
   private static final long serialVersionUID = 1L;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY)
   @JsonIgnore
   @Id
   private User user;

   @NotNull
   @Id
   private String authority;

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   @Override
   public String getAuthority() {
      return authority;
   }

   public void setAuthority(String authority) {
      this.authority = authority;
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof UserAuthority)) {
         return false;
      }

      final UserAuthority that = (UserAuthority)obj;
      return that.getAuthority() == getAuthority() || that.getAuthority().equals(getAuthority());
   }

   @Override
   public int hashCode() {
      return getAuthority() == null ? 0 : getAuthority().hashCode();
   }

   @Override
   public String toString() {
      return getClass().getSimpleName() + ": " + getAuthority();
   }
}
