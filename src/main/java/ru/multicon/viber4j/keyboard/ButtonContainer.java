package ru.multicon.viber4j.keyboard;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import ru.multicon.viber4j.utils.ViberConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for creating a Viber keyboard
 *
 * @author n.zvyagintsev
 */
public class ButtonContainer {

    private List<ViberButton> buttons = new ArrayList<>();
    protected String type;
    protected String altText;

    private Boolean defaultHeight = Boolean.TRUE;
    private String bgColor;
    private Integer customDefaultHeight;
    private Integer buttonsGroupColumns;
    private Integer buttonsGroupRows;
    private Integer heightScale;
    private InputFieldState state;

    /**
     * Adds button to container.
     *
     * @param button the Viber button
     * @return this container instance
     */
    public ButtonContainer addButton(ViberButton button) {
        buttons.add(button);
        return this;
    }

    /**
     *
     * @param bgColor background color code
     * @return this container instance
     */
    public ButtonContainer setBgColor(String bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    /**
     *
     * @param defaultHeight When true - the keyboard will always be displayed with the same height as the native keyboard.
     *                      When false - short keyboards will be displayed with the minimal possible height
     * @return this container instance
     */
    public ButtonContainer setDefaultHeight(Boolean defaultHeight) {
        this.defaultHeight = defaultHeight;
        return this;
    }

    /**
     *
     * @param customDefaultHeight How much percent of free screen space in chat
     *                            should be taken by keyboard.
     * @return this container instance
     */
    public ButtonContainer setCustomDefaultHeight(Integer customDefaultHeight) {
        this.customDefaultHeight = customDefaultHeight;
        return this;
    }

    /**
     *
     * @param heightScale Allow use custom aspect ratio for Carousel content blocks.
     *                    Scales the height of the default square block (which is defined on client side)
     *                    to the given value in percents.
     * @return this container instance
     */
    public ButtonContainer setHeightScale(Integer heightScale) {
        this.heightScale = heightScale;
        return this;
    }

    /**
     *
     * @param buttonsGroupColumns  Represents size of block for grouping buttons during layout
     * @return this container instance
     */
    public ButtonContainer setButtonsGroupColumns(Integer buttonsGroupColumns) {
        this.buttonsGroupColumns = buttonsGroupColumns;
        return this;
    }

    /**
     *
     * @param buttonsGroupRows Represents size of block for grouping buttons during layout
     * @return this container instance
     */
    public ButtonContainer setButtonsGroupRows(Integer buttonsGroupRows) {
        this.buttonsGroupRows = buttonsGroupRows;
        return this;
    }

    /**
     *
     * @param state Customize the keyboard input field. regular- display regular size input field
     * @return this container instance
     */
    public ButtonContainer setInputFieldState(InputFieldState state) {
        this.state = state;
        return this;
    }

    enum InputFieldState {
        REGULAR, MINIMIZED, HIDDEN;
    }

    /**
     * Creates JsonObject with keyboard properties for sending to Viber
     *
     * @return json with button container properties
     */
    public JsonObject toJson() {
        if (CollectionUtils.isEmpty(buttons))
            return null;
        JsonObject keyboard = new JsonObject();
        if(StringUtils.isNotEmpty(type))
            keyboard.addProperty("Type", type);
        if(StringUtils.isNotEmpty(altText))
            keyboard.addProperty("alt_text", altText);
        if(defaultHeight != null)
            keyboard.addProperty("DefaultHeight", defaultHeight);
        if(StringUtils.isNotEmpty(bgColor))
            keyboard.addProperty("BgColor", bgColor);
        if(customDefaultHeight != null)
            keyboard.addProperty("CustomDefaultHeight", customDefaultHeight);
        if(buttonsGroupColumns != null)
            keyboard.addProperty("ButtonsGroupColumns", buttonsGroupColumns);
        if(buttonsGroupRows != null)
            keyboard.addProperty("ButtonsGroupRows", buttonsGroupRows);
        if(heightScale != null)
            keyboard.addProperty("HeightScale", heightScale);
        if(state != null)
        keyboard.addProperty("InputFieldState", state.name().toLowerCase());
        JsonArray buttonsArray = new JsonArray();
        for (ViberButton button : buttons) {
            buttonsArray.add(button.toJson());
        }
        keyboard.add(ViberConstants.BUTTONS, buttonsArray);
        return keyboard;
    }
}
