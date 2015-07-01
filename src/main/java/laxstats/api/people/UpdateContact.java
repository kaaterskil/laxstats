package laxstats.api.people;

/**
 * {@code UpdateContact} represents a command to change the state of an existing contact.
 */
public class UpdateContact extends AbstractPersonCommand {
   private final ContactDTO contactDTO;

   /**
    * Creates an {@code UpdateContact} command with the given aggregate identifier and update
    * information.
    * 
    * @param personId
    * @param contactDTO
    */
   public UpdateContact(PersonId personId, ContactDTO contactDTO) {
      super(personId);
      this.contactDTO = contactDTO;
   }

   /**
    * Returns information with which to update the contact.
    * 
    * @return
    */
   public ContactDTO getContactDTO() {
      return contactDTO;
   }
}
