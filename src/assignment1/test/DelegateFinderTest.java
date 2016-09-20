package assignment1.test;

import assignment1.*;
import graphs.DGraphAdj;
import graphs.Vertex;

import java.util.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Basic tests for the {@link DelegateFinder} implementation class.
 * 
 * We will use a more comprehensive test suite to test your code, so you should
 * add your own tests to this test suite to help you to debug your
 * implementation.
 */
public class DelegateFinderTest {

	/** A basic test based on the example from the assignment handout **/
	@Test
	public void basicTest() {

		// the delegates who will take part in the summit
		Delegate[] delegates = { new Delegate("d0"), new Delegate("d1"),
				new Delegate("d2"), new Delegate("d3") };

		// create the events in the summit
		Delegate[][][] eventArray = {
				{ { delegates[0] }, { delegates[1] }, { delegates[2] },
						{ delegates[3] } },

				{ {}, { delegates[0] }, { delegates[1], delegates[2] },
						{ delegates[3] } },

				{ { delegates[0] }, { delegates[1] }, { delegates[2] },
						{ delegates[3] } }, };

		Event[] events = { createEvent(delegates, eventArray[0]),
				createEvent(delegates, eventArray[1]),
				createEvent(delegates, eventArray[2]) };

		// the graph describing the possible proceedings of the summit
		DGraphAdj<Vertex, Event> graph = new DGraphAdj<>();
		Vertex[] vertices = { new Vertex(), new Vertex(), new Vertex() };
		for (Vertex v : vertices) {
			graph.addVertex(v);
		}
		graph.addEdge(vertices[0], vertices[1], events[0]);
		graph.addEdge(vertices[1], vertices[2], events[1]);
		graph.addEdge(vertices[2], vertices[1], events[2]);

		// the start and end vertices of the proceedings graph
		Vertex start = vertices[0];
		Vertex end = vertices[2];

		// the delegates in favour of the decision at the start of the summit
		Set<Delegate> initial = new HashSet<>();
		initial.add(delegates[0]);

		// the expected result of the test
		Set<Delegate> expectedResult = new HashSet<>();
		expectedResult.add(delegates[1]);
		expectedResult.add(delegates[2]);

		// the actual result of the test
		Set<Delegate> actualResult = DelegateFinder.findDelegates(
				new HashSet<Delegate>(Arrays.asList(delegates)), graph, start,
				end, initial);

		Assert.assertEquals(expectedResult, actualResult);
	}

	/**
	 * A helper function for creating an event from an array of the delegates
	 * who will take part in the event, and an array of arrays describing the
	 * corresponding dependencies for each delegate.
	 */
	private static Event createEvent(Delegate[] delegatesArray,
			Delegate[][] dependentArray) {
		Map<Delegate, Set<Delegate>> event = new HashMap<>();
		for (int i = 0; i < delegatesArray.length; i++) {
			event.put(delegatesArray[i],
					new HashSet<Delegate>(Arrays.asList(dependentArray[i])));
		}
		return new Event(event);
	}

}
