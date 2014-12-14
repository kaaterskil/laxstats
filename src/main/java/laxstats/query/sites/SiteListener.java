package laxstats.query.sites;

import laxstats.api.people.AddressDTO;
import laxstats.api.sites.SiteCreatedEvent;
import laxstats.api.sites.SiteDTO;
import laxstats.api.sites.SiteDeletedEvent;
import laxstats.api.sites.SiteUpdatedEvent;

import laxstats.query.people.AddressEntry;
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
		site.setCreatedAt(dto.getCreatedAt());
		site.setCreatedBy(dto.getCreatedBy());
		site.setDirections(dto.getDirections());
		site.setModifiedAt(dto.getModifiedAt());
		site.setModifiedBy(dto.getModifiedBy());
		site.setName(dto.getName());
		site.setStyle(dto.getStyle());
		site.setSurface(dto.getSurface());
		if (dto.getAddress() != null) {
			AddressDTO addressDTO = dto.getAddress();
			AddressEntry address = new AddressEntry();

			address.setAddress1(addressDTO.getAddress1());
			address.setAddress2(addressDTO.getAddress2());
			address.setAddressType(addressDTO.getAddressType());
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
		}

		repository.save(site);
	}

	@EventHandler
	protected void handle(SiteUpdatedEvent event) {
		final SiteDTO dto = event.getSiteDTO();
		final SiteEntry site = repository
				.findOne(event.getSiteId().toString());

		site.setDirections(dto.getDirections());
		site.setModifiedAt(dto.getModifiedAt());
		site.setModifiedBy(dto.getModifiedBy());
		site.setName(dto.getName());
		site.setStyle(dto.getStyle());
		site.setSurface(dto.getSurface());
		if (site.getAddress() != null) {
			AddressEntry address = site.getAddress();
			if (dto.getAddress() != null) {
				AddressDTO addressDTO = dto.getAddress();

				address.setAddress1(addressDTO.getAddress1());
				address.setAddress2(addressDTO.getAddress2());
				address.setAddressType(addressDTO.getAddressType());
				address.setCity(addressDTO.getCity());
				address.setRegion(addressDTO.getRegion());
				address.setPostalCode(addressDTO.getPostalCode());
				address.setPrimary(addressDTO.isPrimary());
				address.setDoNotUse(addressDTO.isDoNotUse());
				address.setModifiedAt(addressDTO.getModifiedAt());
				address.setModifiedBy(addressDTO.getModifiedBy());
			} else {
				// This should detach the address
				address.setSite(null);
				site.setAddress(null);
			}
		}
		repository.save(site);
	}

	@EventHandler
	protected void handle(SiteDeletedEvent event) {
		repository.delete(event.getSiteId().toString());
	}
}
