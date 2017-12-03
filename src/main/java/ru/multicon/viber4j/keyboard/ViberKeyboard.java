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

    public ViberKeyboard addButton(ViberButton button) {
        buttons.add(button);
        return this;
    }

    public ViberKeyboard setDefaultHeight(Boolean defaultHeight) {
        this.defaultHeight = defaultHeight;
        return this;
    }

    public JsonObject toJson() {
        if (CollectionUtils.isEmpty(buttons))
            return null;
        JsonObject keyboard = new JsonObject();
        keyboard.addProperty("Type", ViberConstants.KEYBOARD);
        keyboard.addProperty("DefaultHeight", defaultHeight);
        JsonArray buttonsArray = new JsonArray();
        for (ViberButton button : buttons) {
            buttonsArray.add(button.toJson());
        }
        keyboard.add(ViberConstants.BUTTONS, buttonsArray);
        return keyboard;
    }
}
