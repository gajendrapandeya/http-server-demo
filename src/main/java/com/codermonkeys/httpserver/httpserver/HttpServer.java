package com.codermonkeys.httpserver.httpserver;

import com.codermonkeys.httpserver.httpserver.config.Configuration;
import com.codermonkeys.httpserver.httpserver.config.ConfigurationManager;
import com.codermonkeys.httpserver.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * Driver class for the http server
 */
public class HttpServer {

    public static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) {
        LOGGER.info("Starting server...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        LOGGER.info("Using Port: " + conf.getPort());
        LOGGER.info("Using webroot: " + conf.getWebroot());

        try {
            ServerListenerThread thread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
            //todo handle later
        }

    }
}
