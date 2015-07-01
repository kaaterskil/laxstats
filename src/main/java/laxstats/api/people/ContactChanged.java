package laxstats.api.people;

/**
 * {@code ContactChanged} represents an event marking a status change in an existing contact.
 */
public class ContactChanged {
   private final PersonId personId;
   private final ContactDTO contactDTO;

   /**
    * Creates a {@code ContactChanged} event with the given aggregate identifier and updated contact
    * information.
    * 
    * @param personId
    * @param contactDTO
    */
   public ContactChanged(PersonId personId, ContactDTO contactDTO) {
      this.personId = personId;
      this.contactDTO = contactDTO;
   }

   /**
    * Returns the updated contact information.
    * 
    * @return
    */
   public ContactDTO getContact() {
      return contactDTO;
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
