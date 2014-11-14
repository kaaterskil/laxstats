package laxstats.common;

import java.io.Serializable;

public class AggregateId implements Serializable {
	private static final long serialVersionUID = -6424800008687430708L;
	private String identifier;
    
    public AggregateId() {
	identifier = IdentifierFactory.getInstance().generateIdentifier();
    }
    
    public AggregateId(String identifier) {
	this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public int hashCode() {
	return identifier.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (!(obj instanceof AggregateId)) {
	    return false;
	}
	AggregateId other = (AggregateId) obj;
	if (identifier == null) {
	    if (other.identifier != null) {
		return false;
	    }
	} else if (!identifier.equals(other.identifier)) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "AggregateId [identifier=" + identifier + "]";
    }
}
