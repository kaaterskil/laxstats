package laxstats.domain.plays;

import laxstats.api.plays.CreatePenaltyTypeCommand;
import laxstats.api.plays.DeletePenaltyTypeCommand;
import laxstats.api.plays.PenaltyTypeId;
import laxstats.api.plays.UpdatePenaltyTypeCommand;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PenaltyTypeCommandHandler {

	private Repository<PenaltyType> repository;

	@Autowired
	@Qualifier("penaltyTypeRepository")
	public void setRepository(Repository<PenaltyType> repository) {
		this.repository = repository;
	}

	@CommandHandler
	public PenaltyTypeId handle(CreatePenaltyTypeCommand command) {
		final PenaltyTypeId identifier = command.getPenaltyTypeId();
		final PenaltyType penaltyType = new PenaltyType(identifier,
				command.getPenaltyTypeDTO());
		repository.add(penaltyType);
		return identifier;
	}

	@CommandHandler
	public void handle(UpdatePenaltyTypeCommand command) {
		final PenaltyTypeId identifier = command.getPenaltyTypeId();
		final PenaltyType penaltyType = repository.load(identifier);
		penaltyType.update(command.getPenaltyTypeId(),
				command.getPenaltyTypeDTO());

	}

	@CommandHandler
	public void handle(DeletePenaltyTypeCommand command) {
		final PenaltyTypeId identifier = command.getPenaltyTypeId();
		final PenaltyType penaltyType = repository.load(identifier);
		penaltyType.delete(command.getPenaltyTypeId());
	}
}
