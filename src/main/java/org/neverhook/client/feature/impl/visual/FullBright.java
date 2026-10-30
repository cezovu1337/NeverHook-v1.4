package org.neverhook.client.feature.impl.visual;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.player.EventUpdate;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.feature.impl.Type;
import org.neverhook.client.settings.impl.ListSetting;

public class FullBright extends Feature {

    public static ListSetting brightMode;

    public FullBright() {
        super("FullBright", "Убирает темноту в игре", Type.Visuals);
        brightMode = new ListSetting("FullBright Mode", "Gamma", () -> true, "Gamma", "Potion");
        addSettings(brightMode);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (getState()) {
            String mode = brightMode.getOptions();
            if (mode.equalsIgnoreCase("Gamma")) {
                mc.options.gamma().set(100.0);
            }
            if (mode.equalsIgnoreCase("Potion")) {
                if (mc.player != null) {
                    mc.player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 817, 1));
                }
            } else {
                if (mc.player != null) {
                    mc.player.removeEffect(MobEffects.NIGHT_VISION);
                }
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.options.gamma().set(1.0);
        if (mc.player != null) {
            mc.player.removeEffect(MobEffects.NIGHT_VISION);
        }
    }
}
