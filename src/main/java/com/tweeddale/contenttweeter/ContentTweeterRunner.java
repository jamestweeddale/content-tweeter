package com.tweeddale.contenttweeter;

import com.tweeddale.contenttweeter.util.ConfigWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by James on 7/18/2015.
 *
 * Sets up a ContentTweeter to be run in its own thread at a specified interval.
 */
public class ContentTweeterRunner implements Runnable {

    private static final Logger logger = LogManager.getLogger(ContentTweeterRunner.class);

    ContentTweeter contentTweeter;
    int tweetIntervalSeconds;


    //if no interval seconds are specified, use default from config file
    public ContentTweeterRunner(ContentTweeter contentTweeter) {
        this.contentTweeter = contentTweeter;
        tweetIntervalSeconds = ConfigWrapper.getConfig().getInt("default-tweet-interval-seconds");
    }

    public ContentTweeterRunner(ContentTweeter contentTweeter, int tweetIntervalSeconds) {
        this.contentTweeter = contentTweeter;
        this.tweetIntervalSeconds = tweetIntervalSeconds;
    }

    public void setContentTweeter(ContentTweeter contentTweeter) {
        this.contentTweeter = contentTweeter;
    }

    /**
     * Tweet at the interval specified by tweetIntervalSeconds forever...
     */
    public void run(){
        logger.info("ContentTweeter runner started");
        while(true){

            try {
                Thread.sleep(tweetIntervalSeconds);

            } catch(InterruptedException e){
                e.printStackTrace();
                logger.debug("ContentTweeterRunner thread interupted.");
            }

            contentTweeter.tweet();
            logger.info("ContentTweeter tweeted status update.");
        }
    }
}
