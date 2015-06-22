package laxstats.api;

import java.io.Serializable;

import org.axonframework.domain.IdentifierFactory;

/**
 * {@code AggregateId} is the base class for all aggregate identifiers.
 */
public class AggregateId implements Serializable {
   private static final long serialVersionUID = -2466843517557507562L;

   private final String identifier;

   /**
    * Creates a new {@code AggregateId} with an internally generated key. To be used when creating a
    * new persistent aggregate.
    */
   public AggregateId() {
      identifier = IdentifierFactory.getInstance().generateIdentifier();
   }

   /**
    * Creates a new {@code AggregateId} with the given key. To be used when instantiating a
    * persisted aggregate.
    *
    * @param identifier
    */
   public AggregateId(String identifier) {
      this.identifier = identifier;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      return identifier.hashCode();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj != null && getClass() == obj.getClass()) {
         final AggregateId that = (AggregateId)obj;
         return identifier.equals(that.identifier);
      }
      return false;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString() {
      return identifier;
   }
}
