package org.neverhook.client.ui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.neverhook.client.helpers.render.rect.RectHelper;

import java.awt.Color;

public class NeverHookMenuButton extends Button {
    private int fade = 20;
    private int fadeOutline = 20;

    public NeverHookMenuButton(int x, int y, int width, int height, String text, Runnable onPress) {
        super(x, y, width, height, Component.literal(text), b -> { if (onPress != null) onPress.run(); }, DEFAULT_NARRATION);
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        if (!this.visible) return;

        boolean isHovered = mouseX >= getX() && mouseY >= getY() && mouseX < getX() + getWidth() && mouseY < getY() + getHeight();
        Color text = new Color(215, 215, 215, 255);
        if (isHovered) {
            if (this.fade < 100) this.fade = Math.min(100, this.fade + 7);
            if (this.fadeOutline < 100) this.fadeOutline = Math.min(100, this.fadeOutline + 7);
            text = Color.WHITE;
        } else {
            if (this.fade > 20) this.fade = Math.max(20, this.fade - 7);
            if (this.fadeOutline > 20) this.fadeOutline = Math.max(20, this.fadeOutline - 7);
        }

        Color color = new Color(this.fade + 14, this.fade + 14, this.fade + 14, 120);
        Color colorOutline = new Color(this.fade + 60, this.fade, this.fade, 255);

        RectHelper.drawOutlineRect(getX(), getY(), getWidth(), getHeight(), color, colorOutline);

        Minecraft mc = Minecraft.getInstance();
        if (mc.circleregular != null) {
            mc.circleregular.drawCenteredString(getMessage().getString(), getX() + getWidth() / 2F, getY() + (getHeight() - 7.5f) / 2F, text.getRGB());
        } else if (mc.robotoRegularFontRender != null) {
            mc.robotoRegularFontRender.drawCenteredString(getMessage().getString(), getX() + getWidth() / 2F, getY() + (getHeight() - 7.5f) / 2F, text.getRGB());
        }
    }
}
