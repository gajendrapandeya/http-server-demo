package com.codermonkeys.httpserver.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {

    public static final Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            //Read request from browser
            inputStream = socket.getInputStream();
            //Write back to the request
            outputStream = socket.getOutputStream();



            String html = "<html><head><title>Simple HTTP server</title></head><body><h1>This page was served by using my simple java HTTP Server</h1></body></html>";

            final String CRLF = "\n\r"; //13, 10

            String response =
                    "HTTP/1.1 200 OK" + CRLF + //Statpous line: HTTP version RESPONSE CODE RESPONSE MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF //HEADER
                            + CRLF +
                            html +
                            CRLF + CRLF;

            outputStream.write(response.getBytes());

            LOGGER.info("*Connection Processing Finished*");
        } catch (IOException e) {
           LOGGER.error("*Problem with communication: ", e.getMessage());
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
