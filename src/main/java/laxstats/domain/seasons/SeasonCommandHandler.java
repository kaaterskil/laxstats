package laxstats.domain.seasons;

import laxstats.api.seasons.CreateSeason;
import laxstats.api.seasons.DeleteSeason;
import laxstats.api.seasons.SeasonId;
import laxstats.api.seasons.UpdateSeason;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * {@code SeasonCommandHandler} manages commands for the season aggregate.
 */
@Component
public class SeasonCommandHandler {

   private Repository<Season> repository;

   @Autowired
   @Qualifier("seasonRepository")
   public void setRepository(Repository<Season> seasonRepository) {
      repository = seasonRepository;
   }

   /**
    * Creates a new season aggregate with information from the payload of the given command, and
    * returns the aggregate identifier.
    *
    * @param command
    * @return
    */
   @CommandHandler
   public SeasonId handle(CreateSeason command) {
      final SeasonId identifier = command.getSeasonId();
      final Season aggregate = new Season(identifier, command.getSeasonDTO());
      repository.add(aggregate);
      return identifier;
   }

   /**
    * Updates an existing season aggregate with information from the payload of the given command.
    *
    * @param command
    */
   @CommandHandler
   public void handle(UpdateSeason command) {
      final SeasonId identifier = command.getSeasonId();
      final Season aggregate = repository.load(identifier);
      aggregate.update(identifier, command.getSeasonDTO());
   }

   /**
    * Deletes the season aggregate matching the identifier in the given payload.
    *
    * @param command
    */
   @CommandHandler
   public void handle(DeleteSeason command) {
      final SeasonId identifier = command.getSeasonId();
      final Season aggregate = repository.load(identifier);
      aggregate.delete(identifier);
   }
}
