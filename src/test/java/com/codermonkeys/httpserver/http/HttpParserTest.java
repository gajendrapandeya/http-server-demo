package com.codermonkeys.httpserver.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HttpParserTest {

    private HttpParser httpParser;

    @BeforeAll
    public void beforeClass() {
        httpParser = new HttpParser();
    }

    @Test
    void parseHttpRequest() {
        HttpRequest request = httpParser.parseHttpRequest(generateValidGETTestCase());

        assertEquals(request.getMethod(), HttpMethod.GET);
    }

    private InputStream generateValidGETTestCase() {
        String rawData = "GET / HTTP/1.1\n\r" +
                "Host: localhost:8080\n\r" +
                "Connection: keep-alive\n\r" +
                "Upgrade-Insecure-Requests: 1\n\r" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36\n\r" +
                "Sec-Fetch-User: ?1\n\r" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\n\r" +
                "Sec-Fetch-Site: none\n\r" +
                "Sec-Fetch-Mode: navigate\n\r" +
                "Accept-Encoding: gzip, deflate, br\n\r" +
                "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4" +
                "\r\n";

        InputStream inputStream = new ByteArrayInputStream(rawData.getBytes(StandardCharsets.US_ASCII));

        return inputStream;
    }
}