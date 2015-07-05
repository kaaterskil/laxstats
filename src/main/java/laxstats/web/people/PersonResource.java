package laxstats.web.people;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.people.DominantHand;
import laxstats.api.people.Gender;
import laxstats.api.utils.Constants;

/**
 * {@code PersonResource} represents a person resource for remote clients.
 */
public class PersonResource {

   /**
    * Returns a concatenated string of the given person's full name.
    *
    * @param form
    * @return
    */
   public static String fullName(PersonResource form) {
      final StringBuilder sb = new StringBuilder();
      boolean needsSpace = false;

      if (form.prefix.length() > 0) {
         sb.append(form.prefix);
         needsSpace = true;
      }
      if (form.firstName.length() > 0) {
         if (needsSpace) {
            sb.append(" ");
         }
         sb.append(form.firstName);
         needsSpace = true;
      }
      if (form.middleName.length() > 0) {
         if (needsSpace) {
            sb.append(" ");
         }
         sb.append(form.middleName);
         needsSpace = true;
      }
      if (form.lastName.length() > 0) {
         if (needsSpace) {
            sb.append(" ");
         }
         sb.append(form.lastName);
         needsSpace = true;
      }
      if (form.suffix.length() > 0) {
         if (needsSpace) {
            sb.append(" ");
         }
         sb.append(form.suffix);
      }
      return sb.toString();
   }

   private String id;

   @Size(max = Constants.MAX_LENGTH_NAME_PREFIX_OR_SUFFIX)
   private String prefix;

   @Size(max = Constants.MAX_LENGTH_FIRST_OR_MIDDLE_NAME)
   private String firstName;

   @Size(max = Constants.MAX_LENGTH_FIRST_OR_MIDDLE_NAME)
   private String middleName;

   @NotNull
   @Size(min = 2, max = Constants.MAX_LENGTH_LAST_NAME)
   private String lastName;

   @Size(max = Constants.MAX_LENGTH_NAME_PREFIX_OR_SUFFIX)
   private String suffix;

   @Size(max = Constants.MAX_LENGTH_FIRST_OR_MIDDLE_NAME)
   private String nickname;

   private Gender gender;
   private DominantHand dominantHand;
   private String birthdate;
   private boolean released = false;
   private String parentReleaseSentOn;
   private String parentReleaseReceivedOn;

   @Size(max = Constants.MAX_LENGTH_TITLE)
   private String college;

   /**
    * Creates a {@code PersonResource} with the given information.
    *
    * @param id
    * @param prefix
    * @param firstName
    * @param middleName
    * @param lastName
    * @param suffix
    * @param nickname
    * @param gender
    * @param dominantHand
    * @param birthdate
    * @param released
    * @param parentReleaseSentOn
    * @param parentReleaseReceivedOn
    * @param college
    */
   public PersonResource(String id, String prefix, String firstName, String middleName,
      String lastName, String suffix, String nickname, Gender gender, DominantHand dominantHand,
      String birthdate, boolean released, String parentReleaseSentOn,
      String parentReleaseReceivedOn, String college) {
      this.id = id;
      this.prefix = prefix;
      this.firstName = firstName;
      this.middleName = middleName;
      this.lastName = lastName;
      this.suffix = suffix;
      this.nickname = nickname;
      this.gender = gender;
      this.dominantHand = dominantHand;
      this.birthdate = birthdate;
      this.released = released;
      this.parentReleaseSentOn = parentReleaseSentOn;
      this.parentReleaseReceivedOn = parentReleaseReceivedOn;
      this.college = college;
   }

   /**
    * Creates an empty {@code PersonResource} for internal use.
    */
   public PersonResource() {
   }

   /**
    * Returns the person's unique identifier, or null if this is a newly created person.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the person's unique identifier. Use null if this person has not been persisted.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the person's name prefix, or null.
    *
    * @return
    */
   public String getPrefix() {
      return prefix;
   }

   /**
    * Sets the persons's name prefix. Use null if none or unknown.
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
    * Sets the person's first name. Use null if not known.
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
    * Sets the person's middle name. Use null if not known.
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
    * Returns the person's name suffix, or null if none or unknown.
    *
    * @return
    */
   public String getSuffix() {
      return suffix;
   }

   /**
    * Sets the person's name suffix. Use null for none or unknown.
    *
    * @param suffix
    */
   public void setSuffix(String suffix) {
      this.suffix = suffix;
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
    * Sets the person's nickname. Use null for none or unknown.
    *
    * @param nickname
    */
   public void setNickname(String nickname) {
      this.nickname = nickname;
   }

   /**
    * Returns the person's full name.
    *
    * @return
    */
   public String getFullName() {
      return PersonResource.fullName(this);
   }

   /**
    * Returns the person's gender or null if not used.
    *
    * @return
    */
   public Gender getGender() {
      return gender;
   }

   /**
    * Sets the person's gender. Use null if not used.
    *
    * @param gender
    */
   public void setGender(Gender gender) {
      this.gender = gender;
   }

   /**
    * Returns the person's dominant hand, or null if unknown.
    *
    * @return
    */
   public DominantHand getDominantHand() {
      return dominantHand;
   }

   /**
    * Sets the person's dominant hand. Use null for unknown.
    *
    * @param dominantHand
    */
   public void setDominantHand(DominantHand dominantHand) {
      this.dominantHand = dominantHand;
   }

   /**
    * Reutnrs the person's birth date, or null if unknown.
    *
    * @return
    */
   public String getBirthdate() {
      return birthdate;
   }

   /**
    * Sets the person's birth date. Use null for unknown.
    *
    * @param birthdate
    */
   public void setBirthdate(String birthdate) {
      this.birthdate = birthdate;
   }

   /**
    * Returns true if the person has a parental release, false otherwise.
    *
    * @return
    */
   public boolean isReleased() {
      return released;
   }

   /**
    * Sets whether the person has a parental release. Defaults to false.
    *
    * @param released
    */
   public void setReleased(boolean released) {
      this.released = released;
   }

   /**
    * Returns the date the person's parental release request was sent.
    *
    * @return
    */
   public String getParentReleaseSentOn() {
      return parentReleaseSentOn;
   }

   /**
    * Sets the date the person's parental release request was sent. Use null for none or unknown.
    *
    * @param parentReleaseSentOn
    */
   public void setParentReleaseSentOn(String parentReleaseSentOn) {
      this.parentReleaseSentOn = parentReleaseSentOn;
   }

   /**
    * Returns the date the person's parental release was received, or null.
    *
    * @return
    */
   public String getParentReleaseReceivedOn() {
      return parentReleaseReceivedOn;
   }

   /**
    * Sets the date the person's parental release was received. Use null for none or unknown.
    *
    * @param parentReleaseReceivedOn
    */
   public void setParentReleaseReceivedOn(String parentReleaseReceivedOn) {
      this.parentReleaseReceivedOn = parentReleaseReceivedOn;
   }

   /**
    * Returns the name of the person's college, or null.
    *
    * @return
    */
   public String getCollege() {
      return college;
   }

   /**
    * Sets the name of the person's college. Use null for none or unknown.
    *
    * @param college
    */
   public void setCollege(String college) {
      this.college = college;
   }

}
