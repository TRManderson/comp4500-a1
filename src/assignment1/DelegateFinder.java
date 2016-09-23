package assignment1;

import graphs.*;
import graphs.Graph.AdjacentEdge;
import sun.misc.Cache;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        private static class CacheKey{
            private Vertex source;
            private Vertex target;
            public CacheKey(Vertex source, Vertex target){
                this.source = source;
                this.target = target;
            }

            @Override
            public String toString() {
                return source + " -> " + target ;
            }

            @Override
            public boolean equals(Object obj) {
                return obj.toString().equals(toString());
            }

            @Override
            public int hashCode() {
                return source.toString().hashCode() + 13 * target.toString().hashCode();
            }
        };
        private DGraphAdj<Vertex, Event> graph;
        private Vertex start;
        private Vertex end;
        private Set<Delegate> initial;
        private Set<Delegate> delegates;
        private Map<CacheKey, Map<Set<Delegate>, Set<Delegate>>> cache;

        public Problem(Set<Delegate> delegates,
                       DGraphAdj<Vertex, Event> graph, Vertex start, Vertex end,
                       Set<Delegate> initial){
            this.graph = graph;
            this.start = start;
            this.end = end;
            this.initial = initial;
            this.delegates = delegates;
            cache = new HashMap<>();
        }

        public Set<Delegate> solve(){
            Set<Delegate> result = new HashSet<>();
            Queue<Step> steps = new LinkedList<>();
            steps.add(new Step(start, initial));
            while (!steps.isEmpty()){
                Step s = steps.remove();
                for (AdjacentEdge<Vertex, Event> edge : graph.adjacent(s.vertex)){
                    CacheKey key = new CacheKey(s.vertex, edge.target);
                    if (!cache.containsKey(key)){
                        cache.put(key, new HashMap<>());
                    }
                    if(cache.get(key).containsKey(s.delegates)){
                        continue;
                    }
                    Set<Delegate> newDelegates = stepDelegates(edge.edgeInfo, s.delegates);
                    if (edge.target.equals(end)){
                        result.addAll(newDelegates);
                    }
                    cache.get(key).put(s.delegates, newDelegates);
                    steps.add(new Step(edge.target, newDelegates));
                }
            }

            return result;
        }

        public Set<Delegate> stepDelegates(Event e, Set<Delegate> initial){
            Set<Delegate> result = new HashSet<>();
            for (Delegate d : delegates){
                Set<Delegate> dependent = e.getDependentDelegates(d);
                dependent.retainAll(initial);
                if (!dependent.isEmpty()){
                    result.add(d);
                }
            }
            return result;
        }

        private static class Step{
            // Equivalent to the tuples used in the queue pseudocode
            public Vertex vertex;
            public Set<Delegate> delegates;
            public Step(Vertex v, Set<Delegate> current){
                this.vertex = v;
                this.delegates = current;
            }
        }
    }
}
