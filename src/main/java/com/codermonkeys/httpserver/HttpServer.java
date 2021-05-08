package com.codermonkeys.httpserver;

import com.codermonkeys.httpserver.config.Configuration;
import com.codermonkeys.httpserver.config.ConfigurationManager;

/**
 *
 * Driver class for the http server
 */
public class HttpServer {

    public static void main(String[] args) {
        System.out.println("Starting Server...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.println("Using Port: " + conf.getPort());
        System.out.println("Using webroot: " + conf.getWebroot());
    }
}
