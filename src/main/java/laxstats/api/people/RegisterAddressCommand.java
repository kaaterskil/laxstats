package laxstats.api.people;

public class RegisterAddressCommand extends AbstractPersonCommand {
    private final AddressDTO addressDTO;

    public RegisterAddressCommand(PersonId personId, AddressDTO addressDTO) {
        super(personId);
        this.addressDTO = addressDTO;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }
}
