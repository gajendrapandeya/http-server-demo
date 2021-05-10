package com.codermonkeys.httpserver;

import com.codermonkeys.httpserver.config.Configuration;
import com.codermonkeys.httpserver.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Driver class for the http server
 */
public class HttpServer {

    public static void main(String[] args) {
        System.out.println("Starting Server...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();
        System.out.println("Using Port: " + conf.getPort());
        System.out.println("Using webroot: " + conf.getWebroot());

        try {
            ServerSocket serverSocket = new ServerSocket(conf.getPort());
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String html = "<html><head><title>Simple HTTP server</title></head><body><h1>This page was served by using my simple java HTTP Server</h1></body></html>";

            final String CRLF = "\n\r"; //13, 10

            String response =
                    "HTTP/1.1 200 OK" + CRLF + //Status line: HTTP version RESPONSE CODE RESPONSE MESSAGE
                    "Content-Length: " + html.getBytes().length + CRLF
                    +CRLF +
                    html +
                    CRLF + CRLF; //HEADER

            outputStream.write(response.getBytes());

            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
