package org.neverhook.client.event.events.impl.player;

import net.minecraft.world.entity.HumanoidArm;
import org.neverhook.client.event.events.Event;

public class EventTransformSideFirstPerson implements Event {

    private final HumanoidArm enumHandSide;

    public EventTransformSideFirstPerson(HumanoidArm enumHandSide) {
        this.enumHandSide = enumHandSide;
    }

    public HumanoidArm getEnumHandSide() {
        return this.enumHandSide;
    }
}
