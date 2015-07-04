package laxstats.web.violations;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.utils.Constants;
import laxstats.api.violations.PenaltyCategory;
import laxstats.api.violations.PenaltyLength;

/**
 * {@code ViolationResource} represents a violation resource for remote clients.
 */
public class ViolationResource {
   private String id;

   @NotNull
   @Size(min = Constants.MIN_LENGTH_STRING, max = 50)
   private String name;

   private String description;

   @NotNull
   private PenaltyCategory category;

   private PenaltyLength penaltyLength;
   private boolean releasable = true;

   /**
    * Creates a {@code ViooationResource} with the given information.
    *
    * @param id
    * @param name
    * @param description
    * @param category
    * @param penaltyLength
    * @param releasable
    */
   public ViolationResource(String id, String name, String description, PenaltyCategory category,
      PenaltyLength penaltyLength, boolean releasable) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.category = category;
      this.penaltyLength = penaltyLength;
      this.releasable = releasable;
   }

   /**
    * Creates an empty {@code SiteResource}. Internal use only.
    */
   public ViolationResource() {
   }

   /**
    * Returns the primary key for this violation.
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
    * Sets a name for this violation. Must not be null.
    *
    * @param name
    */
   public void setName(String name) {
      assert name != null;
      this.name = name;
   }

   /**
    * Returns a description of this violation, or null if none.
    *
    * @return
    */
   public String getDescription() {
      return description;
   }

   /**
    * Sets a description for this violation. Use null for none.
    *
    * @param description
    */
   public void setDescription(String description) {
      this.description = description;
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
    * Sets the violation category. Must not be null.
    *
    * @param category
    */
   public void setCategory(PenaltyCategory category) {
      assert category != null;
      this.category = category;
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
    * Sets the length of the penalty for this violation.
    *
    * @param penaltyLength
    */
   public void setPenaltyLength(PenaltyLength penaltyLength) {
      this.penaltyLength = penaltyLength;
   }

   /**
    * Returns true if the player committing this violation can be released from the penalty, false
    * otherwise.
    *
    * @return
    */
   public boolean isReleasable() {
      return releasable;
   }

   /**
    * Sets a flag indicating whether a player committing this violation can be released from the
    * penalty.
    *
    * @param releasable
    */
   public void setReleasable(boolean releasable) {
      this.releasable = releasable;
   }
}
