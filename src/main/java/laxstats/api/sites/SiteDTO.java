package laxstats.api.sites;

import laxstats.api.people.AddressDTO;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

public class SiteDTO {
	private final SiteId siteId;
	private final String name;
	private final SiteStyle style;
	private final Surface surface;
	private final AddressDTO address;
	private final String directions;
	private final LocalDateTime createdAt;
	private final UserEntry createdBy;
	private final LocalDateTime modifiedAt;
	private final UserEntry modifiedBy;

	public SiteDTO(SiteId siteId, String name, SiteStyle style,
			Surface surface, AddressDTO address, String directions,
			LocalDateTime createdAt, UserEntry createdBy,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this.siteId = siteId;
		this.name = name;
		this.style = style;
		this.surface = surface;
		this.address = address;
		this.directions = directions;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
	}

	public SiteDTO(SiteId siteId, String name, SiteStyle style,
			Surface surface, AddressDTO address, String directions,
			LocalDateTime modifiedAt, UserEntry modifiedBy) {
		this(siteId, name, style, surface, address, directions, null, null,
				modifiedAt, modifiedBy);
	}

	public SiteId getSiteId() {
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

	public AddressDTO getAddress() {
		return address;
	}

	public String getDirections() {
		return directions;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public UserEntry getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public UserEntry getModifiedBy() {
		return modifiedBy;
	}
}
