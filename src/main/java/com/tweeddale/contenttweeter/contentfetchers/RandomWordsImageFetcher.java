package com.tweeddale.contenttweeter.contentfetchers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.tweeddale.contenttweeter.services.ContentFetcher;
import com.tweeddale.contenttweeter.services.DictionaryService;
import com.tweeddale.contenttweeter.services.ImageSearchService;
import com.tweeddale.contenttweeter.util.ConfigWrapper;
import com.tweeddale.contenttweeter.util.RemoteFileGrabber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;

/**
 * Created by James on 7/4/2015.
 *
 * Uses injected dictionary and imageSearch service to fetch random words and query for a related image. The first accessible
 * related image is downloaded and attached to a StatusMessage along with the random words.
 *
 */
@Service
public class RandomWordsImageFetcher implements ContentFetcher {

    private static final Logger logger = LogManager.getLogger(RandomWordsImageFetcher.class);

    @Autowired RemoteFileGrabber remoteFileGrabber;
    @Autowired private DictionaryService dictionaryService;
    @Autowired private ImageSearchService imageSearchService;
    private int numWords = 0;
    private boolean useWordOfTheDay = false;

    public RandomWordsImageFetcher(){
    }

    public RandomWordsImageFetcher(int numWords){
        this.numWords = numWords;
    }

    public void setNumWords(int numWords) {
        this.numWords = numWords;
    }

    public int getNumWords() {
        return numWords;
    }

    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    public void setImageSearchService(ImageSearchService imageSearchService) {
        this.imageSearchService = imageSearchService;
    }

    public boolean isUseWordOfTheDay() {
        return useWordOfTheDay;
    }

    public void setUseWordOfTheDay(boolean useWordOfTheDay) {
        this.useWordOfTheDay = useWordOfTheDay;
    }


    /**
     * Fetches random words and word of the day if applicable. Queries for and downloads related image then attaches it to
     * status update.
     *
     * @return  StatusUpdate composed of three random words with an attached related image ready for tweeting
     */
    public StatusUpdate getTweetableStatus() {

        String wordOfTheDay = "";
        String randomWords = "";
        String tweetableString = "";
        StatusUpdate status = null;

        try {

            //word of the day, if turned on
            if(useWordOfTheDay){
                wordOfTheDay = dictionaryService.getWordOfTheDay();
                logger.debug("Applied word of the day: "+ wordOfTheDay);
            }

            //get random words and search for related image until we get one
            List<String> imgUrls = new ArrayList<String>();
            while(imgUrls.isEmpty()){
                randomWords =  this.fetchRandomWords();
                imgUrls = imageSearchService.search(randomWords);
            }

            //try to download images until one works
            remoteFileGrabber.setRemoteFileUrl(new URL(imgUrls.get(0)));
            File newFile = remoteFileGrabber.getFile();

            int i = 1;
            while(i < imgUrls.size() && newFile == null){
                newFile = remoteFileGrabber.getFile();
                i++;
            }

            tweetableString = wordOfTheDay + " " + randomWords;

            //create Tweet message/status
            status = new StatusUpdate(tweetableString);
            status.setMedia(newFile);

        }catch(Exception e){
            e.printStackTrace();
        }

        return status;
    }


    /**
     * Fetches the set number of random words using the dictionary service. If word of the day is activated then it
     * substitutes it for one of the random words. Stores all fetched words in a space-separated string.
     *
     * @return String containing words fetched from dictionary service, space-separated
     */
    private String fetchRandomWords(){
        String wordString = "";
        int numWordsToFetch = this.numWords;

        if(this.numWords == 0)
            numWordsToFetch = ConfigWrapper.getConfig().getInt("num-words");


        if(this.useWordOfTheDay)  numWordsToFetch--;

        try {
            List<String> words = dictionaryService.getRandomWords(numWordsToFetch);

            for (String word : words) {
                wordString += " " + word;
            }

            wordString = wordString.trim();

        }catch (Exception e){
            e.printStackTrace();
        }

        return wordString;
    }
}
