package com.tweeddale.jtweeter;
/**
 * Created by James on 7/3/2015.
 *
 * A ContentTweeter has everything it needs to generate tweets. It has a contentFetchStrategy to provide tweetable content
 * and a TweetService to post a status update to twitter.
 *
 * Strategy pattern is used to encapsulate and decouple content fetching strategy so that many other strategies for
 * fetching content could be easily integrated and even swapped at runtime.
 */

import com.tweeddale.jtweeter.contentstrategy.ContentFetchStrategy;
import com.tweeddale.jtweeter.services.TweetService;
import org.apache.logging.log4j.*;

import twitter4j.StatusUpdate;

public class ContentTweeter{

    protected static final Logger logger = LogManager.getLogger(ContentTweeter.class);

    protected TweetService tweetService;
    protected ContentFetchStrategy contentFetchStrategy;

    public void setTweetService(TweetService tweetService) {
        this.tweetService = tweetService;
    }
    public void setContentFetchStrategy(ContentFetchStrategy contentFetchStrategy) {
        this.contentFetchStrategy = contentFetchStrategy;
    }

    /**
     * Post a status update to tweetService using content generated/fetched by the contentFetchStrategy
     */
    public void tweet(){

        StatusUpdate tweetableStatus = contentFetchStrategy.getTweetableStatus();
        tweetService.send(tweetableStatus);

        logger.debug("Tweeted: " + tweetableStatus);
        System.out.println("Just tweeted: "+ tweetableStatus.getStatus());
    }


}
