package ru.multicon.viber4j.account;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ru.multicon.viber4j.utils.JsonUtils;
import ru.multicon.viber4j.utils.ViberConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * The account details as registered in Viber
 *
 * @author n.zvyagintsev
 */
public class AccountInfo {

    private AccountInfo(JsonObject json) {
        this.json = json;
    }

    private final JsonObject json;

    public String getStatus() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.STATUS);
    }

    public String getStatusMessage() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.STATUS_MESSAGE);
    }

    public String getId() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.ID);
    }

    public String getName() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.NAME);
    }

    public String getUri() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.URI);
    }

    public String getIcon() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.ICON);
    }

    public String getBackground() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.BACKGROUND);
    }

    public String getCategory() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.CATEGORY);
    }

    public String getSubcategory() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.SUB_CATEGORY);
    }

    public Float getLatitude() {
        return JsonUtils.getJsonValueFloat(json, ViberConstants.LOCATION, ViberConstants.LATITUDE);
    }

    public Float getLongitude() {
        return JsonUtils.getJsonValueFloat(json, ViberConstants.LOCATION, ViberConstants.LONGITUDE);
    }

    public String getCountry() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.COUNTRY);
    }

    public String getWebHook() {
        return JsonUtils.getJsonValueStr(json, ViberConstants.WEBHOOK);
    }

    public List<String> getEventTypes() {
        // TODO: implement
        return null;
    }

    public Integer getSubscribersCount() {
        return JsonUtils.getJsonValueInt(json, ViberConstants.SUBSCRIBERS_COUNT);
    }

    List<Member> getMembers() {
        return Optional.ofNullable(json.get(ViberConstants.MEMBERS)).map(JsonElement::getAsJsonArray).
                map(arr -> {
                    List<Member> members = new ArrayList<>(arr.size());
                    arr.forEach(item -> members.add(JsonUtils.gson.fromJson(item, Member.class)));
                    return members;
                }).orElse(Collections.EMPTY_LIST);
    }

    public class Member {
        private String id;
        private String name;
        private String avatar;
        private String role;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    public static AccountInfo fromStr(String accInfo) {
        return new AccountInfo(JsonUtils.parseString(accInfo));
    }

    public static AccountInfo EMPTY = new AccountInfo(new JsonObject());
}
