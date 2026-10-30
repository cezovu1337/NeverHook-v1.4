package org.neverhook.client.event.events.impl.render;

import net.minecraft.core.BlockPos;
import org.neverhook.client.event.events.callables.EventCancellable;

public class EventRenderChunk extends EventCancellable {

    public BlockPos blockPos;
    public Object renderChunk;

    public EventRenderChunk(Object renderChunk, BlockPos blockPos) {
        this.blockPos = blockPos;
        this.renderChunk = renderChunk;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public void setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public Object getRenderChunk() {
        return renderChunk;
    }
}
