package laxstats.api.sites;

public class SiteCreatedEvent {

	private final SiteId siteId;
	private final SiteDTO siteDTO;

	public SiteCreatedEvent(SiteId siteId, SiteDTO siteDTO) {
		this.siteId = siteId;
		this.siteDTO = siteDTO;
	}

	public SiteId getSiteId() {
		return siteId;
	}

	public SiteDTO getSiteDTO() {
		return siteDTO;
	}
}
