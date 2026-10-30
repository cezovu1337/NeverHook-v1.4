package org.neverhook.client.event.events.impl.render;

import org.neverhook.client.event.events.callables.EventCancellable;

public class EventRenderChunkContainer extends EventCancellable {

    public Object renderChunk;

    public EventRenderChunkContainer(Object renderChunk) {
        this.renderChunk = renderChunk;
    }

    public Object getRenderChunk() {
        return renderChunk;
    }

    public void setRenderChunk(Object renderChunk) {
        this.renderChunk = renderChunk;
    }
}
