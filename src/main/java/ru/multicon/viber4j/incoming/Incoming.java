package ru.multicon.viber4j.incoming;

import com.google.gson.JsonObject;

/**
 * @author n.zvyagintsev
 */
public interface Incoming {

    JsonObject toJsonObject();

    String getEvent();

    String getTimestamp();

    String getMessageToken();

    String getContext();

    Boolean getSubscribed();

    String getSenderAvatar();

    String getSenderName();

    String getSenderId();

    String getSenderLanguage();

    String getSenderApiVersion();

    String getMessageType();

    String getMessageText();

    String getMediaUrl();

    Float getLocationLat();

    Float getLocationLon();
}
