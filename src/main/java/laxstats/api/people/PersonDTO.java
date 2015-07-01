package laxstats.api.people;

import java.io.Serializable;

import laxstats.query.users.UserEntry;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * {@code PersonDTO} transfers information about a person between the application and domain layers.
 */
public class PersonDTO implements Serializable {
   private static final long serialVersionUID = -7208357748147215787L;

   private final PersonId personId;
   private final String prefix;
   private final String firstName;
   private final String middleName;
   private final String lastName;
   private final String suffix;
   private final String nickname;
   private final String fullName;
   private final Gender gender;
   private final DominantHand dominantHand;
   private final boolean isParentReleased;
   private final LocalDate parentReleaseSentOn;
   private final LocalDate parentReleaseReceivedOn;
   private final LocalDate birthdate;
   private final String college;
   private final UserEntry createdBy;
   private final LocalDateTime createdAt;
   private final UserEntry modifiedBy;
   private final LocalDateTime modifiedAt;

   /**
    * Creates a {@code PersonDTO} with the given information.
    *
    * @param personId
    * @param prefix
    * @param firstName
    * @param middleName
    * @param lastName
    * @param suffix
    * @param nickname
    * @param fullName
    * @param gender
    * @param dominantHand
    * @param isParentReleased
    * @param parentReleaseSentOn
    * @param parentReleaseReceivedOn
    * @param birthdate
    * @param college
    * @param createdBy
    * @param createdAt
    * @param modifiedBy
    * @param modifiedAt
    */
   public PersonDTO(PersonId personId, String prefix, String firstName, String middleName,
      String lastName, String suffix, String nickname, String fullName, Gender gender,
      DominantHand dominantHand, boolean isParentReleased, LocalDate parentReleaseSentOn,
      LocalDate parentReleaseReceivedOn, LocalDate birthdate, String college, UserEntry createdBy,
      LocalDateTime createdAt, UserEntry modifiedBy, LocalDateTime modifiedAt) {
      this.personId = personId;
      this.prefix = prefix;
      this.firstName = firstName;
      this.middleName = middleName;
      this.lastName = lastName;
      this.suffix = suffix;
      this.nickname = nickname;
      this.fullName = fullName;
      this.gender = gender;
      this.dominantHand = dominantHand;
      this.isParentReleased = isParentReleased;
      this.parentReleaseSentOn = parentReleaseSentOn;
      this.parentReleaseReceivedOn = parentReleaseReceivedOn;
      this.birthdate = birthdate;
      this.college = college;
      this.createdBy = createdBy;
      this.createdAt = createdAt;
      this.modifiedBy = modifiedBy;
      this.modifiedAt = modifiedAt;
   }

   /**
    * Creates a {@code PersonDTO} with the given information.
    *
    * @param personId
    * @param prefix
    * @param firstName
    * @param middleName
    * @param lastName
    * @param suffix
    * @param nickname
    * @param fullName
    * @param gender
    * @param dominantHand
    * @param isParentReleased
    * @param parentReleaseSentOn
    * @param parentReleaseReceivedOn
    * @param birthdate
    * @param college
    * @param modifiedBy
    * @param modifiedAt
    */
   public PersonDTO(PersonId personId, String prefix, String firstName, String middleName,
      String lastName, String suffix, String nickname, String fullName, Gender gender,
      DominantHand dominantHand, boolean isParentReleased, LocalDate parentReleaseSentOn,
      LocalDate parentReleaseReceivedOn, LocalDate birthdate, String college, UserEntry modifiedBy,
      LocalDateTime modifiedAt) {
      this(personId, prefix, firstName, middleName, lastName, suffix, nickname, fullName, gender,
         dominantHand, isParentReleased, parentReleaseSentOn, parentReleaseReceivedOn, birthdate,
         college, null, null, modifiedBy, modifiedAt);
   }

   /**
    * Computes and returns a concatenation of the person's full name.
    * 
    * @return
    */
   public String fullName() {
      final StringBuilder sb = new StringBuilder();
      boolean needsSpace = false;

      if (prefix.length() > 0) {
         sb.append(prefix);
         needsSpace = true;
      }
      if (firstName.length() > 0) {
         if (needsSpace) {
            sb.append(" ");
         }
         sb.append(firstName);
         needsSpace = true;
      }
      if (middleName.length() > 0) {
         if (needsSpace) {
            sb.append(" ");
         }
         sb.append(middleName);
         needsSpace = true;
      }
      if (lastName.length() > 0) {
         if (needsSpace) {
            sb.append(" ");
         }
         sb.append(lastName);
         needsSpace = true;
      }
      if (suffix.length() > 0) {
         if (needsSpace) {
            sb.append(" ");
         }
         sb.append(suffix);
      }
      return sb.toString();
   }

   /**
    * Returns the person's aggregate identifier.
    *
    * @return
    */
   public PersonId getPersonId() {
      return personId;
   }

   /**
    * Returns the person's name prefix.
    *
    * @return
    */
   public String getPrefix() {
      return prefix;
   }

   /**
    * Returns the person's first name.
    *
    * @return
    */
   public String getFirstName() {
      return firstName;
   }

   /**
    * Returns the person's middle name.
    *
    * @return
    */
   public String getMiddleName() {
      return middleName;
   }

   /**
    * Returns the person's last name.
    *
    * @return
    */
   public String getLastName() {
      return lastName;
   }

   /**
    * Returns the person's name suffix.
    *
    * @return
    */
   public String getSuffix() {
      return suffix;
   }

   /**
    * Returns the person's nickname.
    *
    * @return
    */
   public String getNickname() {
      return nickname;
   }

   /**
    * Returns the person's full name.
    *
    * @return
    */
   public String getFullName() {
      return fullName;
   }

   /**
    * Returns the person's gender.
    *
    * @return
    */
   public Gender getGender() {
      return gender;
   }

   /**
    * Returns the person's dominant hand.
    *
    * @return
    */
   public DominantHand getDominantHand() {
      return dominantHand;
   }

   /**
    * Returns true if the person has received a parental release, false otherwise.
    *
    * @return
    */
   public boolean isParentReleased() {
      return isParentReleased;
   }

   /**
    * Returns the date on which the person's parental release was sent.
    *
    * @return
    */
   public LocalDate getParentReleaseSentOn() {
      return parentReleaseSentOn;
   }

   /**
    * Returns the date on which the person's parental release was received, or null if none.
    *
    * @return
    */
   public LocalDate getParentReleaseReceivedOn() {
      return parentReleaseReceivedOn;
   }

   /**
    * Returns this person's birthdate, or null if unknown.
    *
    * @return
    */
   public LocalDate getBirthdate() {
      return birthdate;
   }

   /**
    * Returns the person's college, or null if none.
    *
    * @return
    */
   public String getCollege() {
      return college;
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
    * Returns the date and time this pserson was first persisted.
    *
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
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
    * Returns the date and time this person was last modified.
    *
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }
}
