package laxstats.api.people;

/**
 * {@code UpdateAddress} represents a command to change the state of an existing address.
 */
public class UpdateAddress extends AbstractPersonCommand {
   private final AddressDTO addressDTO;

   /**
    * Creates a {@code UpdateAddress} command with the given aggregate identifier and update
    * information.
    * 
    * @param personId
    * @param addressDTO
    */
   public UpdateAddress(PersonId personId, AddressDTO addressDTO) {
      super(personId);
      this.addressDTO = addressDTO;
   }

   /**
    * Returns information with which to update the address.
    * 
    * @return
    */
   public AddressDTO getAddressDTO() {
      return addressDTO;
   }
}
