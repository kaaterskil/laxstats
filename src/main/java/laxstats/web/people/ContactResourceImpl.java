package laxstats.web.people;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.people.ContactMethod;
import laxstats.api.utils.Constants;

/**
 * {@code ContactResource} represents a contact resource for remote clients.
 */
public class ContactResourceImpl implements ContactResource {

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
   public ContactResourceImpl(String id, String personId, ContactMethod method, String value,
      boolean isPrimary, boolean doNotUse) {
      this.id = id;
      this.personId = personId;
      this.method = method;
      this.value = value;
      this.isPrimary = isPrimary;
      this.doNotUse = doNotUse;
   }

   /**
    * Creates an empty {@code ContactResource}.
    */
   public ContactResourceImpl() {
   }

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

}
