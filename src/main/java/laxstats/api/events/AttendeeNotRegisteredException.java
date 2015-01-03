package laxstats.api.events;

public class AttendeeNotRegisteredException extends IllegalArgumentException {
	private static final long serialVersionUID = -8573964324017353948L;

	public AttendeeNotRegisteredException() {
		super();
	}

	public AttendeeNotRegisteredException(String s) {
		super(s);
	}

}
