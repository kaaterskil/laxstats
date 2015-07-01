package laxstats.api.people;

/**
 * {@code CreatePerson} represents a command to create a new person.
 */
public class CreatePerson extends AbstractPersonCommand {

   private final PersonDTO personDTO;

   /**
    * Creates a {@code CreatePerson} command with the given aggregate identifier and information.
    * 
    * @param personId
    * @param personDTO
    */
   public CreatePerson(PersonId personId, PersonDTO personDTO) {
      super(personId);
      this.personDTO = personDTO;
   }

   /**
    * Returns the information with which to create a new person.
    * 
    * @return
    */
   public PersonDTO getPersonDTO() {
      return personDTO;
   }
}
