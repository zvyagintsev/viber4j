package ru.multicon.viber4j.incoming;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import ru.multicon.viber4j.utils.JsonUtils;
import ru.multicon.viber4j.utils.ViberConstants;

import java.util.Optional;

/**
 * Class for processing an incoming message from Viber
 *
 * @author n.zvyagintsev
 */
public class IncomingImpl implements Incoming {

    private final JsonObject incoming;

    private IncomingImpl(JsonObject incoming) {
        this.incoming = incoming;
    }

    @Override
    public JsonObject toJsonObject() {
        return incoming;
    }

    @Override
    public String getEvent() {
        return Optional.ofNullable(incoming.get(ViberConstants.EVENT)).
                map(JsonElement::getAsString).orElse(StringUtils.EMPTY);
    }

    @Override
    public String getTimestamp() {
        return JsonUtils.getJsonValueStr(incoming, ViberConstants.TIMESTAMP);
    }

    @Override
    public String getMessageToken() {
        return JsonUtils.getJsonValueStr(incoming, ViberConstants.MESSAGE_TOKEN);
    }

    @Override
    public String getContext() {
        return JsonUtils.getJsonValueStr(incoming, ViberConstants.CONTEXT);
    }

    @Override
    public Boolean getSubscribed() {
        return JsonUtils.getJsonValueBool(incoming, ViberConstants.SUBSCRIBED);
    }

    @Override
    public String getSenderAvatar() {
        return JsonUtils.getJsonValueStr(
                incoming, ViberConstants.SENDER, ViberConstants.AVATAR);
    }

    @Override
    public String getSenderName() {
        return JsonUtils.getJsonValueStr(
                incoming, ViberConstants.SENDER, ViberConstants.NAME);
    }

    @Override
    public String getSenderId() {
        return JsonUtils.getJsonValueStr(
                incoming, ViberConstants.SENDER, ViberConstants.ID);
    }

    @Override
    public String getSenderLanguage() {
        return JsonUtils.getJsonValueStr(
                incoming, ViberConstants.SENDER, ViberConstants.LANGUAGE);
    }

    @Override
    public String getSenderApiVersion() {
        return JsonUtils.getJsonValueStr(
                incoming, ViberConstants.SENDER, ViberConstants.API_VERSION);
    }

    @Override
    public String getMessageType() {
        return JsonUtils.getJsonValueStr(
                incoming, ViberConstants.MESSAGE, ViberConstants.TYPE);
    }

    @Override
    public String getMessageText() {
        return JsonUtils.getJsonValueStr(
                incoming, ViberConstants.MESSAGE, ViberConstants.MESSAGE_TEXT);
    }

    @Override
    public String getMediaUrl() {
        return JsonUtils.getJsonValueStr(
                incoming, ViberConstants.MESSAGE, ViberConstants.MEDIA_URL);
    }

    @Override
    public Float getLocationLat() {
        return JsonUtils.getJsonValueFloat(
                incoming, ViberConstants.MESSAGE, ViberConstants.LATITUDE);
    }

    @Override
    public Float getLocationLon() {
        return JsonUtils.getJsonValueFloat(
                incoming, ViberConstants.MESSAGE, ViberConstants.LONGITUDE);
    }

    public static IncomingImpl fromString(String incoming) {
        return new IncomingImpl(JsonUtils.parseString(incoming));
    }
}
