package laxstats.domain.games;

import java.util.List;

import laxstats.api.games.PlayDTO;

public interface PlayService {
	/**
	 * Returns the <code>Game</code> aggregate for this service.
	 *
	 * @return the <code>Game</code> aggregate for this service.
	 */
	Game getEvent();

	/**
	 * Returns a list of <code>Play</code> entities registered with this
	 * <code>Game</code> aggregate. If the specified <code>discriminator</code>
	 * is not null, only plays of that discriminator type will be returned. If
	 * the plays record elapsed time, the list is ordered by the total elapsed
	 * time since the beginning of the Game.
	 *
	 * @param discriminator String value representing the play type. May be
	 *            <code>null</code>.
	 * @return A list of the event aggregate's plays.
	 */
	List<Play> getPlays(String discriminator);

	/**
	 * Returns true if the play represented by the specified DTO can be recorded
	 * with the <code>Game</code> aggregate, false otherwise.
	 *
	 * @param dto the data transfer object representing the specified play.
	 * @return <code>true</code> if the play represented by the specified DTO
	 *         can be recorded with the <code>Game</code> aggregate,
	 *         <code>false</code> otherwise.
	 */
	boolean canRecordPlay(PlayDTO dto);

	/**
	 * Returns true if the play represented by the specified DTO can be updated,
	 * false otherwise.
	 * 
	 * @param dto the data transfer object representing the specified play.
	 * @return <code>true</code> if the play represented by the specified DTO
	 *         can be updated, <code>false</code> otherwise.
	 */
	boolean canUpdatePlay(PlayDTO dto);

	/**
	 * Tests that the play represented by the specified DTO is not already
	 * registered with the <code>Game</code> aggregate.
	 *
	 * @param dto the data transfer object representing the specified play.
	 * @return <code>true</code> if the specified play is already registered
	 *         with the <code>Game</code> aggregate, <code>false</code>
	 *         otherwise.
	 */
	boolean playRecorded(PlayDTO dto);

	/**
	 * Tests that the participants in the play represented by the specified DTO
	 * are registered with the <code>Game</code> aggregate as attendees.
	 * Returns true if the participants are also attendees, false otherwise.
	 *
	 * @param dto the data transfer object representing the specified play.
	 * @return <code>true</code> if the participants int he specified play are
	 *         registered as attendees with this <code>Game</code> aggregate,
	 *         <code>false</code> otherwise.
	 */
	boolean participantsRegistered(PlayDTO dto);

	/**
	 * Computes and sets invariant values on the DTO representing the specified
	 * play.
	 *
	 * @param dto the data transfer object representing the specified play.
	 */
	void setInvariants(PlayDTO dto);
}
