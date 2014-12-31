package laxstats.api.people;

public class HasPrimaryContactException extends RuntimeException {
	private static final long serialVersionUID = -1690832477609753488L;
	private final String contact;

	public HasPrimaryContactException(String contact) {
		super();
		this.contact = contact;
	}

	public String getContact() {
		return contact;
	}

}
