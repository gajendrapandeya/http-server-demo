package com.codermonkeys.httpserver.config;

import com.codermonkeys.httpserver.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    /**
     * Making ConfigurationManager a singleton
     * as we don't require more than one instance of it
     */
    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        if(myConfigurationManager == null)
            myConfigurationManager = new ConfigurationManager();
        return myConfigurationManager;
    }

    /**
     * Used to load a configuration file by the filePath provided
     * May throw an IOException in case if there is no file at provided filePath or
     * we have no permission to read it
     * @param filePath
     */
    public void loadConfigurationFile(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        while(true) {
            try {
                if (!((i = fileReader.read()) != - 1)) break;
            } catch (IOException e) {
                throw new HttpConfigurationException(e);
            }
            sb.append((char) i);
        }
        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the configuration file",e);
        }
        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the configuration file, internal",e);
        }
    }

    /**
     * Returns the current loaded configuration
     * May throw an exception when there is no configuration loaded
     */
    public Configuration getCurrentConfiguration() {
        if(myCurrentConfiguration == null)
            throw new HttpConfigurationException("No Current Configuration Set.");
        return myCurrentConfiguration;
    }
}
