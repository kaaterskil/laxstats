package laxstats.domain.seasons;

import laxstats.api.seasons.CreateSeasonCommand;
import laxstats.api.seasons.SeasonId;
import laxstats.query.season.SeasonQueryRepository;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SeasonCommandHandler {

	private Repository<Season> repository;

	@SuppressWarnings("unused")
	private SeasonQueryRepository seasonQueryRepository;

	@Autowired
	@Qualifier("seasonRepository")
	public void setRepository(Repository<Season> seasonRepository) {
		this.repository = seasonRepository;
	}

	@Autowired
	public void setSeasonRepository(SeasonQueryRepository seasonRepository) {
		this.seasonQueryRepository = seasonRepository;
	}

	@CommandHandler
	public SeasonId handle(CreateSeasonCommand command) {
		SeasonId identifier = command.getSeasonId();
		Season season = new Season(identifier, command.getDescription(),
				command.getStartsOn(), command.getEndsOn(),
				command.getCreatedBy(), command.getCreatedAt(),
				command.getModifiedBy(), command.getModifiedAt());
		repository.add(season);
		return identifier;
	}
}
