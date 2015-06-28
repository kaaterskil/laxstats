package laxstats.domain.violations;

import laxstats.api.violations.PenaltyCategory;
import laxstats.api.violations.PenaltyLength;
import laxstats.api.violations.ViolationCreated;
import laxstats.api.violations.ViolationDTO;
import laxstats.api.violations.ViolationDeleted;
import laxstats.api.violations.ViolationId;
import laxstats.api.violations.ViolationUpdated;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

/**
 * {@code Violation} represents a persistent domain object model of a breach of a defined playing
 * rule.
 */
public class Violation extends AbstractAnnotatedAggregateRoot<ViolationId> {
   private static final long serialVersionUID = -132099412870035181L;

   @AggregateIdentifier
   private ViolationId violationId;

   private String name;
   private String description;
   private PenaltyCategory category;
   private PenaltyLength duration;
   private boolean releasable;

   /**
    * Applies a creation event to a violation aggregate.
    *
    * @param violationId
    * @param violationDTO
    */
   public Violation(ViolationId violationId, ViolationDTO violationDTO) {
      apply(new ViolationCreated(violationId, violationDTO));
   }

   /**
    * Instantiates a violation. Internal use only.
    */
   protected Violation() {
   }

   /**
    * Instructs the framework to update the state of this violation.
    *
    * @param violationId
    * @param violationDTO
    */
   public void update(ViolationId violationId, ViolationDTO violationDTO) {
      apply(new ViolationUpdated(violationId, violationDTO));
   }

   /**
    * Instructs the framework to mark this violation for deletion.
    *
    * @param violationId
    */
   public void delete(ViolationId violationId) {
      apply(new ViolationDeleted(violationId));
   }

   /**
    * Stores and persists the initial state of this violation.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(ViolationCreated event) {
      final ViolationDTO dto = event.getPenaltyTypeDTO();

      violationId = event.getPenaltyTypeId();
      name = dto.getName();
      description = dto.getDescription();
      category = dto.getCategory();
      duration = dto.getPenaltyLength();
      releasable = dto.isReleasable();
   }

   /**
    * Updates and persists the state of this violation.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(ViolationUpdated event) {
      final ViolationDTO dto = event.getPenaltyTypeDTO();

      name = dto.getName();
      description = dto.getDescription();
      category = dto.getCategory();
      duration = dto.getPenaltyLength();
      releasable = dto.isReleasable();
   }

   /**
    * Marks this violation for deletion.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(ViolationDeleted event) {
      markDeleted();
   }

   /**
    *
    * {@inheritDoc}
    */
   @Override
   public ViolationId getIdentifier() {
      return violationId;
   }

   /**
    * Returns the name of this violation.
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
    * Returns the violation category.
    *
    * @return
    */
   public PenaltyCategory getCategory() {
      return category;
   }

   /**
    * Returns the length of the penalty required by this violation.
    *
    * @return
    */
   public PenaltyLength getDuration() {
      return duration;
   }

   /**
    * Returns true if the player who committed this violation can be released from the penalty,
    * false otherwise.
    *
    * @return
    */
   public boolean isReleasable() {
      return releasable;
   }
}
