package laxstats.api.people;

/**
 * {@code ContactDeleted} represents an event marking the deletion of a person's contact.
 */
public class ContactDeleted {
   private final PersonId personId;
   private final String contactId;

   /**
    * Creates a {@code ContactDeleted} event with the given aggregate identifier and contact key.
    * 
    * @param personId
    * @param contactId
    */
   public ContactDeleted(PersonId personId, String contactId) {
      this.personId = personId;
      this.contactId = contactId;
   }

   /**
    * Returns the unique identifier of the deleted contact.
    * 
    * @return
    */
   public String getContactId() {
      return contactId;
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
