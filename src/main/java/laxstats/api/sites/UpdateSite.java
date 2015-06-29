package laxstats.api.sites;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code UpdateSite} represents a command to update the state of an existing site.
 */
public class UpdateSite {

   @TargetAggregateIdentifier
   private final SiteId siteId;
   private final SiteDTO siteDTO;

   /**
    * Creates an {@code UpdateSite} command with the given aggregate identifier and data to update.
    * 
    * @param siteId
    * @param siteDTO
    */
   public UpdateSite(SiteId siteId, SiteDTO siteDTO) {
      this.siteId = siteId;
      this.siteDTO = siteDTO;
   }

   /**
    * Returns the aggregate identifier of the site to update.
    * 
    * @return
    */
   public SiteId getSiteId() {
      return siteId;
   }

   /**
    * Returns the data with which to update the site.
    * 
    * @return
    */
   public SiteDTO getSiteDTO() {
      return siteDTO;
   }
}
