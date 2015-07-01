package laxstats.api.people;

import java.io.Serializable;

import laxstats.query.people.PersonEntry;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

/**
 * {@code ContactDTO} transfers information about a contact between the application and domain
 * layers.
 */
public class ContactDTO implements Serializable {
   private static final long serialVersionUID = 6585564080550274970L;

   private final String id;
   private final PersonEntry person;
   private final ContactMethod method;
   private final String value;
   private final boolean isPrimary;
   private final boolean doNotUse;
   private final LocalDateTime createdAt;
   private final UserEntry createdBy;
   private final LocalDateTime modifiedAt;
   private final UserEntry modifiedBy;

   /**
    * Creates a {@code ContactDTO} with the given information.
    *
    * @param id
    * @param person
    * @param method
    * @param value
    * @param isPrimary
    * @param doNotUse
    * @param createdAt
    * @param createdBy
    * @param modifiedAt
    * @param modifiedBy
    */
   public ContactDTO(String id, PersonEntry person, ContactMethod method, String value,
      boolean isPrimary, boolean doNotUse, LocalDateTime createdAt, UserEntry createdBy,
      LocalDateTime modifiedAt, UserEntry modifiedBy) {
      this.id = id;
      this.person = person;
      this.method = method;
      this.value = value;
      this.isPrimary = isPrimary;
      this.doNotUse = doNotUse;
      this.createdAt = createdAt;
      this.createdBy = createdBy;
      this.modifiedAt = modifiedAt;
      this.modifiedBy = modifiedBy;
   }

   /**
    * Creates a {@code ContactDTO} with the given information.
    *
    * @param id
    * @param person
    * @param method
    * @param value
    * @param isPrimary
    * @param doNotUse
    * @param modifiedAt
    * @param modifiedBy
    */
   public ContactDTO(String id, PersonEntry person, ContactMethod method, String value,
      boolean isPrimary, boolean doNotUse, LocalDateTime modifiedAt, UserEntry modifiedBy) {
      this(id, person, method, value, isPrimary, doNotUse, null, null, modifiedAt, modifiedBy);
   }

   /**
    * Returns the contact primary key.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Returns the associated person.
    *
    * @return
    */
   public PersonEntry getPerson() {
      return person;
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
    * Returns the contact value, e.g. the email address or telephone number.
    *
    * @return
    */
   public String getValue() {
      return value;
   }

   /**
    * Returns true if this is the person's primary contact, false otherwise.
    *
    * @return
    */
   public boolean isPrimary() {
      return isPrimary;
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
    * Returns the date and time this contact was first persisted.
    *
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Returns ths user who first persisted this contact.
    *
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Returns the date and time this contact was last modified.
    *
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Returns the user who last modified this contact.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }
}
