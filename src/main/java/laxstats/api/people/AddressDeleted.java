package laxstats.api.people;

/**
 * {@code AddressDeleted} represents an event marking the deletion of a person's address.
 */
public class AddressDeleted {
   private final PersonId personId;
   private final String addressId;

   /**
    * Creates an {@code AddressDeleted} event with the given aggregate identifier and address key.
    * 
    * @param personId
    * @param addressId
    */
   public AddressDeleted(PersonId personId, String addressId) {
      this.personId = personId;
      this.addressId = addressId;
   }

   /**
    * Returns the unique identifier of the deleted address.
    * 
    * @return
    */
   public String getAddressId() {
      return addressId;
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
