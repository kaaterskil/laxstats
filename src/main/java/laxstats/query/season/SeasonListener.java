package laxstats.query.season;

import laxstats.api.seasons.SeasonCreatedEvent;

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
	public void handleSeasonCreated(SeasonCreatedEvent event) {
		Season season = new Season();
		season.setId(event.getSeasonId().toString());
		season.setDescription(event.getDescription());
		season.setStartsOn(event.getStartsOn());
		season.setEndsOn(event.getEndsOn());
		season.setCreatedBy(event.getCreatedBy());
		season.setCreatedAt(event.getCreatedAt());
		season.setModifiedBy(event.getModifiedBy());
		season.setModifiedAt(event.getModifiedAt());
		
		seasonRepository.save(season);
	}
}
