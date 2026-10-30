package org.neverhook.client.feature.impl.visual;

import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.render.EventRenderChunk;
import org.neverhook.client.event.events.impl.render.EventRenderChunkContainer;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.feature.impl.Type;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ChunkAnimator extends Feature {

    private final HashMap<Object, AtomicLong> renderChunkMap = new HashMap<>();

    public ChunkAnimator() {
        super("ChunkAnimator", "Анимирует твои чанки", Type.Visuals);
    }

    private double easeOutCubic(double t) {
        return (--t) * t * t + 1;
    }

    @EventTarget
    private void onRenderChunk(EventRenderChunk event) {
        if (mc.player != null && event.getRenderChunk() != null) {
            if (!renderChunkMap.containsKey(event.getRenderChunk())) {
                renderChunkMap.put(event.getRenderChunk(), new AtomicLong(-1L));
            }
        }
    }

    @EventTarget
    private void onChunkRender(EventRenderChunkContainer event) {
        if (event.getRenderChunk() != null && renderChunkMap.containsKey(event.getRenderChunk())) {
            AtomicLong timeAlive = renderChunkMap.get(event.getRenderChunk());
            long timeClone = timeAlive.get();
            if (timeClone == -1L) {
                timeClone = System.currentTimeMillis();
                timeAlive.set(timeClone);
            }
        }
    }
}
