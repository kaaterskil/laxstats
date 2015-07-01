package laxstats.api.people;

import laxstats.api.AggregateId;

/**
 * {@code PerdonId} represents the aggregate identifier of a person.
 */
public class PersonId extends AggregateId {
   private static final long serialVersionUID = 3188225379496683390L;

   /**
    * Creates an new {@code PersonId} with an internally generated key.
    */
   public PersonId() {
      super();
   }

   /**
    * Creates a {@code PersonId} with the given primary key.
    *
    * @param identifier
    */
   public PersonId(String identifier) {
      super(identifier);
   }
}
