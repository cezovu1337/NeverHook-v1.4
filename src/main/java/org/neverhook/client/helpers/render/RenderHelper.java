package org.neverhook.client.helpers.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.neverhook.client.helpers.Helper;
import org.neverhook.client.helpers.render.rect.RectHelper;

import java.awt.*;

public class RenderHelper implements Helper {

    public static void scissorRect(float x, float y, float width, double height) {
        if (GuiGraphicsExtractor.currentExtractor != null) {
            GuiGraphicsExtractor.currentExtractor.enableScissor((int) x, (int) y, (int) width, (int) height);
        }
    }

    public static void disableScissor() {
        if (GuiGraphicsExtractor.currentExtractor != null) {
            GuiGraphicsExtractor.currentExtractor.disableScissor();
        }
    }

    public static int darker(int color, float factor) {
        int r = (int) ((float) (color >> 16 & 0xFF) * factor);
        int g = (int) ((float) (color >> 8 & 0xFF) * factor);
        int b = (int) ((float) (color & 0xFF) * factor);
        int a = color >> 24 & 0xFF;
        return (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF | (a & 0xFF) << 24;
    }

    public static void setColor(int color) {
    }

    public static void setColor(Color color, float alpha) {
    }

    public static void renderItem(ItemStack itemStack, int x, int y) {
        if (GuiGraphicsExtractor.currentExtractor != null && itemStack != null) {
            GuiGraphicsExtractor.currentExtractor.renderItem(itemStack, x, y);
        }
    }

    public static void drawImage(ResourceLocation image, float x, float y, float width, float height) {
        drawImage(image.toIdentifier(), x, y, width, height, -1);
    }

    public static void drawImage(Identifier image, float x, float y, float width, float height) {
        drawImage(image, x, y, width, height, -1);
    }

    public static void drawImage(Identifier image, float x, float y, float width, float height, int color) {
        if (GuiGraphicsExtractor.currentExtractor != null) {
            GuiGraphicsExtractor.currentExtractor.blit(
                RenderPipelines.GUI_TEXTURED,
                image,
                (int) x,
                (int) y,
                0.0f,
                0.0f,
                (int) width,
                (int) height,
                (int) width,
                (int) height,
                (int) width,
                (int) height,
                color
            );
        }
    }

    public static void drawImage(ResourceLocation image, float x, float y, float width, float height, Color color) {
        drawImage(image.toIdentifier(), x, y, width, height, color.getRGB());
    }

    public static void drawCircle(float x, float y, float radius, boolean filled, Color color) {
        drawCircle(x, y, 0, 360, radius, filled, color);
    }

    public static void drawCircle(float x, float y, float start, float end, float radius, boolean filled, Color color) {
        if (GuiGraphicsExtractor.currentExtractor == null || color == null) return;
        int c = color.getRGB();
        int r = (int) Math.ceil(radius);
        int cx = Math.round(x);
        int cy = Math.round(y);
        if (filled && start == 0 && end >= 359) {
            for (int dy = -r; dy <= r; dy++) {
                float distSq = dy * dy;
                if (distSq <= radius * radius) {
                    float dx = (float) Math.sqrt(radius * radius - distSq);
                    GuiGraphicsExtractor.currentExtractor.fill(Math.round(cx - dx), cy + dy, Math.round(cx + dx), cy + dy + 1, c);
                }
            }
        } else {
            for (float i = start; i <= end; i += 4.0f) {
                float rad = (float) Math.toRadians(i);
                int px = Math.round(x + (float) Math.cos(rad) * radius);
                int py = Math.round(y + (float) Math.sin(rad) * radius);
                GuiGraphicsExtractor.currentExtractor.fill(px, py, px + 1, py + 1, c);
            }
        }
    }

    public static void drawSmoothCircle(float x, float y, float radius, Color color) {
        drawCircle(x, y, radius, true, color);
    }

    public static void drawArc(float cx, float cy, float r, float start_angle, float arc_angle, int num_segments, int color) {
        drawCircle(cx, cy, start_angle, start_angle + arc_angle, r, false, new Color(color, true));
    }

    public static void drawArrow(float x, float y, boolean up, int hexColor) {
        if (up) {
            drawTriangle(x, y + 4, 4, 180, hexColor);
        } else {
            drawTriangle(x, y, 4, 0, hexColor);
        }
    }

    public static void drawTriangle(float x, float y, float size, float vector, int color) {
        if (GuiGraphicsExtractor.currentExtractor == null) return;
        if (vector == 0) {
            for (int i = 0; i <= size; i++) {
                float w = (size - i);
                RectHelper.drawRect(x - w, y + i, x + w, y + i + 1, color);
            }
        } else {
            for (int i = 0; i <= size; i++) {
                float w = i;
                RectHelper.drawRect(x - w, y - size + i, x + w, y - size + i + 1, color);
            }
        }
    }

    public static void blockEsp(net.minecraft.core.BlockPos blockPos, Color color, boolean outline) {
    }

    public static void blockEspFrame(net.minecraft.core.BlockPos blockPos, float red, float green, float blue) {
    }
}
