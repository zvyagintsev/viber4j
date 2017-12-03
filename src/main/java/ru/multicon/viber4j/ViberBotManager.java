package ru.multicon.viber4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author n.zvyagintsev
 */
public class ViberBotManager {

    private static final Map<String, ViberBot> bots = new HashMap<>();

    /**
     * Creates Viber bot or loads it from pool
     *
     * @param authToken a Viber bot auth token
     * @return ViberBot instance
     */
    public static ViberBot viberBot(String authToken) {
        if (bots.containsKey(authToken))
            return bots.get(authToken);
        else {
            ViberBot bot = new ViberBotImpl(authToken);
            bots.put(authToken, bot);
            return bot;
        }
    }

    /**
     * Creates Viber bot or loads it from pool
     *
     * @param authToken    a Viber bot auth token
     * @param senderName   name of sender
     * @param senderAvatar avatar of sender
     * @return ViberBot instance
     */
    public static ViberBot viberBot(String authToken, String senderName, String senderAvatar) {
        if (bots.containsKey(authToken))
            return bots.get(authToken);
        else {
            ViberBot bot = new ViberBotImpl(authToken, new SenderInfo(senderName, senderAvatar));
            bots.put(authToken, bot);
            return bot;
        }
    }
}
