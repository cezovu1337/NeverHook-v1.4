package org.neverhook.client.feature.impl.visual;

import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.render.EventRender3D;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.feature.impl.Type;
import org.neverhook.client.settings.impl.BooleanSetting;
import org.neverhook.client.settings.impl.ColorSetting;
import org.neverhook.client.settings.impl.ListSetting;
import org.neverhook.client.settings.impl.NumberSetting;

import java.awt.*;

public class ChinaHat extends Feature {

    public ListSetting colorMode = new ListSetting("Color", "Rainbow", () -> true, "Rainbow", "Astolfo", "Client", "Custom");
    public ListSetting colorModeTop = new ListSetting("Color Top", "Rainbow", () -> true, "Rainbow", "Astolfo", "Client", "Custom");
    public ColorSetting customColor = new ColorSetting("Custom Color", new Color(0x0033FF).getRGB(), () -> colorModeTop.currentMode.equals("Custom"));
    public ColorSetting customColorTwo = new ColorSetting("Custom Color Two", new Color(0x0033FF).getRGB(), () -> colorMode.currentMode.equals("Custom"));
    public NumberSetting heightValue = new NumberSetting("Height", 0.4f, 0.1f, 20, 0.1F, () -> true);
    public NumberSetting widthValue = new NumberSetting("Width", 1.2F, 0.1f, 20, 0.1F, () -> true);
    public NumberSetting point = new NumberSetting("Points", 32, 1, 60, 1, () -> true);
    public BooleanSetting hide = new BooleanSetting("Hide In First Person", true, () -> true);

    public ChinaHat() {
        super("ChinaHat", "Показывает китайскую шляпу", Type.Visuals);
        addSettings(colorMode, colorModeTop, hide, widthValue, heightValue, point, customColor, customColorTwo);
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
    }
}
