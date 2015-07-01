package laxstats.domain.people;

import laxstats.api.people.ContactChanged;
import laxstats.api.people.ContactDTO;
import laxstats.api.people.ContactMethod;
import laxstats.api.people.Contactable;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedEntity;

/**
 * {@code Contact} represents a domain object model of a contact, such as a telephone number or
 * email address.
 */
public class Contact extends AbstractAnnotatedEntity implements Contactable {
   private String id;
   private String personId;
   private ContactMethod method;
   private String value;
   private boolean isPrimary;
   private boolean doNotUse;

   /**
    * Updates and persists canges to this contact from information contained in the given event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(ContactChanged event) {
      if (!id.equals(event.getContact().getId())) {
         return;
      }

      final ContactDTO dto = event.getContact();
      method = dto.getMethod();
      value = dto.getValue();
      isPrimary = dto.isPrimary();
      doNotUse = dto.isDoNotUse();
   }

   /**
    * Returns the unique identifier of this contact.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the unique identifier of this contact.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the identifier of the associated person.
    *
    * @return
    */
   public String getPersonId() {
      return personId;
   }

   /**
    * Sets the identifier of the associated person.
    *
    * @param personId
    */
   public void setPersonId(String personId) {
      this.personId = personId;
   }

   /**
    * Returns the contact method.
    *
    * @return
    */
   public ContactMethod getMethod() {
      return method;
   }

   /**
    * Sets the method of this contact. Never null.
    *
    * @param method
    */
   public void setMethod(ContactMethod method) {
      this.method = method;
   }

   /**
    * Returns the value of this contact.
    *
    * @return
    */
   public String getValue() {
      return value;
   }

   /**
    * Sets the value of this contact.
    *
    * @param value
    */
   public void setValue(String value) {
      this.value = value;
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
}
