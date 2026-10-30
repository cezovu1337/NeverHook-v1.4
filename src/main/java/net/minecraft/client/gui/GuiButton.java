package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvents;
import org.neverhook.client.helpers.render.rect.RectHelper;

import java.awt.Color;

public class GuiButton extends Gui {

    protected int width;
    protected int height;
    public int xPosition;
    public int yPosition;
    public String displayString;
    public int id;
    public boolean enabled = true;
    public boolean visible = true;
    protected boolean hovered;
    private int fade = 20;
    private int fadeOutline = 20;

    public GuiButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        this.width = widthIn;
        this.height = heightIn;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.displayString = buttonText;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float mouseButton) {
        if (this.visible) {
            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
            Color text = new Color(215, 215, 215, 255);
            Color color = new Color(this.fade + 1, this.fade + 1, this.fade + 1);
            if (this.hovered) {
                if (this.fade < 100) {
                    this.fade += 7;
                }
                text = Color.white;
            } else {
                if (this.fade > 20) {
                    this.fade -= 7;
                }
            }

            Color colorOutline = new Color(this.fade + 60, this.fade, this.fade);
            if (this.hovered) {
                if (this.fadeOutline < 100) {
                    this.fadeOutline += 7;
                }
            } else {
                if (this.fadeOutline > 20) {
                    this.fadeOutline -= 7;
                }
            }

            RectHelper.drawOutlineRect(this.xPosition, this.yPosition, this.width, this.height, color, colorOutline);
            if (mc.fontRendererObj != null) {
                mc.fontRendererObj.drawCenteredString(this.displayString, this.xPosition + this.width / 2F, this.yPosition + (this.height - 7.5f) / 2F, text.getRGB());
            } else if (mc.buttonFontRender != null) {
                mc.buttonFontRender.drawCenteredString(this.displayString, this.xPosition + this.width / 2F, this.yPosition + (this.height - 7.5f) / 2F, text.getRGB());
            }
        }
    }

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    public void mouseReleased(int mouseX, int mouseY) {
    }

    public boolean isMouseOver() {
        return this.hovered;
    }

    public void playPressSound(SoundManager soundHandlerIn) {
        soundHandlerIn.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    public int getButtonWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
