/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class ExtractTest {

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

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    /**
     * 测试Extract.getTimeSpan获取两条推文timespan
     */
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }

    /**
     * 测试超过两条的推文的Timespan
     *
     */
    @Test
    public void testGetTimespanMoreThanTwoTweets(){
        List<Tweet> tweets = Stream.of(tweet1, tweet2, tweet3, tweet4).collect(Collectors.toList());
        Timespan timespan = Extract.getTimespan(tweets);
        assertEquals("start",d1,timespan.getStart());
        assertEquals("end",d3,timespan.getEnd());
    }
    @Test
    public void testOneTweetsTimespan(){
        List<Tweet> tweets = Arrays.asList(tweet1);
        Timespan timespan = Extract.getTimespan(tweets);
        assertEquals("d1", d1, timespan.getStart());
        assertEquals("d2", d1, timespan.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    @Test
    public void testGetMentionUserOneMention(){
        List<Tweet> tweets = Arrays.asList(tweet3);
        Set<String> mentionedUsers = Extract.getMentionedUsers(tweets);
        assertEquals("alyssa",mentionedUsers.stream().collect(Collectors.toList()).get(0));
    }
    @Test
    public void testGetMentionUserMoreThanOneMention(){
        List<Tweet> tweets = Arrays.asList(tweet1, tweet2, tweet3,tweet4, tweet5);
        Set<String> mentionedUsers = Extract.getMentionedUsers(tweets);
        for (String mentionedUser : mentionedUsers) {
            System.out.println(mentionedUser);
        }
//        assertEquals("alyssa",mentionedUsers.stream().collect(Collectors.toList()).get(0));
    }
    @Test
    public void testGetMentionUserRepeatMention(){
        List<Tweet> tweets = Arrays.asList(tweet4);
        Set<String> mentionedUsers = Extract.getMentionedUsers(tweets);
        assertEquals(1,mentionedUsers.size());
    }
    @Test
    public void testGetMentionUserExcludeEmailAddress(){
        List<Tweet> tweets = Arrays.asList(tweet2);
        Set<String> mentionedUsers = Extract.getMentionedUsers(tweets);
        assertEquals(0,mentionedUsers.size());
    }
    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
