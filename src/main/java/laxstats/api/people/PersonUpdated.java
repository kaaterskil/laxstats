package laxstats.api.people;

/**
 * {@code PersonUpdated} represents an event marking a generic change in state of an existing
 * person.
 */
public class PersonUpdated {

   private final PersonId personId;
   private final PersonDTO personDTO;

   /**
    * Creates a {@code PersonUpdated} event with the given aggregate identifier and updated person
    * information.
    * 
    * @param personId
    * @param personDTO
    */
   public PersonUpdated(PersonId personId, PersonDTO personDTO) {
      this.personId = personId;
      this.personDTO = personDTO;
   }

   /**
    * Returns the person's updated information.
    * 
    * @return
    */
   public PersonDTO getPersonDTO() {
      return personDTO;
   }

   /**
    * Returns the person's aggregate identifier.
    * 
    * @return
    */
   public PersonId getPersonId() {
      return personId;
   }

}
