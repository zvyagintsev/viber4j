package ru.multicon.viber4j.account;

import com.google.gson.JsonObject;
import ru.multicon.viber4j.utils.JsonUtils;
import ru.multicon.viber4j.utils.ViberConstants;

/**
 * @author n.zvyagintsev
 */
public class UserDetails {

    private final JsonObject json;

    private UserDetails(JsonObject json) {
        this.json = json;
    }

    public String getId() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.ID);
    }

    public String getName() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.NAME);
    }

    public String getAvatar() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.AVATAR);
    }

    /**
     * @return 2 letters country code - ISO ALPHA-2 Code
     */
    public String getCountry() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.COUNTRY);
    }

    /**
     * @return Language code (ISO 639-1)
     */
    public String getLanguage() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.LANGUAGE);
    }


    public String getPrimaryDeviceOs() {
        return JsonUtils.getJsonValueStr(json, "primary_device_os");
    }

    /**
     * @return Currently only 1. Additional versions will be added in the future
     */
    public String getApiVersion() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.API_VERSION);
    }

    public String getViberVersion() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.VIBER_VERSION);
    }

    /**
     * @return Mobile Country Code
     */
    public String getMCC() {
        return null;
    }

    /**
     * @return Mobile Network Code
     */
    public String getMNC() {
        return null;
    }

    /**
     * @return The user’s device type
     */
    public String getDeviceType() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.DEVICE_TYPE);
    }

    /**
     * Parses string with User Details data
     *
     * @param str text with incoming json
     * @return UserDetails object
     */
    public static UserDetails fromString(String str) {
        return new UserDetails(JsonUtils.parseString(str));
    }

    public static UserDetails EMPTY = new UserDetails(new JsonObject());
}

