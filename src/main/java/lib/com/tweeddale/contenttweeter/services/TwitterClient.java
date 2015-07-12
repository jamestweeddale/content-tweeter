package com.tweeddale.contenttweeter.services;

import com.tweeddale.contenttweeter.util.ConfigWrapper;
import org.apache.commons.configuration.XMLConfiguration;
import twitter4j.*;
import twitter4j.auth.*;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Created by James on 7/3/2015.
 */
@Service
public class TwitterClient implements TweetService {

    private static final Logger logger = LogManager.getLogger(TwitterClient.class);

    private AccessToken accessToken;
    private String consumerSecret;
    private String consumerKey;
    TwitterFactory twitterFactory;
    Twitter twitterConnection;


    public TwitterClient() {
        loadAuthInfo();
        connect();
    }

    public void connect() {

        twitterFactory = new TwitterFactory();
        twitterConnection = twitterFactory.getInstance();
        twitterConnection.setOAuthConsumer(this.consumerKey, this.consumerSecret);
        twitterConnection.setOAuthAccessToken(accessToken);
    }

    public void send(StatusUpdate message) {
        try {
            logger.debug("Tweeting: "+message);
            twitterConnection.updateStatus(message);

        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

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



