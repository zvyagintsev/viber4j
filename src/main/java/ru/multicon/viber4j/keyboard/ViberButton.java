package ru.multicon.viber4j.keyboard;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import ru.multicon.viber4j.utils.JsonUtils;
import ru.multicon.viber4j.utils.ViberConstants;

/**
 * Class for creating a custom Viber button.
 * Viber buttons are part of messages with keyboard and carousel messages.
 *
 * @author n.zvyagintsev
 */
public class ViberButton {

    private Integer column;
    private Integer row;
    private String bgColor;
    private String bgMediaType;
    private String bgMedia;
    private Boolean bgLoop;
    private String actionType;
    private final String actionBody;
    private String image;
    private String text;
    private String textVAlign;
    private String textHAlign;
    private Integer textOpacity;
    private String textSize;
    private String textBgGradientColor;
    private InternalBrowser internalBrowser;
    private Integer[] textPaddings;

    /**
     * Default constructor
     *
     * @param actionBody
     */
    public ViberButton(String actionBody) {
        this.actionBody = actionBody;
    }

    /**
     * @param bgColor
     * @return
     */
    public ViberButton setBgColor(String bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public ViberButton setBgMediaType(String bgMediaType) {
        this.bgMediaType = bgMediaType;
        return this;
    }

    public ViberButton setBgLoop(Boolean bgLoop) {
        this.bgLoop = bgLoop;
        return this;
    }

    public ViberButton setActionType(String bgActionType) {
        this.actionType = bgActionType;
        return this;
    }

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
        this.textPaddings[0] = paddings;
        this.textPaddings[1] = paddings;
        this.textPaddings[2] = paddings;
        this.textPaddings[3] = paddings;
        return this;
    }

    public ViberButton setTextVAlign(TextAlign textAlign) {
        this.textVAlign = textAlign.name();
        return this;
    }

    public ViberButton setTextHAlign(TextAlign textAlign) {
        this.textHAlign = textAlign.name();
        return this;
    }

    public ViberButton setTextOpacity(Integer textOpacity) {
        this.textOpacity = textOpacity;
        return this;
    }

    /**
     * Button's capture text size
     *
     * @param textSize
     * @return
     */
    public ViberButton setTextSize(TextSize textSize) {
        this.textSize = textSize.name();
        return this;
    }

    public ViberButton setColumn(Integer column) {
        this.column = column;
        return this;
    }

    public ViberButton setRow(Integer row) {
        this.row = row;
        return this;
    }

    public ViberButton setText(String text) {
        this.text = text;
        return this;
    }

    public ViberButton setBgMedia(String bgMedia) {
        this.bgMedia = bgMedia;
        return this;
    }

    public ViberButton setTextBgGradientColor(String textBgGradientColor) {
        this.textBgGradientColor = textBgGradientColor;
        return this;
    }

    public ViberButton setInternalBrowser(InternalBrowser internalBrowser) {
        this.internalBrowser = internalBrowser;
        return this;
    }

    /**
     * Create JsonObject which contains all properties of a Viber button
     *
     * @return json object with Viber button properties
     */
    public JsonObject toJson() {
        JsonObject button = new JsonObject();
        button.addProperty(ViberConstants.BTN_ACTION_BODY, actionBody);
        if (row != null)
            button.addProperty(ViberConstants.BTN_ROWS, bgColor);
        if (column != null)
            button.addProperty(ViberConstants.BTN_COLUMNS, column);
        if (StringUtils.isNotEmpty(bgColor))
            button.addProperty(ViberConstants.BG_COLOR, bgColor);
        if (StringUtils.isNotEmpty(bgMediaType))
            button.addProperty(ViberConstants.BTN_MEDIA_TYPE, bgMediaType);
        if (StringUtils.isNotEmpty(bgMedia))
            button.addProperty(ViberConstants.BTN_BG_MEDIA, bgMedia);
        if (bgLoop != null)
            button.addProperty(ViberConstants.BTN_BG_LOOP, bgLoop);
        if (StringUtils.isNotEmpty(actionType))
            button.addProperty(ViberConstants.BTN_ACTION_TYPE, actionType);
        if (StringUtils.isNotEmpty(image))
            button.addProperty(ViberConstants.BTN_IMAGE, image);
        if (StringUtils.isNotEmpty(text))
            button.addProperty(ViberConstants.BTN_TEXT, text);
        if(textPaddings != null) {
            JsonArray paddings = new JsonArray(4);
            for(Integer padding: textPaddings)
                paddings.add(padding);
            button.add(ViberConstants.BTN_TEXT_PADDING, paddings);
        }
        if (StringUtils.isNotEmpty(textVAlign))
            button.addProperty(ViberConstants.BTN_TEXT_V_ALIGN, textVAlign);
        if (StringUtils.isNotEmpty(textHAlign))
            button.addProperty(ViberConstants.BTN_TEXT_H_ALIGN, textHAlign);
        if (textOpacity != null)
            button.addProperty(ViberConstants.BTN_TEXT_OPACITY, textOpacity);
        if (StringUtils.isNotEmpty(textSize))
            button.addProperty(ViberConstants.BTN_TEXT_SIZE, textSize);
        if (StringUtils.isNotEmpty(textBgGradientColor))
            button.addProperty(ViberConstants.BTN_TEXT_BG_GRADIENT_COLOR, textBgGradientColor);
        if (internalBrowser != null)
            button.add(ViberConstants.BTN_INTERNAL_BROWSER, internalBrowser.toJson());
        return button;
    }

    public static ViberButton createButtonForUrl(String actionUrl) {
        return new ViberButton(actionUrl).setActionType("open-url");
    }

    /**
     * enum with variants of buttons caption size
     */
    public enum TextSize {
        small, large, medium
    }

    ;

    /**
     * Align of text (for properties TextVAlign, TextHAlign)
     */
    public enum TextAlign {
        left, middle, right
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
