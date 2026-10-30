package org.neverhook.client.feature.impl.visual;

import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.render.EventRender3D;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.feature.impl.Type;
import org.neverhook.client.settings.impl.BooleanSetting;
import org.neverhook.client.settings.impl.ListSetting;
import org.neverhook.client.settings.impl.NumberSetting;

public class NameTags extends Feature {

    public BooleanSetting armor;
    public BooleanSetting backGround;
    public NumberSetting opacity;
    public NumberSetting size;
    public ListSetting hpMode = new ListSetting("Health Mode", "HP", () -> true, "HP", "Percentage");

    public NameTags() {
        super("NameTags", "Показывает игроков, ник, броню и их здоровье сквозь стены", Type.Visuals);
        size = new NumberSetting("NameTags Size", 0.5f, 0.2f, 2, 0.01f, () -> true);
        backGround = new BooleanSetting("NameTags Background", true, () -> true);
        opacity = new NumberSetting("Background Opacity", 120, 0, 255, 10, () -> backGround.getBoolValue());
        armor = new BooleanSetting("Show Armor", true, () -> true);
        addSettings(hpMode, size, backGround, opacity, armor);
    }

    @EventTarget
    public void onRender3d(EventRender3D event) {
    }
}
