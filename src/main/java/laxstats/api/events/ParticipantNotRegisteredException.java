package laxstats.api.events;

public class ParticipantNotRegisteredException extends RuntimeException {
	private static final long serialVersionUID = 6488048098363666314L;

	public ParticipantNotRegisteredException(String string) {
		super(string);
	}

}
