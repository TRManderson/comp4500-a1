package assignment1.test;

import assignment1.Delegate;
import assignment1.DelegateFinder;
import assignment1.Event;
import graphs.DGraphAdj;
import graphs.Vertex;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;
import java.util.function.Supplier;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Created by trm on 20/09/16.
 */
@RunWith(Parameterized.class)
public class DelegateFinderTest {
    private Set<Delegate> delegates;
    private DGraphAdj<Vertex, Event> graph;
    private Vertex start;
    private Vertex end;
    private Set<Delegate> initial;
    private Set<Delegate> expectedResult;

    public DelegateFinderTest(Supplier<TestEntry> fixtureGenerator){
        TestEntry entry = fixtureGenerator.get();
        delegates = entry.delegates;
        graph = entry.graph;
        start = entry.start;
        end = entry.end;
        initial = entry.initial;
        expectedResult = entry.expectedResult;
    }

    private static class TestEntry{
        private Set<Delegate> delegates;
        private DGraphAdj<Vertex, Event> graph;
        private Vertex start;
        private Vertex end;
        private Set<Delegate> initial;
        private Set<Delegate> expectedResult;

        public TestEntry(Set<Delegate> delegates,
                         DGraphAdj<Vertex, Event> graph, Vertex start, Vertex end,
                         Set<Delegate> initial, Set<Delegate> expectedResult){
            this.delegates = delegates;
            this.graph = graph;
            this.start = start;
            this.end = end;
            this.initial = initial;
            this.expectedResult = expectedResult;
        }
    }

    @Parameterized.Parameters
    public static Iterable<Supplier<TestEntry>> testSupplier() {
        List<Supplier<TestEntry>> data = new LinkedList<>();
        data.add(() -> {
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

            Set<Delegate> delegateSet = new HashSet<Delegate>();
            delegateSet.addAll(Arrays.asList(delegates));
            return new TestEntry(delegateSet, graph, start, end, initial, expectedResult);
        });

        return data;
    }

    @Test
    public void testExpectedResult(){
        Set<Delegate> result = DelegateFinder.findDelegates(
                delegates, graph,
                start, end,
                initial);
        Assert.assertEquals(expectedResult, result);
    }

    public static Event createEvent(Delegate[] delegatesArray,
                                    Delegate[][] dependentArray) {
        Map<Delegate, Set<Delegate>> event = new HashMap<>();
        for (int i = 0; i < delegatesArray.length; i++) {
            event.put(delegatesArray[i],
                    new HashSet<Delegate>(Arrays.asList(dependentArray[i])));
        }
        return new Event(event);
    }
}
