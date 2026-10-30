package org.neverhook.client.feature.impl.hud;

import net.minecraft.client.Minecraft;
import org.neverhook.client.NeverHook;
import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.player.EventUpdate;
import org.neverhook.client.event.events.impl.render.EventRender2D;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.feature.impl.Type;
import org.neverhook.client.helpers.misc.ClientHelper;
import org.neverhook.client.helpers.palette.PaletteHelper;
import org.neverhook.client.helpers.render.ScreenHelper;
import org.neverhook.client.helpers.render.rect.RectHelper;
import org.neverhook.client.settings.impl.BooleanSetting;
import org.neverhook.client.settings.impl.ColorSetting;
import org.neverhook.client.settings.impl.ListSetting;
import org.neverhook.client.settings.impl.NumberSetting;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class FeatureList extends Feature {

    public static ListSetting sortMode;
    public static ListSetting fontRenderType;
    public static ListSetting borderMode;

    public static BooleanSetting backGround;
    public static BooleanSetting backGroundGradient = new BooleanSetting("BackGround Gradient", false, () -> backGround.getBoolValue());
    public static ColorSetting backGroundColor2 = new ColorSetting("BackGround Color Two", Color.BLACK.getRGB(), () -> backGround.getBoolValue() && backGroundGradient.getBoolValue());
    public static ColorSetting backGroundColor = new ColorSetting("BackGround Color", Color.BLACK.getRGB(), () -> backGround.getBoolValue());
    public static BooleanSetting border;
    public static BooleanSetting rightBorder;
    public static NumberSetting x;
    public static NumberSetting y;
    public static NumberSetting offset;
    public static NumberSetting size;
    public static NumberSetting borderWidth;
    public static NumberSetting rainbowSaturation;
    public static NumberSetting rainbowBright;
    public static NumberSetting fontX;
    public static NumberSetting fontY;
    public static BooleanSetting blur = new BooleanSetting("Blur", false, () -> backGround.getBoolValue());
    public static BooleanSetting suffix;
    public static ListSetting colorSuffixMode = new ListSetting("Suffix Mode Color", "Default", () -> suffix.getBoolValue(), "Astolfo", "Default", "Static", "Rainbow", "Custom", "Category");
    public static ColorSetting suffixColor = new ColorSetting("Suffix Color", Color.GRAY.getRGB(), () -> colorSuffixMode.currentMode.equals("Custom") || colorSuffixMode.currentMode.equals("Static") && suffix.getBoolValue());
    public static ListSetting position = new ListSetting("Position", "Right", () -> true, "Right", "Left");

    public FeatureList() {
        super("FeatureList", "Показывает список всех включенных модулей", Type.Hud);

        borderMode = new ListSetting("Border Mode", "Full", () -> border.getBoolValue(), "Full", "Single");
        sortMode = new ListSetting("FeatureList Sort", "Length", () -> true, "Length", "Alphabetical");
        fontRenderType = new ListSetting("FontRender Type", "Shadow", () -> true, "Default", "Shadow", "Outline");
        backGround = new BooleanSetting("Background", true, () -> true);
        border = new BooleanSetting("Border", true, () -> true);
        rightBorder = new BooleanSetting("Right Border", true, () -> true);
        suffix = new BooleanSetting("Suffix", true, () -> true);
        rainbowSaturation = new NumberSetting("Rainbow Saturation", 0.8F, 0.1F, 1F, 0.1F, () -> HUD.colorList.currentMode.equals("Rainbow") || colorSuffixMode.currentMode.equals("Rainbow"));
        rainbowBright = new NumberSetting("Rainbow Brightness", 1F, 0.1F, 1F, 0.1F, () -> HUD.colorList.currentMode.equals("Rainbow") || colorSuffixMode.currentMode.equals("Rainbow"));
        fontX = new NumberSetting("FontX", 0, -4, 20, 0.1F, () -> true);
        fontY = new NumberSetting("FontY", 0, -4, 20, 0.01F, () -> true);
        x = new NumberSetting("FeatureList X", 0, 0, 500, 1, () -> !blur.getBoolValue());
        y = new NumberSetting("FeatureList Y", 0, 0, 500, 1, () -> !blur.getBoolValue());
        offset = new NumberSetting("Font Offset", 11, 7, 20, 0.5F, () -> true);
        borderWidth = new NumberSetting("Border Width", 1, 0, 10, 0.1F, () -> rightBorder.getBoolValue());
        addSettings(position, sortMode, fontRenderType, borderMode, colorSuffixMode, suffixColor, fontX, fontY, border, rightBorder, suffix, borderWidth, backGround, backGroundGradient, backGroundColor, backGroundColor2, rainbowSaturation, rainbowBright, x, y, offset);
    }

    private static Feature getNextEnabledFeature(List<Feature> features, int index) {
        for (int i = index; i < features.size(); i++) {
            Feature feature = features.get(i);
            if (feature.getState() && feature.visible) {
                if (!feature.getSuffix().equals("ClickGui") && feature.visible) {
                    return feature;
                }
            }
        }
        return null;
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        String mode = HUD.colorList.getCurrentMode();
        setSuffix(mode);
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        float width = event.getResolution().getScaledWidth() - (FeatureList.rightBorder.getBoolValue() ? borderWidth.getNumberValue() : 0);
        float y = 1;
        String arraySort = sortMode.getCurrentMode();
        if (NeverHook.instance.featureManager.getFeatureByClass(FeatureList.class).getState() && !mc.options.renderDebug) {
            NeverHook.instance.featureManager.getFeatureList().sort(arraySort.equalsIgnoreCase("Alphabetical") ? Comparator.comparing(Feature::getLabel) : Comparator.comparingInt(module -> !HUD.font.currentMode.equals("Minecraft") ? -ClientHelper.getFontRender().getStringWidth(suffix.getBoolValue() ? module.getSuffix() : module.getLabel()) : -mc.fontRendererObj.getStringWidth(suffix.getBoolValue() ? module.getSuffix() : module.getLabel())));
            for (Feature feature : NeverHook.instance.featureManager.getFeatureList()) {
                ScreenHelper animationHelper = feature.getScreenHelper();
                String featureSuffix = suffix.getBoolValue() ? feature.getSuffix() : feature.getLabel();
                float listOffset = FeatureList.offset.getNumberValue();
                float length = !HUD.font.currentMode.equals("Minecraft") ? ClientHelper.getFontRender().getStringWidth(featureSuffix) : mc.fontRendererObj.getStringWidth(featureSuffix);
                float featureX = width - length;
                boolean state = feature.getState() && feature.visible;

                if (state) {
                    animationHelper.interpolate(featureX, y, 4F * 16.0f / 6);
                } else {
                    animationHelper.interpolate(width, y, 4F * 16.0f / 6);
                }

                float yPotion = 2;
                float translateY = animationHelper.getY() + yPotion;
                float translateX = animationHelper.getX() - (FeatureList.rightBorder.getBoolValue() ? 2.5F : 1.5F) - FeatureList.fontX.getNumberValue();
                int color = 0;
                Color onecolor = new Color(HUD.onecolor.getColorValue());
                Color twocolor = new Color(HUD.twocolor.getColorValue());
                double speed = HUD.time.getNumberValue();
                switch (HUD.colorList.currentMode) {
                    case "Astolfo":
                        color = PaletteHelper.astolfo(true, (int) y * 4).getRGB();
                        break;
                    case "Rainbow":
                        color = PaletteHelper.rainbow((int) (y * 16), rainbowSaturation.getNumberValue(), rainbowBright.getNumberValue()).getRGB();
                        break;
                    case "Category":
                        color = feature.getType().getColor();
                        break;
                    case "Custom":
                        color = new Color(PaletteHelper.fadeColor(onecolor.getRGB(), twocolor.getRGB(), (float) Math.abs(((((System.currentTimeMillis() / speed) / speed) + y * 6L / 60 * 2) % 2) - 1))).getRGB();
                        break;
                    case "Fade":
                        color = PaletteHelper.fadeColor(onecolor.getRGB(), onecolor.darker().getRGB(), (float) Math.abs(((((System.currentTimeMillis() / speed) / speed) + y * 6L / 60 * 2) % 2) - 1));
                        break;
                    case "Static":
                        color = onecolor.getRGB();
                        break;
                    case "None":
                        color = -1;
                        break;
                }

                if (state || animationHelper.getX() < width) {
                    if (backGround.getBoolValue()) {
                        RectHelper.drawRect(translateX - 1, translateY - 1, width, translateY + listOffset - 1, new Color(15, 15, 15, 160).getRGB());
                    }

                    if (rightBorder.getBoolValue()) {
                        RectHelper.drawRect(width, translateY - 1, width + borderWidth.getNumberValue(), translateY + listOffset - 1, color);
                    }

                    if (!HUD.font.currentMode.equals("Minecraft")) {
                        ClientHelper.getFontRender().drawStringWithShadow(featureSuffix, translateX, translateY + FeatureList.fontY.getNumberValue(), color);
                    } else {
                        mc.fontRendererObj.drawStringWithShadow(featureSuffix, translateX, translateY + FeatureList.fontY.getNumberValue(), color);
                    }
                    y += listOffset;
                }
            }
        }
    }
}
