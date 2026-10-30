package org.neverhook.client.feature.impl.visual;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.level.LightLayer;
import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.player.EventUpdate;
import org.neverhook.client.event.events.impl.render.EventRenderEntity;
import org.neverhook.client.event.events.impl.render.EventRenderWorldLight;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.feature.impl.Type;
import org.neverhook.client.settings.impl.BooleanSetting;

public class NoRender extends Feature {

    public static BooleanSetting rain;
    public static BooleanSetting hurt;
    public static BooleanSetting pumpkin;
    public static BooleanSetting armor;
    public static BooleanSetting totem;
    public static BooleanSetting blindness;
    public static BooleanSetting cameraSmooth;
    public static BooleanSetting cameraBounds;
    public static BooleanSetting fire;
    public static BooleanSetting light;
    public static BooleanSetting fog;
    public static BooleanSetting armorStand;
    public static BooleanSetting bossBar;
    public static BooleanSetting tnt;
    public static BooleanSetting crystal;
    public static BooleanSetting fireworks;
    public static BooleanSetting swing;
    public static BooleanSetting sign;
    public static BooleanSetting frame;
    public static BooleanSetting banner;
    public static BooleanSetting glintEffect;
    public static BooleanSetting chatRect;

    public NoRender() {
        super("NoOverlay", "Убирает опредленные элементы рендера в игре", Type.Visuals);
        rain = new BooleanSetting("Rain", true, () -> true);
        hurt = new BooleanSetting("HurtCamera", true, () -> true);
        pumpkin = new BooleanSetting("Pumpkin", true, () -> true);
        armor = new BooleanSetting("Armor", false, () -> true);
        totem = new BooleanSetting("Totem", true, () -> true);
        blindness = new BooleanSetting("Blindness", true, () -> true);
        cameraSmooth = new BooleanSetting("Camera Smooth", true, () -> true);
        cameraBounds = new BooleanSetting("Camera Bounds", false, () -> true);
        fire = new BooleanSetting("Fire", true, () -> true);
        light = new BooleanSetting("Light", false, () -> true);
        fog = new BooleanSetting("Fog", false, () -> true);
        armorStand = new BooleanSetting("Armor Stand", false, () -> true);
        bossBar = new BooleanSetting("Boss Bar", true, () -> true);
        tnt = new BooleanSetting("Tnt", false, () -> true);
        crystal = new BooleanSetting("Crystal", false, () -> true);
        fireworks = new BooleanSetting("FireWorks", false, () -> true);
        swing = new BooleanSetting("Swing", false, () -> true);
        sign = new BooleanSetting("Sign", false, () -> true);
        frame = new BooleanSetting("Frame", false, () -> true);
        banner = new BooleanSetting("Banner", false, () -> true);
        glintEffect = new BooleanSetting("Glint Effect", false, () -> true);
        chatRect = new BooleanSetting("Chat Rect", false, () -> false);
        addSettings(rain, hurt, pumpkin, armor, totem, blindness, cameraSmooth, fire, light, fog, armorStand, bossBar, tnt, crystal, fireworks, swing, sign, frame, banner, glintEffect);
    }

    @EventTarget
    public void onEntityRenderer(EventRenderEntity event) {
        if (!getState())
            return;
        if (event.getEntity() != null) {
            if (fireworks.getBoolValue() && event.getEntity() instanceof FireworkRocketEntity) {
                event.setCancelled(true);
            } else if (crystal.getBoolValue() && event.getEntity() instanceof EndCrystal) {
                event.setCancelled(true);
            } else if (tnt.getBoolValue() && event.getEntity() instanceof PrimedTnt) {
                event.setCancelled(true);
            } else if (armorStand.getBoolValue() && event.getEntity() instanceof ArmorStand) {
                event.setCancelled(true);
            } else if (frame.getBoolValue() && event.getEntity() instanceof ItemFrame) {
                event.setCancelled(true);
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.player != null && blindness.getBoolValue()) {
            if (mc.player.hasEffect(MobEffects.BLINDNESS)) {
                mc.player.removeEffect(MobEffects.BLINDNESS);
            }
            if (mc.player.hasEffect(MobEffects.NAUSEA)) {
                mc.player.removeEffect(MobEffects.NAUSEA);
            }
        }
    }

    @EventTarget
    public void onWorldLight(EventRenderWorldLight event) {
        if (!getState())
            return;
        if (light.getBoolValue()) {
            if (event.getEnumSkyBlock() == LightLayer.SKY) {
                event.setCancelled(true);
            }
        }
    }
}
