package laxstats.query.violations;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import laxstats.api.violations.PenaltyCategory;
import laxstats.api.violations.PenaltyLength;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "violations", indexes = { @Index(name = "violations_idx1", columnList = "name"),
   @Index(name = "violations_idx2", columnList = "category") },
   uniqueConstraints = { @UniqueConstraint(name = "violations_uk1", columnNames = { "name" }) })
public class ViolationEntry implements Serializable {
   private static final long serialVersionUID = 3389030857212618605L;

   @Id
   @Column(length = 36)
   private String id;

   @Column(length = 50, nullable = false)
   private String name;

   @Column(columnDefinition = "text")
   private String description;

   @Enumerated(EnumType.STRING)
   @Column(length = 20, nullable = false)
   private PenaltyCategory category;

   @Enumerated(EnumType.STRING)
   @Column(length = 20)
   private PenaltyLength penaltyLength;

   private boolean releasable;

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

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public PenaltyCategory getCategory() {
      return category;
   }

   public void setCategory(PenaltyCategory category) {
      this.category = category;
   }

   public PenaltyLength getPenaltyLength() {
      return penaltyLength;
   }

   public void setPenaltyLength(PenaltyLength penaltyLength) {
      this.penaltyLength = penaltyLength;
   }

   public boolean isReleasable() {
      return releasable;
   }

   public void setReleasable(boolean releasable) {
      this.releasable = releasable;
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
}
