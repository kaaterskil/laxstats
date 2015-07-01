package laxstats.api.people;

/**
 * {@code DeletePerson} represents a command to delete a person.
 */
public class DeletePerson extends AbstractPersonCommand {

   /**
    * Creates a {@code DeletePerson} command with the given aggregate identifier.
    * 
    * @param personId
    */
   public DeletePerson(PersonId personId) {
      super(personId);
   }

}
