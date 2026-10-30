package org.neverhook.client.feature.impl.movement;

import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.player.EventUpdate;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.feature.impl.Type;

public class AutoSprint extends Feature {

    public AutoSprint() {
        super("AutoSprint", "Зажимает CTRL за вас, что бы быстро бежать", Type.Movement);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.player != null && mc.player.input != null && mc.player.input.hasForwardImpulse()) {
            mc.player.setSprinting(true);
        }
    }
}
