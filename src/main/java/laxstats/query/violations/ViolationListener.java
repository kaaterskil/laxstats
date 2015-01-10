package laxstats.query.violations;

import laxstats.api.violations.ViolationCreatedEvent;
import laxstats.api.violations.ViolationDTO;
import laxstats.api.violations.ViolationDeletedEvent;
import laxstats.api.violations.ViolationUpdatedEvent;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViolationListener {
	private ViolationQueryRepository penaltyTypeRepository;

	@Autowired
	public void setPenaltyTypeRepository(
			ViolationQueryRepository penaltyTypeRepository) {
		this.penaltyTypeRepository = penaltyTypeRepository;
	}

	@EventHandler
	protected void handle(ViolationCreatedEvent event) {
		final ViolationDTO dto = event.getPenaltyTypeDTO();

		final ViolationEntry penaltyType = new ViolationEntry();
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
	protected void handle(ViolationUpdatedEvent event) {
		final ViolationDTO dto = event.getPenaltyTypeDTO();
		final ViolationEntry entry = penaltyTypeRepository.findOne(event
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
	protected void handle(ViolationDeletedEvent event) {
		penaltyTypeRepository.delete(event.getPenaltyTypeId().toString());
	}
}
