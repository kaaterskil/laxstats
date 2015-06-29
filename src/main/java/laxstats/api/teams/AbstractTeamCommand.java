package laxstats.api.teams;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code AbstractTeamCommand} is the base class for all team commands.
 */
public abstract class AbstractTeamCommand {
   @TargetAggregateIdentifier
   private final TeamId teamId;

   /**
    * Creates an {@code AbstractTeamCommand} with the given aggregate identifier.
    *
    * @param teamId
    */
   protected AbstractTeamCommand(TeamId teamId) {
      this.teamId = teamId;
   }

   /**
    * Returns the team aggregate identifier
    *
    * @return
    */
   public TeamId getTeamId() {
      return teamId;
   }
}
