package ru.multicon.viber4j.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.apache.http.HttpStatus.SC_OK;

/**
 * @author n.zvyagintsev
 */
public class DefaultViberClient implements ViberClient {

    private static final String TOKEN_HEADER = "X-Viber-Auth-Token";
    private static final String USER_AGENT_NAME = "User-Agent";
    private static final String USER_AGENT_VALUE = "ViberBot-Java/1.0.11";

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultViberClient.class);
    private final String authToken;

    /**
     * Default constructor for Viber HTTP client
     *
     * @param authToken bot auth token
     */
    public DefaultViberClient(String authToken) {
        this.authToken = authToken;
    }

    public String post(String message, String url) throws IOException {
        LOGGER.debug("Posting messageForUser {} to url {}", message, url);
        HttpPost request = new HttpPost(url);
        request.addHeader(TOKEN_HEADER, authToken);
        request.addHeader(USER_AGENT_NAME, USER_AGENT_VALUE);
        request.setEntity(new StringEntity(message, ContentType.APPLICATION_JSON));
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = client.execute(request);
        int status = response.getStatusLine().getStatusCode();
        if (status != SC_OK) {
            LOGGER.error("Message {} was sent with status {}",
                    message,
                    response.getStatusLine().getStatusCode());
            throw new IOException("Bad response status");
        } else {
            String strResponse = EntityUtils.toString(response.getEntity());
            LOGGER.debug("Response from Viber {}", strResponse);
            return strResponse;
        }
    }
}
