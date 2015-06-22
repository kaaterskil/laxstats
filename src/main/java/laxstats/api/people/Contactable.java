package laxstats.api.people;

/**
 * {@code Contactable} defines an interface indicating that the implementing object is an address or
 * contact and that the object can have primacy over other objects in a collection or can indicate
 * that it is no longer usable. The user is responsible for maintaining only one primary object in
 * any collection.
 */
public interface Contactable {

   /**
    * Returns true if the {@code Contactable} is the primary entity in the collection, false
    * otherwise.
    * 
    * @return
    */
      boolean isPrimary();

   /**
    * Sets the flag to indicate that the {@code Contactable} is the primary entity in the
    * collection.
    * 
    * @param isPrimary
    */
      void setPrimary(boolean isPrimary);

   /**
    * Returns true if the {@code Contactable} entity is obsolete or incorrect and should not be
    * used, false otherwise.
    * 
    * @return
    */
      boolean isDoNotUse();

   /**
    * Sets the flag that indicates whether the {@code Contactable} should not be used.
    * 
    * @param doNotUse
    */
      void setDoNotUse(boolean doNotUse);
}
