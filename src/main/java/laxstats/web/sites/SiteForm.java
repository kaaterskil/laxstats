package laxstats.web.sites;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.Region;
import laxstats.api.sites.SiteStyle;
import laxstats.api.sites.Surface;

/**
 * {@code SiteForm} contains user-defined information for the creation and update of a site.
 */
public class SiteForm implements Serializable, SiteResource {
   private static final long serialVersionUID = -3804992444594924489L;

   private String id;

   @NotNull
   @Size(min = 5, max = 100)
   private String name;

   private SiteStyle style;
   private Surface surface;
   private String directions;
   private String address1;
   private String address2;

   @NotNull
   @Size(min = 5, max = 30)
   private String city;

   @NotNull
   private Region region;
   private String postalCode;

   private List<SiteStyle> styles;
   private List<Surface> surfaces;
   private List<Region> regions;

   /**
    * Returns a list of regions for use in a drop-down menu.
    *
    * @return
    */
   public List<Region> getRegions() {
      if (regions == null) {
         regions = Arrays.asList(Region.values());
      }
      return regions;
   }

   /**
    * Returns a list of quality designations for use in a drop-down menu,
    *
    * @return
    */
   public List<SiteStyle> getStyles() {
      if (styles == null) {
         styles = Arrays.asList(SiteStyle.values());
      }
      return styles;
   }

   /**
    * Returns a list of site materials for use in a drop-down menu.
    *
    * @return
    */
   public List<Surface> getSurfaces() {
      if (surfaces == null) {
         surfaces = Arrays.asList(Surface.values());
      }
      return surfaces;
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
