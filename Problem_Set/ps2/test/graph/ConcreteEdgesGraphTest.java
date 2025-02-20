/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public <L> Graph<L> emptyInstance() {
        return new ConcreteEdgesGraph<L>();
    }

    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteEdgesGraph.toString()
    @Test
    public void testToString() {
        Graph<String> g = emptyInstance();
        g.add("1");
        g.set("1", "2", 1);
        g.set("2", "3", 2);
        g.set("1", "3", 2);
        g.set("4", "5", 4);
        System.out.println(g.toString());
        assertEquals("1--1-->2, 2--2-->3, 1--2-->3, 4--4-->5", g.toString());

    }
    @Test
    public void testIntegerToString(){
        Graph<Integer> g = emptyInstance();
        g.add(1);
        g.set(1,3,2);
        System.out.println(g);
    }
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    @Test
    public void testEdgeObservers() {
        Graph<String> graph = emptyInstance();
        ConcreteEdgesGraph<String> c = (ConcreteEdgesGraph<String>) graph;
        ConcreteEdgesGraph.Edge edge = c.new Edge("a","b",3);
        assertEquals(3, edge.getWeight());
        assertEquals("a", edge.getSource());
        assertEquals("b", edge.getTarget());
    }
//
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEdge() {
        Graph<String> graph = emptyInstance();
        ConcreteEdgesGraph.Edge edge =  ((ConcreteEdgesGraph<String>) graph).new Edge("aa", "bb", -2);
    }

    // TODO tests for operations of Edge
    
}
