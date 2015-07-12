package com.tweeddale.contenttweeter.services;

import java.util.List;

/**
 * Created by James on 7/4/2015.
 */
public interface DictionaryService {

    public List<String> getRandomWords(int numWords) throws Exception;

    public String getWordOfTheDay() throws Exception;
}
