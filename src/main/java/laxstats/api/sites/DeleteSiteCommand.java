package laxstats.api.sites;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class DeleteSiteCommand {

	@TargetAggregateIdentifier
	private final SiteId siteId;

	public DeleteSiteCommand(SiteId siteId) {
		this.siteId = siteId;
	}

	public SiteId getSiteId() {
		return siteId;
	}
}
