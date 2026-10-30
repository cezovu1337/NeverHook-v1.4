package org.neverhook.client.helpers.render.rect;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.neverhook.client.helpers.Helper;

import java.awt.*;

public class RectHelper implements Helper {

    public static long delta = 0L;

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        if (GuiGraphicsExtractor.currentExtractor != null) {
            int x1 = (int) Math.min(left, right);
            int x2 = (int) Math.max(left, right);
            int y1 = (int) Math.min(top, bottom);
            int y2 = (int) Math.max(top, bottom);
            GuiGraphicsExtractor.currentExtractor.fill(x1, y1, x2, y2, color);
        }
    }

    public static void drawRect(float left, float top, float right, float bottom, int color) {
        drawRect((double) left, top, right, bottom, color);
    }

    public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
        if (GuiGraphicsExtractor.currentExtractor != null) {
            int x1 = (int) Math.min(left, right);
            int x2 = (int) Math.max(left, right);
            int y1 = (int) Math.min(top, bottom);
            int y2 = (int) Math.max(top, bottom);
            GuiGraphicsExtractor.currentExtractor.fillGradient(x1, y1, x2, y2, startColor, endColor);
        }
    }

    public static void drawGradientRect(float left, float top, float right, float bottom, int startColor, int endColor) {
        drawGradientRect((double) left, top, right, bottom, startColor, endColor);
    }

    public static void drawVerticalGradientRect(float left, float top, float right, float bottom, int color, int color2) {
        drawGradientRect(left, top, right, bottom, color, color2);
    }

    public static void drawVerticalGradientBetterRect(float x, float y, float width, float height, int color, int color2) {
        drawGradientRect(x, y, x + width, y + height, color, color2);
    }

    public static void drawVerticalGradientSmoothRect(float left, float top, float right, float bottom, int color, int color2) {
        drawGradientRect(left, top, right, bottom, color, color2);
    }

    public static void drawSmoothRectBetter(double x, double y, double width, double height, int color) {
        drawRect(x, y, x + width, y + height, color);
    }

    public static void drawSmoothRect(double left, double top, double right, double bottom, int color) {
        drawRect(left, top, right, bottom, color);
        int halfAlpha = ((color >>> 24) / 2) << 24 | (color & 0x00FFFFFF);
        drawRect(left - 0.5, top, left, bottom, halfAlpha);
        drawRect(right, top, right + 0.5, bottom, halfAlpha);
        drawRect(left, top - 0.5, right, top, halfAlpha);
        drawRect(left, bottom, right, bottom + 0.5, halfAlpha);
    }

    public static void drawSmoothRect(float left, float top, float right, float bottom, int color) {
        drawSmoothRect((double) left, top, right, bottom, color);
    }

    public static void drawRectBetter(double x, double y, double width, double height, int color) {
        drawRect(x, y, x + width, y + height, color);
    }

    public static void drawGradientRectBetter(float x, float y, float width, float height, int color, int color2) {
        drawGradientRect(x, y, x + width, y + height, color, color2);
    }

    public static void drawSmoothGradientRect(double left, double top, double right, double bottom, int color, int color2) {
        drawGradientRect(left, top, right, bottom, color, color2);
        int half1 = ((color >>> 24) / 2) << 24 | (color & 0x00FFFFFF);
        int half2 = ((color2 >>> 24) / 2) << 24 | (color2 & 0x00FFFFFF);
        drawGradientRect(left - 0.5, top, left, bottom, half1, half2);
        drawGradientRect(right, top, right + 0.5, bottom, half1, half2);
        drawRect(left, top - 0.5, right, top, half1);
        drawRect(left, bottom, right, bottom + 0.5, half2);
    }

    public static void drawGradientRectHorizontal(double left, double top, double right, double bottom, int color, int color2) {
        drawGradientRect(left, top, right, bottom, color, color2);
    }

    public static void drawBorderedRect(double left, double top, double right, double bottom, double borderWidth, int insideColor, int borderColor, boolean borderIncludedInBounds) {
        drawRect(left - (!borderIncludedInBounds ? borderWidth : 0), top - (!borderIncludedInBounds ? borderWidth : 0), right + (!borderIncludedInBounds ? borderWidth : 0), bottom + (!borderIncludedInBounds ? borderWidth : 0), borderColor);
        drawRect(left + (borderIncludedInBounds ? borderWidth : 0), top + (borderIncludedInBounds ? borderWidth : 0), right - ((borderIncludedInBounds ? borderWidth : 0)), bottom - ((borderIncludedInBounds ? borderWidth : 0)), insideColor);
    }

    public static void drawBorderedRect(float left, float top, float right, float bottom, float borderWidth, int insideColor, int borderColor, boolean borderIncludedInBounds) {
        drawBorderedRect((double) left, top, right, bottom, borderWidth, insideColor, borderColor, borderIncludedInBounds);
    }

    public static void drawBorder(float left, float top, float right, float bottom, float borderWidth, int insideColor, int borderColor, boolean borderIncludedInBounds) {
        drawBorderedRect(left, top, right, bottom, borderWidth, insideColor, borderColor, borderIncludedInBounds);
    }

    public static void drawBorder(double left, double top, double right, double bottom, double borderWidth, int insideColor, int borderColor, boolean borderIncludedInBounds) {
        drawBorderedRect(left, top, right, bottom, borderWidth, insideColor, borderColor, borderIncludedInBounds);
    }

    public static void drawBorderedGradientRect(double left, double top, double right, double bottom, double borderWidth, int insideColor, int borderColor, int borderGradientColor, boolean borderIncludedInBounds) {
        drawGradientRect(left - (!borderIncludedInBounds ? borderWidth : 0), top - (!borderIncludedInBounds ? borderWidth : 0), right + (!borderIncludedInBounds ? borderWidth : 0), bottom + (!borderIncludedInBounds ? borderWidth : 0), borderColor, borderGradientColor);
        drawRect(left + (borderIncludedInBounds ? borderWidth : 0), top + (borderIncludedInBounds ? borderWidth : 0), right - ((borderIncludedInBounds ? borderWidth : 0)), bottom - ((borderIncludedInBounds ? borderWidth : 0)), insideColor);
    }

    public static void drawOutlineRect(float x, float y, float width, float height, Color color, Color colorTwo) {
        drawRect(x, y, x + width, y + height, color.getRGB());
        int colorRgb = colorTwo.getRGB();
        drawRect(x - 1, y, x, y + height, colorRgb);
        drawRect(x + width, y, x + width + 1, y + height, colorRgb);
        drawRect(x - 1, y - 1, x + width + 1, y, colorRgb);
        drawRect(x - 1, y + height, x + width + 1, y + height + 1, colorRgb);
    }

    public static void drawRoundedRect(double x, double y, double width, double height, float radius, Color color) {
        if (GuiGraphicsExtractor.currentExtractor == null || color == null) return;
        int c = color.getRGB();
        float r = Math.min(radius, (float) Math.min(width, height) / 2.0f);
        if (r <= 0.5f) {
            drawRect(x, y, x + width, y + height, c);
            return;
        }

        float x0 = (float) x;
        float y0 = (float) y;
        float x1 = (float) (x + width);
        float y1 = (float) (y + height);

        drawRect(x0, y0 + r, x1, y1 - r, c);

        int ir = (int) Math.ceil(r);
        for (int i = 0; i <= ir; i++) {
            float dy = r - i;
            float dx = (float) Math.sqrt(Math.max(0, r * r - dy * dy));
            drawRect(x0 + r - dx, y0 + i, x1 - r + dx, y0 + i + 1, c);
            drawRect(x0 + r - dx, y1 - i - 1, x1 - r + dx, y1 - i, c);
        }
    }

    public static void polygon(double x, double y, double sideLength, double amountOfSides, boolean filled, Color color) {
        float radius = (float) (sideLength / 2.0);
        org.neverhook.client.helpers.render.RenderHelper.drawCircle((float) (x + radius), (float) (y + radius), radius, filled, color);
    }

    public static void drawSkeetButton(float x, float y, float right, float bottom) {
        drawSmoothRect(x - 31.0f, y - 43.0f, right + 31.0f, bottom - 30.0f, new Color(0, 0, 0, 255).getRGB());
        drawSmoothRect(x - 30.5f, y - 42.5f, right + 30.5f, bottom - 30.5f, new Color(45, 45, 45, 255).getRGB());
        drawGradientRect((int) x - 30, (int) y - 42, right + 30, bottom - 31, new Color(48, 48, 48, 255).getRGB(), new Color(19, 19, 19, 255).getRGB());
    }

    public static void drawSkeetRectWithoutBorder(float x, float y, float right, float bottom) {
        drawSmoothRect(x - 41f, y - 61f, right + 41f, bottom + 61f, new Color(48, 48, 48, 255).getRGB());
        drawSmoothRect(x - 40.0f, y - 60.0f, right + 40.0f, bottom + 60.0f, new Color(17, 17, 17, 255).getRGB());
    }

    public static void drawSkeetRect(float x, float y, float right, float bottom) {
        drawRect(x - 46.5f, y - 66.5f, right + 46.5f, bottom + 66.5f, new Color(0, 0, 0, 255).getRGB());
        drawRect(x - 46.0f, y - 66.0f, right + 46.0f, bottom + 66.0f, new Color(48, 48, 48, 255).getRGB());
        drawRect(x - 44.5f, y - 64.5f, right + 44.5f, bottom + 64.5f, new Color(33, 33, 33, 255).getRGB());
        drawRect(x - 43.5f, y - 63.5f, right + 43.5f, bottom + 63.5f, new Color(0, 0, 0, 255).getRGB());
        drawRect(x - 43.0f, y - 63.0f, right + 43.0f, bottom + 63.0f, new Color(9, 9, 9, 255).getRGB());
        drawRect(x - 40.5f, y - 60.5f, right + 40.5f, bottom + 60.5f, new Color(48, 48, 48, 255).getRGB());
        drawRect(x - 40.0f, y - 60.0f, right + 40.0f, bottom + 60.0f, new Color(17, 17, 17, 255).getRGB());
    }
}
