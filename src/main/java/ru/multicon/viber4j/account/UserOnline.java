package ru.multicon.viber4j.account;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ru.multicon.viber4j.utils.JsonUtils;
import ru.multicon.viber4j.utils.ViberConstants;

import java.util.*;

public class UserOnline {

    private final JsonObject userOnline;

    private UserOnline(JsonObject userOnline) {
        this.userOnline = userOnline;
    }

    public String getId() {
        return JsonUtils.getJsonValueStr(userOnline, ViberConstants.ID);
    }

    public Integer getOnlineStatus() {
        return JsonUtils.getJsonValueInt(userOnline, ViberConstants.ONLINE_STATUS);
    }

    public String getOnlineStatusMessage() {
        return JsonUtils.getJsonValueStr(userOnline, ViberConstants.ONLINE_STATUS_MESSAGE);
    }

    public Date getLastOnline() {
        return Optional.ofNullable(userOnline.get("last_online")).
                map(jsonElement -> new Date(1000 * jsonElement.getAsLong())).orElse(null);
    }

    public static List<UserOnline> fromString(String str) {
        JsonObject onlineStatuses = JsonUtils.parseString(str);
        if (JsonUtils.getJsonValueBool(onlineStatuses, ViberConstants.STATUS) != null)
            return Collections.EMPTY_LIST;

        return Optional.ofNullable(onlineStatuses.get("users")).map(JsonElement::getAsJsonArray).
                map(array -> {
                    List<UserOnline> result = new ArrayList<>(array.size());
                    array.forEach(
                            item -> result.add(new UserOnline(item.getAsJsonObject())));
                    return result;
                }).orElse(Collections.EMPTY_LIST);
    }

    public static final UserOnline EMPTY = new UserOnline(new JsonObject());
}
