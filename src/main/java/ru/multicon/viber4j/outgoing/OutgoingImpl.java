package ru.multicon.viber4j.outgoing;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import ru.multicon.viber4j.SenderInfo;
import ru.multicon.viber4j.http.ViberClient;
import ru.multicon.viber4j.keyboard.RichMedia;
import ru.multicon.viber4j.keyboard.ViberKeyboard;
import ru.multicon.viber4j.utils.ViberConstants;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Class for working with outgoing messages
 *
 * @author n.zvyagintsev
 */
public class OutgoingImpl implements Outgoing {

    private final ViberClient viberClient;
    private final JsonObject message;

    private final String sendingUrl;

    private OutgoingImpl(
            JsonObject message, ViberClient viberClient, String sendingUrl, SenderInfo sender) {
        this.message = message;
        this.viberClient = viberClient;
        // default min_api_version
        message.addProperty(ViberConstants.MIN_API_VERSION, 1);
        if (sender != null)
            message.add(ViberConstants.SENDER, sender.toJson());
        this.sendingUrl = sendingUrl;
    }

    /**
     * Creates a public message
     *
     * @param fromId      ID sender of message
     * @param viberClient http client for sending message
     * @param sender      sender info
     * @return stub message
     */
    public static Outgoing outgoingForPublic(String fromId, ViberClient viberClient, SenderInfo sender) {
        if (StringUtils.isEmpty(fromId))
            throw new IllegalArgumentException();
        JsonObject message = new JsonObject();
        message.addProperty(ViberConstants.FROM, fromId);
        return new OutgoingImpl(message, viberClient, ViberConstants.VIBER_POST_PUBLIC_URL, sender);
    }

    /**
     * Creates a message to a client
     *
     * @param receiverId  client Id
     * @param viberClient http client for sending message
     * @param sender      sender info
     * @return stub message
     */
    public static Outgoing outgoingForReceiver(
            String receiverId, ViberClient viberClient, SenderInfo sender) {
        if (StringUtils.isEmpty(receiverId))
            throw new IllegalArgumentException();
        JsonObject message = new JsonObject();
        message.addProperty(ViberConstants.RECEIVER, receiverId);
        return new OutgoingImpl(message, viberClient, ViberConstants.VIBER_SEND_MSG_URL, sender);
    }

    /**
     * Creating a message to multiple Viber users who subscribe to the account
     *
     * @param receiverIds list of receivers Id
     * @param viberClient http client for sending message
     * @param sender      sender info
     * @return stub message
     */
    public static Outgoing outgoingForBroadcast(
            List<String> receiverIds, ViberClient viberClient, SenderInfo sender) {
        JsonObject message = new JsonObject();
        JsonArray broadcastList = new JsonArray();
        for (String receiverId : receiverIds)
            broadcastList.add(receiverId);
        message.add(ViberConstants.BROADCAST_LIST, broadcastList);
        return new OutgoingImpl(message, viberClient, ViberConstants.VIBER_POST_BROADCAST_URL, sender);
    }

    @Override
    public boolean postText(String text) {
        return postText(text, null);
    }

    @Override
    public boolean postText(String text, ViberKeyboard keyboard) {
        setMessageType(MessageType.TEXT);
        message.addProperty(ViberConstants.MESSAGE_TEXT, text);
        Optional.ofNullable(keyboard).
                map(viberKeyboard -> viberKeyboard.toJson()).
                ifPresent(jsonObject -> message.add(ViberConstants.KEYBOARD, jsonObject));
        return sendMessage();
    }

    @Override
    public boolean postPicture(String pictureUrl, String description) {
        return postPicture(pictureUrl, description, StringUtils.EMPTY);
    }

    @Override
    public boolean postPicture(String pictureUrl, String description, String thumbnailUrl) {
        setMessageType(MessageType.PICTURE);
        message.addProperty(ViberConstants.MEDIA_URL, pictureUrl);
        if (StringUtils.isNotEmpty(thumbnailUrl))
            message.addProperty(ViberConstants.THUMBNAIL, thumbnailUrl);
        message.addProperty(ViberConstants.MESSAGE_TEXT, description);
        return sendMessage();
    }

    @Override
    public boolean postVideo(String videoUrl, Integer size) {
        return postVideo(videoUrl, size, null, StringUtils.EMPTY);
    }

    @Override
    public boolean postVideo(String videoUrl, Integer size, Integer duration, String thumbnailUrl) {
        setMessageType(MessageType.VIDEO);
        message.addProperty(ViberConstants.MEDIA_URL, videoUrl);
        if (StringUtils.isNotEmpty(thumbnailUrl))
            message.addProperty(ViberConstants.THUMBNAIL, thumbnailUrl);
        message.addProperty(ViberConstants.ATTACHMENT_SIZE, size);
        if (duration != null)
            message.addProperty(ViberConstants.DURATION, duration);
        return sendMessage();
    }

    @Override
    public boolean postFile(String fileUrl, Integer size, String fileName) {
        setMessageType(MessageType.FILE);
        message.addProperty(ViberConstants.MEDIA_URL, fileUrl);
        message.addProperty(ViberConstants.ATTACHMENT_SIZE, size);
        message.addProperty(ViberConstants.FILE_NAME, fileName);
        return sendMessage();
    }

    @Override
    public boolean postContact(String contactName, String phone) {
        setMessageType(MessageType.CONTACT);
        JsonObject contact = new JsonObject();
        contact.addProperty(ViberConstants.NAME, contactName);
        contact.addProperty(ViberConstants.PHONE_NUMBER, phone);
        message.add(ViberConstants.CONTACT, contact);
        return sendMessage();
    }

    @Override
    public boolean postLocation(Float latitude, Float longitude) {
        setMessageType(MessageType.LOCATION);
        JsonObject location = new JsonObject();
        location.addProperty(ViberConstants.LATITUDE, latitude);
        location.addProperty(ViberConstants.LONGITUDE, longitude);
        message.add(ViberConstants.LOCATION, location);
        return sendMessage();
    }

    @Override
    public boolean postUrl(String url) {
        setMessageType(MessageType.URL);
        message.addProperty(ViberConstants.MEDIA_URL, url);
        return sendMessage();
    }

    @Override
    public boolean postSticker(Integer stickerId) {
        setMessageType(MessageType.STICKER);
        message.addProperty(ViberConstants.STICKER_ID, stickerId);
        return sendMessage();
    }

    @Override
    public boolean postCarousel(RichMedia richMedia) {
        setMessageType(MessageType.CAROUSEL);
        message.addProperty(ViberConstants.MIN_API_VERSION, 2);
        //message.addProperty(MessageType.CAROUSEL.getKeyName(), text);
        Optional.ofNullable(richMedia).
                map(rm -> rm.toJson()).
                ifPresent(jsonObject -> message.add(MessageType.CAROUSEL.getKeyName(), jsonObject));
        return sendMessage();
    }

    private boolean sendMessage() {
        try {
            String response = viberClient.post(
                    message.toString(), sendingUrl);
            return StringUtils.isNotEmpty(response);
        } catch (IOException e) {
            return false;
        }
    }

    private void setMessageType(MessageType type) {
        message.addProperty(
                ViberConstants.ATTACHMENT_TYPE, type.getKeyName());
    }
}
