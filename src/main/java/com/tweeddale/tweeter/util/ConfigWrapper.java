package com.tweeddale.tweeter.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * Created by James on 7/11/2015.
 *
 * A singleton wrapper around an Apache commons configuration object. Allows any object in the application to obtain
 * configuration information from the application confif xml file.
 */
public class ConfigWrapper {

    private static XMLConfiguration appConfig;

    protected ConfigWrapper() {}

    public static XMLConfiguration getConfig() {
        try {
            if (appConfig == null) {
                appConfig = new XMLConfiguration("/var/opt/tweeter/tweeter-config.xml");
            }
        }catch(ConfigurationException e){
            e.printStackTrace();
            throw new Error("Unable to read application configuration.");
        }

        return appConfig;
    }
}
