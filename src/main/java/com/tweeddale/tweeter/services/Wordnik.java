package com.tweeddale.tweeter.services;

import com.tweeddale.tweeter.util.ConfigWrapper;
import java.util.ArrayList;
import java.util.List;
import net.jeremybrooks.knicker.*;
import net.jeremybrooks.knicker.dto.*;

/**
 * Created by James on 7/3/2015.
 *
 * This implementation of DictionaryServices uses the Knicker API to fetch words from the Wordnik online dictionary.
 */
public class Wordnik implements DictionaryService {

    private TokenStatus tokenStatus;

    /**
     * Upon object creation, load the Wordnik API authentication token from application config
     * @throws Exception
     */
    public Wordnik(){

            try {
                String wordnikApiKey = ConfigWrapper.getConfig().getString("services.wordnik.api-key");
                System.setProperty("WORDNIK_API_KEY", wordnikApiKey);
                this.tokenStatus = AccountApi.apiTokenStatus();

                if (!this.tokenStatus.isValid()) {
                    throw new Error("Wordnik token has expired.");
                }

            }catch(KnickerException e){
                //if we can't connect to the dictionary we are dead in the water
                e.printStackTrace();
                throw new Error("Failed connecting to dictionary service. Execution terminated.");
            }
    }


    /**
     * Fetches numWords random words from the Wordnik online dictionary
     * todo: make corpusCount and dictionaryCount configurable
     * @param numWords  the number of random words to fetch
     * @return a list of random words containing numWords items
     * @throws Exception
     */
    public List<String> getRandomWords(int numWords) throws Exception {

        List<String> randomWords = new ArrayList();

        List<Word> words = WordsApi.randomWords(true, null, null, 2, 0, 2, 0, 0, 0, null, null, numWords);

        for (Word word : words) {
            randomWords.add(word.getWord());
        }

        return randomWords;

    }

    /**
     * Fetches the word of the day from Wordnik
     * @return String containing the word of the day
     * @throws Exception
     */
    public String getWordOfTheDay() throws Exception {
        String wordOfTheDay;

        wordOfTheDay = WordsApi.wordOfTheDay().getWord();

        return wordOfTheDay;
    }
}
