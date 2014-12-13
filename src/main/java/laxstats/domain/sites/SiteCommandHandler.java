package laxstats.domain.sites;

import laxstats.api.sites.CreateSiteCommand;
import laxstats.api.sites.DeleteSiteCommand;
import laxstats.api.sites.SiteId;
import laxstats.api.sites.UpdateSiteCommand;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SiteCommandHandler {

	private Repository<Site> repository;

	@Autowired
	@Qualifier("siteRepository")
	public void SetRepository(Repository<Site> repository) {
		this.repository = repository;
	}

	@CommandHandler
	public SiteId handle(CreateSiteCommand command) {
		final SiteId siteId = command.getSiteId();
		final Site site = new Site(siteId, command.getSiteDTO());
		repository.add(site);
		return siteId;
	}

	@CommandHandler
	public void handle(UpdateSiteCommand command) {
		final SiteId siteId = command.getSiteId();
		final Site site = repository.load(siteId);
		site.update(command);
	}

	@CommandHandler
	public void handle(DeleteSiteCommand command) {
		final SiteId siteId = command.getSiteId();
		final Site site = repository.load(siteId);
		site.delete(command);
	}

}
