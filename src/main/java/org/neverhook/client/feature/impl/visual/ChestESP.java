package org.neverhook.client.feature.impl.visual;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.render.EventRender2D;
import org.neverhook.client.event.events.impl.render.EventRender3D;
import org.neverhook.client.feature.Feature;
import org.neverhook.client.feature.impl.Type;
import org.neverhook.client.helpers.misc.ClientHelper;
import org.neverhook.client.helpers.palette.PaletteHelper;
import org.neverhook.client.helpers.render.RenderHelper;
import org.neverhook.client.settings.impl.BooleanSetting;
import org.neverhook.client.settings.impl.ColorSetting;
import org.neverhook.client.settings.impl.ListSetting;

import java.awt.*;

public class ChestESP extends Feature {

    public static ListSetting chestEspMode = new ListSetting("ChestEsp Mode", "2D", () -> true, "2D", "3D", "Chams");
    public static ColorSetting chamsColor = new ColorSetting("Chams Color", Color.PINK.getRGB(), () -> chestEspMode.currentMode.equals("Chams"));
    private final BooleanSetting fullBox;
    private final ColorSetting colorEsp;
    public ListSetting colorMode = new ListSetting("Color Box Mode", "Custom", () -> true, "Astolfo", "Rainbow", "Client", "Custom");

    public ChestESP() {
        super("ChestESP", "Показывает сундуки", Type.Visuals);
        colorEsp = new ColorSetting("ChestEsp Color", new Color(0xFFFFFF).getRGB(), () -> colorMode.currentMode.equals("Custom") && !chestEspMode.currentMode.equals("Chams"));
        fullBox = new BooleanSetting("Full Box", false, () -> chestEspMode.currentMode.equals("3D"));
        addSettings(chestEspMode, colorMode, colorEsp, chamsColor, fullBox);
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        String mode = chestEspMode.getOptions();
        setSuffix(mode);
    }

    private boolean isValid(BlockEntity entity) {
        return entity instanceof ChestBlockEntity;
    }
}
