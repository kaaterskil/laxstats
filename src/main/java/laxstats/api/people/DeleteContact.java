package laxstats.api.people;

/**
 * {@code DeleteContact} represents a command to delete a person's contact.
 */
public class DeleteContact extends AbstractPersonCommand {
   private final String contactId;

   /**
    * Creates a {@code DeleteContact} command with the given aggregate identifier and contact key.
    * 
    * @param personId
    * @param contactId
    */
   public DeleteContact(PersonId personId, String contactId) {
      super(personId);
      this.contactId = contactId;
   }

   /**
    * Returns the unique identifier of the contact.
    * 
    * @return
    */
   public String getContactId() {
      return contactId;
   }
}
