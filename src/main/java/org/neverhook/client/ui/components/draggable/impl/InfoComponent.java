package org.neverhook.client.ui.components.draggable.impl;

import net.minecraft.ChatFormatting;
import org.neverhook.client.feature.impl.hud.HUD;
import org.neverhook.client.helpers.misc.ClientHelper;
import org.neverhook.client.ui.components.draggable.DraggableModule;

public class InfoComponent extends DraggableModule {

    public InfoComponent() {
        super("InfoComponent", 100, 200);
    }

    @Override
    public int getWidth() {
        return 75;
    }

    @Override
    public int getHeight() {
        return 27;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        draw();
        super.render(mouseX, mouseY);
    }

    @Override
    public void draw() {
        if (HUD.worldInfo.getBoolValue() && mc.player != null && mc.robotoRegularFontRender != null) {
            String fps = "" + mc.fps;
            String xCoord = "" + Math.round(mc.player.getX());
            String yCoord = "" + Math.round(mc.player.getY());
            String zCoord = "" + Math.round(mc.player.getZ());
            mc.robotoRegularFontRender.drawStringWithShadow("X: ", getX(), getY(), ClientHelper.getClientColor().getRGB());
            mc.robotoRegularFontRender.drawStringWithShadow(xCoord, getX() + 10, getY(), -1);
            mc.robotoRegularFontRender.drawStringWithShadow("Y: ", getX() + 30 + mc.robotoRegularFontRender.getStringWidth(xCoord) - 17, getY(), ClientHelper.getClientColor().getRGB());
            mc.robotoRegularFontRender.drawStringWithShadow(yCoord, getX() + 40 + mc.robotoRegularFontRender.getStringWidth(xCoord) - 17, getY(), -1);
            mc.robotoRegularFontRender.drawStringWithShadow("Z: ", getX() + 66 + mc.robotoRegularFontRender.getStringWidth(xCoord) - 23 + mc.robotoRegularFontRender.getStringWidth(yCoord) - 17, getY(), ClientHelper.getClientColor().getRGB());
            mc.robotoRegularFontRender.drawStringWithShadow(zCoord, getX() + 76 + mc.robotoRegularFontRender.getStringWidth(xCoord) - 23 + mc.robotoRegularFontRender.getStringWidth(yCoord) - 17, getY(), -1);

            mc.robotoRegularFontRender.drawStringWithShadow("FPS: ", getX(), getY() + 11, ClientHelper.getClientColor().getRGB());
            mc.robotoRegularFontRender.drawStringWithShadow(fps, getX() + 22, getY() + 11, -1);
        }
        super.draw();
    }
}
