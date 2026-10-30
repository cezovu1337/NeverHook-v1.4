package org.neverhook.client.feature.impl.visual;

import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.render.EventRender3D;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.feature.impl.Type;
import org.neverhook.client.settings.impl.BooleanSetting;

public class EnderPearlESP extends Feature {

    public BooleanSetting tracers = new BooleanSetting("Tracers", true, () -> true);

    public EnderPearlESP() {
        super("EnderPearlESP", "Показывает есп перла", Type.Visuals);
        addSettings(tracers);
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
    }
}
