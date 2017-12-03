package ru.multicon.viber4j;

import ru.multicon.viber4j.account.AccountInfo;
import ru.multicon.viber4j.account.UserDetails;
import ru.multicon.viber4j.account.UserOnline;
import ru.multicon.viber4j.outgoing.Outgoing;

import java.util.List;

/**
 * @author n.zvyagintsev
 */
public interface ViberBot {

    /**
     * Registers endpoint for receiving messages from the Viber
     *
     * @param webHookUrl endpoint for receiving messages from the Viber.
     * @return post message result
     */
    boolean setWebHook(String webHookUrl);

    boolean setWebHook(String webHookUrl, List<CallbackEvent> events);

    boolean removeWebHook();

    Outgoing messageForUser(String receiverId);

    Outgoing broadcastMessage(List<String> receiverIds);

    Outgoing publicMessage(String fromId);

    AccountInfo getAccountInfo();

    UserDetails getUserDetails(String userId);

    List<UserOnline> getUserOnline(List<String> receiverIds);

    enum CallbackEvent {
        delivered, seen, failed, subscribed, unsubscribed, conversation_started
    }
}
