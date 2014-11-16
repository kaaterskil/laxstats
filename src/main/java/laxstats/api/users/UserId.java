package laxstats.api.users;

import java.io.Serializable;

import org.axonframework.domain.IdentifierFactory;

public class UserId implements Serializable {
	private static final long serialVersionUID = -1004370727752692687L;
	
	private String identifier;
	
	public UserId(){
		identifier = IdentifierFactory.getInstance().generateIdentifier();
	}
	
	public UserId(String identifier) {
		if(identifier == null) {
			throw new IllegalArgumentException("Identifier may not be null");
		}
		this.identifier = identifier;
	}
	
	public boolean equals(Object o) {
		if(o != null && o instanceof UserId) {
			UserId that = (UserId) o;
			return this.identifier.equals(that.identifier);
		}
		return false;
	}
	
	public int hashCode() {
		return identifier.hashCode();
	}

	public String toString() {
		return identifier;
	}
}
