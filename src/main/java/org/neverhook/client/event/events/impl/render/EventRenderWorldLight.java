package org.neverhook.client.event.events.impl.render;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LightLayer;
import org.neverhook.client.event.events.callables.EventCancellable;

public class EventRenderWorldLight extends EventCancellable {

    private final LightLayer enumSkyBlock;
    private final BlockPos pos;

    public EventRenderWorldLight(LightLayer enumSkyBlock, BlockPos pos) {
        this.enumSkyBlock = enumSkyBlock;
        this.pos = pos;
    }

    public LightLayer getEnumSkyBlock() {
        return enumSkyBlock;
    }

    public BlockPos getPos() {
        return pos;
    }
}
