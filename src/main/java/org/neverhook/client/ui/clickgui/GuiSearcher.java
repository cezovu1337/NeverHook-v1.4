package org.neverhook.client.ui.clickgui;

import net.minecraft.client.gui.Gui;
import org.neverhook.client.helpers.render.rect.RectHelper;
import org.neverhook.client.ui.font.MCFontRenderer;

import java.awt.*;

public class GuiSearcher extends Gui {
    private final int id;
    private final MCFontRenderer fontRendererInstance;
    private final int width;
    private final int height;
    public int xPosition;
    public int yPosition;
    public int maxStringLength = 32;
    public boolean isFocused;
    private String text = "";
    private int cursorCounter;
    private boolean isEnabled = true;

    public GuiSearcher(int componentId, MCFontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
        this.id = componentId;
        this.fontRendererInstance = fontrendererObj;
        this.xPosition = x;
        this.yPosition = y;
        this.width = par5Width;
        this.height = par6Height;
    }

    public void updateCursorCounter() {
        this.cursorCounter++;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String textIn) {
        if (textIn.length() > this.maxStringLength) {
            this.text = textIn.substring(0, this.maxStringLength);
        } else {
            this.text = textIn;
        }
    }

    public void drawTextBox() {
        if (this.fontRendererInstance != null) {
            RectHelper.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, new Color(30, 30, 30, 180).getRGB());
            this.fontRendererInstance.drawString(this.text + (this.isFocused && (this.cursorCounter / 6 % 2 == 0) ? "_" : ""), this.xPosition + 4, this.yPosition + (this.height - 8) / 2f, -1);
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
        boolean wasFocused = this.isFocused;
        this.isFocused = mouseX >= this.xPosition && mouseX < this.xPosition + this.width && mouseY >= this.yPosition && mouseY < this.yPosition + this.height;
    }

    public boolean isFocused() {
        return this.isFocused;
    }

    public void setFocused(boolean isFocusedIn) {
        this.isFocused = isFocusedIn;
    }

    public int getId() {
        return this.id;
    }
}
