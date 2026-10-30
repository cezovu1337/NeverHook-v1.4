package org.neverhook.client.ui.components.altmanager;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.neverhook.client.helpers.render.RenderHelper;
import org.neverhook.client.helpers.render.rect.RectHelper;
import org.neverhook.client.ui.button.GuiAltButton;
import org.neverhook.client.ui.components.altmanager.alt.Alt;
import org.neverhook.client.ui.components.altmanager.alt.AltManager;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;

public class GuiAddAlt extends GuiScreen {

    private final GuiAltManager manager;
    private PasswordField password;
    private String status;
    private GuiTextField username;

    GuiAddAlt(GuiAltManager manager) {
        this.status = ChatFormatting.GRAY + "Idle...";
        this.manager = manager;
    }

    private static void setStatus(GuiAddAlt guiAddAlt, String status) {
        guiAddAlt.status = status;
    }

    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                AddAltThread login = new AddAltThread(this.username.getText(), this.password.getText());
                login.start();
                break;
            case 1:
                this.mc.displayGuiScreen(this.manager);
                break;
            case 2:
                String data;
                try {
                    data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                } catch (Exception var4) {
                    return;
                }

                if (data.contains(":")) {
                    String[] credentials = data.split(":");
                    this.username.setText(credentials[0]);
                    this.password.setText(credentials[1]);
                }
        }
    }

    public void drawScreen(int i, int j, float f) {
        RectHelper.drawBorderedRect(0, 0, width, height, 0.5F, (new Color(22, 22, 22, 255)).getRGB(), (new Color(60, 60, 60, 255)).getRGB(), true);
        RenderHelper.drawImage(new ResourceLocation("neverhook/skeet.png"), 1, 1, 958, 1, Color.white);
        this.username.drawTextBox();
        this.password.drawTextBox();
        mc.circleregular.drawCenteredString("Add Account", width / 2F, 15, -1);
        if (this.username.getText().isEmpty() && !this.username.isFocused()) {
            mc.circleregular.drawStringWithShadow("Username / E-Mail", width / 2 - 96, 66, -7829368);
        }

        if (this.password.getText().isEmpty() && !this.password.isFocused()) {
            mc.circleregular.drawStringWithShadow("Password", width / 2 - 96, 106, -7829368);
        }

        mc.circleregular.drawCenteredString(this.status, width / 2F, 30, -1);
        super.drawScreen(i, j, f);
    }

    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiAltButton(0, width / 2 - 100, height / 4 + 92 + 12, "Login"));
        this.buttonList.add(new GuiAltButton(1, width / 2 - 100, height / 4 + 116 + 12, "Back"));
        this.buttonList.add(new GuiAltButton(2, width / 2 - 100, height / 4 + 92 - 12, "Import User:Pass"));
        this.username = new GuiTextField(this.eventButton, this.mc.fontRendererObj, width / 2 - 100, 60, 200, 20);
        this.password = new PasswordField(this.mc.fontRendererObj, width / 2 - 100, 100, 200, 20);
    }

    protected void keyTyped(char par1, int par2) {
        this.username.textboxKeyTyped(par1, par2);
        this.password.textboxKeyTyped(par1, par2);
        if (par1 == '\t' && (this.username.isFocused() || this.password.isFocused())) {
            this.username.setFocused(!this.username.isFocused());
            this.password.setFocused(!this.password.isFocused());
        }

        if (par1 == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
    }

    protected void mouseClicked(int par1, int par2, int par3) {
        try {
            super.mouseClicked(par1, par2, par3);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        this.username.mouseClicked(par1, par2, par3);
        this.password.mouseClicked(par1, par2, par3);
    }

    private class AddAltThread extends Thread {
        private final String password;
        private final String username;

        AddAltThread(String username, String password) {
            this.username = username;
            this.password = password;
            GuiAddAlt.setStatus(GuiAddAlt.this, ChatFormatting.GRAY + "Idle...");
        }

        public void run() {
            AltManager.registry.add(new Alt(this.username, this.password));
            GuiAddAlt.setStatus(GuiAddAlt.this, "§aAdded alt - " + ChatFormatting.RED + this.username);
        }
    }
}
