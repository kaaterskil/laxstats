package laxstats.api;


/**
 * {@code Region} enumerates the U.S. states and protectorates.
 */
public enum Region {
   AL("Alabama"),
   AK("Alaska"),
   AZ("Arizona"),
   AR("Arkansas"),
   CA("California"),
   CO("Colorado"),
   CT("Connecticut"),
   DC("Distric of Columbia"),
   DE("Delaware"),
   FL("Florida"),
   GA("Georgia"),
   HI("Hawaii"),
   ID("Idaho"),
   IL("Illinois"),
   IN("Indiana"),
   IA("Iowa"),
   KS("Kansas"),
   KY("Kentucky"),
   LA("Louisiana"),
   ME("Maine"),
   MD("Maryland"),
   MA("Massachusetts"),
   MI("Michigan"),
   MN("Minnesota"),
   MS("Mississippi"),
   MO("Missouri"),
   MT("Montana"),
   NE("Nebraska"),
   NV("Nevada"),
   NH("New Hampshire"),
   NJ("New Jersey"),
   NM("New Mexico"),
   NY("New York"),
   NC("North Carolina"),
   ND("North Dakota"),
   OH("Ohio"),
   OK("Oklahoma"),
   OR("Oregon"),
   PA("Pennsylvania"),
   RI("Rhode Island"),
   SC("South Carolina"),
   SD("South Dakota"),
   TN("Tennessee"),
   TX("Texas"),
   UT("Utah"),
   VT("Vermont"),
   VA("Virginia"),
   WA("Washington"),
   WV("West Virginia"),
   WI("Wisconsin"),
   WY("Wyoming"),
   AS("American Samoa"),
   FM("Federated States of Micronesia"),
   GU("Guam"),
   MH("Marshall Islands"),
   MP("Northern Mariana Islands"),
   PW("Palau"),
   PR("Puerto Rico"),
   VI("Virgin Islands");

   /**
    * Returns the pretty name of a {@code Region} for use in a drop-down menu.
    *
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Returns the abbreviation of a {@code Region}.
    *
    * @return
    */
   public String getAbbreviation() {
      return name();
   }

   /**
    * Creates a {@code Region} with the given abbreviation and label.
    *
    * @param label
    */
   private Region(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
