package ru.multicon.viber4j.keyboard;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.collections4.CollectionUtils;
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
    private Integer state;

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

    public ViberKeyboard setInputFieldState(Integer state) {
        this.state = state;
        return this;
    }

    enum InputFieldState {
        regular, minimized, hidden;
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
        keyboard.addProperty("Type", type);
        keyboard.addProperty("DefaultHeight", defaultHeight);
        keyboard.addProperty("BgColor", bgColor);
        keyboard.addProperty("CustomDefaultHeight", customDefaultHeight);
        keyboard.addProperty("ButtonsGroupColumns", buttonsGroupColumns);
        keyboard.addProperty("ButtonsGroupRows", buttonsGroupRows);
        keyboard.addProperty("HeightScale", heightScale);
        keyboard.addProperty("InputFieldState", state);
        JsonArray buttonsArray = new JsonArray();
        for (ViberButton button : buttons) {
            buttonsArray.add(button.toJson());
        }
        keyboard.add(ViberConstants.BUTTONS, buttonsArray);
        return keyboard;
    }
}
