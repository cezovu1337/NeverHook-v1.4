package org.neverhook.client.feature.impl.visual;

import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.render.EventRender2D;
import org.neverhook.client.event.events.impl.render.EventRender3D;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.feature.impl.Type;
import org.neverhook.client.settings.impl.BooleanSetting;
import org.neverhook.client.settings.impl.ColorSetting;
import org.neverhook.client.settings.impl.ListSetting;

import java.awt.*;

public class ItemESP extends Feature {

    public BooleanSetting fullBox;
    public ColorSetting colorEsp;
    public ListSetting colorMode = new ListSetting("Color Box Mode", "Custom", () -> true, "Astolfo", "Rainbow", "Client", "Custom");
    public ListSetting itemEspMode = new ListSetting("ItemESP Mode", "2D", () -> true, "2D", "3D");
    public BooleanSetting entityName = new BooleanSetting("Entity Name", true, () -> true);

    public ItemESP() {
        super("ItemESP", "Отображение айтемов", Type.Visuals);
        colorEsp = new ColorSetting("ItemEsp Color", new Color(0xFFFFFF).getRGB(), () -> colorMode.currentMode.equals("Custom"));
        fullBox = new BooleanSetting("Full Box", false, () -> itemEspMode.currentMode.equals("3D"));
        addSettings(itemEspMode, colorMode, colorEsp, fullBox, entityName);
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        String mode = itemEspMode.getOptions();
        setSuffix(mode);
    }
}
