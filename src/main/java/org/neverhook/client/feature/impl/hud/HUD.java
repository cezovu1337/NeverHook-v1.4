package org.neverhook.client.feature.impl.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import org.neverhook.client.NeverHook;
import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.render.EventRender2D;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.feature.impl.Type;
import org.neverhook.client.helpers.render.rect.RectHelper;
import org.neverhook.client.settings.impl.BooleanSetting;
import org.neverhook.client.settings.impl.ColorSetting;
import org.neverhook.client.settings.impl.ListSetting;
import org.neverhook.client.settings.impl.NumberSetting;
import org.neverhook.client.ui.components.draggable.DraggableModule;

import java.awt.*;

public class HUD extends Feature {

    public static float globalOffset;
    public static ListSetting font;
    public static BooleanSetting clientInfo;
    public static BooleanSetting worldInfo;
    public static BooleanSetting potion;
    public static BooleanSetting potionIcons;
    public static BooleanSetting potionTimeColor;
    public static BooleanSetting armor;
    public static BooleanSetting rustHUD;
    public static BooleanSetting indicator;
    public static ListSetting indicatorMode;
    public static BooleanSetting blur = new BooleanSetting("Blur", false, () -> true);
    public static ListSetting colorList = new ListSetting("Global Color", "Astolfo", () -> true, "Astolfo", "Static", "Fade", "Rainbow", "Custom", "None", "Category");
    public static NumberSetting time = new NumberSetting("Color Time", 30, 1, 100, 1, () -> true);
    public static ColorSetting onecolor = new ColorSetting("One Color", new Color(0xFFFFFF).getRGB(), () -> HUD.colorList.currentMode.equals("Fade") || HUD.colorList.currentMode.equals("Custom") || HUD.colorList.currentMode.equals("Static"));
    public static ColorSetting twocolor = new ColorSetting("Two Color", new Color(0xFF0000).getRGB(), () -> HUD.colorList.currentMode.equals("Custom"));
    public float animation = 0;
    private float cooledAttackStrength = 0;
    private float move = 0;

    public HUD() {
        super("HUD", "Показывает информацию на экране", Type.Hud);
        clientInfo = new BooleanSetting("Client Info", true, () -> true);
        worldInfo = new BooleanSetting("World Info", true, () -> true);
        potion = new BooleanSetting("Potion Status", true, () -> true);
        potionIcons = new BooleanSetting("Potion Icons", true, potion::getBoolValue);
        potionTimeColor = new BooleanSetting("Potion Time Color", true, potion::getBoolValue);
        armor = new BooleanSetting("Armor Status", true, () -> true);
        rustHUD = new BooleanSetting("Rust HUD", false, () -> true);
        indicator = new BooleanSetting("Crosshair Indicator", false, () -> true);
        indicatorMode = new ListSetting("Indicator Mode", "Onetap v2", () -> indicator.getBoolValue(), "Skeet", "Onetap v2");
        font = new ListSetting("FontList", "RobotoRegular", () -> true, "Minecraft", "MontserratRegular", "MontserratLight", "Menlo", "Comfortaa", "SF UI", "Calibri", "Verdana", "CircleRegular", "RobotoRegular", "JetBrains Mono", "LucidaConsole", "Corporative Sans", "Lato", "RaleWay", "Product Sans", "Arial", "Open Sans", "Kollektif", "Ubuntu");
        addSettings(colorList, time, onecolor, twocolor, font, clientInfo, worldInfo, potion, potionIcons, potionTimeColor, armor, rustHUD, blur, indicator, indicatorMode);
    }

    @EventTarget
    public void onRender2(EventRender2D e) {
    }

    @EventTarget
    public void onRender2D(EventRender2D e) {
        for (DraggableModule draggableModule : NeverHook.instance.draggableManager.getMods()) {
            if (getState() && !draggableModule.name.equals("LogoComponent")) {
                draggableModule.draw();
            }
        }
        float target = (mc.currentScreen instanceof ChatScreen) ? 15 : 0;
        float delta = globalOffset - target;
        globalOffset -= delta / Math.max(1, mc.fps) * 10;
        if (!Double.isFinite(globalOffset)) {
            globalOffset = 0;
        }
        if (globalOffset > 15) {
            globalOffset = 15;
        }
        if (globalOffset < 0) {
            globalOffset = 0;
        }
    }
}
