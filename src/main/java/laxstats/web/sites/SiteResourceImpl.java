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
public class SiteResourceImpl implements Serializable, SiteResource {
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
   public SiteResourceImpl(String id, String name, SiteStyle style, Surface surface,
      String directions) {
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
   public SiteResourceImpl(String id, String name, SiteStyle style, Surface surface,
      String directions, String address1, String address2, String city, Region region,
      String postalCode) {
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
    * Creates an empty {@code SiteResource}.
    */
   public SiteResourceImpl() {
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getId() {
      return id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setId(String id) {
      this.id = id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getName() {
      return name;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setName(String name) {
      this.name = name;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public SiteStyle getStyle() {
      return style;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setStyle(SiteStyle style) {
      this.style = style;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Surface getSurface() {
      return surface;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setSurface(Surface surface) {
      this.surface = surface;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getDirections() {
      return directions;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setDirections(String directions) {
      this.directions = directions;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getAddress1() {
      return address1;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setAddress1(String address1) {
      this.address1 = address1;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getAddress2() {
      return address2;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setAddress2(String address2) {
      this.address2 = address2;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getCity() {
      return city;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setCity(String city) {
      this.city = city;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Region getRegion() {
      return region;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setRegion(Region region) {
      this.region = region;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String getPostalCode() {
      return postalCode;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
   }

}
