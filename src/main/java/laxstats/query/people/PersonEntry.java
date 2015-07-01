package laxstats.query.people;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import laxstats.api.people.Contactable;
import laxstats.api.people.DominantHand;
import laxstats.api.people.Gender;
import laxstats.api.relationships.RelationshipType;
import laxstats.api.utils.Constants;
import laxstats.query.players.PlayerEntry;
import laxstats.query.relationships.RelationshipEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * {@code PersonEntry} is a query object model of a person.
 */
@Entity
@Table(name = "people", indexes = { @Index(name = "people_idx1", columnList = "lastName"),
   @Index(name = "people_idx2", columnList = "isParentReleased"),
   @Index(name = "people_idx3", columnList = "parentReleaseSentOn"),
   @Index(name = "people_idx4", columnList = "parentReleaseReceivedOn"),
   @Index(name = "people_idx5", columnList = "lastName, firstName") })
public class PersonEntry implements Serializable {
   private static final long serialVersionUID = 7491763841222491421L;

   /**
    * Returns a concatenated string of the given person's full name.
    *
    * @param person
    * @return
    */
   public static String computeFullName(PersonEntry person) {
      final StringBuilder sb = new StringBuilder();
      boolean needsConjunction = false;

      if (person.getPrefix() != null) {
         sb.append(person.getPrefix());
         needsConjunction = true;
      }
      if (person.getFirstName() != null) {
         if (needsConjunction) {
            sb.append(" ");
         }
         sb.append(person.getFirstName());
         needsConjunction = true;
      }
      if (person.getMiddleName() != null) {
         if (needsConjunction) {
            sb.append(" ");
         }
         sb.append(person.getMiddleName());
         needsConjunction = true;
      }
      if (person.getLastName() != null) {
         if (needsConjunction) {
            sb.append(" ");
         }
         sb.append(person.getLastName());
      }
      if (person.getSuffix() != null) {
         sb.append(", ").append(person.getSuffix());
      }
      return sb.toString();
   }

   @Id
   @Column(length = Constants.MAX_LENGTH_DATABASE_KEY)
   private String id;

   @Column(length = Constants.MAX_LENGTH_NAME_PREFIX_OR_SUFFIX)
   private String prefix;

   @Column(length = Constants.MAX_LENGTH_FIRST_OR_MIDDLE_NAME)
   private String firstName;

   @Column(length = Constants.MAX_LENGTH_FIRST_OR_MIDDLE_NAME)
   private String middleName;

   @Column(length = Constants.MAX_LENGTH_LAST_NAME, nullable = false)
   private String lastName;

   @Column(length = Constants.MAX_LENGTH_NAME_PREFIX_OR_SUFFIX)
   private String suffix;

   @Column(length = Constants.MAX_LENGTH_FIRST_OR_MIDDLE_NAME)
   private String nickname;

   @Column(length = 100)
   private String fullName;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING)
   private Gender gender;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING)
   private DominantHand dominantHand;

   private boolean isParentReleased = false;

   @Type(type = Constants.LOCAL_DATE_DATABASE_TYPE)
   private LocalDate parentReleaseSentOn;

   @Type(type = Constants.LOCAL_DATE_DATABASE_TYPE)
   private LocalDate parentReleaseReceivedOn;

   @Type(type = Constants.LOCAL_DATE_DATABASE_TYPE)
   private LocalDate birthdate;

   @Column(length = 100)
   private String college;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   @OneToMany(cascade = { CascadeType.ALL })
   @JoinColumn(name = "person")
   private final List<AddressEntry> addresses = new ArrayList<>();

   @OneToMany(cascade = { CascadeType.ALL })
   @JoinColumn(name = "person")
   private final List<ContactEntry> contacts = new ArrayList<>();

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
   private final Set<RelationshipEntry> childRelationships = new HashSet<>();

   @OneToMany(cascade = CascadeType.ALL, mappedBy = "child")
   private final Set<RelationshipEntry> parentRelationships = new HashSet<>();

   @OneToMany(cascade = { CascadeType.ALL })
   @JoinColumn(name = "person")
   private final Set<PlayerEntry> playedSeasons = new HashSet<>();

   /**
    * Returns true if the given person and relation type already exist in the person's set of parent
    * relationships, false otherwise. This test is recursive and can prevent a cycle from being
    * created when adding a new parental relationship..
    *
    * @param sample
    * @param type
    * @return
    */
   public boolean ancestorsInclude(PersonEntry sample, RelationshipType type) {
      final Iterator<PersonEntry> iter = getParents(type).iterator();
      while (iter.hasNext()) {
         final PersonEntry each = iter.next();
         if (each.equals(sample)) {
            return true;
         }
         if (each.ancestorsInclude(sample, type)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Returns a set of child relationships for the given relationship type.
    *
    * @param type
    * @return
    */
   public Set<PersonEntry> getChildren(RelationshipType type) {
      final Set<PersonEntry> result = new HashSet<>();
      for (final RelationshipEntry each : childRelationships) {
         if (type == null || each.getType().equals(type)) {
            result.add(each.getChild());
         }
      }
      return result;
   }

   /**
    * Returns a set of parent relationships for the given relationship type.
    *
    * @param type
    * @return
    */
   public Set<PersonEntry> getParents(RelationshipType type) {
      final Set<PersonEntry> result = new HashSet<>();
      for (final RelationshipEntry each : parentRelationships) {
         if (type == null || each.getType().equals(type)) {
            result.add(each.getParent());
         }
      }
      return result;
   }

   /**
    * Adds the given child relationship.
    *
    * @param relationship
    */
   public void addChildRelationship(RelationshipEntry relationship) {
      relationship.setParent(this);
      childRelationships.add(relationship);
   }

   /**
    * Adds the given parent relationship.
    *
    * @param relationship
    */
   public void addParentRelationship(RelationshipEntry relationship) {
      relationship.setChild(this);
      parentRelationships.add(relationship);
   }

   /**
    * Returns the address matching the given identifier, or null if no matching address is found.
    *
    * @param id
    * @return
    */
   public AddressEntry getAddress(String id) {
      for (final AddressEntry each : addresses) {
         if (each.getId().equals(id)) {
            return each;
         }
      }
      return null;
   }

   /**
    * Returns the contact matching the given identifier, or null if no matching is found.
    *
    * @param id
    * @return
    */
   public ContactEntry getContact(String id) {
      for (final ContactEntry each : contacts) {
         if (each.getId().equals(id)) {
            return each;
         }
      }
      return null;
   }

   /**
    * Returns the person's primary address. If there is no primary address, the method returns the
    * first address in the collection or <code>null</code> if the collection is empty.
    *
    * @return The person's primary address.
    */
   public AddressEntry primaryAddress() {
      final List<Contactable> list = new ArrayList<>(addresses);
      return (AddressEntry)getPrimaryContactOrAddress(list);
   }

   /**
    * Returns the person's primary contact. If there is no primary contact, the method returns the
    * first contact in the collection or null if the collection is empty.
    *
    * @return The person's primary contact.
    */
   public ContactEntry primaryContact() {
      final List<Contactable> list = new ArrayList<>(contacts);
      return (ContactEntry)getPrimaryContactOrAddress(list);
   }

   private Contactable getPrimaryContactOrAddress(List<Contactable> c) {
      Contactable primary = null;
      Contactable first = null;
      int i = 0;
      for (final Contactable each : c) {
         if (i == 0) {
            first = each;
         }
         if (each.isPrimary()) {
            primary = each;
         }
         i++;
      }
      return primary != null ? primary : first;
   }

   /**
    * Adds the given contact to the person's set.
    *
    * @param address
    */
   public void addAddress(AddressEntry address) {
      address.setPerson(this);
      addresses.add(address);
   }

   /**
    * Removes the given address from the person's set. This will create an orphan address suitable
    * for deletion in the unit of work.
    *
    * @param address
    */
   public void removeAddress(AddressEntry address) {
      address.setPerson(null);
      addresses.remove(address);
   }

   /**
    * Adds the given contact to the person's set.
    *
    * @param contact
    */
   public void addContact(ContactEntry contact) {
      contact.setPerson(this);
      contacts.add(contact);
   }

   /**
    * Removes the given contact from the person's set. This will create an orphan contact suitable
    * for deletion in the unit of work.
    *
    * @param contact
    */
   public void removeContact(ContactEntry contact) {
      contact.setPerson(null);
      contacts.remove(contact);
   }

   /**
    * Returns the primary key.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the primary key.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the person's name prefix, or null if not used or known.
    *
    * @return
    */
   public String getPrefix() {
      return prefix;
   }

   /**
    * Sets the person's name prefix. Use null if not used or known.
    *
    * @param prefix
    */
   public void setPrefix(String prefix) {
      this.prefix = prefix;
   }

   /**
    * Returns the person's first name, or null if not known.
    *
    * @return
    */
   public String getFirstName() {
      return firstName;
   }

   /**
    * Sets the person's first name, Use null if not known.
    *
    * @param firstName
    */
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   /**
    * Returns the person's middle name, or null if not known.
    *
    * @return
    */
   public String getMiddleName() {
      return middleName;
   }

   /**
    * Returns the person's middle name. Use null if not known or used.
    *
    * @param middleName
    */
   public void setMiddleName(String middleName) {
      this.middleName = middleName;
   }

   /**
    * Returns the person's last name. Never null.
    *
    * @return
    */
   public String getLastName() {
      return lastName;
   }

   /**
    * Sets the person's last name. Must not be null.
    *
    * @param lastName
    */
   public void setLastName(String lastName) {
      assert lastName != null;
      this.lastName = lastName;
   }

   /**
    * Returns the persons's name suffix, or null if not used.
    *
    * @return
    */
   public String getSuffix() {
      return suffix;
   }

   /**
    * Sets the person's name suffix. Use null if not used.
    *
    * @param suffix
    */
   public void setSuffix(String suffix) {
      this.suffix = suffix;
   }

   /**
    * Returns the person's nickname, or null if not known.
    *
    * @return
    */
   public String getNickname() {
      return nickname;
   }

   /**
    * Sets the person's nickname. Use null if not known.
    *
    * @param nickname
    */
   public void setNickname(String nickname) {
      this.nickname = nickname;
   }

   /**
    * Returns this person's full name. A concatenated full name will be computed and returned if not
    * previously set.
    *
    * @return
    */
   public String getFullName() {
      if (fullName == null) {
         setFullName(PersonEntry.computeFullName(this));
      }
      return fullName;
   }

   /**
    * Sets this person's full name.
    *
    * @param fullName
    */
   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   /**
    * Returns this persons gender, or null if not known.
    *
    * @return
    */
   public Gender getGender() {
      return gender;
   }

   /**
    * Sets this person's gender.
    *
    * @param gender
    */
   public void setGender(Gender gender) {
      this.gender = gender;
   }

   /**
    * Returns this person's dominant hand, or null if not known.
    *
    * @return
    */
   public DominantHand getDominantHand() {
      return dominantHand;
   }

   /**
    * Sets this person's dominant hand. Use null if not known.
    *
    * @param dominantHand
    */
   public void setDominantHand(DominantHand dominantHand) {
      this.dominantHand = dominantHand;
   }

   /**
    * Returns true if this person has a parental release, false otherwise.
    *
    * @return
    */
   public boolean isParentReleased() {
      return isParentReleased;
   }

   /**
    * Sets a flag to determine if the person has a parental release.
    *
    * @param isParentReleased
    */
   public void setParentReleased(boolean isParentReleased) {
      this.isParentReleased = isParentReleased;
   }

   /**
    * Returns the date this person's parental release request was sent, or null.
    *
    * @return
    */
   public LocalDate getParentReleaseSentOn() {
      return parentReleaseSentOn;
   }

   /**
    * Sets the date this person's parental release request was sent. Use null if not used or known.
    *
    * @param parentReleaseSentOn
    */
   public void setParentReleaseSentOn(LocalDate parentReleaseSentOn) {
      this.parentReleaseSentOn = parentReleaseSentOn;
   }

   /**
    * Returns the date this person's parental release was received, or null.
    *
    * @return
    */
   public LocalDate getParentReleaseReceivedOn() {
      return parentReleaseReceivedOn;
   }

   /**
    * Sets the date this person's parental release was received. Use null if not used or known.
    *
    * @param parentReleaseReceivedOn
    */
   public void setParentReleaseReceivedOn(LocalDate parentReleaseReceivedOn) {
      this.parentReleaseReceivedOn = parentReleaseReceivedOn;
   }

   /**
    * Returns this person's birth date, or null if not known.
    *
    * @return
    */
   public LocalDate getBirthdate() {
      return birthdate;
   }

   /**
    * Sets this person's birth date. Use null if not knoen.
    *
    * @param birthdate
    */
   public void setBirthdate(LocalDate birthdate) {
      this.birthdate = birthdate;
   }

   /**
    * Returns the name of this person's college, or null if not known.
    *
    * @return
    */
   public String getCollege() {
      return college;
   }

   /**
    * Sets the name of this person's college. Use null if not known.
    *
    * @param college
    */
   public void setCollege(String college) {
      this.college = college;
   }

   /**
    * Returns the list of addresses.
    *
    * @return
    */
   public List<AddressEntry> getAddresses() {
      return addresses;
   }

   /**
    * Returns the list of contacts.
    *
    * @return
    */
   public List<ContactEntry> getContacts() {
      return contacts;
   }

   /**
    * Returns the set of child relationships.
    *
    * @return
    */
   public Set<RelationshipEntry> getChildRelationships() {
      return childRelationships;
   }

   /**
    * Returns the set of parent relationships.
    *
    * @return
    */
   public Set<RelationshipEntry> getParentRelationships() {
      return parentRelationships;
   }

   /**
    * Returns the set of played seasons for this person.
    *
    * @return
    */
   public Set<PlayerEntry> getPlayedSeasons() {
      return playedSeasons;
   }

   /**
    * Returns the date and time this persos was first persisted.
    *
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Sets the date and time this person was first persisted.
    *
    * @param createdAt
    */
   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   /**
    * Returns the user who first persisted this person.
    *
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Sets the user who first persisted this person.
    *
    * @param createdBy
    */
   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
   }

   /**
    * Returns the date and time this person was last modified.
    *
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Sets the date and time this person was last modified.
    *
    * @param modifiedAt
    */
   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   /**
    * Returns the user who last modified this person.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   /**
    * Sets the user who last modified this person.
    *
    * @param modifiedBy
    */
   public void setModifiedBy(UserEntry modifiedBy) {
      this.modifiedBy = modifiedBy;
   }
}
