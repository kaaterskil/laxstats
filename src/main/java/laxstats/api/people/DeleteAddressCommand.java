package laxstats.api.people;

public class DeleteAddressCommand extends AbstractPersonCommand {
	private final String addressId;

	public DeleteAddressCommand(PersonId personId, String addressId) {
		super(personId);
		this.addressId = addressId;
	}

	public String getAddressId() {
		return addressId;
	}
}
