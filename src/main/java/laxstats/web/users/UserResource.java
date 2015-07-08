package laxstats.web.users;

import laxstats.api.users.UserRole;

public interface UserResource {

   /**
    * Returns the user's primary key, or null if the user has not been persisted.
    *
    * @return
    */
   public String getId();

   /**
    * Sets the user's primary key. Use null if the user has not been persisted.
    *
    * @param id
    */
   public void setId(String id);

   /**
    * Returns the identifier of the user's team association, or null if none.
    *
    * @return
    */
   public String getTeamId();

   /**
    * Sets the identifier of the user's team association. Use null for none.
    *
    * @param teamId
    */
   public void setTeamId(String teamId);

   /**
    * Returns the user's email address. Never null.
    *
    * @return
    */
   public String getEmail();

   /**
    * Sets the user's email address. Must not be null.
    *
    * @param email
    */
   public void setEmail(String email);

   /**
    * Returns the user's password. Never null.
    *
    * @return
    */
   public String getPassword();

   /**
    * Sets the user's password. Must not be null.
    *
    * @param password
    */
   public void setPassword(String password);

   /**
    * Returns true if the user is enabled to user this application, false otherwise.
    *
    * @return
    */
   public boolean isEnabled();

   /**
    * Sets a flag to determine if the user is enabled to use this application. Defaults to true.
    *
    * @param enabled
    */
   public void setEnabled(boolean enabled);

   /**
    * Returns the user's first name, or null if not provided.
    *
    * @return
    */
   public String getFirstName();

   /**
    * Sets the user's first name. Use null for none or unknown.
    *
    * @param firstName
    */
   public void setFirstName(String firstName);

   /**
    * Returns the user's last name. Never null.
    *
    * @return
    */
   public String getLastName();

   /**
    * Sets the user's last name. Must not be null.
    *
    * @param lastName
    */
   public void setLastName(String lastName);

   /**
    * Returns the user's role. Never null.
    *
    * @return
    */
   public UserRole getRole();

   /**
    * Sets the user's role. Must not be null.
    *
    * @param role
    */
   public void setRole(UserRole role);

}
