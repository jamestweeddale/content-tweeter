package com.tweeddale.tweeter.contentstrategy;
import twitter4j.*;

/**
 * Created by James on 7/4/2015.
 *
 * Content fetchers collect content from one or more sources and use it to create a StatusUpdate for tweeting. ContentFetchers
 * are what ContentTweeters use to build tweetable messages. ContentFetchers must simply implement in getTweetableStatus method
 */
public interface ContentFetchStrategy {

    //** TO DO, genericize/wrap StatusUpdate so that Twitter4j is not directly included
    public StatusUpdate getTweetableStatus();


}
