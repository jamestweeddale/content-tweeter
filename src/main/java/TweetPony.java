import com.tweeddale.contenttweeter.ContentTweeter;
import com.tweeddale.contenttweeter.ContentTweeterScheduler;
import com.tweeddale.contenttweeter.contentstrategy.RandomWordsImageStrategy;
import com.tweeddale.contenttweeter.services.GoogleImageSearch;
import com.tweeddale.contenttweeter.services.TwitterClient;
import com.tweeddale.contenttweeter.services.Wordnik;

/**
 * Created by James on 7/4/2015.
 */
public class TweetPony {

    public static void main(String[] args){

        //TO DO: strip out Quartz and repalce with timed thread

        //choose and create a content fetching strategy
        RandomWordsImageStrategy contentStrategy = new RandomWordsImageStrategy(3);
        contentStrategy.setDictionaryService(new Wordnik());
        contentStrategy.setImageSearchService(new GoogleImageSearch());

        //setup a ContentTweeter with the content strategy and tweet!
        ContentTweeter contentTweeter = new ContentTweeter();
        contentTweeter.setTweetService(new TwitterClient());
        contentTweeter.setContentFetchStrategy(contentStrategy);
        contentTweeter.tweet();

        ContentTweeterScheduler scheduler = new ContentTweeterScheduler(contentTweeter, "myTrigger", "myJob", "myGroup", 10000);
        scheduler.start();

    }
}
