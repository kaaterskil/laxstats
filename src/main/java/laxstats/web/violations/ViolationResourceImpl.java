package laxstats.web.violations;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import laxstats.api.utils.Constants;
import laxstats.api.violations.PenaltyCategory;
import laxstats.api.violations.PenaltyLength;

/**
 * {@code ViolationResource} represents a violation resource for remote clients.
 */
public class ViolationResourceImpl implements ViolationResource {
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
   public ViolationResourceImpl(String id, String name, String description,
      PenaltyCategory category, PenaltyLength penaltyLength, boolean releasable) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.category = category;
      this.penaltyLength = penaltyLength;
      this.releasable = releasable;
   }

   /**
    * Creates an empty {@code SiteResource}.
    */
   public ViolationResourceImpl() {
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
      assert name != null;
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
      assert category != null;
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
}
