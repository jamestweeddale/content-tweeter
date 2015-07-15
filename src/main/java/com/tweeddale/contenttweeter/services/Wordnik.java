package com.tweeddale.contenttweeter.services;

import com.tweeddale.contenttweeter.util.ConfigWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import net.jeremybrooks.knicker.*;
import net.jeremybrooks.knicker.dto.*;

/**
 * Created by James on 7/3/2015.
 */
@Service
public class Wordnik implements DictionaryService {

    private TokenStatus tokenStatus;

    public Wordnik() throws Exception{

            String wordnikApiKey = ConfigWrapper.getConfig().getString("services.wordnik.api-key");
            System.setProperty("WORDNIK_API_KEY", wordnikApiKey);
            this.tokenStatus = AccountApi.apiTokenStatus();

            if (!this.tokenStatus.isValid()) {
                throw new Error("Wordnik token has expired.");
            }
    }


    public List<String> getRandomWords(int numWords) throws Exception {

        List<String> randomWords = new ArrayList();

        List<Word> words = WordsApi.randomWords(true, null, null, 2, 0, 2, 0, 0, 0, null, null, numWords);

        for (Word word : words) {
            randomWords.add(word.getWord());
        }

        return randomWords;

    }

    public String getWordOfTheDay() throws Exception {
        String wordOfTheDay;

        wordOfTheDay = WordsApi.wordOfTheDay().getWord();

        return wordOfTheDay;
    }
}
