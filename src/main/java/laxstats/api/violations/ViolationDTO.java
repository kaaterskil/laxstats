package laxstats.api.violations;

import java.io.Serializable;

import laxstats.query.users.UserEntry;

import org.joda.time.LocalDateTime;

/**
 * {@code ViolationDTO} transfers information about a violation between the presentation and domain
 * layers of the application.
 */
public class ViolationDTO implements Serializable {
   private static final long serialVersionUID = -9029462956117913320L;

   private final String id;
   private final String name;
   private final String description;
   private final PenaltyCategory category;
   private final PenaltyLength penaltyLength;
   private final boolean releasable;
   private final LocalDateTime createdAt;
   private final UserEntry createdBy;
   private final LocalDateTime modifiedAt;
   private final UserEntry modifiedBy;

   /**
    * Creates a {@code ViolationDTO} with the given information.
    * 
    * @param id
    * @param name
    * @param description
    * @param category
    * @param penaltyLength
    * @param releasable
    * @param createdAt
    * @param createdBy
    * @param modifiedAt
    * @param modifiedBy
    */
   public ViolationDTO(String id, String name, String description, PenaltyCategory category,
      PenaltyLength penaltyLength, boolean releasable, LocalDateTime createdAt, UserEntry createdBy,
      LocalDateTime modifiedAt, UserEntry modifiedBy) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.category = category;
      this.penaltyLength = penaltyLength;
      this.releasable = releasable;
      this.createdAt = createdAt;
      this.createdBy = createdBy;
      this.modifiedAt = modifiedAt;
      this.modifiedBy = modifiedBy;
   }

   /**
    * Creates a {@code ViolationDTO} with the given information.
    * 
    * @param id
    * @param name
    * @param description
    * @param category
    * @param penaltyLength
    * @param releasable
    * @param modifiedAt
    * @param modifiedBy
    */
   public ViolationDTO(String id, String name, String description, PenaltyCategory category,
      PenaltyLength penaltyLength, boolean releasable, LocalDateTime modifiedAt, UserEntry modifiedBy) {
      this(id, name, description, category, penaltyLength, releasable, null, null, modifiedAt,
         modifiedBy);
   }

   /**
    * Returns the primary key of this violation.
    * 
    * @return
    */
   public String getId() {
      return id;
   }

   /**
    * Returns the violation name. Never null.
    * 
    * @return
    */
   public String getName() {
      return name;
   }

   /**
    * Returns a description of this violation.
    * 
    * @return
    */
   public String getDescription() {
      return description;
   }

   /**
    * Returns the violation category. Never null.
    * 
    * @return
    */
   public PenaltyCategory getCategory() {
      return category;
   }

   /**
    * Returns the length of the penalty for this violation.
    * 
    * @return
    */
   public PenaltyLength getPenaltyLength() {
      return penaltyLength;
   }

   /**
    * Returns true if the player who committed htis violation can be released from the penalty,
    * false otherwise.
    * 
    * @return
    */
   public boolean isReleasable() {
      return releasable;
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
    * Returns the user who first persisted this violation.
    * 
    * @return
    */
   public UserEntry getCreatedBy() {
      return createdBy;
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
    * Returns the user who last modified this violation.
    * 
    * @return
    */
   public UserEntry getModifiedBy() {
      return modifiedBy;
   }
}
