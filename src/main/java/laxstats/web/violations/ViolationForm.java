package laxstats.web.violations;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.violations.PenaltyCategory;
import laxstats.api.violations.PenaltyLength;

/**
 * {@code ViolationForm} contains user-defined information to create and update a violation.
 */
public class ViolationForm implements Serializable {
   private static final long serialVersionUID = -8383405468604959469L;

   private String id;

   @NotNull
   @Size(min = 3, max = 50)
   private String name;
   private String description;

   @NotNull
   private PenaltyCategory category;

   private PenaltyLength penaltyLength;
   private boolean releasable = true;
   private List<PenaltyLength> penaltyLengths;
   private List<PenaltyCategory> categories;

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
    * Returns a name for this violation.
    *
    * @return
    */
   public String getName() {
      return name;
   }

   /**
    * Sets a name for this violation.
    *
    * @param name
    */
   public void setName(String name) {
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
    * Sets a description for this violation.
    *
    * @param description
    */
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * Returns the violation category.
    *
    * @return
    */
   public PenaltyCategory getCategory() {
      return category;
   }

   /**
    * Sets the violation category.
    *
    * @param category
    */
   public void setCategory(PenaltyCategory category) {
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

   /*---------- Select element options ----------*/

   public List<PenaltyLength> getPenaltyLengths() {
      if (penaltyLengths == null) {
         penaltyLengths = Arrays.asList(PenaltyLength.values());
      }
      return penaltyLengths;
   }

   public List<PenaltyCategory> getCategories() {
      if (categories == null) {
         categories = Arrays.asList(PenaltyCategory.values());
      }
      return categories;
   }
}
