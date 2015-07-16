package com.tweeddale.contenttweeter;
/**
 * Created by James on 7/3/2015.
 *
 * A ContentTweeter has everything it needs to generate tweets. It has a contentFetcher to provide tweetable content
 * and a TweetService to post a status update to twitter.
 *
 * This class is where everything gets wired together by Spring so that a TweetService can use a contentGenerator to tweet
 * status updates.
 *
 * A ContentTweeter object is injected with a TweetService.
 */

import com.tweeddale.contenttweeter.services.ContentFetcher;
import com.tweeddale.contenttweeter.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.*;
import twitter4j.StatusUpdate;

@Service
public class ContentTweeter{

    protected static final Logger logger = LogManager.getLogger(ContentTweeter.class);

    @Autowired protected TweetService tweetService;
    protected ContentFetcher contentFetcher;

    public void setTweetService(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    /**
     * Post a status update to twitter using content generated/fetched by the contentFetcher
     */
    public void tweet(){
        StatusUpdate tweetableStatus = contentFetcher.getTweetableStatus();
        logger.debug("Tweeting: "+ tweetableStatus);
        tweetService.send(tweetableStatus);
    }

}
