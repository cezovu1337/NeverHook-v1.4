package org.neverhook.client.ui.font;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.texture.DynamicTexture;

import java.awt.*;

public class MCFontRenderer extends CFont {

    public int FONT_HEIGHT = 9;
    private final int[] colorCode = new int[32];
    protected CFont.CharData[] boldChars = new CFont.CharData[256];
    protected CFont.CharData[] italicChars = new CFont.CharData[256];
    protected CFont.CharData[] boldItalicChars = new CFont.CharData[256];
    protected DynamicTexture texBold;
    protected DynamicTexture texItalic;
    protected DynamicTexture texItalicBold;

    public MCFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
        super(font, antiAlias, fractionalMetrics);
        this.FONT_HEIGHT = Math.max(9, getFontHeight());
        setupBoldItalicIDs();

        for (int index = 0; index < 32; index++) {
            int noClue = (index >> 3 & 0x1) * 85;
            int red = (index >> 2 & 0x1) * 170 + noClue;
            int green = (index >> 1 & 0x1) * 170 + noClue;
            int blue = (index & 0x1) * 170 + noClue;

            if (index == 6) {
                red += 85;
            }

            if (index >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }

            this.colorCode[index] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF);
        }
    }

    public static void drawStringWithOutline(MCFontRenderer fontRenderer, String text, float x, float y, int color) {
        fontRenderer.drawString(text, x - 0.8F, y, Color.BLACK.getRGB());
        fontRenderer.drawString(text, x + 0.8F, y, Color.BLACK.getRGB());
        fontRenderer.drawString(text, x, y - 0.8F, Color.BLACK.getRGB());
        fontRenderer.drawString(text, x, y + 0.8F, Color.BLACK.getRGB());
        fontRenderer.drawString(text, x, y, color);
    }

    public void drawCenteredStringWithOutline(MCFontRenderer fontRenderer, String text, float x, float y, int color) {
        drawCenteredString(text, x - 1, y, Color.BLACK.getRGB());
        drawCenteredString(text, x + 1, y, Color.BLACK.getRGB());
        drawCenteredString(text, x, y - 1, Color.BLACK.getRGB());
        drawCenteredString(text, x, y + 1, Color.BLACK.getRGB());
        drawCenteredString(text, x, y, color);
    }

    public float drawStringWithShadow(String text, double x, double y, int color) {
        float shadowWidth = drawString(text, x + 0.9D, y + 0.7D, color, true);
        return Math.max(shadowWidth, drawString(text, x, y, color, false));
    }

    public float drawString(String text, float x, float y, int color) {
        return drawString(text, (double) x, (double) y, color, false);
    }

    public float drawCenteredString(String text, float x, float y, int color) {
        return drawString(text, x - getStringWidth(text) / 2F, y, color);
    }

    public float drawCenteredStringWithShadow(String text, float x, float y, int color) {
        return drawStringWithShadow(text, x - getStringWidth(text) / 2f, y, color);
    }

    public float drawString(String text, double x, double y, int color, boolean shadow) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        if (color == 0x20FFFFFF) {
            color = 0xFFFFFF;
        }
        if ((color & 0xFC000000) == 0) {
            color |= 0xFF000000;
        }
        if (shadow) {
            color = (color & 0xFCFCFC) >> 2 | color & new Color(20, 20, 20, 200).getRGB();
        }

        CFont.CharData[] currentData = this.charData;
        DynamicTexture currentTex = this.tex;
        int currentColor = color;
        float startX = (float) x;
        float currX = startX;
        float currY = (float) y - 1.5f;

        boolean bold = false;
        boolean italic = false;
        boolean underline = false;
        boolean strikethrough = false;

        int size = text.length();
        for (int i = 0; i < size; i++) {
            char character = text.charAt(i);
            if (character == '\u00A7' && i + 1 < size) {
                int colorIndex = "0123456789abcdefklmnor".indexOf(Character.toLowerCase(text.charAt(i + 1)));
                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                    underline = false;
                    strikethrough = false;
                    currentTex = this.tex;
                    currentData = this.charData;
                    if (colorIndex < 0) {
                        colorIndex = 15;
                    }
                    if (shadow) {
                        colorIndex += 16;
                    }
                    int c = this.colorCode[colorIndex];
                    currentColor = (c & 0x00FFFFFF) | (color & 0xFF000000);
                } else if (colorIndex == 17) {
                    bold = true;
                    if (italic) {
                        currentTex = this.texItalicBold;
                        currentData = this.boldItalicChars;
                    } else {
                        currentTex = this.texBold;
                        currentData = this.boldChars;
                    }
                } else if (colorIndex == 18) {
                    strikethrough = true;
                } else if (colorIndex == 19) {
                    underline = true;
                } else if (colorIndex == 20) {
                    italic = true;
                    if (bold) {
                        currentTex = this.texItalicBold;
                        currentData = this.boldItalicChars;
                    } else {
                        currentTex = this.texItalic;
                        currentData = this.italicChars;
                    }
                } else if (colorIndex == 21) {
                    bold = false;
                    italic = false;
                    underline = false;
                    strikethrough = false;
                    currentTex = this.tex;
                    currentData = this.charData;
                    currentColor = color;
                }
                i++;
            } else if (character < currentData.length && currentData[character] != null) {
                this.drawChar(currentTex, currentData, character, currX, currY, currentColor);
                float charW = (currentData[character].width - 8 + this.charOffset) * 0.5f;
                if (strikethrough && GuiGraphicsExtractor.currentExtractor != null) {
                    GuiGraphicsExtractor.currentExtractor.fill(Math.round(currX), Math.round(currY + currentData[character].height * 0.25f), Math.round(currX + charW), Math.round(currY + currentData[character].height * 0.25f + 1), currentColor);
                }
                if (underline && GuiGraphicsExtractor.currentExtractor != null) {
                    GuiGraphicsExtractor.currentExtractor.fill(Math.round(currX), Math.round(currY + currentData[character].height * 0.5f - 1), Math.round(currX + charW), Math.round(currY + currentData[character].height * 0.5f), currentColor);
                }
                currX += charW;
            }
        }
        return currX;
    }

    @Override
    public int getStringWidth(String text) {
        if (text == null) {
            return 0;
        }
        int width = 0;
        CFont.CharData[] currentData = this.charData;
        boolean bold = false;
        boolean italic = false;
        int size = text.length();
        for (int i = 0; i < size; i++) {
            char character = text.charAt(i);
            if (character == '\u00A7' && i + 1 < size) {
                int colorIndex = "0123456789abcdefklmnor".indexOf(Character.toLowerCase(text.charAt(i + 1)));
                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                } else if (colorIndex == 17) {
                    bold = true;
                    currentData = italic ? this.boldItalicChars : this.boldChars;
                } else if (colorIndex == 20) {
                    italic = true;
                    currentData = bold ? this.boldItalicChars : this.italicChars;
                } else if (colorIndex == 21) {
                    bold = false;
                    italic = false;
                    currentData = this.charData;
                }
                i++;
            } else if (character < currentData.length && currentData[character] != null) {
                width += currentData[character].width - 8 + this.charOffset;
            }
        }
        return width / 2;
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        setupBoldItalicIDs();
    }

    @Override
    public void setAntiAlias(boolean antiAlias) {
        super.setAntiAlias(antiAlias);
        setupBoldItalicIDs();
    }

    @Override
    public void setFractionalMetrics(boolean fractionalMetrics) {
        super.setFractionalMetrics(fractionalMetrics);
        setupBoldItalicIDs();
    }

    private void setupBoldItalicIDs() {
        if (this.font != null) {
            texBold = setupTexture(this.font.deriveFont(Font.BOLD), this.antiAlias, this.fractionalMetrics, this.boldChars);
            texItalic = setupTexture(this.font.deriveFont(Font.ITALIC), this.antiAlias, this.fractionalMetrics, this.italicChars);
            texItalicBold = setupTexture(this.font.deriveFont(Font.BOLD | Font.ITALIC), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
        }
    }

    public void drawStringWithOutline(String text, double x, double y, int color) {
        drawString(text, x - 0.5, y, Color.BLACK.getRGB(), false);
        drawString(text, x + 0.5F, y, Color.BLACK.getRGB(), false);
        drawString(text, x, y - 0.5F, Color.BLACK.getRGB(), false);
        drawString(text, x, y + 0.5F, Color.BLACK.getRGB(), false);
        drawString(text, x, y, color, false);
    }

    public void drawCenteredStringWithOutline(String text, float x, float y, int color) {
        drawCenteredString(text, x - 0.5F, y, Color.BLACK.getRGB());
        drawCenteredString(text, x + 0.5F, y, Color.BLACK.getRGB());
        drawCenteredString(text, x - 0.5F, y, Color.BLACK.getRGB());
        drawCenteredString(text, x + 0.5F, y, Color.BLACK.getRGB());
        drawCenteredString(text, x, y, color);
    }
}
