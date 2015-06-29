package laxstats.web.sites;

/**
 * Exception thrown when a site cannot be found.
 */
public class SiteNotFoundException extends RuntimeException {
   private static final long serialVersionUID = -3902399869005777770L;

   String siteId;

   /**
    * Creates a {@code SiteNotFoundException} with the given primary key.
    *
    * @param siteId
    */
   public SiteNotFoundException(String siteId) {
      super("Site not found");
      this.siteId = siteId;
   }

   /**
    * Returns the site's primary key.
    *
    * @return
    */
   public String getSiteId() {
      return siteId;
   }

}
