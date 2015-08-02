import com.tweeddale.jtweeter.ContentTweeter;
import com.tweeddale.jtweeter.ContentTweeterRunner;
import com.tweeddale.jtweeter.contentstrategy.ContentFetchStrategy;
import com.tweeddale.jtweeter.contentstrategy.FortuneStrategy;
import com.tweeddale.jtweeter.contentstrategy.RandomWordsImageStrategy;
import com.tweeddale.jtweeter.services.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * Created by James on 7/4/2015.
 *
 * Creates a ContentTweeter equipped with a RandomWordsImageStragy. Sets the ContentTweeter to run at a
 * user-defined interval (or defaults to 2 hours). Runs until user enters 'quit'
 */
public class Run {

    private static final Logger logger = LogManager.getLogger(Run.class);

    public static void main(String[] args) {

        try {
            int secondsBetweenTweets = 7200; //default tweet time is every two hours
            if (args.length > 0) {
                //get user inputted tweet interval (if exists)
                secondsBetweenTweets = Integer.parseInt(args[0]);
            }

            //choose and create a content fetching strategy for fortunes
            ContentFetchStrategy fortuneStrategy = new FortuneStrategy();

            //strategy for fetching random words and images
            ContentFetchStrategy wordImageStrategy = new RandomWordsImageStrategy(new Wordnik(), new GoogleImageSearch(), 3, false);

            //setup a ContentTweeter with the content strategy and tweet!
            ContentTweeter contentTweeter = new ContentTweeter();
            contentTweeter.setTweetService(new TwitterClient());
            contentTweeter.addStrategy("fortunes", fortuneStrategy);
            contentTweeter.addStrategy("randomWordsImages", wordImageStrategy);

            //kick the tweet process off in a threaded TweetRunner to tweet at the desired interval
            Thread tweetThread = new Thread(new ContentTweeterRunner(contentTweeter, secondsBetweenTweets));
            tweetThread.setDaemon(true);  //start as daemon thread so that it stops when main terminates
            tweetThread.start();

            System.out.println("ContentTweeter started!");
            System.out.println("Enter 'quit' to stop tweeting and terminate program: ");

            //continue to execute until user enters 'quit'
            Scanner stdin = new Scanner(new BufferedInputStream(System.in));
            while (stdin.hasNext()) {

                if (stdin.next().equals("quit")) {
                    break;
                }
            }

            System.out.println("Tweeter terminated.");

        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception in main program");
        }
    }
}
