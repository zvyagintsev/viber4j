package ru.multicon.viber4j;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import ru.multicon.viber4j.http.DefaultViberClient;
import ru.multicon.viber4j.outgoing.Outgoing;
import ru.multicon.viber4j.outgoing.OutgoingImpl;
import ru.multicon.viber4j.account.AccountInfo;
import ru.multicon.viber4j.account.UserDetails;
import ru.multicon.viber4j.account.UserOnline;
import ru.multicon.viber4j.http.ViberClient;
import ru.multicon.viber4j.utils.ViberConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class ViberBotImpl implements ViberBot {

    private static final Logger logger = LoggerFactory.getLogger(ViberBotImpl.class);

    private final ViberClient viberClient;

    private SenderInfo sender;

    /**
     * Constructor for creating Viber bot object
     *
     * @param botToken auth token
     */
    ViberBotImpl(String botToken) {
        this.viberClient = new DefaultViberClient(botToken);
    }

    ViberBotImpl(String botToken, SenderInfo sender) {
        this.sender = sender;
        this.viberClient = new DefaultViberClient(botToken);
    }

    @Override
    public boolean setWebHook(String webHookUrl) {
        return setWebHook(webHookUrl, Arrays.asList(CallbackEvent.values()));
    }

    @Override
    public boolean setWebHook(String webHookUrl, List<CallbackEvent> events) {
        JsonObject message = new JsonObject();
        message.addProperty("url", webHookUrl);
        JsonArray eventsArray = new JsonArray();
        Stream.of(CallbackEvent.values()).forEach(
                viberEvent -> eventsArray.add(viberEvent.name()));
        message.add(ViberConstants.EVENT_TYPES, eventsArray);
        String response = null;
        try {
            response = viberClient.post(
                    message.toString(), ViberConstants.VIBER_REGISTRATION_URL);
            logger.info("Registration web-hook {} response {}", webHookUrl, response);
        } catch (IOException e) {
            logger.error("Web-hook {} registration failed {}", webHookUrl, e);
        }
        return StringUtils.isNotEmpty(response);
    }

    public boolean removeWebHook() {
        JsonObject message = new JsonObject();
        message.addProperty("url", StringUtils.EMPTY);
        String response = null;
        try {
            response = viberClient.post(
                    message.toString(), ViberConstants.VIBER_REGISTRATION_URL);
            logger.error("Removing web-hook response {}", message);
        } catch (IOException e) {
            logger.error("Removing web-hook error {}", e);
        }
        return StringUtils.isNotEmpty(response);
    }

    @Override
    public Outgoing messageForUser(String receiverId) {
        return OutgoingImpl.outgoingForReceiver(receiverId, viberClient, sender);
    }

    @Override
    public Outgoing broadcastMessage(List<String> receiverIds) {
        return OutgoingImpl.outgoingForBroadcast(receiverIds, viberClient, sender);
    }

    @Override
    public Outgoing publicMessage(String fromId) {
        return OutgoingImpl.outgoingForPublic(fromId, viberClient, sender);
    }

    @Override
    public AccountInfo getAccountInfo() {
        String response;
        try {
            response = viberClient.post("{}", ViberConstants.VIBER_ACC_INFO_URL);
        } catch (IOException e) {
            return AccountInfo.EMPTY;
        }
        return AccountInfo.fromStr(response);
    }

    @Override
    public UserDetails getUserDetails(String userId) {
        String request = "{\"id\":\"" + userId + "\"}";
        String response;
        try {
            response = viberClient.post(request, ViberConstants.VIBER_USER_DETAILS_URL);
        } catch (IOException e) {
            return UserDetails.EMPTY;
        }
        return UserDetails.fromString(response);
    }

    @Override
    public List<UserOnline> getUserOnline(List<String> receiverIds) {
        StringBuilder request = new StringBuilder("{\"ids\":[");
        receiverIds.forEach(id -> request.append('"').append(id).append('"')); // TODO: Where ","?
        request.append("]}");
        String response;
        try {
            response = viberClient.post(request.toString(), ViberConstants.VIBER_USER_ONLINE_URL);
        } catch (IOException e) {
            return Collections.EMPTY_LIST;
        }
        return UserOnline.fromString(response);
    }
}
