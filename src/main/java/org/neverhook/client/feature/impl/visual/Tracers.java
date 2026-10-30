package org.neverhook.client.feature.impl.visual;

import net.minecraft.world.entity.Entity;
import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.render.EventRender3D;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.feature.impl.Type;
import org.neverhook.client.settings.impl.BooleanSetting;
import org.neverhook.client.settings.impl.ColorSetting;
import org.neverhook.client.settings.impl.NumberSetting;

import java.awt.*;

public class Tracers extends Feature {

    public static BooleanSetting clientColor;
    public static ColorSetting colorGlobal;
    public static BooleanSetting friend;
    public static BooleanSetting onlyPlayer = new BooleanSetting("Only Player", true, () -> true);
    public static NumberSetting width;
    public static BooleanSetting seeOnly = new BooleanSetting("See Only", false, () -> true);

    public Tracers() {
        super("Tracers", "Рисует линию к игрокам", Type.Visuals);
        clientColor = new BooleanSetting("Client Colored", false, () -> true);
        friend = new BooleanSetting("Friend Highlight", true, () -> true);
        colorGlobal = new ColorSetting("Tracers Color", new Color(0xFFFFFF).getRGB(), () -> !clientColor.getBoolValue());
        width = new NumberSetting("Tracers Width", 1.5F, 0.1F, 5F, 0.1F, () -> true);
        addSettings(colorGlobal, friend, seeOnly, onlyPlayer, width, clientColor);
    }

    public static boolean canSeeEntityAtFov(Entity entityLiving, float scope) {
        if (mc.player == null || entityLiving == null) return false;
        double diffX = entityLiving.getX() - mc.player.getX();
        double diffZ = entityLiving.getZ() - mc.player.getZ();
        float yaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        double difference = angleDifference(yaw, mc.player.getYRot());
        return difference <= scope;
    }

    public static double angleDifference(float oldYaw, float newYaw) {
        float yaw = Math.abs(oldYaw - newYaw) % 360;
        if (yaw > 180) {
            yaw = 360 - yaw;
        }
        return yaw;
    }

    @EventTarget
    public void onEvent3D(EventRender3D event) {
    }
}
