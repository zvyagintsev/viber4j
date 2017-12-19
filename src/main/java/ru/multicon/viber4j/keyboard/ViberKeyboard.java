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
public class ViberKeyboard {

    private List<ViberButton> buttons = new ArrayList<>();

    private Boolean defaultHeight = Boolean.TRUE;
    private String bgColor;
    private Integer customDefaultHeight;
    private Integer buttonsGroupColumns;
    private Integer buttonsGroupRows;
    private Integer heightScale;
    private InputFieldState state;

    public ViberKeyboard addButton(ViberButton button) {
        buttons.add(button);
        return this;
    }

    public ViberKeyboard setBgColor(String bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public ViberKeyboard setDefaultHeight(Boolean defaultHeight) {
        this.defaultHeight = defaultHeight;
        return this;
    }

    public ViberKeyboard setCustomDefaultHeight(Integer customDefaultHeight) {
        this.customDefaultHeight = customDefaultHeight;
        return this;
    }

    public ViberKeyboard setHeightScale(Integer heightScale) {
        this.heightScale = heightScale;
        return this;
    }

    public ViberKeyboard setButtonsGroupColumns(Integer buttonsGroupColumns) {
        this.buttonsGroupColumns = buttonsGroupColumns;
        return this;
    }

    public ViberKeyboard setButtonsGroupRows(Integer buttonsGroupRows) {
        this.buttonsGroupRows = buttonsGroupRows;
        return this;
    }

    public ViberKeyboard setInputFieldState(InputFieldState state) {
        this.state = state;
        return this;
    }

    enum InputFieldState {
        REGULAR, MINIMIZED, HIDDEN;
    }

    /**
     * Creates JsonObject with keyboard properties for sending to Viber
     *
     * @param type type of Keyboard
     * @return
     */
    public JsonObject toJson(String type) {
        if (CollectionUtils.isEmpty(buttons))
            return null;
        JsonObject keyboard = new JsonObject();
        if(StringUtils.isNotEmpty(type))
            keyboard.addProperty("Type", type);
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
