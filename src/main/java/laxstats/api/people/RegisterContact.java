package laxstats.api.people;

/**
 * {@code RegisterContact} represents a command to create a new contact for a person.
 */
public class RegisterContact extends AbstractPersonCommand {
   private final ContactDTO contactDTO;

   /**
    * Creates a {@code RegisterContact} command with the given aggregate identifier and new contact
    * information.
    * 
    * @param personId
    * @param contactDTO
    */
   public RegisterContact(PersonId personId, ContactDTO contactDTO) {
      super(personId);
      this.contactDTO = contactDTO;
   }

   /**
    * Returns information with which to create a new contact.
    * 
    * @return
    */
   public ContactDTO getContactDTO() {
      return contactDTO;
   }
}
