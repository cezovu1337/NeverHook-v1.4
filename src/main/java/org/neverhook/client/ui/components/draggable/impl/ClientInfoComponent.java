package org.neverhook.client.ui.components.draggable.impl;

import net.minecraft.ChatFormatting;
import org.neverhook.client.NeverHook;
import org.neverhook.client.feature.impl.hud.HUD;
import org.neverhook.client.helpers.misc.ClientHelper;
import org.neverhook.client.ui.components.draggable.DraggableModule;

public class ClientInfoComponent extends DraggableModule {

    public ClientInfoComponent() {
        super("ClientInfoComponent", sr.getScaledWidth() - 150, sr.getScaledHeight() - 20);
    }

    @Override
    public int getWidth() {
        return 120;
    }

    @Override
    public int getHeight() {
        return 15;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        draw();
        super.render(mouseX, mouseY);
    }

    @Override
    public void draw() {
        if (HUD.clientInfo.getBoolValue()) {
            String name = "https://t.me/oldhouse1337";
            String buildStr = NeverHook.instance.type + " - " + ChatFormatting.WHITE + NeverHook.instance.version + ChatFormatting.RESET + " - " + name;
            if (mc.player != null && mc.level != null && mc.robotoRegularFontRender != null) {
                mc.robotoRegularFontRender.drawStringWithShadow(buildStr, getX(), getY(), ClientHelper.getClientColor().getRGB());
            }
        }
        super.draw();
    }
}
