package com.tweeddale.contenttweeter.util;


import org.apache.commons.configuration.XMLConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.nio.channels.*;

/**
 * Created by James on 7/5/2015.
 */
@Component
public class RemoteFileGrabber {

    private static final Logger logger = LogManager.getLogger(RemoteFileGrabber.class);

    String localFileDestDir;
    URL remoteFileUrl;

    public RemoteFileGrabber() {
    }

    public RemoteFileGrabber(URL remoteFileUrl) {
        this();
        this.remoteFileUrl = remoteFileUrl;

    }


    public String getLocalFileDestDir() {
        return localFileDestDir;
    }

    public void setLocalFileDestDir(String localFileDestDir) {
        this.localFileDestDir = localFileDestDir;
    }

    public URL getRemoteFileUrl() {
        return remoteFileUrl;
    }

    public void setRemoteFileUrl(URL remoteFileUrl) {
        this.remoteFileUrl = remoteFileUrl;
    }


    public File getFile(){
        File newLocalFile = null;
        logger.debug("APP CONFIG: " + ConfigWrapper.getConfig().getString("downloads-dir"));
        try {

            //check to see if remote file is accessible
            HttpURLConnection connection = (HttpURLConnection)this.remoteFileUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();
            logger.debug("response code for imag "+ code);
            if(code != 404) {

                ReadableByteChannel rbc = Channels.newChannel(this.remoteFileUrl.openStream());

                String remoteUrl = remoteFileUrl.toString();

                //get name of new file
                String newFileName = remoteUrl.substring(remoteUrl.lastIndexOf('/') + 1, remoteUrl.length());
                newLocalFile = new File(ConfigWrapper.getConfig().getString("downloads-dir") + "/" + newFileName);

                logger.debug("About to download " + remoteUrl + " to " + newLocalFile);
                FileOutputStream fos = new FileOutputStream(newLocalFile);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            }else{
                logger.debug(" IMage "+ this.remoteFileUrl + " is not acessible (404)");
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return newLocalFile;
    }
}
