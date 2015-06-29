package laxstats.api.sites;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code DeleteSite} represents a command to delete a site.
 */
public class DeleteSite {

   @TargetAggregateIdentifier
   private final SiteId siteId;

   /**
    * Creates a {@code DeleteSite} command with the given aggregate identifier.
    * 
    * @param siteId
    */
   public DeleteSite(SiteId siteId) {
      this.siteId = siteId;
   }

   /**
    * Returns the aggregate identifier for the site to delete.
    * 
    * @return
    */
   public SiteId getSiteId() {
      return siteId;
   }
}
