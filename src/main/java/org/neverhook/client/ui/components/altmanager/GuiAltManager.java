package org.neverhook.client.ui.components.altmanager;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.screens.TitleScreen;
import org.apache.commons.lang3.RandomStringUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.neverhook.client.helpers.palette.PaletteHelper;
import org.neverhook.client.helpers.render.RenderHelper;
import org.neverhook.client.helpers.render.rect.RectHelper;
import org.neverhook.client.ui.button.GuiAltButton;
import org.neverhook.client.ui.components.altmanager.alt.Alt;
import org.neverhook.client.ui.components.altmanager.alt.AltLoginThread;
import org.neverhook.client.ui.components.altmanager.alt.AltManager;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiAltManager extends GuiScreen {
    public Alt selectedAlt = null;
    public String status;
    private GuiAltButton login;
    private GuiAltButton remove;
    private GuiAltButton rename;
    private AltLoginThread loginThread;
    private float offset;
    private GuiTextField searchField;

    public GuiAltManager() {
        this.status = "§7(" + AltManager.registry.size() + ")";
    }

    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 1:
                if (this.selectedAlt != null) {
                    (this.loginThread = new AltLoginThread(this.selectedAlt)).start();
                }
                break;
            case 2:
                if (this.selectedAlt != null) {
                    AltManager.registry.remove(this.selectedAlt);
                    this.status = "§aRemoved.";
                    this.selectedAlt = null;
                }
                break;
            case 3:
                this.mc.displayGuiScreen(new GuiAddAlt(this));
                break;
            case 4:
                this.mc.displayGuiScreen(new GuiAltLogin(this));
                break;
            case 5:
                String randomName = "NeverHook" + RandomStringUtils.randomNumeric(4);
                (this.loginThread = new AltLoginThread(new Alt(randomName, ""))).start();
                AltManager.registry.add(new Alt(randomName, ""));
                break;
            case 6:
                this.mc.displayGuiScreen(new GuiRenameAlt(this));
                break;
            case 7:
                this.mc.displayGuiScreen(new TitleScreen());
                break;
            case 8:
                AltManager.registry.clear();
                this.status = "§bCleared!";
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);

        int wheel = Mouse.getDWheel();
        if (wheel < 0) {
            this.offset += 26;
        } else if (wheel > 0) {
            this.offset = Math.max(0, this.offset - 26);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            this.offset = Math.max(0, this.offset - 26);
        } else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            this.offset += 26;
        }

        RectHelper.drawBorderedRect(-5, 0, sr.getScaledWidth() + 6, sr.getScaledHeight(), 0.5F, (new Color(44, 44, 44, 255)).getRGB(), (new Color(33, 33, 33, 255)).getRGB(), true);
        RectHelper.drawBorderedRect(1F, 1.4F, sr.getScaledWidth() - 1, sr.getScaledHeight() - 1.7F, 0.5F, (new Color(17, 17, 17, 255)).getRGB(), (new Color(33, 33, 33, 255)).getRGB(), true);

        for (int i = 0; i < width; i++) {
            RectHelper.drawRect(i, 0, i + 1, 2, PaletteHelper.rainbow(i * 7, 0.5F, 1).getRGB());
        }

        String altName = "Name: " + (this.mc.user != null ? this.mc.user.getName() : "None");
        if (mc.circleregular != null) {
            mc.circleregular.drawStringWithShadow("§7~ User Info ~", 16, 75, -1);
            mc.circleregular.drawStringWithShadow(altName, 11, 89, 14540253);
            mc.circleregular.drawStringWithShadow("Account Status: §aWorking", 11, 100, 14540253);
            mc.circleregular.drawCenteredString("Account Manager", width / 2F, 10, -1);
            mc.circleregular.drawCenteredString(this.loginThread == null ? this.status : this.loginThread.getStatus(), width / 2F, 21, -1);
        } else {
            mc.fontRenderer.drawStringWithShadow("§7~ User Info ~", 16, 75, -1);
            mc.fontRenderer.drawStringWithShadow(altName, 11, 89, 14540253);
            mc.fontRenderer.drawStringWithShadow("Account Status: §aWorking", 11, 100, 14540253);
            mc.fontRenderer.drawCenteredString("Account Manager", width / 2F, 10, -1);
            mc.fontRenderer.drawCenteredString(this.loginThread == null ? this.status : this.loginThread.getStatus(), width / 2F, 21, -1);
        }

        RenderHelper.scissorRect(0.0F, 33.0F, (float) width, (float) (height - 50));
        int y = 38;
        int number = 0;
        for (Alt alt : this.getAlts()) {
            if (!this.isAltInArea(y)) {
                y += 36;
                continue;
            }
            number++;
            String name = alt.getMask().isEmpty() ? alt.getUsername() : alt.getMask();
            String pass = alt.getPassword().isEmpty() ? "§cCrack" : alt.getPassword().replaceAll(".", "*");
            boolean isSelected = alt == this.selectedAlt;
            float left = width / 2F - 125;
            float right = width / 2F + 125;
            float top = y - this.offset;
            float bottom = top + 28;

            if (isSelected) {
                RectHelper.drawBorderedRect(left, top, right, bottom, 1.0F, new Color(40, 40, 40, 220).getRGB(), PaletteHelper.getColor(255), true);
            } else {
                boolean hovering = mouseX >= left && mouseX <= right && mouseY >= top && mouseY <= bottom;
                RectHelper.drawBorderedRect(left, top, right, bottom, 1.0F, hovering ? new Color(30, 30, 30, 220).getRGB() : new Color(22, 22, 22, 180).getRGB(), new Color(45, 45, 45, 255).getRGB(), true);
            }

            if (mc.circleregular != null) {
                mc.circleregular.drawStringWithShadow("§7" + number + ". §f" + name, left + 8, top + 5, -1);
                mc.circleregular.drawStringWithShadow(pass, left + 8, top + 16, Color.GRAY.getRGB());
            } else {
                mc.fontRenderer.drawStringWithShadow("§7" + number + ". §f" + name, left + 8, top + 5, -1);
                mc.fontRenderer.drawStringWithShadow(pass, left + 8, top + 16, Color.GRAY.getRGB());
            }

            y += 36;
        }

        RenderHelper.disableScissor();

        if (this.selectedAlt == null) {
            if (this.login != null) this.login.enabled = false;
            if (this.remove != null) this.remove.enabled = false;
            if (this.rename != null) this.rename.enabled = false;
        } else {
            if (this.login != null) this.login.enabled = true;
            if (this.remove != null) this.remove.enabled = true;
            if (this.rename != null) this.rename.enabled = true;
        }

        if (this.searchField != null) {
            this.searchField.drawTextBox();
            if (this.searchField.getText().isEmpty() && !this.searchField.isFocused()) {
                if (mc.circleregular != null) {
                    mc.circleregular.drawStringWithShadow("Search Alt", width / 2F + 125, height - 18, PaletteHelper.getColor(180));
                }
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public List<Alt> getAlts() {
        if (this.searchField == null || this.searchField.getText().isEmpty()) {
            return AltManager.registry;
        }
        List<Alt> list = new ArrayList<>();
        String q = this.searchField.getText().toLowerCase();
        for (Alt a : AltManager.registry) {
            if (a.getUsername().toLowerCase().contains(q) || a.getMask().toLowerCase().contains(q)) {
                list.add(a);
            }
        }
        return list;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.login = new GuiAltButton(1, width / 2 - 122, height - 48, 100, 20, "Login");
        this.buttonList.add(this.login);
        this.remove = new GuiAltButton(2, width / 2 - 18, height - 24, 100, 20, "Remove");
        this.buttonList.add(this.remove);
        this.buttonList.add(new GuiAltButton(3, width / 2 + 86, height - 48, 100, 20, "Add"));
        this.buttonList.add(new GuiAltButton(4, width / 2 - 18, height - 48, 100, 20, "Direct"));
        this.buttonList.add(new GuiAltButton(5, width / 2 - 122, height - 24, 100, 20, "Random"));
        this.rename = new GuiAltButton(6, width / 2 + 86, height - 24, 100, 20, "Edit");
        this.buttonList.add(this.rename);
        this.buttonList.add(new GuiAltButton(7, width - 80, height - 24, 70, 20, "Back"));
        this.buttonList.add(new GuiAltButton(8, width / 2 + 86, height - 72, 100, 20, "Clear"));

        this.searchField = new GuiTextField(1337, mc.fontRendererObj, width / 2 + 120, height - 18, 100, 15);
        super.initGui();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (this.searchField != null) {
            this.searchField.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (mouseButton == 0) {
            int y = 38;
            for (Alt alt : this.getAlts()) {
                float left = width / 2F - 125;
                float right = width / 2F + 125;
                float top = y - this.offset;
                float bottom = top + 28;
                if (mouseX >= left && mouseX <= right && mouseY >= top && mouseY <= bottom && mouseY >= 33 && mouseY <= height - 50) {
                    if (alt == this.selectedAlt) {
                        this.actionPerformed(this.login);
                        return;
                    }
                    this.selectedAlt = alt;
                    break;
                }
                y += 36;
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.searchField != null && this.searchField.isFocused()) {
            this.searchField.textboxKeyTyped(typedChar, keyCode);
        }
        if (keyCode == 256 || keyCode == 1) {
            this.mc.displayGuiScreen(new TitleScreen());
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }

    private boolean isAltInArea(int y) {
        return y - this.offset <= height - 50;
    }
}
