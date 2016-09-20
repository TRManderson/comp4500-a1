package assignment1;

import graphs.*;
import graphs.Graph.AdjacentEdge;
import java.util.*;

public class DelegateFinder {

	/**
	 * @param delegates
	 *            The set of delegates who will take part in the summit. You may
	 *            assume that the set of delegates is not null and does not
	 *            contain null values.
	 * @param graph
	 *            A (non-null) directed graph describing the possible
	 *            proceedings of the summit. The graph should consist of zero or
	 *            more vertices, and each vertex in the graph should be
	 *            associated with an event that the given delegates take part
	 *            in.
	 * @param start
	 *            A vertex from the graph that is designated as the start
	 *            vertex. Every vertex of the graph should be reachable from the
	 *            start vertex.
	 * @param end
	 *            A vertex from the graph that is designated as the end vertex.
	 *            Every vertex of the graph should be able to reach the end
	 *            vertex.
	 * @param initial
	 *            A subset of the given delegates who are in favour of the
	 *            decision at the start of the summit.
	 * @return This method returns the set of delegates who may be in favour of
	 *         the decision at the end of the summit. (See assignment handout
	 *         for details.)
	 */
	public static Set<Delegate> findDelegates(Set<Delegate> delegates,
			DGraphAdj<Vertex, Event> graph, Vertex start, Vertex end,
			Set<Delegate> initial) {
		return null; // REMOVE THIS LINE AND WRITE THIS METHOD
	}

}
