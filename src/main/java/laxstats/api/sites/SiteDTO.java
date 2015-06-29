package laxstats.api.sites;

import java.io.Serializable;

import laxstats.api.people.AddressDTO;
import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

/**
 * {@code SiteDTO} transfers information about a site between the presentation and domain layers.
 */
public class SiteDTO implements Serializable {
   private static final long serialVersionUID = 5631169753502489130L;

   private final SiteId siteId;
   private final String name;
   private final SiteStyle style;
   private final Surface surface;
   private final AddressDTO address;
   private final String directions;
   private final LocalDateTime createdAt;
   private final UserEntry createdBy;
   private final LocalDateTime modifiedAt;
   private final UserEntry modifiedBy;

   /**
    * Creates a {@code SiteDTO} with the given information.
    *
    * @param siteId
    * @param name
    * @param style
    * @param surface
    * @param address
    * @param directions
    * @param createdAt
    * @param createdBy
    * @param modifiedAt
    * @param modifiedBy
    */
   public SiteDTO(SiteId siteId, String name, SiteStyle style, Surface surface, AddressDTO address,
      String directions, LocalDateTime createdAt, UserEntry createdBy, LocalDateTime modifiedAt,
      UserEntry modifiedBy) {
      this.siteId = siteId;
      this.name = name;
      this.style = style;
      this.surface = surface;
      this.address = address;
      this.directions = directions;
      this.createdAt = createdAt;
      this.createdBy = createdBy;
      this.modifiedAt = modifiedAt;
      this.modifiedBy = modifiedBy;
   }

   /**
    * Creates a {@code SiteDTO} with the given information.
    *
    * @param siteId
    * @param name
    * @param style
    * @param surface
    * @param address
    * @param directions
    * @param modifiedAt
    * @param modifiedBy
    */
   public SiteDTO(SiteId siteId, String name, SiteStyle style, Surface surface, AddressDTO address,
      String directions, LocalDateTime modifiedAt, UserEntry modifiedBy) {
      this(siteId, name, style, surface, address, directions, null, null, modifiedAt, modifiedBy);
   }

   /**
    * Returns the site's aggregate identifier.
    * 
    * @return
    */
   public SiteId getSiteId() {
      return siteId;
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
    * Returns the site playing quality.
    * 
    * @return
    */
   public SiteStyle getStyle() {
      return style;
   }

   /**
    * Returns the site surface.
    * 
    * @return
    */
   public Surface getSurface() {
      return surface;
   }

   /**
    * Returns the site address, or null if none.
    * 
    * @return
    */
   public AddressDTO getAddress() {
      return address;
   }

   /**
    * Returns driving directions, or null if none.
    * 
    * @return
    */
   public String getDirections() {
      return directions;
   }

   /**
    * Returns the date and time this site was first persisted.
    * 
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Returns the user who first persisted this site.
    * 
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Returns hte date and time this site was last modified.
    * 
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Returns the user who last modified this site.
    * 
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }
}
