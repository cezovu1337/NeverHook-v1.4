package org.neverhook.client.event.events.impl.render;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.neverhook.client.event.events.callables.EventCancellable;

public class EventRenderBlock extends EventCancellable {

    private final BlockState state;
    private final BlockPos pos;
    private final BlockGetter access;

    public EventRenderBlock(BlockState state, BlockPos pos, BlockGetter access) {
        this.state = state;
        this.pos = pos;
        this.access = access;
    }

    public BlockState getState() {
        return state;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockGetter getAccess() {
        return access;
    }
}
