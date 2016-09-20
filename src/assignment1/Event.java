package assignment1;

import java.util.*;

/**
 * A class to represent an event at the summit.
 */
public class Event implements Iterable<Delegate> {

	// A mapping from the delegates who are taking part in the event to the
	// delegates that their decision will be based on after the event.
	private Map<Delegate, Set<Delegate>> dependentDelegates;

	/*
	 * invariant: for each delegate d in the dependentDelegates.keySet(),
	 * dependentDelegates.get(d) is a subset of dependentDelegates.keySet().
	 */

	/**
	 * Creates a new event from the given mapping.
	 * 
	 * @param dependentDelegates
	 *            A mapping from the delegates who are taking part in the event
	 *            to the delegates that each one will be basing their decision
	 *            on at the end of the event.
	 * 
	 * @throws IllegalArgumentException
	 *             If there exists a delegate d in dependentDelegates.keySet(),
	 *             such that dependentDelegates.get(d) is not a subset of
	 *             dependentDelegates.keySet().
	 */
	public Event(Map<Delegate, Set<Delegate>> dependentDelegates) {
		this.dependentDelegates = new HashMap<>();
		for (Delegate d : dependentDelegates.keySet()) {
			if (!dependentDelegates.keySet().containsAll(
					dependentDelegates.get(d))) {
				throw new IllegalArgumentException(
						"A delegate can only base their decision on the "
								+ "delegates who take part in the event.");
			}
			this.dependentDelegates.put(d, new HashSet<Delegate>(
					dependentDelegates.get(d)));
		}
	}

	/**
	 * Returns an iterator over the set of delegates taking part in the event.
	 */
	@Override
	public Iterator<Delegate> iterator() {
		return dependentDelegates.keySet().iterator();
	}

	/**
	 * Returns the set of delegates that the given delegate will be basing their
	 * decision on at the end of the event.
	 * 
	 * @param delegate
	 *            the delegate whose dependent delegates will be returned.
	 * @return The set of delegates that the given delegate will base their
	 *         decision on after the event.
	 */
	public Set<Delegate> getDependentDelegates(Delegate delegate) {
		Set<Delegate> delegates = dependentDelegates.get(delegate);
		return new HashSet<>(delegates);
	}

}
