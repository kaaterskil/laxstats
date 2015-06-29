package laxstats.api.sites;

/**
 * {@code SiteDeleted} represents an event marking the deletion of a site.
 */
public class SiteDeleted {

   private final SiteId siteId;

   /**
    * Creates a {@code SiteDeleted} event with the given aggregate identifier.
    * 
    * @param siteId
    */
   public SiteDeleted(SiteId siteId) {
      this.siteId = siteId;
   }

   /**
    * Returns the aggregate identifier of the deleted site.
    * 
    * @return
    */
   public SiteId getSiteId() {
      return siteId;
   }
}
