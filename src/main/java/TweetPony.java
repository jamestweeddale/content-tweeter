import com.tweeddale.contenttweeter.ContentTweeter;
import com.tweeddale.contenttweeter.ContentTweeterRunner;
import com.tweeddale.contenttweeter.contentstrategy.RandomWordsImageStrategy;
import com.tweeddale.contenttweeter.services.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by James on 7/4/2015.
 */
public class TweetPony {

    private static final Logger logger = LogManager.getLogger(TweetPony.class);

    public static void main(String[] args) {

        try {
            int secondsBetweenTweets = 10000; //default tweet time is every two hours

            if (args.length > 0) {
                secondsBetweenTweets = Integer.parseInt(args[0]);
            }

            //choose and create a content fetching strategy
            RandomWordsImageStrategy contentStrategy = new RandomWordsImageStrategy(3);
            contentStrategy.setDictionaryService(new Wordnik());
            contentStrategy.setImageSearchService(new GoogleImageSearch());

            //setup a ContentTweeter with the content strategy and tweet!
            ContentTweeter contentTweeter = new ContentTweeter();
            contentTweeter.setTweetService(new TwitterClient());
            contentTweeter.setContentFetchStrategy(contentStrategy);

            //kick the tweet process off on its own thread to tweet at the desired interval
            new Thread(new ContentTweeterRunner(contentTweeter, secondsBetweenTweets)).start();

            System.out.println("ContentTweeter started");

        }catch(Exception e){
            e.printStackTrace();
            logger.debug("Exception in main program");
        }
    }
}
