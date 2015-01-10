package laxstats.domain.violations;

import laxstats.api.violations.CreateViolationCommand;
import laxstats.api.violations.DeleteViolationCommand;
import laxstats.api.violations.UpdateViolationCommand;
import laxstats.api.violations.ViolationId;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ViolationCommandHandler {

	private Repository<Violation> repository;

	@Autowired
	@Qualifier("penaltyTypeRepository")
	public void setRepository(Repository<Violation> repository) {
		this.repository = repository;
	}

	@CommandHandler
	public ViolationId handle(CreateViolationCommand command) {
		final ViolationId identifier = command.getPenaltyTypeId();
		final Violation aggregate = new Violation(identifier,
				command.getPenaltyTypeDTO());
		repository.add(aggregate);
		return identifier;
	}

	@CommandHandler
	public void handle(UpdateViolationCommand command) {
		final ViolationId identifier = command.getPenaltyTypeId();
		final Violation aggregate = repository.load(identifier);
		aggregate.update(command.getPenaltyTypeId(),
				command.getPenaltyTypeDTO());

	}

	@CommandHandler
	public void handle(DeleteViolationCommand command) {
		final ViolationId identifier = command.getPenaltyTypeId();
		final Violation aggregate = repository.load(identifier);
		aggregate.delete(command.getPenaltyTypeId());
	}
}
