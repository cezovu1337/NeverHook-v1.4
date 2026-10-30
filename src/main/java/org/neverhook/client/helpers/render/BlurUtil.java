package org.neverhook.client.helpers.render;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.neverhook.client.helpers.render.rect.RectHelper;

import java.awt.*;

public class BlurUtil {

    public void init() {
    }

    public void blur(float xBlur, float yBlur, float widthBlur, float heightBlur, int strength) {
        if (GuiGraphicsExtractor.currentExtractor != null) {
            GuiGraphicsExtractor.currentExtractor.blurBeforeThisStratum();
        }
        int alpha = Math.min(180, Math.max(30, strength * 20));
        RectHelper.drawRect(xBlur, yBlur, widthBlur, heightBlur, new Color(15, 15, 15, alpha).getRGB());
    }

    public void blur(float xBlur, float yBlur, float widthBlur, float heightBlur) {
        blur(xBlur, yBlur, widthBlur, heightBlur, 5);
    }
}
