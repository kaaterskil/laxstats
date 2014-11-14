package laxstats.common;

import java.util.UUID;

/**
 * The identifierFactory is responsible for generating unique identifiers for
 * domain objects.
 * 
 * @author blair
 *
 */
public class IdentifierFactory {

    private static final IdentifierFactory INSTANCE;

    static {
	INSTANCE = new IdentifierFactory();
    }

    /**
     * Returns a singleton instance of the IdentifierFactory.
     * 
     * @return the IdentifierFactory instance.
     */
    public static IdentifierFactory getInstance() {
	return INSTANCE;
    }

    private IdentifierFactory() {
    }

    /**
     * Generate a unique identifier for use by domain objects with randomly
     * chosen <code>java.util.UUID</code>s.
     * 
     * @return a String representation of a unique identifier.
     */
    public String generateIdentifier() {
	return UUID.randomUUID().toString();
    }
}
