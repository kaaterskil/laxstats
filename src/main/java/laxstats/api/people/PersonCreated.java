package laxstats.api.people;

/**
 * {@code PersonCreated} represents an event marking the creation of a new person.
 */
public class PersonCreated {

   private final PersonId personId;
   private final PersonDTO personDTO;

   /**
    * Creates a {@code PersonCreated} event with the given aggregate identifier and information
    * about the new person.
    * 
    * @param personId
    * @param personDTO
    */
   public PersonCreated(PersonId personId, PersonDTO personDTO) {
      this.personId = personId;
      this.personDTO = personDTO;
   }

   /**
    * Returns information about the new person.
    * 
    * @return
    */
   public PersonDTO getPersonDTO() {
      return personDTO;
   }

   /**
    * Returns the aggregate identifier of the new person.
    * 
    * @return
    */
   public PersonId getPersonId() {
      return personId;
   }

}
