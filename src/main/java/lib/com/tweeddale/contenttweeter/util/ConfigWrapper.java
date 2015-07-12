package com.tweeddale.contenttweeter.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by James on 7/11/2015.
 */
public class ConfigWrapper {

    private static XMLConfiguration appConfig;

    protected ConfigWrapper() {}

    public static XMLConfiguration getConfig() {
        try {
            if (appConfig == null) {
                appConfig = new XMLConfiguration("appConfig.xml");
            }
        }catch(ConfigurationException e){
            e.printStackTrace();
            throw new Error("Unable to read application configuration.");
        }

        return appConfig;
    }
}
