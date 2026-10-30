package org.neverhook.client.ui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.neverhook.client.helpers.render.rect.RectHelper;

import java.awt.*;

public class GuiNeverHookMenuButton extends GuiButton {

    private int fade = 20;

    public GuiNeverHookMenuButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, 200, 35, buttonText);
    }

    public GuiNeverHookMenuButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float mouseButton) {
        if (this.visible) {
            this.hovered = (mouseX >= this.xPosition && mouseY >= (this.yPosition) && mouseX < this.xPosition + this.width && mouseY < (this.yPosition) + this.height + 10);
            Color text = new Color(155, 155, 155, 255);
            Color color = new Color(this.fade + 14, this.fade + 14, this.fade + 14, 55);
            if (this.hovered) {
                if (this.fade < 100)
                    this.fade += 8;
                text = Color.white;
            } else {
                if (this.fade > 20)
                    this.fade -= 8;
            }
            int height = this.height + 11;
            RectHelper.drawSmoothRect(this.xPosition, this.yPosition, this.width + xPosition, height + yPosition, color.getRGB());
            if (mc.bigButtonFontRender != null) {
                mc.bigButtonFontRender.drawCenteredString(this.displayString, this.xPosition + this.width / 2F, (this.yPosition) + (this.height - 12), text.getRGB());
            }
        }
    }

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height + 10);
    }
}
