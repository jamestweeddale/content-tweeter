package com.tweeddale.jtweeter.contentstrategy;

import org.apache.logging.log4j.LogManager;
import twitter4j.StatusUpdate;

import java.util.logging.Logger;

/**
 * Created by James on 7/18/2015.
 */
public class FortuneStrategy implements ContentFetchStrategy{

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(FortuneStrategy.class);
    private static int MAX_FORTUNE_CHARS = 140;


    public StatusUpdate getTweetableStatus() {
        return new StatusUpdate(getFortune());
    }

    public String getFortune() {
        String fortune = null;

        try {

            do {
                //get fortunes until we get one shorter than 141 characters
                Process proc = Runtime.getRuntime().exec("fortune");
                java.io.InputStream is = proc.getInputStream();
                fortune = "";

                logger.debug("getting fortune");
                java.util.Scanner s = new java.util.Scanner(is);
                while(s.hasNext()) {
                    fortune += s.next() + " ";
                }

                if(fortune.length() > 0) fortune.trim();

            }while((fortune == null) || (fortune.length() > MAX_FORTUNE_CHARS)) ;

        }catch(Exception e){
            e.printStackTrace();
            logger.debug("Exception occurred getting fortune");
        }
        return fortune;
    }
}
