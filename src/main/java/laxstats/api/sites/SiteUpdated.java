package laxstats.api.sites;

/**
 * {@code SiteUpdated} represents an event marking the update of an existing site.
 */
public class SiteUpdated {

   private final SiteId siteId;
   private final SiteDTO siteDTO;

   /**
    * Creates a {@code SiteUpdated} event with the given aggregat identifier and updated data.
    * 
    * @param siteId
    * @param siteDTO
    */
   public SiteUpdated(SiteId siteId, SiteDTO siteDTO) {
      this.siteId = siteId;
      this.siteDTO = siteDTO;
   }

   /**
    * Returns the aggregate identifier of the updated site.
    * 
    * @return
    */
   public SiteId getSiteId() {
      return siteId;
   }

   /**
    * Returns the site's updated data.
    * 
    * @return
    */
   public SiteDTO getSiteDTO() {
      return siteDTO;
   }
}
