package laxstats.web.site;

public class SiteNotFoundException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   String siteId;

   public SiteNotFoundException(String siteId) {
      super("Site not found");
      this.siteId = siteId;
   }

   public String getSiteId() {
      return siteId;
   }

}
