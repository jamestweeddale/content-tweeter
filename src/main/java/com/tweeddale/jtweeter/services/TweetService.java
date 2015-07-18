package com.tweeddale.jtweeter.services;
import twitter4j.StatusUpdate;

/**
 * Created by James on 7/4/2015.
 *
 * A TweetService provides a method to post a tweet(status update) to Twitter.
 */
public interface TweetService{

    public void send(StatusUpdate message);
}
