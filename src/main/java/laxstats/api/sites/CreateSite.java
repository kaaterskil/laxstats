package laxstats.api.sites;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code CreateSite} represents a command to create an persist a new site.
 */
public class CreateSite {

   @TargetAggregateIdentifier
   private final SiteId siteId;
   private final SiteDTO siteDTO;

   /**
    * Creates a {@code CreateSite} command with the given aggregate identifier and data.
    * 
    * @param siteId
    * @param siteDTO
    */
   public CreateSite(SiteId siteId, SiteDTO siteDTO) {
      this.siteId = siteId;
      this.siteDTO = siteDTO;
   }

   /**
    * Returns the aggregate identifier for the new site.
    * 
    * @return
    */
   public SiteId getSiteId() {
      return siteId;
   }

   /**
    * Returns the data with which to create a new site.
    * 
    * @return
    */
   public SiteDTO getSiteDTO() {
      return siteDTO;
   }
}
