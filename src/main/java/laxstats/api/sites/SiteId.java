package laxstats.api.sites;

import laxstats.api.AggregateId;

/**
 * {@code SiteId} represents a site's aggregate identifier.
 */
public class SiteId extends AggregateId {
   private static final long serialVersionUID = 5119138108815863082L;

   /**
    * Creates a {@code SiteId} with a new unique identifier.
    */
   public SiteId() {
      super();
   }

   /**
    * Creates a {@code SiteId} with the given unique identifier.
    * 
    * @param identifier
    */
   public SiteId(String identifier) {
      super(identifier);
   }
}
