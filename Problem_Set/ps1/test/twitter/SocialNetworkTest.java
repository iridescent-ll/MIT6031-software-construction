/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.*;

import org.junit.Test;

public class SocialNetworkTest {

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.now();
    private static final Instant d4 = Instant.now().minusSeconds(2 * 60 * 60);

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype qwer@mit.edu", d2);
    private static final Tweet tweet3 = new Tweet(3, "ci", "rivest talk in 30 minutes #hype@alyssa", d3);
    private static final Tweet tweet4 = new Tweet(4, "dylan", "test @LOGOS @logos", d4);
    private static final Tweet tweet5 = new Tweet(5, "dylan", "test @daoxuan @logos", d4);
    private static final Tweet tweet6 = new Tweet(6, "theQueenOfPain", "Defense of the Ancients", d4);
    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowsGraphNotEmpty() {
        List<Tweet> tweets = Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5);
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);
        Set<Map.Entry<String, Set<String>>> entries = followsGraph.entrySet();
        Iterator<Map.Entry<String, Set<String>>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Set<String>> followGraph = iterator.next();
            System.out.println("Author: "+followGraph.getKey());
            System.out.println("Follows: ");
            for (String s : followGraph.getValue()) {
                System.out.println(s);
            }
        }
        assertFalse("expected empty graph", followsGraph.isEmpty());
    }
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
