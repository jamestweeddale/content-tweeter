package com.tweeddale.contenttweeter; /**
 * Created by James on 7/3/2015.
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
    public void setContentFetcher(ContentFetcher contentFetcher) {
        this.contentFetcher = contentFetcher;
    }

    public void tweet(){
        StatusUpdate tweetableStatus = contentFetcher.getTweetableStatus();
        logger.debug("Tweeting: "+ tweetableStatus);
        tweetService.send(tweetableStatus);
    }

}
