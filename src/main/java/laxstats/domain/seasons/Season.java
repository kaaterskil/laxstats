package laxstats.domain.seasons;

import laxstats.api.seasons.SeasonCreated;
import laxstats.api.seasons.SeasonDTO;
import laxstats.api.seasons.SeasonDeleted;
import laxstats.api.seasons.SeasonId;
import laxstats.api.seasons.SeasonUpdated;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.LocalDate;

/**
 * {@code Season} is a persistent domain object model representing a playing season that defines a
 * period of competition with a defined start and end date.
 */
public class Season extends AbstractAnnotatedAggregateRoot<SeasonId> {
   private static final long serialVersionUID = 8223916312274072503L;

   @AggregateIdentifier
   private SeasonId seasonId;
   private String description;
   private LocalDate startsOn;
   private LocalDate endsOn;

   /**
    * Applies a creation event to a season aggregate.
    *
    * @param seasonId
    * @param seasonDTO
    */
   public Season(SeasonId seasonId, SeasonDTO seasonDTO) {

      apply(new SeasonCreated(seasonId, seasonDTO));
   }

   /**
    * Creates a season aggregate. Internal use only.
    */
   protected Season() {
   }

   /**
    * Instructs the framework to update the season aggregate with the given identifier with the
    * given information.
    *
    * @param seasonId
    * @param seasonDTO
    */
   public void update(SeasonId seasonId, SeasonDTO seasonDTO) {
      apply(new SeasonUpdated(seasonId, seasonDTO));
   }

   /**
    * Instructs the framework to delete the season aggregate with the given identifier.
    *
    * @param seasonId
    */
   public void delete(SeasonId seasonId) {
      apply(new SeasonDeleted(seasonId));
   }

   /**
    * Stores the initial state of this season aggregate.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(SeasonCreated event) {
      final SeasonDTO dto = event.getSeasonDTO();
      seasonId = event.getSeasonId();
      description = dto.getDescription();
      startsOn = dto.getStartsOn();
      endsOn = dto.getEndsOn();
   }

   /**
    * Updates the state of this season aggregate.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(SeasonUpdated event) {
      final SeasonDTO dto = event.getSeasonDTO();
      description = dto.getDescription();
      startsOn = dto.getStartsOn();
      endsOn = dto.getEndsOn();
   }

   /**
    * Marks this season aggregate for deletion.
    *
    * @param event
    */
   @EventSourcingHandler
   protected void handle(SeasonDeleted event) {
      markDeleted();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public SeasonId getIdentifier() {
      return seasonId;
   }

   /**
    * Returns a description for this season aggregate, or null if none.
    *
    * @return
    */
   public String getDescription() {
      return description;
   }

   /**
    * Returns the starting date of this season aggregate.
    *
    * @return
    */
   public LocalDate getStartsOn() {
      return startsOn;
   }

   /**
    * Returns the ending date of this season aggregate, or null.
    *
    * @return
    */
   public LocalDate getEndsOn() {
      return endsOn;
   }
}
