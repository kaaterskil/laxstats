package laxstats.api.people;

/**
 * {@code PersonDeleted} represents an event marking the deletion of a person.
 */
public class PersonDeleted {

   private final PersonId personId;

   /**
    * Creates a {@code PersonDeleted} event with the given aggregate identifier.
    * 
    * @param personId
    */
   public PersonDeleted(PersonId personId) {
      this.personId = personId;
   }

   /**
    * Returns the aggregate identifier of the deleted person.
    * 
    * @return
    */
   public PersonId getPersonId() {
      return personId;
   }

}
