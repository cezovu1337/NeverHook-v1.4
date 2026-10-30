package org.neverhook.client.feature.impl.visual;

import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.render.EventRender2D;
import org.neverhook.client.event.events.impl.render.EventRender3D;
import org.neverhook.client.event.events.impl.render.EventRenderPlayerName;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.feature.impl.Type;
import org.neverhook.client.settings.impl.BooleanSetting;
import org.neverhook.client.settings.impl.ColorSetting;
import org.neverhook.client.settings.impl.ListSetting;
import org.neverhook.client.settings.impl.NumberSetting;

import java.awt.*;

public class EntityESP extends Feature {

    public static ListSetting espMode;
    public static BooleanSetting tags;
    public static NumberSetting glowAlpha;
    private final ColorSetting colorEsp;
    private final ColorSetting triangleColor;
    private final BooleanSetting fullBox;
    private final BooleanSetting heathPercentage;
    private final BooleanSetting tagsBackground;
    private final BooleanSetting border;
    private final BooleanSetting filled = new BooleanSetting("Filled", false, () -> espMode.currentMode.equals("2D"));
    private final ColorSetting filledColor = new ColorSetting("Filled Color", Integer.MIN_VALUE, () -> filled.getBoolValue() && espMode.currentMode.equals("2D"));
    public BooleanSetting healRect = new BooleanSetting("Health Rect", true, () -> true);
    public BooleanSetting triangleESP;
    public BooleanSetting ignoreInvisible = new BooleanSetting("Ignore Invisible", true, () -> true);
    public ListSetting healcolorMode = new ListSetting("Color Health Mode", "Custom", () -> healRect.getBoolValue(), "Astolfo", "Health", "Rainbow", "Client", "Custom");
    private final ColorSetting healColor = new ColorSetting("Health Border Color", 0xffffffff, () -> healcolorMode.currentMode.equals("Custom"));
    public ListSetting csgoMode;
    public ListSetting colorMode = new ListSetting("Color Box Mode", "Custom", () -> espMode.currentMode.equals("Box") || espMode.currentMode.equals("2D"), "Astolfo", "Rainbow", "Client", "Custom");
    public ListSetting triangleMode = new ListSetting("Triangle Mode", "Custom", () -> triangleESP.getBoolValue(), "Astolfo", "Rainbow", "Client", "Custom");
    public NumberSetting xOffset;
    public NumberSetting yOffset;
    public NumberSetting size;

    public EntityESP() {
        super("EntityESP", "Показывает игроков, ник и их здоровье сквозь стены", Type.Visuals);
        espMode = new ListSetting("ESP Mode", "2D", () -> true, "2D", "Box", "Glow");
        border = new BooleanSetting("Border Rect", true, () -> espMode.currentMode.equals("2D"));
        csgoMode = new ListSetting("Border Mode", "Box", () -> espMode.currentMode.equals("2D") && border.getBoolValue(), "Box", "Corner");
        tags = new BooleanSetting("Render Tags", true, () -> espMode.currentMode.equals("2D"));
        tagsBackground = new BooleanSetting("Tags Background", true, tags::getBoolValue);
        colorEsp = new ColorSetting("ESP Color", new Color(0xFFFFFF).getRGB(), () -> !colorMode.currentMode.equals("Client") && (espMode.currentMode.equals("2D") || espMode.currentMode.equals("Box") || espMode.currentMode.equals("Glow")));
        glowAlpha = new NumberSetting("Glow Alpha", 1, 0.2F, 1, 0.1F, () -> espMode.currentMode.equals("Glow"));
        fullBox = new BooleanSetting("Full Box", false, () -> espMode.currentMode.equals("Box"));
        heathPercentage = new BooleanSetting("Health Percentage", false, () -> espMode.currentMode.equals("2D"));
        triangleESP = new BooleanSetting("Triangle ESP", true, () -> true);
        triangleColor = new ColorSetting("Triangle Color", Color.PINK.getRGB(), () -> triangleESP.getBoolValue() && triangleMode.currentMode.equals("Custom"));
        xOffset = new NumberSetting("Triangle XOffset", 10, 0, 50, 5, () -> triangleESP.getBoolValue());
        yOffset = new NumberSetting("Triangle YOffset", 0, 0, 50, 5, () -> triangleESP.getBoolValue());
        size = new NumberSetting("Triangle Size", 5, 0, 20, 1, () -> triangleESP.getBoolValue());
        addSettings(espMode, csgoMode, colorMode, healcolorMode, healColor, colorEsp, border, fullBox, filled, filledColor, healRect, heathPercentage, tags, tagsBackground, ignoreInvisible, triangleESP, triangleMode, triangleColor, xOffset, yOffset, size);
    }

    @EventTarget
    public void onRender3D(EventRender3D event3D) {
    }

    @EventTarget
    public void onRenderTriangle(EventRender2D eventRender2D) {
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        String mode = espMode.getOptions();
        setSuffix(mode);
    }

    @EventTarget
    public void onRenderName(EventRenderPlayerName event) {
        if (getState()) {
            event.setCancelled(true);
        }
    }
}
