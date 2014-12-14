package laxstats.api.people;

public class AddressChangedEvent {
    private final PersonId personId;
    private final AddressDTO addressDTO;

    public AddressChangedEvent(PersonId personId, AddressDTO addressDTO) {
        this.personId = personId;
        this.addressDTO = addressDTO;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public PersonId getPersonId() {
        return personId;
    }

}
