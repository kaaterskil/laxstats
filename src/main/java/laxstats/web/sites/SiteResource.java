package laxstats.web.sites;

import laxstats.api.Region;
import laxstats.api.sites.SiteStyle;
import laxstats.api.sites.Surface;

public interface SiteResource {

   /**
    * Returns the site primary key, or null if a new site.
    *
    * @return
    */
   public String getId();

   /**
    * Sets the site primary key.
    *
    * @param id
    */
   public void setId(String id);

   /**
    * Returns the site name.
    *
    * @return
    */
   public String getName();

   /**
    * Sets the site name.
    *
    * @param name
    */
   public void setName(String name);

   /**
    * Returns the site quality.
    *
    * @return
    */
   public SiteStyle getStyle();

   /**
    * Sets the site quality.
    *
    * @param style
    */
   public void setStyle(SiteStyle style);

   /**
    * Returns the site surface material.
    *
    * @return
    */
   public Surface getSurface();

   /**
    * Sets the site surface material.
    *
    * @param surface
    */
   public void setSurface(Surface surface);

   /**
    * Returns driving directions.
    *
    * @return
    */
   public String getDirections();

   /**
    * Sets driving directions.
    *
    * @param directions
    */
   public void setDirections(String directions);

   /**
    * Returns the first street address line.
    *
    * @return
    */
   public String getAddress1();

   /**
    * Sets the first street address line.
    *
    * @param address1
    */
   public void setAddress1(String address1);

   /**
    * Returns the second street address line.
    *
    * @return
    */
   public String getAddress2();

   /**
    * Sets the second street address line.
    *
    * @param address2
    */
   public void setAddress2(String address2);

   /**
    * Returns the city.
    *
    * @return
    */
   public String getCity();

   /**
    * Sets the city.
    *
    * @param city
    */
   public void setCity(String city);

   /**
    * Returns the region.
    *
    * @return
    */
   public Region getRegion();

   /**
    * Sets the region.
    *
    * @param region
    */
   public void setRegion(Region region);

   /**
    * Returns the postal code.
    *
    * @return
    */
   public String getPostalCode();

   /**
    * Sets the postal code.
    *
    * @param postalCode
    */
   public void setPostalCode(String postalCode);

}
