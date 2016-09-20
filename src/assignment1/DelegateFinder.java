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
        Problem p = new Problem(delegates, graph, start, end, initial);
        return p.solve();
    }

    private static class Problem{
        private DGraphAdj<Vertex, Event> graph;
        private Vertex start;
        private Vertex end;
        private Set<Delegate> initial;
        private Set<Delegate> delegates;

        public Problem(Set<Delegate> delegates,
                       DGraphAdj<Vertex, Event> graph, Vertex start, Vertex end,
                       Set<Delegate> initial){
            this.graph = graph;
            this.start = start;
            this.end = end;
            this.initial = initial;
            this.delegates = delegates;
        }

        public Set<Delegate> solve(){
            Set<Delegate> maybe = new HashSet<>();
            Queue<Step> steps = new LinkedList<>();
            steps.add(new Step(start, initial));
            while (!steps.isEmpty()){
                Step s = steps.remove();
                if (s.v.equals(end)){
                    maybe.addAll(s.delegates);
                } else {
                    steps.addAll(step(s));
                }
            }
            return maybe;
        }


        public List<Step> step(Step s){
            List<Step> result = new ArrayList<>();
            for (AdjacentEdge<Vertex, Event> e :
                    graph.adjacent(s.v)) {
                Event m = e.edgeInfo;
                Set<Delegate> newDelegates = new HashSet<>();
                for (Delegate d : delegates) {
                    Set<Delegate> dependent = m.getDependentDelegates(d);
                    dependent.retainAll(s.delegates);
                    if (!dependent.isEmpty()){
                        newDelegates.add(d);
                    }
                }
                result.add(new Step(e.target, newDelegates));
            }
            return result;
        }

        private class Step{
            public Vertex v;
            public Set<Delegate> delegates;
            public Step(Vertex v, Set<Delegate> current){
                this.v = v;
                this.delegates = current;
            }
        }
    }
}
