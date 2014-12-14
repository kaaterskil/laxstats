package laxstats.api.people;

public class UpdateAddressCommand extends AbstractPersonCommand {
    private final AddressDTO addressDTO;

    public UpdateAddressCommand(PersonId personId, AddressDTO addressDTO) {
        super(personId);
        this.addressDTO = addressDTO;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }
}
