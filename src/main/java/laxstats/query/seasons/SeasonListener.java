package laxstats.query.seasons;

import laxstats.api.seasons.SeasonCreated;
import laxstats.api.seasons.SeasonDTO;
import laxstats.api.seasons.SeasonDeleted;
import laxstats.api.seasons.SeasonUpdated;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeasonListener {
	private SeasonQueryRepository seasonRepository;

	@Autowired
	public void setSeasonRepository(SeasonQueryRepository seasonRepository) {
		this.seasonRepository = seasonRepository;
	}

	@EventHandler
	protected void handle(SeasonCreated event) {
		final String id = event.getSeasonId().toString();
		final SeasonDTO dto = event.getSeasonDTO();

		final SeasonEntry aggregate = new SeasonEntry();
		aggregate.setId(id);
		aggregate.setDescription(dto.getDescription());
		aggregate.setStartsOn(dto.getStartsOn());
		aggregate.setEndsOn(dto.getEndsOn());
		aggregate.setCreatedAt(dto.getCreatedAt());
		aggregate.setCreatedBy(dto.getCreatedBy());
		aggregate.setModifiedAt(dto.getModifiedAt());
		aggregate.setModifiedBy(dto.getModifiedBy());

		seasonRepository.save(aggregate);
	}

	@EventHandler
	protected void handle(SeasonUpdated event) {
		final String id = event.getSeasonId().toString();
		final SeasonDTO dto = event.getSeasonDTO();

		final SeasonEntry aggregate = seasonRepository.findOne(id);
		aggregate.setDescription(dto.getDescription());
		aggregate.setStartsOn(dto.getStartsOn());
		aggregate.setEndsOn(dto.getEndsOn());
		aggregate.setModifiedAt(dto.getModifiedAt());
		aggregate.setModifiedBy(dto.getModifiedBy());

		seasonRepository.save(aggregate);
	}

	@EventHandler
	protected void handle(SeasonDeleted event) {
		final String id = event.getSeasonId().toString();
		final SeasonEntry aggregate = seasonRepository.findOne(id);
		seasonRepository.delete(aggregate);
	}

}
