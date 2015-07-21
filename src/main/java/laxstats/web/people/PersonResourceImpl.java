package laxstats.web.people;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.people.DominantHand;
import laxstats.api.people.Gender;
import laxstats.api.utils.Constants;

import org.joda.time.LocalDate;

/**
 * {@code PersonResource} represents a person resource for remote clients.
 */
public class PersonResourceImpl implements PersonResource {

   /**
    * Returns a concatenated string of the given person's full name.
    *
    * @param form
    * @return
    */
   public static String fullName(PersonResourceImpl form) {
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
    * @param college
    */
   public PersonResourceImpl(String id, String prefix, String firstName, String middleName,
      String lastName, String suffix, String nickname, Gender gender, DominantHand dominantHand,
      String birthdate, String college) {
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
      this.college = college;
   }

   /**
    * Creates an empty {@code PersonResource}.
    */
   public PersonResourceImpl() {
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
   public String getPrefix() {
      return prefix;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setPrefix(String prefix) {
      this.prefix = prefix;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getFirstName() {
      return firstName;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getMiddleName() {
      return middleName;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setMiddleName(String middleName) {
      this.middleName = middleName;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getLastName() {
      return lastName;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setLastName(String lastName) {
      assert lastName != null;
      this.lastName = lastName;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getSuffix() {
      return suffix;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setSuffix(String suffix) {
      this.suffix = suffix;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getNickname() {
      return nickname;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setNickname(String nickname) {
      this.nickname = nickname;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getFullName() {
      return PersonResourceImpl.fullName(this);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Gender getGender() {
      return gender;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setGender(Gender gender) {
      this.gender = gender;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public DominantHand getDominantHand() {
      return dominantHand;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setDominantHand(DominantHand dominantHand) {
      this.dominantHand = dominantHand;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getBirthdate() {
      return birthdate;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setBirthdate(String birthdate) {
      this.birthdate = birthdate;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LocalDate getBirthdateAsLocalDate() {
      return birthdate == null ? null : LocalDate.parse(birthdate);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setBirthdateFromLocalDate(LocalDate birthdate) {
      this.birthdate = birthdate == null ? null : birthdate.toString();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getCollege() {
      return college;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setCollege(String college) {
      this.college = college;
   }

}
