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
public class ViolationForm implements ViolationResource, Serializable {
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
   public String getDescription() {
      return description;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setDescription(String description) {
      this.description = description;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public PenaltyCategory getCategory() {
      return category;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setCategory(PenaltyCategory category) {
      this.category = category;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public PenaltyLength getPenaltyLength() {
      return penaltyLength;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setPenaltyLength(PenaltyLength penaltyLength) {
      this.penaltyLength = penaltyLength;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isReleasable() {
      return releasable;
   }

   /**
    * {@inheritDoc}
    */
   @Override
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
