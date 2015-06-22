package laxstats.api.people;

/**
 * {@code AddressType} enumerates the types of addresses a {@code Person} or a {@code Site} may
 * have.
 */
public enum AddressType {
   /**
    * The {@code Address} represents the specified person's home.
    */
   HOME("Home"),

   /**
    * The {@code Address} represents the specified person's workplace.
    */
   WORK("Work"),

   /**
    * the {@code Address} represents the specified person's vacation or second home.
    */
   VACATION("Vacation"),

   /**
    * The {@code Address} represents a playing field's location.
    */
   SITE("Site");

   private String label;

   /**
    * Creates an {@code AddressType} with the given pretty name.
    * 
    * @param label
    */
   private AddressType(String label) {
      this.label = label;
   }

   /**
    * Returns the pretty name of this {@code AddressType} for use in a drop-down menu.
    * 
    * @return
    */
   public String getLabel() {
      return label;
   }
}
