package org.neverhook.client.ui.newclickgui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.neverhook.client.feature.impl.Type;
import org.neverhook.client.feature.impl.hud.ClickGui;
import org.neverhook.client.helpers.misc.ClientHelper;
import org.neverhook.client.helpers.palette.PaletteHelper;
import org.neverhook.client.helpers.render.RenderHelper;
import org.neverhook.client.helpers.render.ScreenHelper;
import org.neverhook.client.helpers.render.rect.RectHelper;
import org.neverhook.client.ui.button.ImageButton;
import org.neverhook.client.ui.clickgui.GuiSearcher;
import org.neverhook.client.ui.newclickgui.settings.Component;
import org.neverhook.client.ui.newclickgui.settings.KeybindButton;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

public class ClickGuiScreen extends GuiScreen {

    public static GuiSearcher search;
    public ArrayList<Panel> panels = new ArrayList<>();
    public Theme hud = new Theme();
    public boolean exit = false;
    public boolean usingSetting = false;
    public int x = 20;
    public int y = 80;
    public ScreenHelper screenHelper;
    public ScreenHelper screenHelperSetting;
    protected ArrayList<ImageButton> imageButtons = new ArrayList<>();

    public ClickGuiScreen() {
        for (Type modCategory : Type.values()) {
            panels.add(new Panel(modCategory, x, y));
            y += height + 30;
        }
        this.screenHelper = new ScreenHelper(0, 0);
        this.screenHelperSetting = new ScreenHelper(0, 0);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);

        Color color = Color.WHITE;
        Color onecolor = new Color(ClickGui.color.getColorValue());
        Color twocolor = new Color(ClickGui.colorTwo.getColorValue());
        double speed = ClickGui.speed.getNumberValue();
        switch (ClickGui.clickGuiColor.currentMode) {
            case "Client":
                color = ClientHelper.getClientColor();
                break;
            case "Fade":
                color = new Color(ClickGui.color.getColorValue());
                break;
            case "Astolfo":
                color = PaletteHelper.astolfo(true, width);
                break;
            case "Color Two":
                color = new Color(PaletteHelper.fadeColor(onecolor.getRGB(), twocolor.getRGB(), (float) Math.abs(((((System.currentTimeMillis() / speed) / speed) + height * 6L / 60 * 2) % 2) - 1)));
                break;
            case "Rainbow":
                color = PaletteHelper.rainbow(300, 1, 1);
                break;
            case "Static":
                color = onecolor;
                break;
        }

        Color color1 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
        if (ClickGui.background.getBoolValue()) {
            drawDefaultBackground();
            drawGradientRect(0, 0, width, height, color1.getRGB(), color1.brighter().getRGB());
        }

        if (!usingSetting) {
            updateMouseWheel();
        }

        for (Panel panel : panels) {
            panel.drawScreen(mouseX, mouseY);
        }

        if (search != null) {
            search.drawTextBox();
            RectHelper.drawGradientRect(width - 150 - 20, sr.getScaledHeight() - 32, sr.getScaledWidth() - 20, sr.getScaledHeight() - 30, color.getRGB(), color.darker().getRGB());
            if (search.getText().isEmpty() && !search.isFocused()) {
                mc.fontRenderer.drawStringWithShadow("Search Feature...", width - 150 - 15, sr.getScaledHeight() - 26, -1);
            }
        }

        for (ImageButton imageButton : imageButtons) {
            imageButton.draw(mouseX, mouseY, Color.WHITE);
            if (Mouse.isButtonDown(0)) {
                imageButton.onClick(mouseX, mouseY);
            }
        }

        if (usingSetting) {
            for (Panel panel : panels) {
                for (FeaturePanel featurePanel : panel.featurePanel) {
                    if (featurePanel.usingSettings) {
                        RectHelper.drawRoundedRect(sr.getScaledWidth() / 2F - 151, 49, 150 * 2 + 2, sr.getScaledHeight() - 98, 4, new Color(48, 48, 48, 255));
                        RectHelper.drawRoundedRect(sr.getScaledWidth() / 2F - 150, 50, 150 * 2, sr.getScaledHeight() - 100, 4, new Color(17, 17, 17));
                        RectHelper.drawRoundedRect(sr.getScaledWidth() / 2 - 130, 74, 131 * 2, 1, 0, new Color(49, 48, 48));

                        if (mc.circleregular != null) {
                            mc.circleregular.drawStringWithOutline(featurePanel.feature.getLabel() + " Settings", sr.getScaledWidth() / 2F - 37, 56, Color.gray.getRGB());
                        }

                        RenderHelper.scissorRect(sr.getScaledWidth() / 2F - 150, 76, sr.getScaledWidth() / 2F + 155, sr.getScaledHeight() - 47);

                        for (Component component : featurePanel.components) {
                            if (component.setting.isVisible()) {
                                component.drawScreen(mouseX, mouseY);
                            }
                        }

                        for (KeybindButton keybindButton : featurePanel.keybindButtons) {
                            keybindButton.drawScreen();
                        }

                        RenderHelper.disableScissor();
                    }
                }
            }
        }

        if (exit) {
            exit = false;
            mc.displayGuiScreen(null);
            if (mc.currentScreen == null) {
                mc.setIngameFocus();
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void updateMouseWheel() {
        int scrollWheel = Mouse.getDWheel();
        if (scrollWheel == 0) return;
        for (Panel panel : panels) {
            if (scrollWheel > 0) {
                panel.setY(panel.getY() + 15);
            } else if (scrollWheel < 0) {
                panel.setY(panel.getY() - 15);
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        ScaledResolution sr = new ScaledResolution(mc);
        if (usingSetting) {
            float dialogX = sr.getScaledWidth() / 2F - 151;
            float dialogY = 49;
            float dialogW = 150 * 2 + 2;
            float dialogH = sr.getScaledHeight() - 98;
            if (mouseX < dialogX || mouseX > dialogX + dialogW || mouseY < dialogY || mouseY > dialogY + dialogH) {
                usingSetting = false;
                for (Panel panel : panels) {
                    for (FeaturePanel featurePanel : panel.featurePanel) {
                        featurePanel.usingSettings = false;
                    }
                }
                return;
            }
        }

        for (ImageButton imageButton : imageButtons) {
            imageButton.onClick(mouseX, mouseY);
        }

        if (search != null) {
            search.setFocused(false);
            search.setText("");
            search.mouseClicked(mouseX, mouseY, mouseButton);
        }

        for (Panel panel : panels) {
            if (panel.isWithinHeader(mouseX, mouseY) && mouseButton == 1 && !usingSetting) {
                panel.setOpen(!panel.isOpen());
            }
            panel.mouseClicked(mouseX, mouseY, mouseButton);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Panel panel : panels) {
            panel.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 256 || keyCode == 1) {
            if (usingSetting) {
                usingSetting = false;
                for (Panel panel : panels) {
                    for (FeaturePanel featurePanel : panel.featurePanel) {
                        featurePanel.usingSettings = false;
                    }
                }
            } else {
                exit = true;
            }
            return;
        }

        for (Panel panel : panels) {
            for (FeaturePanel featurePanel : panel.featurePanel) {
                if (usingSetting && featurePanel.usingSettings) {
                    featurePanel.keyTyped(typedChar, keyCode);
                    return;
                }
            }
        }

        if (search != null) {
            search.textboxKeyTyped(typedChar, keyCode);
            if ((typedChar == '\t' || typedChar == '\r') && search.isFocused()) {
                search.setFocused(!search.isFocused());
            }
        }

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(mc);
        this.imageButtons.clear();
        this.imageButtons.add(new ImageButton(new ResourceLocation("neverhook/brush.png"), sr.getScaledWidth() - 70, sr.getScaledHeight() - 50, 40, 40, "", 18));
        this.imageButtons.add(new ImageButton(new ResourceLocation("neverhook/config.png"), sr.getScaledWidth() - 19, sr.getScaledHeight() - 50, 40, 40, "", 22));
        this.imageButtons.add(new ImageButton(new ResourceLocation("neverhook/capeicon.png"), sr.getScaledWidth() - 115, sr.getScaledHeight() - 50, 40, 40, "", 23));
        search = new GuiSearcher(1337, mc.fontRendererObj, width - 150 - 20, sr.getScaledHeight() - 30, 150, 18);

        int totalPanels = panels.size();
        int panelW = 105;
        int spacing = 12;
        int totalW = totalPanels * panelW + (totalPanels - 1) * spacing;
        int startX = (sr.getScaledWidth() - totalW) / 2;
        int startY = 40;

        for (int i = 0; i < panels.size(); i++) {
            Panel p = panels.get(i);
            p.setX(startX + i * (panelW + spacing));
            p.setY(startY);
        }

        super.initGui();
    }
}
