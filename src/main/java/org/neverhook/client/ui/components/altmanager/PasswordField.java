package org.neverhook.client.ui.components.altmanager;

import net.minecraft.client.gui.Gui;
import org.neverhook.client.helpers.render.rect.RectHelper;
import org.neverhook.client.ui.font.MCFontRenderer;

import java.awt.*;

public class PasswordField extends Gui {
    private final MCFontRenderer fontRenderer;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private String text = "";
    private int maxStringLength = 64;
    private boolean isFocused;
    private int cursorCounter;

    public PasswordField(MCFontRenderer fontRenderer, int x, int y, int width, int height) {
        this.fontRenderer = fontRenderer;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void updateCursorCounter() {
        this.cursorCounter++;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text != null ? text : "";
    }

    public void drawTextBox() {
        RectHelper.drawBorderedRect(this.x, this.y, this.x + this.width, this.y + this.height, 1.0f, new Color(30, 30, 30, 200).getRGB(), this.isFocused ? new Color(100, 100, 255).getRGB() : new Color(60, 60, 60).getRGB(), true);
        String displayText = "*".repeat(this.text.length()) + (this.isFocused && (this.cursorCounter / 6 % 2 == 0) ? "_" : "");
        if (this.fontRenderer != null) {
            this.fontRenderer.drawString(displayText, this.x + 4, this.y + (this.height - 8) / 2f, -1);
        }
    }

    public boolean textboxKeyTyped(char typedChar, int keyCode) {
        if (!this.isFocused) {
            return false;
        } else if (keyCode == 259 || keyCode == 14) {
            if (!this.text.isEmpty()) {
                this.text = this.text.substring(0, this.text.length() - 1);
                return true;
            }
            return false;
        } else if (typedChar >= 32 && typedChar != 127 && this.text.length() < this.maxStringLength) {
            this.text += typedChar;
            return true;
        }
        return false;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.isFocused = mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;
    }

    public boolean isFocused() {
        return this.isFocused;
    }

    public void setFocused(boolean isFocused) {
        this.isFocused = isFocused;
    }
}
