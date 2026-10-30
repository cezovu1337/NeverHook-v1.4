package org.neverhook.client.ui.font;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;

public class FontUtil {

    public static Font getFontFromTTF(ResourceLocation loc, float fontSize, int fontType) {
        return getFontFromTTF(loc.toIdentifier(), fontSize, fontType);
    }

    public static Font getFontFromTTF(Identifier loc, float fontSize, int fontType) {
        try {
            var resource = Minecraft.getInstance().getResourceManager().getResource(loc);
            if (resource.isPresent()) {
                try (InputStream stream = resource.get().open()) {
                    Font output = Font.createFont(fontType, stream);
                    return output.deriveFont(fontSize);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Font("Arial", fontType, (int) fontSize);
    }
}
