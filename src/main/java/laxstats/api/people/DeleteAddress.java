package laxstats.api.people;

/**
 * {@code DeleteAddress} represents a command to delete an address from a person or playing field.
 */
public class DeleteAddress extends AbstractPersonCommand {
   private final String addressId;

   /**
    * Creates a {@code DeleteAddress} command with the given aggregate identifier and address key.
    * 
    * @param personId
    * @param addressId
    */
   public DeleteAddress(PersonId personId, String addressId) {
      super(personId);
      this.addressId = addressId;
   }

   /**
    * Returns the unique identifier of the address.
    * 
    * @return
    */
   public String getAddressId() {
      return addressId;
   }
}
