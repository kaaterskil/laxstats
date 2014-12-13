package laxstats.api.sites;

public class SiteUpdatedEvent {

	private final SiteId siteId;
	private final SiteDTO siteDTO;

	public SiteUpdatedEvent(SiteId siteId, SiteDTO siteDTO) {
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
