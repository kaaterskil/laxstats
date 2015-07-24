package laxstats.web.people;

import laxstats.api.people.DominantHand;
import laxstats.api.people.Gender;

import org.joda.time.LocalDate;

public interface PersonResource {

   /**
    * Returns the person's unique identifier, or null if this is a newly created person.
    *
    * @return
    */
   public String getId();

   /**
    * Sets the person's unique identifier. Use null if this person has not been persisted.
    *
    * @param id
    */
   public void setId(String id);

   /**
    * Returns the person's name prefix, or null.
    *
    * @return
    */
   public String getPrefix();

   /**
    * Sets the persons's name prefix. Use null if none or unknown.
    *
    * @param prefix
    */
   public void setPrefix(String prefix);

   /**
    * Returns the person's first name, or null if not known.
    *
    * @return
    */
   public String getFirstName();

   /**
    * Sets the person's first name. Use null if not known.
    *
    * @param firstName
    */
   public void setFirstName(String firstName);

   /**
    * Returns the person's middle name, or null if not known.
    *
    * @return
    */
   public String getMiddleName();

   /**
    * Sets the person's middle name. Use null if not known.
    *
    * @param middleName
    */
   public void setMiddleName(String middleName);

   /**
    * Returns the person's last name. Never null.
    *
    * @return
    */
   public String getLastName();

   /**
    * Sets the person's last name. Must not be null.
    *
    * @param lastName
    */
   public void setLastName(String lastName);

   /**
    * Returns the person's name suffix, or null if none or unknown.
    *
    * @return
    */
   public String getSuffix();

   /**
    * Sets the person's name suffix. Use null for none or unknown.
    *
    * @param suffix
    */
   public void setSuffix(String suffix);

   /**
    * Returns the person's nickname.
    *
    * @return
    */
   public String getNickname();

   /**
    * Sets the person's nickname. Use null for none or unknown.
    *
    * @param nickname
    */
   public void setNickname(String nickname);

   /**
    * Returns the person's full name.
    *
    * @return
    */
   public String getFullName();

   /**
    * Sets the person's full name, or null if not used.
    * 
    * @param fullName
    */
   public void setFullName(String fullName);

   /**
    * Returns the person's gender or null if not used.
    *
    * @return
    */
   public Gender getGender();

   /**
    * Sets the person's gender. Use null if not used.
    *
    * @param gender
    */
   public void setGender(Gender gender);

   /**
    * Returns the person's dominant hand, or null if unknown.
    *
    * @return
    */
   public DominantHand getDominantHand();

   /**
    * Sets the person's dominant hand. Use null for unknown.
    *
    * @param dominantHand
    */
   public void setDominantHand(DominantHand dominantHand);

   /**
    * Returns the person's birth date, or null if unknown.
    *
    * @return
    */
   public String getBirthdate();

   /**
    * Sets the person's birth date. Use null for unknown.
    *
    * @param birthdate
    */
   public void setBirthdate(String birthdate);

   /**
    * Returns the person's birth date as a LocalDate, or null if unknown.
    *
    * @return
    */
   public LocalDate getBirthdateAsLocalDate();

   /**
    * Sets the person's birth date. Use null for unknown.
    *
    * @param birthdate
    */
   public void setBirthdateFromLocalDate(LocalDate birthdate);

   /**
    * Returns the name of the person's college, or null.
    *
    * @return
    */
   public String getCollege();

   /**
    * Sets the name of the person's college. Use null for none or unknown.
    *
    * @param college
    */
   public void setCollege(String college);

}
