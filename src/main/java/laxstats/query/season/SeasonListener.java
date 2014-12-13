package laxstats.query.season;

import laxstats.api.seasons.*;
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
	protected void handle(SeasonCreatedEvent event) {
		SeasonId identifier = event.getSeasonId();
		SeasonDTO dto = event.getSeasonDTO();

		SeasonEntry entry = new SeasonEntry();
		entry.setCreatedAt(dto.getCreatedAt());
		entry.setCreatedBy(dto.getCreatedBy());
		entry.setDescription(dto.getDescription());
		entry.setEndsOn(dto.getEndsOn());
		entry.setId(identifier.toString());
		entry.setModifiedAt(dto.getModifiedAt());
		entry.setModifiedBy(dto.getModifiedBy());
		entry.setStartsOn(dto.getStartsOn());

		seasonRepository.save(entry);
	}

	@EventHandler
	protected void handle(SeasonUpdatedEvent event) {
		SeasonId identifier = event.getSeasonId();
		SeasonDTO dto = event.getSeasonDTO();

		SeasonEntry entry = seasonRepository.findOne(identifier.toString());
		entry.setDescription(dto.getDescription());
		entry.setEndsOn(dto.getEndsOn());
		entry.setModifiedAt(dto.getModifiedAt());
		entry.setModifiedBy(dto.getModifiedBy());
		entry.setStartsOn(dto.getStartsOn());

		seasonRepository.save(entry);
	}

	@EventHandler
	protected void handle(SeasonDeletedEvent event) {
		SeasonId identifier = event.getSeasonId();
		SeasonEntry entry = seasonRepository.findOne(identifier.toString());
		seasonRepository.delete(entry);
	}

}
