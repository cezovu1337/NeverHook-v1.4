package org.neverhook.client.ui.font;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.texture.DynamicTexture;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class CFont {

    protected final float imgSize = 1024;
    protected CharData[] charData = new CharData[256];
    protected Font font;
    protected boolean antiAlias;
    protected boolean fractionalMetrics;
    protected int fontHeight = -1;
    protected int charOffset = 0;
    protected DynamicTexture tex;

    public CFont(Font font, boolean antiAlias, boolean fractionalMetrics) {
        this.font = font;
        this.antiAlias = antiAlias;
        this.fractionalMetrics = fractionalMetrics;
        this.tex = setupTexture(font, antiAlias, fractionalMetrics, this.charData);
    }

    protected DynamicTexture setupTexture(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
        if (font == null) {
            font = new Font("Arial", Font.PLAIN, 16);
        }
        BufferedImage img = generateFontImage(font, antiAlias, fractionalMetrics, chars);
        try {
            return new DynamicTexture(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected BufferedImage generateFontImage(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
        int imgSize = (int) this.imgSize;
        BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.setFont(font);
        g.setColor(new Color(255, 255, 255, 0));
        g.fillRect(0, 0, imgSize, imgSize);
        g.setColor(Color.WHITE);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        FontMetrics fontMetrics = g.getFontMetrics();
        int charHeight = 0;
        int positionX = 0;
        int positionY = 1;

        for (int i = 0; i < chars.length; ++i) {
            char ch = (char) i;
            CharData charData = new CharData();
            Rectangle2D dimensions = fontMetrics.getStringBounds(String.valueOf(ch), g);
            charData.width = dimensions.getBounds().width + 8;
            charData.height = dimensions.getBounds().height;
            if (positionX + charData.width >= imgSize) {
                positionX = 0;
                positionY += charHeight;
                charHeight = 0;
            }

            if (charData.height > charHeight) {
                charHeight = charData.height;
            }

            charData.storedX = positionX;
            charData.storedY = positionY;
            if (charData.height > this.fontHeight) {
                this.fontHeight = charData.height;
            }

            chars[i] = charData;
            g.drawString(String.valueOf(ch), positionX + 2, positionY + fontMetrics.getAscent());
            positionX += charData.width;
        }

        g.dispose();
        return bufferedImage;
    }

    public void drawChar(DynamicTexture texture, CharData[] chars, char c, float x, float y, int color) {
        if (texture == null || GuiGraphicsExtractor.currentExtractor == null || c >= chars.length || chars[c] == null) {
            return;
        }
        CharData d = chars[c];
        float u0 = (float) d.storedX / this.imgSize;
        float v0 = (float) d.storedY / this.imgSize;
        float u1 = (float) (d.storedX + d.width) / this.imgSize;
        float v1 = (float) (d.storedY + d.height) / this.imgSize;
        int rx = Math.round(x);
        int ry = Math.round(y);
        int rw = Math.round(d.width * 0.5f);
        int rh = Math.round(d.height * 0.5f);
        GuiGraphicsExtractor.currentExtractor.blitTexture(
            texture,
            rx,
            ry,
            rx + rw,
            ry + rh,
            u0, u1, v0, v1,
            color
        );
    }

    public int getStringHeight(String text) {
        return getFontHeight();
    }

    public int getFontHeight() {
        return (this.fontHeight - 8) / 2;
    }

    public int getStringWidth(String text) {
        if (text == null) return 0;
        int width = 0;
        for (char c : text.toCharArray()) {
            if (c < this.charData.length && this.charData[c] != null) {
                width += this.charData[c].width - 8 + this.charOffset;
            }
        }
        return width / 2;
    }

    public boolean isAntiAlias() {
        return this.antiAlias;
    }

    public void setAntiAlias(boolean antiAlias) {
        if (this.antiAlias != antiAlias) {
            this.antiAlias = antiAlias;
            tex = setupTexture(this.font, antiAlias, this.fractionalMetrics, this.charData);
        }
    }

    public boolean isFractionalMetrics() {
        return this.fractionalMetrics;
    }

    public void setFractionalMetrics(boolean fractionalMetrics) {
        if (this.fractionalMetrics != fractionalMetrics) {
            this.fractionalMetrics = fractionalMetrics;
            tex = setupTexture(this.font, this.antiAlias, fractionalMetrics, this.charData);
        }
    }

    public Font getFont() {
        return this.font;
    }

    public void setFont(Font font) {
        this.font = font;
        tex = setupTexture(font, this.antiAlias, this.fractionalMetrics, this.charData);
    }

    protected static class CharData {
        public int width;
        public int height;
        public int storedX;
        public int storedY;
    }
}
