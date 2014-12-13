package laxstats.api.sites;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class CreateSiteCommand {

	@TargetAggregateIdentifier
	private final SiteId siteId;
	private final SiteDTO siteDTO;

	public CreateSiteCommand(SiteId siteId, SiteDTO siteDTO) {
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
