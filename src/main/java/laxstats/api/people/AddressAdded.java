package laxstats.api.people;

/**
 * {@code AddressAdded} represents an event marking the creation of a person's address.
 */
public class AddressAdded {

   private final PersonId personId;
   private final AddressDTO address;

   /**
    * Creates an {@code AddressAdded} event with the given aggregate identifier and address
    * information.
    * 
    * @param personId
    * @param address
    */
   public AddressAdded(PersonId personId, AddressDTO address) {
      this.personId = personId;
      this.address = address;
   }

   /**
    * Returns information about the new address.
    * 
    * @return
    */
   public AddressDTO getAddress() {
      return address;
   }

   /**
    * Returns the aggregate identifier of the person.
    * 
    * @return
    */
   public PersonId getPersonId() {
      return personId;
   }

}
