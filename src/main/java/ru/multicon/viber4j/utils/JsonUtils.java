package ru.multicon.viber4j.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Utility class for working with json
 *
 * @author n.zvyagintsev
 */
public class JsonUtils {

    public static String getJsonValueStr(JsonObject jsonObject, String key1, String key2) {
        return Optional.ofNullable(jsonObject.get(key1)).
                map(JsonElement::getAsJsonObject).
                map(internalJson -> internalJson.get(key2)).
                map(JsonElement::getAsString).orElse(StringUtils.EMPTY);
    }

    public static Float getJsonValueFloat(JsonObject jsonObject, String key1, String key2) {
        return Optional.ofNullable(jsonObject.get(key1)).
                map(JsonElement::getAsJsonObject).
                map(internalJson -> internalJson.get(key2)).
                map(JsonElement::getAsFloat).orElse(null);
    }

    public static String getJsonValueStr(JsonObject jsonObject, String key) {
        return Optional.ofNullable(jsonObject.get(key)).
                map(JsonElement::getAsString).orElse(StringUtils.EMPTY);
    }

    public static Boolean getJsonValueBool(JsonObject jsonObject, String key) {
        return Optional.ofNullable(jsonObject.get(key)).
                map(JsonElement::getAsBoolean).orElse(null);
    }

    public static Integer getJsonValueInt(JsonObject jsonObject, String key) {
        return Optional.ofNullable(jsonObject.get(key)).
                map(JsonElement::getAsInt).orElse(null);
    }

    public final static Gson gson = new GsonBuilder().create();

    public static JsonObject parseString(String message) {
        return gson.fromJson(message, JsonObject.class);
    }
}
