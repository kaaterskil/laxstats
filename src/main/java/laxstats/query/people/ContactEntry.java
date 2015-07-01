package laxstats.query.people;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.api.people.ContactMethod;
import laxstats.api.people.Contactable;
import laxstats.api.utils.Constants;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * {@code ContactEntry} is a query object model of a person's contact.
 */
@Entity
@Table(name = "contacts",
         indexes = { @Index(name = "contacts_idx1", columnList = "method"),
            @Index(name = "contacts_idx2", columnList = "isPrimary"),
            @Index(name = "contacts_idx3", columnList = "doNotUse") },
         uniqueConstraints = { @UniqueConstraint(name = "contacts_uk1", columnNames = { "person_id",
            "isPrimary" }) })
public class ContactEntry implements Contactable, Serializable {
   private static final long serialVersionUID = -572352451858382338L;

   @Id
   @Column(length = Constants.MAX_LENGTH_DATABASE_KEY)
   private String id;

   @ManyToOne
   @JoinColumn(name = "person_id")
   private PersonEntry person;

   @Enumerated(EnumType.STRING)
   @Column(length = 20, nullable = false)
   private ContactMethod method;

   @Column(length = Constants.MAX_LENGTH_CONTACT_VALUE, nullable = false)
   private String value;

   private boolean isPrimary = false;

   private boolean doNotUse = false;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   /**
    * Returns an HTML string of the contact. If the contact method is ContactMethod.EMAIL, the value
    * is enclosed in a link tag.
    *
    * @return
    */
   public String getHtml() {
      final StringBuilder sb = new StringBuilder();
      if (method.equals(ContactMethod.EMAIL)) {
         sb.append("<a href='mailto:").append(value).append("'>");
         sb.append(value);
         sb.append("</a>");
      }
      else {
         sb.append(value);
      }
      return sb.toString();
   }

   /**
    * Returns the contact's primary key.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the contact's primary key.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
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
    * Sets the contact method.
    *
    * @param method
    */
   public void setMethod(ContactMethod method) {
      assert method != null;
      this.method = method;
   }

   /**
    * Returns the associated person. Never null.
    *
    * @return
    */
   public PersonEntry getPerson() {
      return person;
   }

   /**
    * Sets the associated person.
    *
    * @param person
    */
   public void setPerson(PersonEntry person) {
      assert person != null;
      this.person = person;
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
    * Sets the value of this contact.
    *
    * @param value
    */
   public void setValue(String value) {
      assert value != null;
      this.value = value;
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
    * Returns the date and time this contact was frst persisted.
    *
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Sets the date and time this contact was first persisted.
    *
    * @param createdAt
    */
   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   /**
    * Returns the user who first persisted this contact.
    *
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Sets the user who first persisted this contact.
    *
    * @param createdBy
    */
   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
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
    * Sets the date and time this contact was last modified.
    *
    * @param modifiedAt
    */
   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   /**
    * Returns the user who last modified this contact.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   /**
    * Sets the user who last modified this contact.
    *
    * @param modifiedBy
    */
   public void setModifiedBy(UserEntry modifiedBy) {
      this.modifiedBy = modifiedBy;
   }
}
