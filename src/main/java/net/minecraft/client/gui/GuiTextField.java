package net.minecraft.client.gui;

import org.lwjgl.glfw.GLFW;
import org.neverhook.client.helpers.render.rect.RectHelper;
import org.neverhook.client.ui.font.MCFontRenderer;

import java.awt.Color;

public class GuiTextField {

    private final int id;
    private final MCFontRenderer fontRenderer;
    public int xPosition;
    public int yPosition;
    public int width;
    public int height;
    private String text = "";
    private int maxStringLength = 50;
    private int cursorCounter;
    public boolean isFocused;
    private boolean isEnabled = true;

    public GuiTextField(int componentId, MCFontRenderer fontRenderer, int x, int y, int width, int height) {
        this.id = componentId;
        this.fontRenderer = fontRenderer;
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
    }

    public void updateCursorCounter() {
        this.cursorCounter++;
    }

    public void setText(String textIn) {
        if (textIn.length() > this.maxStringLength) {
            this.text = textIn.substring(0, this.maxStringLength);
        } else {
            this.text = textIn;
        }
    }

    public String getText() {
        return this.text;
    }

    public void drawTextBox() {
        RectHelper.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, new Color(30, 30, 30, 185).getRGB());
        RectHelper.drawGradientRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, new Color(1, 1, 1, 255).getRGB(), new Color(5, 5, 5, 255).getRGB());
        String display = this.text;
        if (this.isFocused && (this.cursorCounter / 6 % 2 == 0)) {
            display += "_";
        }
        if (this.fontRenderer != null) {
            this.fontRenderer.drawStringWithShadow(display, this.xPosition + 4, this.yPosition + (this.height - 8) / 2.0F, 0xFFE0E0E0);
        }
    }

    public boolean textboxKeyTyped(char typedChar, int keyCode) {
        if (!this.isFocused) {
            return false;
        }
        if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            if (this.isEnabled && !this.text.isEmpty()) {
                this.text = this.text.substring(0, this.text.length() - 1);
                return true;
            }
            return false;
        }
        if (typedChar >= 32 && typedChar != 127) {
            if (this.isEnabled && this.text.length() < this.maxStringLength) {
                this.text += typedChar;
                return true;
            }
        }
        return false;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        boolean hovered = mouseX >= this.xPosition && mouseX < this.xPosition + this.width && mouseY >= this.yPosition && mouseY < this.yPosition + this.height;
        this.setFocused(hovered);
    }

    public void setFocused(boolean isFocusedIn) {
        this.isFocused = isFocusedIn;
    }

    public boolean isFocused() {
        return this.isFocused;
    }

    public void setMaxStringLength(int length) {
        this.maxStringLength = length;
    }
}
