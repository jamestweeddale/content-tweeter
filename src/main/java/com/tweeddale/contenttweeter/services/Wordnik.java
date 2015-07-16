package com.tweeddale.contenttweeter.services;

import com.tweeddale.contenttweeter.util.ConfigWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import net.jeremybrooks.knicker.*;
import net.jeremybrooks.knicker.dto.*;

/**
 * Created by James on 7/3/2015.
 *
 * This implementation of DictionaryServices uses the Knicker API to fetch words from the Wordnik online dictionary.
 */
@Service
public class Wordnik implements DictionaryService {

    private TokenStatus tokenStatus;

    /**
     * Upon object creation, load the Wordnik API authentication token from application config
     * @throws Exception
     */
    public Wordnik() throws Exception{

            String wordnikApiKey = ConfigWrapper.getConfig().getString("services.wordnik.api-key");
            System.setProperty("WORDNIK_API_KEY", wordnikApiKey);
            this.tokenStatus = AccountApi.apiTokenStatus();

            if (!this.tokenStatus.isValid()) {
                throw new Error("Wordnik token has expired.");
            }
    }


    /**
     * Fetches numWords random words from the Wordnik online dictionary
     *
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
