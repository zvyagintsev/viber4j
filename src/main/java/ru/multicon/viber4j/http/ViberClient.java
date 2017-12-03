package ru.multicon.viber4j.http;

import java.io.IOException;

/**
 * Http client for Viber
 *
 * @author n.zvyagintsev
 */
public interface ViberClient {

    /**
     * Posts messageForUser to Viber
     *
     * @param message messageForUser with request for Viber
     * @param url     url of Viber endpoint
     * @return response from Viber
     * @throws IOException if response status is not 200
     */
    String post(String message, String url) throws IOException;
}
