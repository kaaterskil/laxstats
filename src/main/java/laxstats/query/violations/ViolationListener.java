package laxstats.query.violations;

import laxstats.api.violations.ViolationCreated;
import laxstats.api.violations.ViolationDTO;
import laxstats.api.violations.ViolationDeleted;
import laxstats.api.violations.ViolationUpdated;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * {@code ViolationListener} manages events that write to the query database.
 */
@Component
public class ViolationListener {
   private ViolationQueryRepository penaltyTypeRepository;

   @Autowired
   public void setPenaltyTypeRepository(ViolationQueryRepository penaltyTypeRepository) {
      this.penaltyTypeRepository = penaltyTypeRepository;
   }

   /**
    * Creates and persists a {@code ViolationEntry} with information in the payload of the given
    * event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(ViolationCreated event) {
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

   /**
    * Updates and persists a {@code ViolationEntry} with information in the payload of the given
    * event.
    *
    * @param event
    */
   @EventHandler
   protected void handle(ViolationUpdated event) {
      final ViolationDTO dto = event.getPenaltyTypeDTO();
      final ViolationEntry entry =
         penaltyTypeRepository.findOne(event.getPenaltyTypeId().toString());

      entry.setCategory(dto.getCategory());
      entry.setDescription(dto.getDescription());
      entry.setModifiedAt(dto.getModifiedAt());
      entry.setModifiedBy(dto.getModifiedBy());
      entry.setName(dto.getName());
      entry.setPenaltyLength(dto.getPenaltyLength());
      entry.setReleasable(dto.isReleasable());

      penaltyTypeRepository.save(entry);
   }

   /**
    * Deletes a {@code ViolationEntry} matching the primary key in the payload of the given event
    * from the query database.
    *
    * @param event
    */
   @EventHandler
   protected void handle(ViolationDeleted event) {
      penaltyTypeRepository.delete(event.getPenaltyTypeId().toString());
   }
}
