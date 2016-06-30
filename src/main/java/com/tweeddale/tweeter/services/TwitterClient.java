package com.tweeddale.tweeter.services;

import com.tweeddale.tweeter.util.ConfigWrapper;
import twitter4j.*;
import twitter4j.auth.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by James on 7/3/2015.
 *
 * This implementation of TweetService uses the Twitter4j library to authenticate to a twitter account and post
 * status updates.
 */
public class TwitterClient implements TweetService {

    private static final Logger logger = LogManager.getLogger(TwitterClient.class);

    private AccessToken accessToken;
    private String consumerSecret;
    private String consumerKey;
    TwitterFactory twitterFactory;
    Twitter twitterConnection;


    /**
     * upon constriction the object will get authentication info and connect to twitter
     */
    public TwitterClient() {
        loadAuthInfo();
        connect();
    }

    /**
     * Provide local twitter connection with authentication information
     */
    public void connect() {

        twitterFactory = new TwitterFactory();
        twitterConnection = twitterFactory.getInstance();
        twitterConnection.setOAuthConsumer(this.consumerKey, this.consumerSecret);
        twitterConnection.setOAuthAccessToken(accessToken);
    }

    /**
     * Posts the passed-in StatusMessage to Twitter
     *
      * @param message a status update ready to be posted to twitter. It contains text and optionally an image.
     */
    public void send(StatusUpdate message) {
        try {
            logger.debug("Tweeting: "+message);
            twitterConnection.updateStatus(message);

        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all mentions from the last n seconds. If operation fails, returns an empty list.
     * @return list containing all mentions from the last n seconds.
     */
    public List<Status> getMentions(int secondsAgo) {

        List<Status> recentMentions = new ArrayList();

        try {
            List<Status> allMentions = twitterConnection.getMentionsTimeline();
            Date since = new Date();
            since.setSeconds(new Date().getSeconds() - secondsAgo);

            for(Status status : allMentions){
                if(status.getCreatedAt().after(since)){
                    recentMentions.add(status);
                }
            }

            return recentMentions;

        }catch(TwitterException e){
            e.printStackTrace();
            logger.debug("Failed to getMentions");
            return new ArrayList<Status>();
        }
    }

    /**
     * Loads authentication information into object from application configuration
     */
    private void loadAuthInfo() {

        try {

            String accessTokenStr = ConfigWrapper.getConfig().getString("services.twitter.access-token");
            String tokenSecret = ConfigWrapper.getConfig().getString("services.twitter.token-secret");
            this.consumerKey = ConfigWrapper.getConfig().getString("services.twitter.consumer-key");
            this.consumerSecret = ConfigWrapper.getConfig().getString("services.twitter.consumer-secret");

            this.accessToken = new AccessToken(accessTokenStr, tokenSecret);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



