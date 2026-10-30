package org.neverhook.client.ui.newclickgui.settings;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.helpers.Helper;

import java.awt.*;
import java.io.IOException;

public class KeybindButton implements Helper {

    private final Feature feature;
    public boolean isBinding;
    private int x, y, width, height;

    public KeybindButton(Feature feature) {
        this.feature = feature;
    }

    public void drawScreen() {
        String keybind = isBinding ? "..." : Keyboard.getKeyName(feature.getBind());
        String text = ">> " + ChatFormatting.GRAY + "Keybind: " + keybind;
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getInstance());
        if (mc.circleregular != null) {
            mc.circleregular.drawStringWithShadow(text, scaledResolution.getScaledWidth() / 2F - 140, y + height / 2F - mc.circleregular.getFontHeight() / 2F - 3, new Color(123, 153, 183).getRGB());
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            this.isBinding = true;
        }
    }

    public void keyTyped(int key) {
        if (!isBinding) {
            return;
        }
        if (key == Keyboard.KEY_ESCAPE) {
            isBinding = false;
            return;
        }
        if (key == Keyboard.KEY_DELETE) {
            feature.setBind(Keyboard.KEY_NONE);
        } else {
            feature.setBind(key);
        }
        this.isBinding = false;
    }

    public void setPosition(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width + 4 && mouseY > y && mouseY < y + height;
    }
}
