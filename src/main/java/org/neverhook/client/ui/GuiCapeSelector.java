package org.neverhook.client.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.util.ResourceLocation;
import org.neverhook.client.helpers.render.RenderHelper;
import org.neverhook.client.helpers.render.rect.RectHelper;
import org.neverhook.client.ui.button.ImageButton;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GuiCapeSelector extends GuiScreen {

    protected ArrayList<ImageButton> imageButtons = new ArrayList<>();
    private int width, height;
    private float spin;

    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(mc);
        width = sr.getScaledWidth() / 2;
        height = sr.getScaledHeight() / 2;
        this.imageButtons.clear();
        this.imageButtons.add(new ImageButton(new ResourceLocation("neverhook/close-button.png"), width + 106, height - 135, 8, 8, "", 19));
        this.imageButtons.add(new ImageButton(new ResourceLocation("neverhook/arrow/arrow-right.png"), width + 30, height + 52, 32, 25, "", 56));
        this.imageButtons.add(new ImageButton(new ResourceLocation("neverhook/arrow/arrow-left.png"), width - 50, height + 52, 32, 25, "", 55));
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawWorldBackground(0);
        RectHelper.drawSkeetRectWithoutBorder(width - 70, height - 80, width + 80, height + 20);
        RectHelper.drawSkeetButton(width - 70, height - 78, width + 80, height + 80);
        RenderHelper.drawImage(new ResourceLocation("neverhook/skeet.png"), width - 110, height - 140, 230, 1, Color.WHITE);
        if (mc.circleregular != null) {
            mc.circleregular.drawStringWithOutline("Cape Changer", width - 100, height - 133, -1);
        }
        drawEntityOnScreen(width + 7, height + 38, spin);
        spin += 0.9;
        for (ImageButton imageButton : this.imageButtons) {
            imageButton.draw(mouseX, mouseY, Color.LIGHT_GRAY);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0) {
            for (ImageButton imageButton : this.imageButtons) {
                imageButton.onClick(mouseX, mouseY);
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void drawEntityOnScreen(float posX, float posY, float mouseX) {
        if (mc.player != null && GuiGraphicsExtractor.currentExtractor != null) {
            InventoryScreen.extractEntityInInventoryFollowsMouse(
                GuiGraphicsExtractor.currentExtractor,
                (int) posX - 25, (int) posY - 40, (int) posX + 25, (int) posY + 40,
                30, 0.0625F, (int) mouseX, (int) posY, mc.player
            );
        }
    }

    public static class Selector {
        public static String capeName;

        public static String getCapeName() {
            return capeName;
        }

        public static void setCapeName(String capeName) {
            Selector.capeName = capeName;
        }
    }
}
