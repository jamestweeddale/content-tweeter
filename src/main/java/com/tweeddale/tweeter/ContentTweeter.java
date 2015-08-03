package com.tweeddale.tweeter;
/**
 * Created by James on 7/3/2015.
 *
 * A ContentTweeter has everything it needs to generate tweets. It has contentFetchStrategies to provide tweetable content
 * and a TweetService to post a status update to twitter.
 *
 * If it has more than one contentFetchStrategy in its ocllection, then it selects a random one when generating a tweet
 *
 * Strategy pattern is used to encapsulate and decouple content fetching strategy so that many other strategies for
 * fetching content could be easily integrated and even swapped at runtime.
 */

import com.tweeddale.tweeter.contentstrategy.ContentFetchStrategy;
import com.tweeddale.tweeter.contentstrategy.StrategyCollection;
import com.tweeddale.tweeter.services.TweetService;
import org.apache.logging.log4j.*;

import twitter4j.StatusUpdate;

public class ContentTweeter{

    private static final Logger logger = LogManager.getLogger(ContentTweeter.class);

    private TweetService tweetService;
    private StrategyCollection contentFetchStrategies;

    public void setTweetService(TweetService tweetService) {
        this.tweetService = tweetService;
        contentFetchStrategies = new StrategyCollection();
    }

    public void setContentFetchStrategies(StrategyCollection contentFetchStrategies) {
        this.contentFetchStrategies = contentFetchStrategies;
    }

    /**
     * adds a new content strategy to the collection. Identifies it by name for layter reference.
     * @param name  a name chosen by user used to reference this strategy
     * @param strategy  a content fetch strategy
     */
    public void addStrategy(String name, ContentFetchStrategy strategy){
        contentFetchStrategies.addStrategy(name, strategy);
    }


    /**
     * Post a status update to tweetService using content generated/fetched by the contentFetchStrategies
     * If there is more than one strategy in the collection, it selects a random one
     */
    public void tweet(){
        StatusUpdate tweetableStatus = contentFetchStrategies.getRandomStrategy().getTweetableStatus();
        tweetService.send(tweetableStatus);

        logger.debug("Tweeted: " + tweetableStatus);
        System.out.println("Just tweeted: " + tweetableStatus.getStatus());
    }

    /**
     * Tweets using strategy identified by name
     */
    public void tweet(String strategyName){
        StatusUpdate tweetableStatus = contentFetchStrategies.getStrategy(strategyName).getTweetableStatus();
        tweetService.send(tweetableStatus);

        logger.debug("Tweeted: " + tweetableStatus);
        System.out.println("Just tweeted: " + tweetableStatus.getStatus());
    }



}
