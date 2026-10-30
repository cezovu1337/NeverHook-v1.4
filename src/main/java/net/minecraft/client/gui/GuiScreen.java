package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiScreen extends Screen {

    public Minecraft mc = Minecraft.getInstance();
    public int eventButton;
    public List<GuiButton> buttonList = new ArrayList<>();

    public GuiScreen() {
        super(Component.literal("Screen"));
    }

    public GuiScreen(Component title) {
        super(title);
    }

    public static boolean isCtrlKeyDown() {
        long window = Minecraft.getInstance().getWindow().handle();
        return GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_CONTROL) == GLFW.GLFW_PRESS || GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_CONTROL) == GLFW.GLFW_PRESS;
    }

    public static boolean isShiftKeyDown() {
        long window = Minecraft.getInstance().getWindow().handle();
        return GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS || GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;
    }

    public static boolean isAltKeyDown() {
        long window = Minecraft.getInstance().getWindow().handle();
        return GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS || GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_ALT) == GLFW.GLFW_PRESS;
    }

    public static boolean isKeyComboCtrlA(int keyID) {
        return keyID == GLFW.GLFW_KEY_A && isCtrlKeyDown();
    }

    public static boolean isKeyComboCtrlC(int keyID) {
        return keyID == GLFW.GLFW_KEY_C && isCtrlKeyDown();
    }

    public static boolean isKeyComboCtrlV(int keyID) {
        return keyID == GLFW.GLFW_KEY_V && isCtrlKeyDown();
    }

    public static boolean isKeyComboCtrlX(int keyID) {
        return keyID == GLFW.GLFW_KEY_X && isCtrlKeyDown();
    }

    public void initGui() {
    }

    @Override
    protected void init() {
        super.init();
        this.buttonList.clear();
        this.initGui();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        for (GuiButton button : this.buttonList) {
            button.drawButton(this.mc, mouseX, mouseY, 0);
        }
    }

    @Override
    public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
        GuiGraphicsExtractor.currentExtractor = graphics;
        this.drawScreen(mouseX, mouseY, a);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (GuiButton button : this.buttonList) {
            if (button.mousePressed(this.mc, mouseX, mouseY)) {
                this.actionPerformed(button);
            }
        }
    }

    protected void actionPerformed(GuiButton button) throws IOException {
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (GuiButton button : this.buttonList) {
            button.mouseReleased(mouseX, mouseY);
        }
    }

    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.mc.displayGuiScreen(null);
        }
    }

    public void drawString(org.neverhook.client.ui.font.MCFontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawStringWithShadow(text, x, y, color);
    }

    public void drawCenteredString(org.neverhook.client.ui.font.MCFontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawCenteredString(text, x, y, color);
    }

    public void drawWorldBackground(int tint) {
        if (GuiGraphicsExtractor.currentExtractor != null) {
            GuiGraphicsExtractor.currentExtractor.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
        }
    }

    public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        org.neverhook.client.helpers.render.rect.RectHelper.drawGradientRect(left, top, right, bottom, startColor, endColor);
    }

    @Override
    public boolean mouseClicked(final MouseButtonEvent event, final boolean doubleClick) {
        try {
            this.mouseClicked((int) event.x(), (int) event.y(), event.button());
        } catch (Exception ignored) {
        }
        return true;
    }

    @Override
    public boolean mouseReleased(final MouseButtonEvent event) {
        this.mouseReleased((int) event.x(), (int) event.y(), event.button());
        return true;
    }

    @Override
    public boolean mouseDragged(final MouseButtonEvent event, final double dx, final double dy) {
        this.mouseClickMove((int) event.x(), (int) event.y(), event.button(), 0L);
        return true;
    }

    @Override
    public boolean mouseScrolled(final double x, final double y, final double scrollX, final double scrollY) {
        org.lwjgl.input.Mouse.dWheel = (int) (scrollY * 120);
        return super.mouseScrolled(x, y, scrollX, scrollY);
    }

    @Override
    public boolean keyPressed(final KeyEvent event) {
        try {
            this.keyTyped((char) 0, event.key());
        } catch (Exception ignored) {
        }
        return true;
    }

    @Override
    public boolean charTyped(final CharacterEvent event) {
        try {
            this.keyTyped((char) event.codepoint(), 0);
        } catch (Exception ignored) {
        }
        return true;
    }

    public void onGuiClosed() {
    }

    @Override
    public void removed() {
        super.removed();
        this.onGuiClosed();
    }

    public void updateScreen() {
    }

    @Override
    public void tick() {
        super.tick();
        this.updateScreen();
    }

    public void drawDefaultBackground() {
        if (GuiGraphicsExtractor.currentExtractor != null) {
            GuiGraphicsExtractor.currentExtractor.fill(0, 0, this.width, this.height, 0xC0101010);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}
