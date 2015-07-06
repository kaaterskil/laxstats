package laxstats.web.violations;

import laxstats.api.violations.PenaltyCategory;
import laxstats.api.violations.PenaltyLength;

public interface ViolationResource {

   /**
    * Returns the primary key for this violation.
    *
    * @return
    */
   public String getId();

   /**
    * Sets the primary key for this violation.
    *
    * @param id
    */
   public void setId(String id);

   /**
    * Returns the name of this violation. Never null.
    *
    * @return
    */
   public String getName();

   /**
    * Sets a name for this violation. Must not be null.
    *
    * @param name
    */
   public void setName(String name);

   /**
    * Returns a description of this violation, or null if none.
    *
    * @return
    */
   public String getDescription();

   /**
    * Sets a description for this violation. Use null for none.
    *
    * @param description
    */
   public void setDescription(String description);

   /**
    * Returns the violation category. Never null.
    *
    * @return
    */
   public PenaltyCategory getCategory();

   /**
    * Sets the violation category. Must not be null.
    *
    * @param category
    */
   public void setCategory(PenaltyCategory category);

   /**
    * Returns the length of the penalty for this violation.
    *
    * @return
    */
   public PenaltyLength getPenaltyLength();

   /**
    * Sets the length of the penalty for this violation.
    *
    * @param penaltyLength
    */
   public void setPenaltyLength(PenaltyLength penaltyLength);

   /**
    * Returns true if the player committing this violation can be released from the penalty, false
    * otherwise.
    *
    * @return
    */
   public boolean isReleasable();

   /**
    * Sets a flag indicating whether a player committing this violation can be released from the
    * penalty.
    *
    * @param releasable
    */
   public void setReleasable(boolean releasable);

}
