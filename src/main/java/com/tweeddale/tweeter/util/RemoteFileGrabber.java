package com.tweeddale.tweeter.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.*;
import java.nio.channels.*;

/**
 * Created by James on 7/5/2015.
 *
 *Downloads files from URL objects into a local directory specified in application configuration
 */
public class RemoteFileGrabber {

    private static final Logger logger = LogManager.getLogger(RemoteFileGrabber.class);

    /**
     * Given a URL to a remote file, attempts to download it via HTTP and store in the local downloads-dir identified
     * in the appConfig.xml file. If remote file is inaccessible (404 status) a log message is generated and a null
     * file is returned.
     *
     * @param remoteFileUrl URL of remote file to download
     * @return File handle to newley created/downloaded file. NUll if file was innaccessible or failed to download
     */
    public static File getFile(URL remoteFileUrl){
        File newLocalFile = null;
        logger.debug("APP CONFIG: " + ConfigWrapper.getConfig().getString("downloads-dir"));
        try {

            //check to see if remote file is accessible
            HttpURLConnection connection = (HttpURLConnection)remoteFileUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();
            logger.debug("response code for image "+ code);
            if(code == 200) {  //if HTTP status code for download was success

                ReadableByteChannel rbc = Channels.newChannel(remoteFileUrl.openStream());

                String remoteUrl = remoteFileUrl.toString();

                //get name of new file
                String newFileName = remoteUrl.substring(remoteUrl.lastIndexOf('/') + 1, remoteUrl.length());
                newLocalFile = new File(ConfigWrapper.getConfig().getString("downloads-dir") + File.separator + newFileName);

                logger.debug("About to download " + remoteUrl + " to " + newLocalFile);
                FileOutputStream fos = new FileOutputStream(newLocalFile);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            }else{
                logger.debug(" Image "+ remoteFileUrl + " is not accessible (404)");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return newLocalFile;
    }
}
