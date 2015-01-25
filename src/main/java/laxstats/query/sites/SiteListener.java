package laxstats.query.sites;

import laxstats.api.people.AddressDTO;
import laxstats.api.sites.SiteCreatedEvent;
import laxstats.api.sites.SiteDTO;
import laxstats.api.sites.SiteDeletedEvent;
import laxstats.api.sites.SiteUpdatedEvent;
import laxstats.query.people.AddressEntry;

import org.axonframework.domain.IdentifierFactory;
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

		final SiteEntry site = new SiteEntry();
		site.setId(event.getSiteId().toString());
		site.setName(dto.getName());
		site.setStyle(dto.getStyle());
		site.setSurface(dto.getSurface());
		site.setDirections(dto.getDirections());
		site.setCreatedAt(dto.getCreatedAt());
		site.setCreatedBy(dto.getCreatedBy());
		site.setModifiedAt(dto.getModifiedAt());
		site.setModifiedBy(dto.getModifiedBy());

		repository.save(site);

		if (dto.getAddress() != null) {
			final AddressDTO addressDTO = dto.getAddress();
			final AddressEntry address = new AddressEntry();

			address.setId(addressDTO.getId());
			address.setAddressType(addressDTO.getAddressType());
			address.setAddress1(addressDTO.getAddress1());
			address.setAddress2(addressDTO.getAddress2());
			address.setCity(addressDTO.getCity());
			address.setRegion(addressDTO.getRegion());
			address.setPostalCode(addressDTO.getPostalCode());
			address.setPrimary(addressDTO.isPrimary());
			address.setDoNotUse(addressDTO.isDoNotUse());
			address.setCreatedAt(addressDTO.getCreatedAt());
			address.setCreatedBy(addressDTO.getCreatedBy());
			address.setModifiedAt(addressDTO.getModifiedAt());
			address.setModifiedBy(addressDTO.getModifiedBy());

			address.setSite(site);
			site.setAddress(address);

			repository.save(site);
		}
	}

	@EventHandler
	protected void handle(SiteUpdatedEvent event) {
		final SiteDTO dto = event.getSiteDTO();
		final SiteEntry site = repository.findOne(event.getSiteId().toString());

		site.setName(dto.getName());
		site.setStyle(dto.getStyle());
		site.setSurface(dto.getSurface());
		site.setDirections(dto.getDirections());
		site.setModifiedAt(dto.getModifiedAt());
		site.setModifiedBy(dto.getModifiedBy());

		repository.save(site);

		// null -> address
		// address -> address
		// address -> null
		AddressEntry address = site.getAddress();
		final AddressDTO addressDTO = dto.getAddress();
		if (dto.getAddress() != null) {
			if (address == null) {
				final String id = IdentifierFactory.getInstance()
						.generateIdentifier();

				address = new AddressEntry();
				address.setId(id);
				address.setSite(site);
				address.setCreatedAt(addressDTO.getModifiedAt());
				address.setCreatedBy(addressDTO.getModifiedBy());

				site.setAddress(address);
			}
			address.setAddressType(addressDTO.getAddressType());
			address.setAddress1(addressDTO.getAddress1());
			address.setAddress2(addressDTO.getAddress2());
			address.setCity(addressDTO.getCity());
			address.setRegion(addressDTO.getRegion());
			address.setPostalCode(addressDTO.getPostalCode());
			address.setPrimary(addressDTO.isPrimary());
			address.setDoNotUse(addressDTO.isDoNotUse());
			address.setModifiedAt(addressDTO.getModifiedAt());
			address.setModifiedBy(addressDTO.getModifiedBy());

		} else if (address != null) {
			address.setSite(null);
			site.setAddress(null);
		}

		repository.save(site);
	}

	@EventHandler
	protected void handle(SiteDeletedEvent event) {
		repository.delete(event.getSiteId().toString());
	}
}
