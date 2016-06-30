package com.tweeddale.tweeter.contentstrategy;

import org.apache.logging.log4j.LogManager;
import twitter4j.StatusUpdate;

/**
 * Created by James on 8/3/2015.
 */
public class TwitterMentionStrategy implements ContentFetchStrategy {

    protected static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(RandomWordsImageStrategy.class);


    public StatusUpdate getTweetableStatus() {
        return null;
    }
}
