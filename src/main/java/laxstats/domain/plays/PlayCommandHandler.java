package laxstats.domain.plays;

import laxstats.api.plays.CreateClearCommand;
import laxstats.api.plays.CreateFaceoffCommand;
import laxstats.api.plays.CreateGoalCommand;
import laxstats.api.plays.CreateGroundBallCommand;
import laxstats.api.plays.CreatePenaltyCommand;
import laxstats.api.plays.CreateShotCommand;
import laxstats.api.plays.PlayId;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PlayCommandHandler {
	private Repository<Play> repository;

	@Autowired
	@Qualifier("playRepository")
	public void setRepository(Repository<Play> repository) {
		this.repository = repository;
	}

	@CommandHandler
	public PlayId handle(CreateClearCommand command) {
		final PlayId playId = command.getPlayId();
		final Play play = new Clear(playId, command.getPlayDTO());
		repository.add(play);
		return playId;
	}

	@CommandHandler
	public PlayId handle(CreateFaceoffCommand command) {
		final PlayId playId = command.getPlayId();
		final Play play = new FaceOff(playId, command.getPlayDTO());
		repository.add(play);
		return playId;
	}

	@CommandHandler
	public PlayId handle(CreateGoalCommand command) {
		final PlayId playId = command.getPlayId();
		final Play play = new Goal(playId, command.getPlayDTO());
		repository.add(play);
		return playId;
	}

	@CommandHandler
	public PlayId handle(CreateGroundBallCommand command) {
		final PlayId playId = command.getPlayId();
		final Play play = new GroundBall(playId, command.getPlayDTO());
		repository.add(play);
		return playId;
	}

	@CommandHandler
	public PlayId handle(CreatePenaltyCommand command) {
		final PlayId playId = command.getPlayId();
		final Play play = new Penalty(playId, command.getPlayDTO());
		repository.add(play);
		return playId;
	}

	@CommandHandler
	public PlayId handle(CreateShotCommand command) {
		final PlayId playId = command.getPlayId();
		final Play play = new Shot(playId, command.getPlayDTO());
		repository.add(play);
		return playId;
	}
}
