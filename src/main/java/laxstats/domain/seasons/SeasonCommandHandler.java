package laxstats.domain.seasons;

import laxstats.api.seasons.CreateSeasonCommand;
import laxstats.api.seasons.DeleteSeasonCommand;
import laxstats.api.seasons.SeasonId;
import laxstats.api.seasons.UpdateSeasonCommand;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SeasonCommandHandler {

	private Repository<Season> repository;

	@Autowired
	@Qualifier("seasonRepository")
	public void setRepository(Repository<Season> seasonRepository) {
		this.repository = seasonRepository;
	}

	@CommandHandler
	public SeasonId handle(CreateSeasonCommand command) {
		SeasonId identifier = command.getSeasonId();
		Season aggregate = new Season(identifier, command.getSeasonDTO());
		repository.add(aggregate);
		return identifier;
	}

	@CommandHandler
	public void handle(UpdateSeasonCommand command) {
		SeasonId identifier = command.getSeasonId();
		Season aggregate = repository.load(identifier);
		aggregate.update(identifier, command.getSeasonDTO());
	}

	@CommandHandler
	public void handle(DeleteSeasonCommand command) {
		SeasonId identifier = command.getSeasonId();
		Season aggregate = repository.load(identifier);
		aggregate.delete(identifier);
	}
}
