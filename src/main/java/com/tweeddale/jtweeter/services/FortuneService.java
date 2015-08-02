package com.tweeddale.jtweeter.services;

import org.apache.logging.log4j.LogManager;

/**
 * Created by James on 7/18/2015.
 *
 * Gets fortune using the Linux Fortune command
 */
public class FortuneService {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(FortuneService.class);
    private static int MAX_FORTUNE_CHARS = 140;


    public static String getFortune() {
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
