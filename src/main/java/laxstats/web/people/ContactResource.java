package laxstats.web.people;

import laxstats.api.people.ContactMethod;

public interface ContactResource {

   /**
    * Returns the unique identifier of this contact, or null if the contact has not been persisted.
    *
    * @return
    */
   public String getId();

   /**
    * Sets the unique identifier of this contact. Use null if this is a new contact.
    *
    * @param id
    */
   public void setId(String id);

   /**
    * Returns the identifier of the associated person. Never null.
    *
    * @return
    */
   public String getPersonId();

   /**
    * Sets the identifier of the associated person. Must not be null.
    *
    * @param personId
    */
   public void setPersonId(String personId);

   /**
    * Returns true if this contact is obsolete, false otherwise.
    *
    * @return
    */
   public boolean isDoNotUse();

   /**
    * Sets whether this contact is obsolete. Defaults to false.
    *
    * @param doNotUse
    */
   public void setDoNotUse(boolean doNotUse);

   /**
    * Returns true if this is the peron's primary contact, false otherwise.
    *
    * @return
    */
   public boolean isPrimary();

   /**
    * Sets whether this is the person's primary contact. Defaults to false.
    *
    * @param isPrimary
    */
   public void setPrimary(boolean isPrimary);

   /**
    * Returns the contact method. Never null.
    *
    * @return
    */
   public ContactMethod getMethod();

   /**
    * Sets the contact method. Mustnot be null.
    *
    * @param method
    */
   public void setMethod(ContactMethod method);

   /**
    * Returns the contact value. Never null.
    *
    * @return
    */
   public String getValue();

   /**
    * Sets the value of the contact. Must not be null.
    *
    * @param value
    */
   public void setValue(String value);

}
