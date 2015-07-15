package com.tweeddale.contenttweeter.services;
import twitter4j.StatusUpdate;

/**
 * Created by James on 7/4/2015.
 */
public interface TweetService{

    public void send(StatusUpdate message);
}
