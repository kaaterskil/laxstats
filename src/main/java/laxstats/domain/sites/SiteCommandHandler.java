package laxstats.domain.sites;

import laxstats.api.sites.CreateSite;
import laxstats.api.sites.DeleteSite;
import laxstats.api.sites.SiteId;
import laxstats.api.sites.UpdateSite;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * {@code SiteCommandHandler} manages commands for the site aggregate.
 */
@Component
public class SiteCommandHandler {

   private Repository<Site> repository;

   @Autowired
   @Qualifier("siteRepository")
   public void SetRepository(Repository<Site> repository) {
      this.repository = repository;
   }

   /**
    * Creates a new site aggregate with information in the payload of the given command, and returns
    * the aggregate identifier.
    * 
    * @param command
    * @return
    */
   @CommandHandler
   public SiteId handle(CreateSite command) {
      final SiteId siteId = command.getSiteId();
      final Site site = new Site(siteId, command.getSiteDTO());
      repository.add(site);
      return siteId;
   }

   /**
    * Updates an existing site with information contained in the payload of the given command.
    * 
    * @param command
    */
   @CommandHandler
   public void handle(UpdateSite command) {
      final SiteId siteId = command.getSiteId();
      final Site site = repository.load(siteId);
      site.update(command);
   }

   /**
    * Deletes an existing site.
    * 
    * @param command
    */
   @CommandHandler
   public void handle(DeleteSite command) {
      final SiteId siteId = command.getSiteId();
      final Site site = repository.load(siteId);
      site.delete(command);
   }

}
