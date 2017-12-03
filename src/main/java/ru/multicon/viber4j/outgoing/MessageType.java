package ru.multicon.viber4j.outgoing;

/**
 * Enum with message types
 *
 * @author n.zvyagintsev
 */
public enum MessageType {
    TEXT("text"),
    PICTURE("picture"),
    VIDEO("video"),
    FILE("file"),
    LOCATION("location"),
    CONTACT("contact"),
    STICKER("sticker"),
    CAROUSEL("rich_media"),
    URL("url");

    private String keyName;

    MessageType(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }
}
