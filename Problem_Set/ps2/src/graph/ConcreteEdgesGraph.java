/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    // Abstraction function:
    //
    // Representation invariant:
    //   All of the targets and sources in vertices, edge's weight >0;
    // Safety from rep exposure:
    //
    
    // TODO constructor
    
    // TODO checkRep
    
    @Override public boolean add(L vertex) {
//        throw new RuntimeException("not implemented");
        return vertices.add(vertex);
    }
    
    @Override public int set(L source, L target, int weight) {
//        throw new RuntimeException("not implemented");
        if(weight < 0){
            throw new IllegalArgumentException("Weight must be non-negative.");
        }
        for (Edge edge : edges) {
            int oldWeight = edge.find(source, target);
            if(oldWeight!=0){
               edge.setWeight(weight);
               return oldWeight;
            }
        }
        Edge edge = new Edge(source, target, weight);
        edges.add(edge);
        return 0;
    }
    
    @Override public boolean remove(L vertex) {
//        throw new RuntimeException("not implemented");
        boolean modified = false;
        if(!vertices.contains(vertex)){
            return false;
        }
        Iterator<Edge> it = edges.iterator();
        if(it.hasNext()){
            Edge next = it.next();
            if(next.find(vertex)){
              it.remove();
              modified = true;
            }
        }
        return modified;
    }
    
    @Override public Set<L> vertices() {
//        throw new RuntimeException("not implemented");
        return new HashSet<>(vertices);
    }
    
    @Override public Map<L, Integer> sources(L target) {
//        throw new RuntimeException("not implemented");
        return edges.stream()
                .filter(edge -> edge.getTarget()==target)
                .collect(Collectors.toMap(Edge::getSource, Edge::getWeight));
    }
    
    @Override public Map<L, Integer> targets(L source) {
//        throw new RuntimeException("not implemented");
        return edges.stream()
                .filter(edge -> edge.getSource()==source)
                .collect(Collectors.toMap(Edge::getTarget, Edge::getWeight));
    }
    
    // TODO toL()

    /**
     * TODO specification
     * Immutable.
     * This class is internal to the rep of ConcreteEdgesGraph.
     *
     * <p>PS2 instructions: the specification and implementation of this class is
     * up to you.
     */
    final class Edge {

        // TODO fields
        private final L source;
        private final L target;
        private int weight;

        // Abstraction function:
        //   (L source, L target, int weight) -> edge(source->target, weight=this.weight)
        // Representation invariant:
        //   weight>0;
        // Safety from rep exposure:
        //   TODO

        public Edge(L source, L target, int weight) {
           this.source = source;
           this.target = target;
           this.weight = weight;
           vertices.addAll(Stream.of(source,target).collect(Collectors.toSet()));
           checkRep();
        }
        // TODO checkRep
        private void checkRep() {
            assert weight > 0 : "weight must be positive";
            assert vertices.contains(source) : "source vertex must be in vertices";
            assert vertices.contains(target) : "target vertex must be in vertices";
            assert source!= target : "source and target vertices must be distinct";
        }

        // TODO methods
        public int find(L source, L target){
            if(source==this.source && target==this.target){
                return this.weight;
            }
            return 0;
        }
        public boolean find(L vertex){
            return target == vertex || source == vertex;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
        public L getSource(){
            return this.source;
        }
        public L getTarget(){
            return this.target;
        }
        public int getWeight(){
            return this.weight;
        }
        // TODO toL()

    }
}

