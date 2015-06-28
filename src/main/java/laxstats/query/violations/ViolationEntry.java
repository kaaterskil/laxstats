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

import laxstats.api.utils.Constants;
import laxstats.api.violations.PenaltyCategory;
import laxstats.api.violations.PenaltyLength;
import laxstats.query.users.UserEntry;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * {@code ViolationEntry} represents a persistent query object model of a breach of playing rules.
 */
@Entity
@Table(name = "violations",
         indexes = { @Index(name = "violations_idx1", columnList = "name"),
            @Index(name = "violations_idx2", columnList = "category") },
         uniqueConstraints = { @UniqueConstraint(name = "violations_uk1", columnNames = { "name" }) })
public class ViolationEntry implements Serializable {
   private static final long serialVersionUID = 3389030857212618605L;

   @Id
   @Column(length = Constants.MAX_LENGTH_DATABASE_KEY)
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

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime createdAt;

   @ManyToOne
   private UserEntry createdBy;

   @Type(type = Constants.LOCAL_DATETIME_DATABASE_TYPE)
   private LocalDateTime modifiedAt;

   @ManyToOne
   private UserEntry modifiedBy;

   /**
    * Returns the primary key of this violation.
    * 
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Sets the primary key for this violation.
    * 
    * @param id
    */
   public void setId(String id) {
      this.id = id;
   }

   /**
    * Returns the name of this violation. Never null.
    * 
    * @return
    */
   public String getName() {
      return name;
   }

   /**
    * Sets a name for this violation. Never null.
    * 
    * @param name
    */
   public void setName(String name) {
      assert name != null;
      this.name = name;
   }

   /**
    * Returns a description for this violation.
    * 
    * @return
    */
   public String getDescription() {
      return description;
   }

   /**
    * Sets a description for this violation.
    * 
    * @param description
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * Returns this violation category. Never null.
    * 
    * @return
    */
   public PenaltyCategory getCategory() {
      return category;
   }

   /**
    * Sets this violation category.
    * 
    * @param category
    */
   public void setCategory(PenaltyCategory category) {
      assert category != null;
      this.category = category;
   }

   /**
    * Returns the lnegth of the penalty for this violation.
    * 
    * @return
    */
   public PenaltyLength getPenaltyLength() {
      return penaltyLength;
   }

   /**
    * Sets the length of the penalty for thsi violation.
    * 
    * @param penaltyLength
    */
   public void setPenaltyLength(PenaltyLength penaltyLength) {
      this.penaltyLength = penaltyLength;
   }

   /**
    * Returns true if the plaeyer who committed this violation can be released from their penalty.
    * 
    * @return
    */
   public boolean isReleasable() {
      return releasable;
   }

   /**
    * Sets the flag to determine if the player who committed this violation can be released from
    * their penalty.
    * 
    * @param releasable
    */
   public void setReleasable(boolean releasable) {
      this.releasable = releasable;
   }

   /**
    * Returns the date and time this violation was first persisted.
    * 
    * @return
    */
   public LocalDateTime getCreatedAt() {
      return createdAt;
   }

   /**
    * Sets the date and time this violation was first persisted.
    * 
    * @param createdAt
    */
   public void setCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
   }

   /**
    * Returns the user who first persisted this violation.
    * 
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
   }

   /**
    * Sets the user who first persisted this violation.
    *
    * @param createdBy
    */
   public void setCreatedBy(UserEntry createdBy) {
      this.createdBy = createdBy;
   }

   /**
    * Returns the date and time this violation was last modified.
    *
    * @return
    */
   public LocalDateTime getModifiedAt() {
      return modifiedAt;
   }

   /**
    * Sets the date and time this violation was last modified.
    *
    * @param modifiedAt
    */
   public void setModifiedAt(LocalDateTime modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   /**
    * Returns the user who last modified this violation.
    *
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }

   /**
    * Sets the user who last modified this violation.
    *
    * @param modifiedBy
    */
   public void setModifiedBy(UserEntry modifiedBy) {
      this.modifiedBy = modifiedBy;
   }
}
