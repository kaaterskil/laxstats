package laxstats.query.plays;

import laxstats.api.plays.PenaltyTypeCreatedEvent;
import laxstats.api.plays.PenaltyTypeDTO;
import laxstats.api.plays.PenaltyTypeDeletedEvent;
import laxstats.api.plays.PenaltyTypeUpdatedEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PenaltyTypeListener {
	private PenaltyTypeQueryRepository penaltyTypeRepository;

	@Autowired
	public void setPenaltyTypeRepository(
			PenaltyTypeQueryRepository penaltyTypeRepository) {
		this.penaltyTypeRepository = penaltyTypeRepository;
	}

	@EventHandler
	protected void handle(PenaltyTypeCreatedEvent event) {
		final PenaltyTypeDTO dto = event.getPenaltyTypeDTO();

		final PenaltyTypeEntry penaltyType = new PenaltyTypeEntry();
		penaltyType.setId(event.getPenaltyTypeId().toString());
		penaltyType.setCategory(dto.getCategory());
		penaltyType.setCreatedAt(dto.getCreatedAt());
		penaltyType.setCreatedBy(dto.getCreatedBy());
		penaltyType.setDescription(dto.getDescription());
		penaltyType.setModifiedAt(dto.getModifiedAt());
		penaltyType.setModifiedBy(dto.getModifiedBy());
		penaltyType.setName(dto.getName());
		penaltyType.setPenaltyLength(dto.getPenaltyLength());
		penaltyType.setReleasable(dto.isReleasable());

		penaltyTypeRepository.save(penaltyType);
	}

	@EventHandler
	protected void handle(PenaltyTypeUpdatedEvent event) {
		final PenaltyTypeDTO dto = event.getPenaltyTypeDTO();
		final PenaltyTypeEntry entry = penaltyTypeRepository.findOne(event
				.getPenaltyTypeId().toString());

		entry.setCategory(dto.getCategory());
		entry.setDescription(dto.getDescription());
		entry.setModifiedAt(dto.getModifiedAt());
		entry.setModifiedBy(dto.getModifiedBy());
		entry.setName(dto.getName());
		entry.setPenaltyLength(dto.getPenaltyLength());
		entry.setReleasable(dto.isReleasable());

		penaltyTypeRepository.save(entry);
	}

	@EventHandler
	protected void handle(PenaltyTypeDeletedEvent event) {
		penaltyTypeRepository.delete(event.getPenaltyTypeId().toString());
	}
}
