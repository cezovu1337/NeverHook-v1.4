package org.neverhook.client.ui.components.altmanager.alt;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;

import java.util.Optional;
import java.util.UUID;

public class AltLoginThread extends Thread {

    private final Alt alt;
    private final Minecraft mc = Minecraft.getInstance();
    private String status;

    public AltLoginThread(Alt alt) {
        this.alt = alt;
        this.status = "§7Waiting...";
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void run() {
        this.mc.user = new User(this.alt.getUsername(), UUID.randomUUID(), "", Optional.empty(), Optional.empty());
        this.status = "§aLogged in - " + ChatFormatting.RED + this.alt.getUsername() + ChatFormatting.GREEN + " §c" + ChatFormatting.BOLD + "(offline)";
        AltManager.lastAlt = new Alt(this.alt.getUsername(), this.alt.getPassword());
        this.alt.setStatus(Alt.Status.Working);
    }
}
