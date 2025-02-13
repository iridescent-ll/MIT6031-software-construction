/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */

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
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }

    @Test
    public void testWrittenByEmptyResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "noOne");
        assertEquals("expected singleton list", 0, writtenBy.size());
         writtenBy = Filter.writtenBy(Collections.emptyList(),"alyssa");
         assertTrue(writtenBy.isEmpty());
    }

    @Test
    public void testWrittenByMultipleTweetsMultiResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2, tweet3 ,tweet4, tweet5), "dylan");
        assertEquals("expected list.size equals 2", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet4));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet5));
    }
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }

    @Test
    public void testInTimespanMultipleTweetsEmptyResults() {
        Instant testStart = Instant.now();
        Instant testEnd = Instant.now().plusSeconds(60);

        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));

        assertTrue("expected empty list", inTimespan.isEmpty());
        assertFalse("expected list to not contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
    }
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2, tweet3)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    @Test
    public void testContainingEmptyResults() {
        List<Tweet> containing1 = Filter.containing(Collections.emptyList(), Arrays.asList("talk"));
        assertTrue(containing1.isEmpty());
        List<Tweet> containing2 = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5), Collections.emptyList());
        assertTrue(containing2.isEmpty());
    }

    @Test
    public void testContainingMultiResults() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5,tweet6),Arrays.asList("talk","LoGos"));
        assertEquals(5,containing.size());
    }
    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
