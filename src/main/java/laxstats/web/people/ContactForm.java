package laxstats.web.people;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.people.ContactMethod;
import laxstats.api.utils.Constants;

/**
 * {@code ContactForm} contains user-defined information with which to create or update a contact.
 */
public class ContactForm implements Serializable {
   private static final long serialVersionUID = 5552593715712624548L;

   private String id;

   @NotNull
   private String personId;

   @NotNull
   private ContactMethod method;

   @NotNull
   @Size(min = 3, max = Constants.MAX_LENGTH_CONTACT_VALUE)
   private String value;

   private boolean isPrimary = false;

   private boolean doNotUse = false;

   private List<ContactMethod> contactMethods;

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

   public List<ContactMethod> getContactMethods() {
      if (contactMethods == null) {
         contactMethods = Arrays.asList(ContactMethod.values());
      }
      return contactMethods;
   }

}
