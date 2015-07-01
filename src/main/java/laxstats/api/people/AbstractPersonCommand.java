package laxstats.api.people;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * {@code AbstractPersonCommand} is the base class for all person commands.
 */
public abstract class AbstractPersonCommand {

   @TargetAggregateIdentifier
   private final PersonId personId;

   protected AbstractPersonCommand(PersonId personId) {
      this.personId = personId;
   }

   /**
    * Returns the aggregate identifier of the person on which to perform the command.
    * 
    * @return
    */
   public PersonId getPersonId() {
      return personId;
   }

}
