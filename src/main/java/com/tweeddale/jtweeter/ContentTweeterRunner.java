package com.tweeddale.jtweeter;

import com.tweeddale.jtweeter.util.ConfigWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Created by James on 7/18/2015.
 * <p>
 * Sets up a ContentTweeter to be run in its own thread at a specified interval.
 */
public class ContentTweeterRunner extends Thread {

    private static final Logger logger = LogManager.getLogger(ContentTweeterRunner.class);

    ContentTweeter contentTweeter;
    int tweetIntervalSeconds;


    public ContentTweeterRunner(ContentTweeter contentTweeter) {
        this.contentTweeter = contentTweeter;

        //if no interval seconds are specified, use default from config file
        tweetIntervalSeconds = ConfigWrapper.getConfig().getInt("default-tweet-interval-seconds");
    }

    public ContentTweeterRunner(ContentTweeter contentTweeter, int tweetIntervalSeconds) {
        this.contentTweeter = contentTweeter;
        this.tweetIntervalSeconds = tweetIntervalSeconds;
    }

    public void setContentTweeter(ContentTweeter contentTweeter) {
        this.contentTweeter = contentTweeter;
    }

    public int getTweetIntervalSeconds() {
        return tweetIntervalSeconds;
    }

    public void setTweetIntervalSeconds(int tweetIntervalSeconds) {
        this.tweetIntervalSeconds = tweetIntervalSeconds;
    }

    /**
     * Tweet at the interval specified by tweetIntervalSeconds until existence of killFile is detected
     */
    public void run() {

        //tweet and then sleep. repeat.
        do {
            //tweet
            contentTweeter.tweet();

            //sleep
            try {
                Thread.sleep(tweetIntervalSeconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (true);

    }
}
