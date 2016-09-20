package assignment1;

/**
 * An immutable class to represent a delegate.
 * 
 * Each delegate has a unique identifier. Two delegates are considered to be
 * equal if they have the same identifier.
 */
public class Delegate {

	// the identifier of the delegate
	private String identifier;

	/* invariant: identifier != null */

	/**
	 * Creates a new delegate with the given identifier.
	 * 
	 * @param identifier
	 *            the identifier of the delegate
	 * @throws NullPointerException
	 *             if identifier is null
	 */
	public Delegate(String identifier) {
		if (identifier == null) {
			throw new NullPointerException(
					"The identifier of a delegate cannot be null.");
		}
		this.identifier = identifier;
	}

	/**
	 * Returns the identifier of the delegate.
	 * 
	 * @return the identifier of the delegate
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Two delegates are equal if they are both instances of the Delegate class
	 * and they have the same identifier.
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Delegate)) {
			return false;
		} else {
			Delegate d = (Delegate) object;
			return identifier.equals(d.identifier);
		}
	}

	@Override
	public int hashCode() {
		return identifier.hashCode();
	}

	@Override
	public String toString() {
		return identifier;
	}

}
