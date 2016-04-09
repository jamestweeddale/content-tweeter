package com.tweeddale.tweeter.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by James on 7/11/2015.
 * <p>
 * A singleton wrapper around an Apache commons configuration object. Allows any object in the application to obtain
 * configuration information from the application config xml file.
 */
public class ConfigWrapper {
    private static final Logger logger = LogManager.getLogger(ConfigWrapper.class);

    private static XMLConfiguration appConfig;

    protected ConfigWrapper() {
    }

    //todo: move path Strings into configuration file
    public static XMLConfiguration getConfig() {

        //if config object has not yet been created, load it
        if (appConfig == null) {
            //attempt to find config file in Linux location
            try {
                logger.debug("Looking for config file in Linux location: /var/opt/tweeter/tweeter-config.xml");
                appConfig = new XMLConfiguration("/var/opt/tweeter/tweeter-config.xml");
                return appConfig;
            } catch (ConfigurationException e) {
                logger.debug("Did not find config file in Linux location. Looking in Windows location: C:\\tweeterConfig\\tweeter-config.xml");
            }

            //attempt to find config file in Windows location
            try {
                appConfig = new XMLConfiguration("C:\\tweeterConfig\\tweeter-config.xml");
                return appConfig;
            }
            catch (ConfigurationException e) {
                e.printStackTrace();
                throw new Error("Unable to read/locate application configuration.");
            }
        }

        return appConfig;
    }
}
