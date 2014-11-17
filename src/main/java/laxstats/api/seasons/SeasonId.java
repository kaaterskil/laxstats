package laxstats.api.seasons;

import java.io.Serializable;

import org.axonframework.domain.IdentifierFactory;

public class SeasonId implements Serializable {
	private static final long serialVersionUID = -6670155975114925291L;
	
	private String identifier;
	
	public SeasonId() {
		identifier = IdentifierFactory.getInstance().generateIdentifier();
	}

	public SeasonId(String identifier) {
		this.identifier = identifier;
	}

	public int hashCode() {
		return identifier.hashCode();
	}

	public boolean equals(Object o) {
		if(o != null && o instanceof SeasonId) {
			SeasonId that = (SeasonId) o;
			return this.identifier.equals(that.identifier);
		}
		return false;
	}
	
	public String toString() {
		return identifier;
	}
}
