package laxstats.api.people;

/**
 * {@code AddContact} represents a command to create a new contact for a person.
 */
public class AddContact extends AbstractPersonCommand {
   private final ContactDTO contactDTO;

   /**
    * Creates a {@code AddContact} command with the given aggregate identifier and contact
    * information.
    *
    * @param personId
    * @param contactDTO
    */
   public AddContact(PersonId personId, ContactDTO contactDTO) {
      super(personId);
      this.contactDTO = contactDTO;
   }

   /**
    * Returns information to create a contact.
    *
    * @return
    */
   public ContactDTO getContactDTO() {
      return contactDTO;
   }
}
