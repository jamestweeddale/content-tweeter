package com.tweeddale.jtweeter.contentstrategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Stores a collection of ContentFetchStrategies with methods for addding/removing/selecting them
 * Created by James on 8/2/2015.
 */
public class StrategyCollection {
    private static final Logger logger = LogManager.getLogger(StrategyCollection.class);

    private Map<String, ContentFetchStrategy> strategyMap = new HashMap();

    public void addStrategy(String name, ContentFetchStrategy strategy){
        strategyMap.put(name, strategy);
    }

    public void removeStrategy(String name){
        strategyMap.remove(name);
    }

    public ContentFetchStrategy getStrategy(String name){
        return strategyMap.get(name);
    }

    /**
     * @return a randomly selected contentFetchStrategy from this collection
     */
    public ContentFetchStrategy getRandomStrategy(){

        Set <String> strategyNames = strategyMap.keySet();

        int numStrategies = strategyMap.size();
        int randomNum = 0;
        if(numStrategies > 0){
            Random rand = new Random();
            randomNum = rand.nextInt(numStrategies);
        }

        return strategyMap.get(strategyNames.toArray()[randomNum]);
    }


}
