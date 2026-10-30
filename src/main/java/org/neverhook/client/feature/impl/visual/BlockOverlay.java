package org.neverhook.client.feature.impl.visual;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.neverhook.client.event.EventTarget;
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

public class BlockOverlay extends Feature {

    public ListSetting colorMode = new ListSetting("Color Box Mode", "Custom", () -> true, "Astolfo", "Rainbow", "Client", "Custom");
    public ColorSetting colorPicker = new ColorSetting("Color BlockOverlay", -1, () -> colorMode.currentMode.equals("Custom"));
    public BooleanSetting outline = new BooleanSetting("Outline BlockOverlay", false, () -> true);

    public BlockOverlay() {
        super("BlockOverlay", "Показывает блоки на которые вы навелись", Type.Visuals);
        addSettings(colorMode, colorPicker, outline);
    }

    @EventTarget
    public void onRender3D(EventRender3D event) {
        if (mc.hitResult == null || mc.hitResult.getType() != HitResult.Type.BLOCK || mc.level == null) return;
        BlockHitResult blockHit = (BlockHitResult) mc.hitResult;
        BlockPos pos = blockHit.getBlockPos();
        if (mc.level.getBlockState(pos).isAir()) return;

        int color = 0;
        switch (colorMode.currentMode) {
            case "Client":
                color = ClientHelper.getClientColor().getRGB();
                break;
            case "Custom":
                color = colorPicker.getColorValue();
                break;
            case "Astolfo":
                color = PaletteHelper.astolfo(false, pos.getY()).getRGB();
                break;
            case "Rainbow":
                color = PaletteHelper.rainbow(300, 1, 1).getRGB();
                break;
        }
        RenderHelper.blockEsp(pos, new Color(color), outline.getBoolValue());
    }
}
