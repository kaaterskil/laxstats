package laxstats.query.sites;

import laxstats.api.sites.SiteCreatedEvent;
import laxstats.api.sites.SiteDTO;
import laxstats.api.sites.SiteDeletedEvent;
import laxstats.api.sites.SiteUpdatedEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SiteListener {

	private SiteQueryRepository repository;

	@Autowired
	public void setRepository(SiteQueryRepository repository) {
		this.repository = repository;
	}

	@EventHandler
	protected void handle(SiteCreatedEvent event) {
		final SiteDTO dto = event.getSiteDTO();

		final SiteEntry entry = new SiteEntry();
		entry.setId(event.getSiteId().toString());
		// TODO address
		entry.setCreatedAt(dto.getCreatedAt());
		entry.setCreatedBy(dto.getCreatedBy());
		entry.setDirections(dto.getDirections());
		entry.setModifiedAt(dto.getModifiedAt());
		entry.setModifiedBy(dto.getModifiedBy());
		entry.setName(dto.getName());
		entry.setStyle(dto.getStyle());
		entry.setSurface(dto.getSurface());
		repository.save(entry);
	}

	@EventHandler
	protected void handle(SiteUpdatedEvent event) {
		final SiteDTO dto = event.getSiteDTO();
		final SiteEntry entry = repository
				.findOne(event.getSiteId().toString());

		// TODO address
		entry.setDirections(dto.getDirections());
		entry.setModifiedAt(dto.getModifiedAt());
		entry.setModifiedBy(dto.getModifiedBy());
		entry.setName(dto.getName());
		entry.setStyle(dto.getStyle());
		entry.setSurface(dto.getSurface());
		repository.save(entry);
	}

	@EventHandler
	protected void handle(SiteDeletedEvent event) {
		repository.delete(event.getSiteId().toString());
	}
}
