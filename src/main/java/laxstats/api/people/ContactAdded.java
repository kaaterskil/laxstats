package laxstats.api.people;

/**
 * {@code ContactAdded} represents an event marking the creation of a person's new contact.
 */
public class ContactAdded {
   private final PersonId personId;
   private final ContactDTO contactDTO;

   /**
    * Creates a {@code ContactAdded} event with teh given aggregate identifier and new contact
    * information.
    * 
    * @param personId
    * @param contactDTO
    */
   public ContactAdded(PersonId personId, ContactDTO contactDTO) {
      this.personId = personId;
      this.contactDTO = contactDTO;
   }

   /**
    * Returns information about the new contact.
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
