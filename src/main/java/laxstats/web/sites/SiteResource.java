package laxstats.web.sites;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.Region;
import laxstats.api.sites.SiteStyle;
import laxstats.api.sites.Surface;
import laxstats.api.utils.Constants;

/**
 * {@code SiteResource} represents a playing field resource for remote clients.
 */
public class SiteResource implements Serializable {
   private static final long serialVersionUID = 6903405993610427278L;

   private String id;

   @NotNull
   @Size(min = Constants.MIN_LENGTH_STRING, max = Constants.MAX_LENGTH_TITLE)
   private String name;

   private SiteStyle style;
   private Surface surface;
   private String directions;

   @Size(max = Constants.MAX_LENGTH_ADDRESS)
   private String address1;

   @Size(max = Constants.MAX_LENGTH_ADDRESS)
   private String address2;

   @NotNull
   @Size(min = Constants.MIN_LENGTH_STRING, max = Constants.MAX_LENGTH_CITY)
   private String city;

   @NotNull
   private Region region;

   @Size(max = Constants.MAX_LENGTH_LONG_POSTAL_CODE)
   private String postalCode;

   /**
    * Creates a {@code SiteResource} with the given information.
    *
    * @param id
    * @param name
    * @param style
    * @param surface
    * @param directions
    */
   public SiteResource(String id, String name, SiteStyle style, Surface surface, String directions) {
      this(id, name, style, surface, directions, null, null, null, null, null);
   }

   /**
    * Creates a {@code SiteResource} with the given information.
    *
    * @param id
    * @param name
    * @param style
    * @param surface
    * @param directions
    * @param address1
    * @param address2
    * @param city
    * @param region
    * @param postalCode
    */
   public SiteResource(String id, String name, SiteStyle style, Surface surface, String directions,
      String address1, String address2, String city, Region region, String postalCode) {
      this.id = id;
      this.name = name;
      this.style = style;
      this.surface = surface;
      this.directions = directions;
      this.address1 = address1;
      this.address2 = address2;
      this.city = city;
      this.region = region;
      this.postalCode = postalCode;
   }

   /**
    * Creates a {@code SiteResource}. Internal use only.
    */
   public SiteResource() {
   }

   /**
    * Returns the site primary key, or null if a new site.
    *
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the site primary key.
    *
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the site name.
    *
    * @return
    */
   public String getName() {
      return name;
   }

   /**
    * Sets the site name.
    *
    * @param name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * Returns the site quality.
    *
    * @return
    */
   public SiteStyle getStyle() {
      return style;
   }

   /**
    * Sets the site quality.
    *
    * @param style
    */
   public void setStyle(SiteStyle style) {
      this.style = style;
   }

   /**
    * Returns the site surface material.
    *
    * @return
    */
   public Surface getSurface() {
      return surface;
   }

   /**
    * Sets the site surface material.
    *
    * @param surface
    */
   public void setSurface(Surface surface) {
      this.surface = surface;
   }

   /**
    * Returns driving directions.
    *
    * @return
    */
   public String getDirections() {
      return directions;
   }

   /**
    * Sets driving directions.
    *
    * @param directions
    */
   public void setDirections(String directions) {
      this.directions = directions;
   }

   /**
    * Returns the first street address line.
    *
    * @return
    */
   public String getAddress1() {
      return address1;
   }

   /**
    * Sets the first street address line.
    *
    * @param address1
    */
   public void setAddress1(String address1) {
      this.address1 = address1;
   }

   /**
    * Returns the second street address line.
    *
    * @return
    */
   public String getAddress2() {
      return address2;
   }

   /**
    * Sets the second street address line.
    *
    * @param address2
    */
   public void setAddress2(String address2) {
      this.address2 = address2;
   }

   /**
    * Returns the city.
    *
    * @return
    */
   public String getCity() {
      return city;
   }

   /**
    * Sets the city.
    *
    * @param city
    */
   public void setCity(String city) {
      this.city = city;
   }

   /**
    * Returns the region.
    *
    * @return
    */
   public Region getRegion() {
      return region;
   }

   /**
    * Sets the region.
    *
    * @param region
    */
   public void setRegion(Region region) {
      this.region = region;
   }

   /**
    * Returns the postal code.
    *
    * @return
    */
   public String getPostalCode() {
      return postalCode;
   }

   /**
    * Sets the postal code.
    *
    * @param postalCode
    */
   public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
   }

}
