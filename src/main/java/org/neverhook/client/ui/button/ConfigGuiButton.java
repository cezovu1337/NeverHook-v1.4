package org.neverhook.client.ui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.neverhook.client.helpers.render.rect.RectHelper;

import java.awt.*;

public class ConfigGuiButton extends GuiButton {

    private int fade = 20;

    public ConfigGuiButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, 200, 35, buttonText);
    }

    public ConfigGuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float mouseButton) {
        if (this.visible) {
            int height = this.height - 31;
            this.hovered = (mouseX >= this.xPosition + 93 && mouseY >= (this.yPosition - 41) && mouseX < this.xPosition + this.width - 43 && mouseY < height + yPosition - 30);
            Color text = new Color(155, 155, 155, 255);
            if (this.hovered) {
                if (this.fade < 100)
                    this.fade += 8;
                text = Color.white;
            } else {
                if (this.fade > 20)
                    this.fade -= 8;
            }

            RectHelper.drawSkeetButton(this.xPosition + 125, this.yPosition + 2, this.width + xPosition - 75, height + yPosition);
            mc.fontRendererObj.drawCenteredString(this.displayString, this.xPosition + this.width / 2F + 25, (this.yPosition) + (this.height - 73), text.getRGB());
        }
    }

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        int height = this.height - 31;
        return (this.enabled && this.visible && (mouseX >= this.xPosition + 93 && mouseY >= (this.yPosition - 45) && mouseX < this.xPosition + this.width - 43 && mouseY < height + yPosition - 30));
    }
}
