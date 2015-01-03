package laxstats.api.events;

public class PlayExistsException extends IllegalArgumentException {
	private static final long serialVersionUID = 4950419347832310342L;

	public PlayExistsException() {
	}

	public PlayExistsException(String s) {
		super(s);
	}

}
