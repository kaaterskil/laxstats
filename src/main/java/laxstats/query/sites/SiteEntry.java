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

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import laxstats.api.sites.SiteStyle;
import laxstats.api.sites.Surface;
import laxstats.query.people.AddressEntry;
import laxstats.query.users.UserEntry;

/**
 * {@code SiteEntry} represents a playing field. A site may have a {@code SiteStyle} indicating if
 * it is of competition or other quality, and a defined {@code Surface}.
 */
@Entity
@Table(name = "sites", indexes = { @Index(name = "site_idx1", columnList = "style"),
   @Index(name = "site_idx2", columnList = "surface") })
public class SiteEntry implements Serializable {
   private static final long serialVersionUID = 1L;

   @Id
   @Column(length = 36)
   private String id;

   @Column(length = 100, nullable = false)
   private String name;

   @Enumerated(EnumType.STRING)
   @Column(length = 20)
   private SiteStyle style;

   @Enumerated(EnumType.STRING)
   @Column(length = 20)
   private Surface surface;

   @OneToOne(mappedBy = "site", cascade = CascadeType.ALL)
   private AddressEntry address;

   @Column(columnDefinition = "text")
   private String directions;

   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   /*---------- Getter/Setters ----------*/

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

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

   public SiteStyle getStyle() {
      return style;
   }

   public void setStyle(SiteStyle style) {
      this.style = style;
   }

   public Surface getSurface() {
      return surface;
   }

   public void setSurface(Surface surface) {
      this.surface = surface;
   }

   public AddressEntry getAddress() {
      return address;
   }

   public void setAddress(AddressEntry address) {
      this.address = address;
   }

   public String getDirections() {
      return directions;
   }

   public void setDirections(String directions) {
      this.directions = directions;
   }

   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   public UserEntry getCreatedBy() {
      return createdBy;
   }

   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
   }

   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

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
