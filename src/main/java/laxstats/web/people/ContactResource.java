package laxstats.web.people;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.people.ContactMethod;
import laxstats.api.utils.Constants;

/**
 * {@code ContactResource} represents a contact resource for remote clients.
 */
public class ContactResource {

   private String id;

   @NotNull
   private String personId;

   @NotNull
   private ContactMethod method;

   @NotNull
   @Size(min = Constants.MIN_LENGTH_STRING, max = Constants.MAX_LENGTH_CONTACT_VALUE)
   private String value;

   private boolean isPrimary = false;
   private boolean doNotUse = false;

   /**
    * Creates a {@code ContactResource} with the given information.
    *
    * @param id
    * @param personId
    * @param method
    * @param value
    * @param isPrimary
    * @param doNotUse
    */
   public ContactResource(String id, String personId, ContactMethod method, String value,
      boolean isPrimary, boolean doNotUse) {
      this.id = id;
      this.personId = personId;
      this.method = method;
      this.value = value;
      this.isPrimary = isPrimary;
      this.doNotUse = doNotUse;
   }

   /**
    * Creates an empty {@code ContactResource} for internal use.
    */
   public ContactResource() {
   }

   /**
    * Returns the unique identifier of this contact, or null if the contact has not been persisted.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the unique identifier of this contact. Use null if this is a new contact.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the identifier of the associated person. Never null.
    *
    * @return
    */
   public String getPersonId() {
      return personId;
   }

   /**
    * Sets the identifier of the associated person. Must not be null.
    *
    * @param personId
    */
   public void setPersonId(String personId) {
      assert personId != null;
      this.personId = personId;
   }

   /**
    * Returns true if this contact is obsolete, false otherwise.
    *
    * @return
    */
   public boolean isDoNotUse() {
      return doNotUse;
   }

   /**
    * Sets whether this contact is obsolete. Defaults to false.
    *
    * @param doNotUse
    */
   public void setDoNotUse(boolean doNotUse) {
      this.doNotUse = doNotUse;
   }

   /**
    * Returns true if this is the peron's primary contact, false otherwise.
    *
    * @return
    */
   public boolean isPrimary() {
      return isPrimary;
   }

   /**
    * Sets whether this is the person's primary contact. Defaults to false.
    *
    * @param isPrimary
    */
   public void setPrimary(boolean isPrimary) {
      this.isPrimary = isPrimary;
   }

   /**
    * Returns the contact method. Never null.
    *
    * @return
    */
   public ContactMethod getMethod() {
      return method;
   }

   /**
    * Sets the contact method. Mustnot be null.
    *
    * @param method
    */
   public void setMethod(ContactMethod method) {
      assert method != null;
      this.method = method;
   }

   /**
    * Returns the contact value. Never null.
    *
    * @return
    */
   public String getValue() {
      return value;
   }

   /**
    * ets he value of the contact. Must not be null.
    *
    * @param value
    */
   public void setValue(String value) {
      assert value != null;
      this.value = value;
   }

}
