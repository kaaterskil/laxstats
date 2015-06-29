package laxstats.api.sites;

/**
 * {@code SiteCreated} represents an event marking the creation of a new site.
 */
public class SiteCreated {

   private final SiteId siteId;
   private final SiteDTO siteDTO;

   /**
    * Creates a {@code SiteCreated} event with the given aggregate identifier and data.
    *
    * @param siteId
    * @param siteDTO
    */
   public SiteCreated(SiteId siteId, SiteDTO siteDTO) {
      this.siteId = siteId;
      this.siteDTO = siteDTO;
   }

   /**
    * Returns the aggregate identifier of the created site.
    *
    * @return
    */
   public SiteId getSiteId() {
      return siteId;
   }

   /**
    * Returns the created site data.
    *
    * @return
    */
   public SiteDTO getSiteDTO() {
      return siteDTO;
   }
}
