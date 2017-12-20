package ru.multicon.viber4j.keyboard;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import ru.multicon.viber4j.utils.JsonUtils;
import ru.multicon.viber4j.utils.ViberConstants;

import java.util.stream.Stream;

/**
 * Class for creating a custom Viber button.
 * Viber buttons are part of messages with keyboard and carousel messages.
 *
 * @author n.zvyagintsev
 */
public class ViberButton {

    private Integer columns;
    private Integer rows;
    private String bgColor;
    private BgMediaType bgMediaType;
    private String bgMedia;
    private Boolean bgLoop;
    private BtnActionType actionType;
    private String actionBody;
    private String image;
    private String text;
    private TextAlign textVAlign;
    private TextAlign textHAlign;
    private Integer textOpacity;
    private TextSize textSize;
    private String textBgGradientColor;
    private OpenURLType openURLType;
    private InternalBrowser internalBrowser;
    private Integer[] textPaddings;

    public ViberButton(String actionBody) {
        this.actionBody = actionBody;
    }

    /**
     * @param bgColor color code
     * @return current button instance
     */
    public ViberButton setBgColor(String bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    /**
     * @param bgMediaType type of button media (picture or gif)
     * @return current button instance
     */
    public ViberButton setBgMediaType(BgMediaType bgMediaType) {
        this.bgMediaType = bgMediaType;
        return this;
    }

    /**
     * @param bgLoop When true - animated background media (gif) will loop continuously.
     *               When false - animated background media will play once and stop
     * @return current button instance
     */
    public ViberButton setBgLoop(Boolean bgLoop) {
        this.bgLoop = bgLoop;
        return this;
    }

    /**
     * @param actionType push button processing type
     * @return current button instance
     */
    public ViberButton setActionType(BtnActionType actionType) {
        this.actionType = actionType;
        return this;
    }

    /**
     * @param image Valid image URL
     * @return current button instance
     */
    public ViberButton setImage(String image) {
        this.image = image;
        return this;
    }

    public ViberButton setTextPaddings(Integer top, Integer left, Integer bottom, Integer right) {
        this.textPaddings = new Integer[4];
        this.textPaddings[0] = top;
        this.textPaddings[1] = left;
        this.textPaddings[2] = bottom;
        this.textPaddings[3] = right;
        return this;
    }

    public ViberButton setTextPaddings(Integer paddings) {
        this.textPaddings = new Integer[4];
        Stream.of(0, 1, 2, 3).forEach(ind -> textPaddings[ind] = paddings);
        return this;
    }

    /**
     * @param textAlign vertical alignment of button text
     * @return current button instance
     */
    public ViberButton setTextVAlign(TextAlign textAlign) {
        this.textVAlign = textAlign;
        return this;
    }

    /**
     * @param textAlign horizontal alignment of button text
     * @return current button instance
     */
    public ViberButton setTextHAlign(TextAlign textAlign) {
        this.textHAlign = textAlign;
        return this;
    }

    public ViberButton setTextOpacity(Integer textOpacity) {
        this.textOpacity = textOpacity;
        return this;
    }

    /**
     * @param textSize button text size
     * @return current button instance
     */
    public ViberButton setTextSize(TextSize textSize) {
        this.textSize = textSize;
        return this;
    }

    /**
     * @param columns button width in columns
     * @return current button instance
     */
    public ViberButton setColumns(Integer columns) {
        this.columns = columns;
        return this;
    }

    /**
     * @param rows Button height in columns
     * @return current button instance
     */
    public ViberButton setRows(Integer rows) {
        this.rows = rows;
        return this;
    }

    /**
     * @param text button capture
     * @return current button instance
     */
    public ViberButton setText(String text) {
        this.text = text;
        return this;
    }

    /**
     * @param bgMedia URL of background media (gif or picture)
     * @return current button instance
     */
    public ViberButton setBgMedia(String bgMedia) {
        this.bgMedia = bgMedia;
        return this;
    }

    public ViberButton setTextBgGradientColor(String textBgGradientColor) {
        this.textBgGradientColor = textBgGradientColor;
        return this;
    }

    public ViberButton setOpenURLType(OpenURLType openURLType) {
        this.openURLType = openURLType;
        return this;
    }

    public ViberButton setInternalBrowser(InternalBrowser internalBrowser) {
        this.internalBrowser = internalBrowser;
        return this;
    }

    /**
     * Creates json object that contains all the properties of the Viber button
     *
     * @return a json object with Viber button properties
     */
    public JsonObject toJson() {
        JsonObject button = new JsonObject();
        button.addProperty(ViberConstants.BTN_ACTION_BODY, actionBody);
        if (rows != null)
            button.addProperty(ViberConstants.BTN_ROWS, rows);
        if (columns != null)
            button.addProperty(ViberConstants.BTN_COLUMNS, columns);
        if (StringUtils.isNotEmpty(bgColor))
            button.addProperty(ViberConstants.BG_COLOR, bgColor);
        if (bgMediaType != null)
            button.addProperty(ViberConstants.BTN_MEDIA_TYPE, bgMediaType.name().toLowerCase());
        if (StringUtils.isNotEmpty(bgMedia))
            button.addProperty(ViberConstants.BTN_BG_MEDIA, bgMedia);
        if (bgLoop != null)
            button.addProperty(ViberConstants.BTN_BG_LOOP, bgLoop);
        button.addProperty(ViberConstants.BTN_ACTION_TYPE, actionType.actionName());
        if (StringUtils.isNotEmpty(image))
            button.addProperty(ViberConstants.BTN_IMAGE, image);
        if (StringUtils.isNotEmpty(text))
            button.addProperty(ViberConstants.BTN_TEXT, text);
        if (textPaddings != null) {
            JsonArray paddings = new JsonArray(4);
            for (Integer padding : textPaddings)
                paddings.add(padding);
            button.add(ViberConstants.BTN_TEXT_PADDING, paddings);
        }
        if (this.textVAlign != null)
            button.addProperty(
                    ViberConstants.BTN_TEXT_V_ALIGN, textVAlign.name().toLowerCase());
        if (this.textHAlign != null)
            button.addProperty(
                    ViberConstants.BTN_TEXT_H_ALIGN, textHAlign.name().toLowerCase());
        if (textOpacity != null)
            button.addProperty(ViberConstants.BTN_TEXT_OPACITY, textOpacity);
        if (textSize != null)
            button.addProperty(ViberConstants.BTN_TEXT_SIZE, textSize.name().toLowerCase());
        if (StringUtils.isNotEmpty(textBgGradientColor))
            button.addProperty(ViberConstants.BTN_TEXT_BG_GRADIENT_COLOR, textBgGradientColor);
        if (openURLType != null)
            button.addProperty(
                    ViberConstants.BTN_OPEN_URL_TYPE, openURLType.name().toLowerCase());
        if (internalBrowser != null)
            button.add(ViberConstants.BTN_INTERNAL_BROWSER, internalBrowser.toJson());
        return button;
    }

    public static ViberButton createButtonForUrl(String actionUrl) {
        return new ViberButton(actionUrl).setActionType(BtnActionType.OPEN_URL);
    }

    /**
     * Enum with button text size options
     */
    public enum TextSize {
        SMALL, LARGE, MEDIUM
    }

    /**
     * Enum with text alignment types (for properties TextVAlign, TextHAlign)
     */
    public enum TextAlign {
        LEFT, MIDDLE, RIGHT
    }

    public enum BgMediaType {
        PICTURE, GIF
    }

    public enum OpenURLType {
        INTERNAL, EXTERNAL
    }

    public static class InternalBrowser {
        private String actionButton;
        private String actionPredefinedURL;
        private String titleType;
        private String customTitle;
        private String mode;
        private String footerType;

        public JsonObject toJson() {
            String str = JsonUtils.gson.toJson(this, InternalBrowser.class);
            return JsonUtils.parseString(str);
        }

        public void setActionButton(String actionButton) {
            this.actionButton = actionButton;
        }

        public void setTitleType(String titleType) {
            this.titleType = titleType;
        }

        public void setCustomTitle(String customTitle) {
            this.customTitle = customTitle;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public void setFooterType(String footerType) {
            this.footerType = footerType;
        }

        public void setActionPredefinedURL(String actionPredefinedURL) {
            this.actionPredefinedURL = actionPredefinedURL;
        }
    }
}
