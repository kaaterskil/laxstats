package laxstats.api.people;

/**
 * {@code addressChanged} represents an event marking a change in an existing address.
 */
public class AddressChanged {
   private final PersonId personId;
   private final AddressDTO addressDTO;

   /**
    * Creates an {@code AddressChanged} event with the given aggregate identifier and updated
    * address information.
    * 
    * @param personId
    * @param addressDTO
    */
   public AddressChanged(PersonId personId, AddressDTO addressDTO) {
      this.personId = personId;
      this.addressDTO = addressDTO;
   }

   /**
    * Returns the updated address information.
    * 
    * @return
    */
   public AddressDTO getAddressDTO() {
      return addressDTO;
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
