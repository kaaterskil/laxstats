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
public class ContactForm implements Serializable, ContactResource {
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
   public String getPersonId() {
      return personId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setPersonId(String personId) {
      assert personId != null;
      this.personId = personId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isDoNotUse() {
      return doNotUse;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setDoNotUse(boolean doNotUse) {
      this.doNotUse = doNotUse;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isPrimary() {
      return isPrimary;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setPrimary(boolean isPrimary) {
      this.isPrimary = isPrimary;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ContactMethod getMethod() {
      return method;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setMethod(ContactMethod method) {
      assert method != null;
      this.method = method;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getValue() {
      return value;
   }

   /**
    * {@inheritDoc}
    */
   @Override
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
