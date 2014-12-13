package laxstats.api.sites;

public class SiteDeletedEvent {

	private final SiteId siteId;

	public SiteDeletedEvent(SiteId siteId) {
		this.siteId = siteId;
	}

	public SiteId getSiteId() {
		return siteId;
	}
}
