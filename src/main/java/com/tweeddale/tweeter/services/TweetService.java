package com.tweeddale.tweeter.services;
import twitter4j.Status;
import twitter4j.StatusUpdate;

import java.util.List;

/**
 * Created by James on 7/4/2015.
 *
 * A TweetService provides a method to post a tweet(status update) to Twitter.
 */
public interface TweetService{

    public void send(StatusUpdate message);

    public List<Status> getMentions(int secondsAgo);
}
