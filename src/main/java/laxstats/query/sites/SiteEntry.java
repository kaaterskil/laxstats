package laxstats.query.sites;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import laxstats.api.sites.SiteStyle;
import laxstats.api.sites.Surface;
import laxstats.api.utils.Constants;
import laxstats.query.people.AddressEntry;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * {@code SiteEntry} represents a query object model of a playing field. A site may have a
 * {@code SiteStyle} indicating if it is of competition or other quality, and a defined
 * {@code Surface}.
 */
@Entity
@Table(name = "sites", indexes = { @Index(name = "site_idx1", columnList = "style"),
   @Index(name = "site_idx2", columnList = "surface") })
public class SiteEntry implements Serializable {
   private static final long serialVersionUID = -8778384626555397624L;

   @Id
   @Column(length = Constants.MAX_LENGTH_DATABASE_KEY)
   private String id;

   @Column(length = 100, nullable = false)
   private String name;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING)
   private SiteStyle style;

   @Enumerated(EnumType.STRING)
   @Column(length = Constants.MAX_LENGTH_ENUM_STRING)
   private Surface surface;

   @OneToOne(mappedBy = "site", cascade = CascadeType.ALL)
   private AddressEntry address;

   @Column(columnDefinition = "text")
   private String directions;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   /**
    * Returns the site primary key.
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
    * Sets the name for this site. Never null.
    *
    * @param name
    */
   public void setName(String name) {
      assert name != null;
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
    * Returns the site surface, or null if unknown.
    *
    * @return
    */
   public Surface getSurface() {
      return surface;
   }

   /**
    * Sets the site surface.
    *
    * @param surface
    */
   public void setSurface(Surface surface) {
      this.surface = surface;
   }

   /**
    * Returns a site address, or null if none.
    *
    * @return
    */
   public AddressEntry getAddress() {
      return address;
   }

   /**
    * Sets a site address.
    *
    * @param address
    */
   public void setAddress(AddressEntry address) {
      this.address = address;
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
    * Sets driving directions.
    *
    * @param directions
    */
   public void setDirections(String directions) {
      this.directions = directions;
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
    * Sets the date and time this site was first persisted.
    *
    * @param createdAt
    */
   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
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
    * Sets the user who first persisted this site.
    *
    * @param createdBy
    */
   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
   }

   /**
    * Returns the date and time the site was last modified.
    *
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Sets the date and time the site was last modified.
    *
    * @param modifiedAt
    */
   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   /**
    * Returns the user who last modified this site.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   /**
    * Sets the user who last modified htis site.
    *
    * @param modifiedBy
    */
   public void setModifiedBy(UserEntry modifiedBy) {
      this.modifiedBy = modifiedBy;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return String.format("SiteEntry[id=%s, name=%s, style=%s, surface=%s]", getId(), getName(),
         (getStyle() == null ? "null" : getStyle()), (getSurface() == null ? "null" : getSurface()));
   }
}
