package org.neverhook.client.feature.impl.visual;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.packet.EventReceivePacket;
import org.neverhook.client.event.events.impl.player.EventPreMotion;
import org.neverhook.client.event.events.impl.render.EventRender3D;
import org.neverhook.client.event.events.impl.render.EventRenderBlock;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.feature.impl.Type;
import org.neverhook.client.settings.impl.BooleanSetting;
import org.neverhook.client.settings.impl.NumberSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class XRay extends Feature {

    public static int done;
    public static int all;
    public static BooleanSetting brutForce;
    public static BooleanSetting diamond;
    public static BooleanSetting gold;
    public static BooleanSetting iron;
    public static BooleanSetting emerald;
    public static BooleanSetting redstone;
    public static BooleanSetting lapis;
    public static BooleanSetting coal;
    private final NumberSetting checkSpeed;
    private final NumberSetting renderDist;
    private final NumberSetting rxz;
    private final NumberSetting ry;
    private final ArrayList<BlockPos> ores = new ArrayList<>();
    private final ArrayList<BlockPos> toCheck = new ArrayList<>();
    private final List<Vec3i> blocks = new CopyOnWriteArrayList<>();

    public XRay() {
        super("XRay", "Иксрей, позволяющий обойти анти-иксрей на серверах", Type.Visuals);
        brutForce = new BooleanSetting("BrutForce", false, () -> true);
        renderDist = new NumberSetting("Render Distance", 35, 15, 150, 5, () -> !brutForce.getBoolValue());
        diamond = new BooleanSetting("Diamond", true, () -> true);
        gold = new BooleanSetting("Gold", false, () -> true);
        iron = new BooleanSetting("Iron", false, () -> true);
        emerald = new BooleanSetting("Emerald", false, () -> true);
        redstone = new BooleanSetting("Redstone", false, () -> true);
        lapis = new BooleanSetting("Lapis", false, () -> true);
        coal = new BooleanSetting("Coal", false, () -> true);
        checkSpeed = new NumberSetting("CheckSpeed", 4, 1, 10, 1, brutForce::getBoolValue);
        rxz = new NumberSetting("Radius XZ", 20, 5, 200, 1, brutForce::getBoolValue);
        ry = new NumberSetting("Radius Y", 6, 2, 50, 1, brutForce::getBoolValue);
        addSettings(renderDist, brutForce, checkSpeed, rxz, ry, diamond, gold, iron, emerald, redstone, lapis, coal);
    }

    @EventTarget
    public void onPreMotion(EventPreMotion event) {
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket e) {
    }

    @EventTarget
    public void onRenderBlock(EventRenderBlock event) {
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
    }
}
