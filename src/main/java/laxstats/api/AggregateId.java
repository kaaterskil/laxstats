package laxstats.api;

import java.io.Serializable;

import org.axonframework.domain.IdentifierFactory;

public class AggregateId implements Serializable {
	private static final long serialVersionUID = -2466843517557507562L;
	
	private final String identifier;
	
	public AggregateId() {
		identifier = IdentifierFactory.getInstance().generateIdentifier();
	}

	public AggregateId(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public int hashCode() {
		return identifier.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj != null && getClass() == obj.getClass()) {
			AggregateId that = (AggregateId) obj;
			return this.identifier.equals(that.identifier);
		}
		return false;
	}

	@Override
	public String toString() {
		return identifier;
	}
}
