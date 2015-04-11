package laxstats.api.people;

/**
 * {@code ContactMethod} enumerates the type of {@code Contact} by which to establish communication
 * with a {@code Person}.
 */
public enum ContactMethod {

   /**
    * The number for a hand-held mobile telephone.
    */
   MOBILE("Mobile"),

   /**
    * An address for an electronic communication.
    */
   EMAIL("Email"),

   /**
    * The number for a land-based telephone.
    */
   TELEPHONE("Telephone"),

   /**
    * The number for a facsimile machine.
    */
   FAX("Fax"),

   /**
    * The number for a wireless signalling device.
    */
   PAGER("Pager");

   /**
    * Returns the pretty name of a {@code ContactMethod} for use in a drop-down menu.
    *
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates a {@code ContactMethod} with the given pretty name.
    *
    * @param label
    */
   private ContactMethod(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
