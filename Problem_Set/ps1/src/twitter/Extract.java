/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     *
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
//        throw new RuntimeException("not implemented");
//        tweets.sort((o1, o2) -> {return o1.getTimestamp().compareTo(o2.getTimestamp());}); //不要改变tweets对象
//        return new Timespan(tweets.get(0).getTimestamp(), tweets.get(tweets.size()-1).getTimestamp());
        List<Tweet> sortedTweets = tweets.stream().sorted((o1, o2) -> o1.getTimestamp().compareTo(o2.getTimestamp())).collect(Collectors.toList());
        return new Timespan(sortedTweets.get(0).getTimestamp(), sortedTweets.get(sortedTweets.size() - 1).getTimestamp());
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
//        throw new RuntimeException("not implemented");
       if(tweets.isEmpty()){
           return Collections.emptySet();
       }
        return tweets.stream()
                .map(tweet -> tweet.getText().toLowerCase())
                .filter(s -> s.contains("@"))
                .map(Extract::getMentionsString)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
    public static List<String> getMentionsString(String text){
        String regex = "@([^\\s,]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        List<String> mentions = new ArrayList<>();
        while (matcher.find()){
            mentions.add(matcher.group(1));
        }
        return mentions.stream().filter(excludeEmailAddress()).collect(Collectors.toList());
    }
    public static Predicate<String> excludeEmailAddress(){
            return s -> !s.contains(".");
    }

}
