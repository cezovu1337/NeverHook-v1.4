package org.neverhook.client.feature.impl.visual;

import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.render.EventRender3D;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.feature.impl.Type;

public class Trajectories extends Feature {

    public Trajectories() {
        super("Trajectories", "Показывает траекторию полета снарядов", Type.Visuals);
    }

    @EventTarget
    public void onRender(EventRender3D event) {
    }
}
