package laxstats.api.people;

/**
 * {@code UpdatePerson} represents a generic command to change the status of an existing person.
 */
public class UpdatePerson extends AbstractPersonCommand {
   private final PersonDTO personDTO;

   /**
    * Creates an {@code UpdatePerson} command with the given aggregate identifier and update
    * information.
    * 
    * @param personId
    * @param personDTO
    */
   public UpdatePerson(PersonId personId, PersonDTO personDTO) {
      super(personId);
      this.personDTO = personDTO;
   }

   /**
    * Returns information with which to update the person.
    * 
    * @return
    */
   public PersonDTO getPersonDTO() {
      return personDTO;
   }

}
