package com.tweeddale.contenttweeter.services;
import twitter4j.*;

/**
 * Created by James on 7/4/2015.
 */
public interface ContentFetcher {

    //** TO DO, genericize/wrap StatusUpdate so that Twitter4j is not directly included
    public StatusUpdate getTweetableStatus();

}
