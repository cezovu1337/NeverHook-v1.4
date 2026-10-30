package org.neverhook.client.ui.components.draggable.impl;

import net.minecraft.ChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.neverhook.client.NeverHook;
import org.neverhook.client.feature.impl.hud.HUD;
import org.neverhook.client.feature.impl.hud.WaterMark;
import org.neverhook.client.helpers.misc.ClientHelper;
import org.neverhook.client.helpers.palette.PaletteHelper;
import org.neverhook.client.helpers.render.RenderHelper;
import org.neverhook.client.helpers.render.rect.RectHelper;
import org.neverhook.client.ui.components.draggable.DraggableModule;

import java.awt.*;

public class WaterMarkComponent extends DraggableModule {

    public WaterMarkComponent() {
        super("LogoComponent", 4, 8);
    }

    @Override
    public int getWidth() {
        return 135;
    }

    @Override
    public int getHeight() {
        return 20;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        draw();
        super.render(mouseX, mouseY);
    }

    @Override
    public void draw() {
        if (!mc.options.renderDebug) {
            String server = "localhost";
            String mode = WaterMark.logoMode.getCurrentMode();
            Color color = Color.BLACK;
            switch (WaterMark.logoColor.currentMode) {
                case "Gradient": {
                    for (int i = x; i < x + getWidth(); i++) {
                        color = new Color(PaletteHelper.fadeColor(WaterMark.customRect.getColorValue(), WaterMark.customRectTwo.getColorValue(), i));
                    }
                    break;
                }
                case "Client": {
                    color = ClientHelper.getClientColor();
                    break;
                }
                case "Rainbow": {
                    color = PaletteHelper.rainbow(300, 1, 1);
                    break;
                }
                case "Default": {
                    color = WaterMark.logoMode.currentMode.equals("OneTap v2") ? new Color(161, 0, 255) : Color.RED;
                }
            }
            if (mode.equalsIgnoreCase("Default")) {
                if (!HUD.font.currentMode.equals("Minecraft") && mc.robotoRegularFontRender != null) {
                    mc.robotoRegularFontRender.drawStringWithOutline("N", getX(), getY() + 1, color.getRGB());
                    mc.robotoRegularFontRender.drawStringWithOutline("ever", getX() + 7, getY() + 1, -1);
                    mc.robotoRegularFontRender.drawStringWithOutline("H", getX() + 27, getY() + 1, color.getRGB());
                    mc.robotoRegularFontRender.drawStringWithOutline("ook", getX() + 34, getY() + 1, -1);
                } else if (mc.fontRendererObj != null) {
                    mc.fontRendererObj.drawStringWithOutline("N", getX(), getY() + 1, color.getRGB());
                    mc.fontRendererObj.drawStringWithOutline("ever", getX() + 6, getY() + 1, -1);
                    mc.fontRendererObj.drawStringWithOutline("H", getX() + 30, getY() + 1, color.getRGB());
                    mc.fontRendererObj.drawStringWithOutline("ook", getX() + 36, getY() + 1, -1);
                }
            } else if (mode.equalsIgnoreCase("Skeet")) {
                String name = mc.user != null ? mc.user.getName() : "Player";
                String text = "never" + ChatFormatting.GREEN + "hook" + ChatFormatting.RESET + " | " + name + " | " + mc.fps + "fps | " + server;
                float width = (mc.fontRenderer != null ? mc.fontRenderer.getStringWidth(text) : 100) + 6;
                int height = 20;
                int posX = getX();
                int posY = getY();
                RectHelper.drawRect(posX, posY, posX + width + 2.0f, posY + height, new Color(5, 5, 5, 255).getRGB());
                RectHelper.drawBorderedRect(posX + 0.5f, posY + 0.5f, posX + width + 1.5f, posY + height - 0.5f, 0.5f, new Color(40, 40, 40, 255).getRGB(), new Color(60, 60, 60, 255).getRGB(), true);
                RectHelper.drawBorderedRect(posX + 2, posY + 2, posX + width, posY + height - 2, 0.5f, new Color(22, 22, 22, 255).getRGB(), new Color(60, 60, 60, 255).getRGB(), true);

                RenderHelper.drawImage(new ResourceLocation("neverhook/skeet.png"), posX + 2.5f, posY + 2.5f, width - 3.0f, 1.0f, Color.WHITE);
                if (mc.fontRenderer != null) {
                    mc.fontRenderer.drawString(text, posX + 4, posY + 7, -1);
                }
            } else {
                String name = mc.user != null ? mc.user.getName() : "Player";
                String text = NeverHook.instance.name + " | " + name + " | " + mc.fps + " FPS";
                if (mc.fontRenderer != null) {
                    mc.fontRenderer.drawStringWithShadow(text, getX(), getY(), color.getRGB());
                }
            }
        }
        super.draw();
    }
}
