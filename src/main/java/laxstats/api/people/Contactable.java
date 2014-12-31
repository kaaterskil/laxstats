package laxstats.api.people;

/**
 * Interface indicating that the implementing object is an address or contact
 * and that the object can have primacy over other objects in a collection or
 * can indicate that it is no longer usable. The user is responsible for
 * maintaining only one primary object in any collection.
 */
public interface Contactable {
	boolean isPrimary();

	void setPrimary(boolean isPrimary);

	boolean isDoNotUse();

	void setDoNotUse(boolean doNotUse);
}
