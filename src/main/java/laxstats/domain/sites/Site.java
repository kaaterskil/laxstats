package laxstats.domain.sites;

import laxstats.api.people.Address;
import laxstats.api.sites.DeleteSiteCommand;
import laxstats.api.sites.SiteCreatedEvent;
import laxstats.api.sites.SiteDTO;
import laxstats.api.sites.SiteDeletedEvent;
import laxstats.api.sites.SiteId;
import laxstats.api.sites.SiteStyle;
import laxstats.api.sites.SiteUpdatedEvent;
import laxstats.api.sites.Surface;
import laxstats.api.sites.UpdateSiteCommand;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

public class Site extends AbstractAnnotatedAggregateRoot<SiteId> {
	private static final long serialVersionUID = 6097187976724565990L;

	@AggregateIdentifier
	private SiteId siteId;
	private String name;
	private SiteStyle style;
	private Surface surface;
	private Address address;
	private String directions;

	public Site(SiteId siteId, SiteDTO siteDTO) {
		apply(new SiteCreatedEvent(siteId, siteDTO));
	}

	protected Site() {
	}

	// ---------- Methods ----------//

	public void update(UpdateSiteCommand command) {
		apply(new SiteUpdatedEvent(command.getSiteId(), command.getSiteDTO()));
	}

	public void delete(DeleteSiteCommand command) {
		apply(new SiteDeletedEvent(command.getSiteId()));
	}

	// ---------- Event handlers ----------//

	@EventSourcingHandler
	protected void handle(SiteCreatedEvent event) {
		final SiteDTO dto = event.getSiteDTO();

		siteId = event.getSiteId();
		name = dto.getName();
		style = dto.getStyle();
		surface = dto.getSurface();
		address = dto.getAddress();
		directions = dto.getDirections();
	}

	@EventSourcingHandler
	protected void handle(SiteUpdatedEvent event) {
		final SiteDTO dto = event.getSiteDTO();

		name = dto.getName();
		style = dto.getStyle();
		surface = dto.getSurface();
		address = dto.getAddress();
		directions = dto.getDirections();
	}

	@EventSourcingHandler
	protected void handle(SiteDeletedEvent event) {
		markDeleted();
	}

	// ---------- Getters ----------//

	@Override
	public SiteId getIdentifier() {
		return siteId;
	}

	public String getName() {
		return name;
	}

	public SiteStyle getStyle() {
		return style;
	}

	public Surface getSurface() {
		return surface;
	}

	public Address getAddress() {
		return address;
	}

	public String getDirections() {
		return directions;
	}
}
