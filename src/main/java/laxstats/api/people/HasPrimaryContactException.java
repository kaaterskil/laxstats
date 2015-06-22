package laxstats.api.people;

/**
 * Exception indicating that a {@code Contactable} with primary status is being added to a
 * collection that contains a primary contact that cannot be overridden.
 */
public class HasPrimaryContactException extends RuntimeException {
   private static final long serialVersionUID = -1690832477609753488L;
   private final String contact;

   /**
    * Creates the exception with the current primary contact value.
    *
    * @param contact
    */
   public HasPrimaryContactException(String contact) {
      super();
      this.contact = contact;
   }

   /**
    * Returns the current primary contact value.
    *
    * @return
    */
   public String getContact() {
      return contact;
   }

}
