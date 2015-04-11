package laxstats.api.relationships;

/**
 * {@code RelationshipType} enumerates the various connections between people.
 */
public enum RelationshipType {

   /**
    * A player's parent.
    */
   PARENT("Parent"),

   /**
    * A player's legal guardian.
    */
   GUARDIAN("Guardian"),

   /**
    * A school official charged with gudiing a player's academic goals.
    */
   COUNSELOR("Counselor"),

   /**
    * An individual guiding a player's academic learning.
    */
   TEACHER("Teacher"),

   /**
    * A member of a player's family.
    */
   FAMILY("Family member");

   /**
    * Returns the pretty name for a {@code RelationshipType} for use in a drop-down menu.
    * 
    * @return
    */
   public String getLabel() {
      return label;
   }

   /**
    * Creates a {@code RelationshipType} with the given pretty name.
    * 
    * @param label
    */
   private RelationshipType(String label) {
      assert label != null;

      this.label = label;
   }

   private String label;
}
