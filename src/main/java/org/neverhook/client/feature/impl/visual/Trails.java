package org.neverhook.client.feature.impl.visual;

import net.minecraft.world.phys.Vec3;
import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.packet.EventSendPacket;
import org.neverhook.client.event.events.impl.render.EventRender3D;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.feature.impl.Type;
import org.neverhook.client.settings.impl.ColorSetting;
import org.neverhook.client.settings.impl.ListSetting;
import org.neverhook.client.settings.impl.NumberSetting;

import java.awt.*;
import java.util.ArrayList;

public class Trails extends Feature {

    public ListSetting colorMode;
    public ColorSetting customColor;
    public NumberSetting width;
    public NumberSetting height;
    public NumberSetting sizeToRemove;

    public ArrayList<Point> points = new ArrayList<>();
    public float pointCount = 0;

    public Trails() {
        super("Trails", "Оставляет линию ходьбы", Type.Visuals);
        colorMode = new ListSetting("Color", "Rainbow", () -> true, "Rainbow", "Astolfo", "Client", "Custom");
        customColor = new ColorSetting("Custom Color", new Color(0xFFFFFF).getRGB(), () -> colorMode.currentMode.equals("Custom"));
        width = new NumberSetting("Width", 2F, 1F, 6F, 1F, () -> true);
        height = new NumberSetting("Height", 0, 0, 6F, 0.01F, () -> true);
        sizeToRemove = new NumberSetting("Time", 150, 10, 300, 10F, () -> true);
        addSettings(colorMode, customColor, width, height, sizeToRemove);
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
    }

    @EventTarget
    public void sendPacket(EventSendPacket event) {
    }

    public static class Point {
        private final Vec3 pos;
        private final long time;

        public Point(Vec3 pos) {
            this.pos = pos;
            this.time = System.currentTimeMillis();
        }

        public Vec3 getPos() {
            return pos;
        }

        public long getTime() {
            return time;
        }
    }
}
