package laxstats.domain.violations;

import laxstats.api.violations.CreateViolation;
import laxstats.api.violations.DeleteViolation;
import laxstats.api.violations.UpdateViolation;
import laxstats.api.violations.ViolationId;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * {@code ViolationCommandHandler} manages commands for the violation aggregate.
 */
@Component
public class ViolationCommandHandler {

   private Repository<Violation> repository;

   @Autowired
   @Qualifier("penaltyTypeRepository")
   public void setRepository(Repository<Violation> repository) {
      this.repository = repository;
   }

   /**
    * Creates a new violation aggregate with information from the payload of the given command, and
    * returns the aggregate identifier.
    * 
    * @param command
    * @return
    */
   @CommandHandler
   public ViolationId handle(CreateViolation command) {
      final ViolationId identifier = command.getPenaltyTypeId();
      final Violation aggregate = new Violation(identifier, command.getPenaltyTypeDTO());
      repository.add(aggregate);
      return identifier;
   }

   /**
    * Updates the state of a violation with information from the payload of the given command.
    * 
    * @param command
    */
   @CommandHandler
   public void handle(UpdateViolation command) {
      final ViolationId identifier = command.getPenaltyTypeId();
      final Violation aggregate = repository.load(identifier);
      aggregate.update(command.getPenaltyTypeId(), command.getPenaltyTypeDTO());

   }

   /**
    * Marks for deletion the violation matching the aggregate identifier in the given command.
    * 
    * @param command
    */
   @CommandHandler
   public void handle(DeleteViolation command) {
      final ViolationId identifier = command.getPenaltyTypeId();
      final Violation aggregate = repository.load(identifier);
      aggregate.delete(command.getPenaltyTypeId());
   }
}
