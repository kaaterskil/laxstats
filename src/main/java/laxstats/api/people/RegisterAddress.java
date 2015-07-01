package laxstats.api.people;

/**
 * {@code RegisterAddress} represents a command to create and associate a new address with a person.
 */
public class RegisterAddress extends AbstractPersonCommand {
   private final AddressDTO addressDTO;

   /**
    * Creates a {@code RegisterAddress} command with the given aggregate identifier and address
    * information.
    *
    * @param personId
    * @param addressDTO
    */
   public RegisterAddress(PersonId personId, AddressDTO addressDTO) {
      super(personId);
      this.addressDTO = addressDTO;
   }

   /**
    * Returns information with which to create a new address.
    *
    * @return
    */
   public AddressDTO getAddressDTO() {
      return addressDTO;
   }
}
