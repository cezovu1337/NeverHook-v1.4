package org.neverhook.client.cmd;

import net.minecraft.ChatFormatting;
import org.neverhook.client.event.EventTarget;
import org.neverhook.client.event.events.impl.packet.EventMessage;
import org.neverhook.client.helpers.misc.ChatHelper;

public class CommandHandler {

    public CommandManager commandManager;

    public CommandHandler(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @EventTarget
    public void onMessage(EventMessage event) {
        String msg = event.getMessage();
        if (msg != null && msg.length() > 0 && msg.startsWith(".")) {
            event.setCancelled(true);
            if (!this.commandManager.execute(msg)) {
                ChatHelper.addChatMessage(ChatFormatting.RED + "Unknown command! Type " + ChatFormatting.LIGHT_PURPLE + ".help" + ChatFormatting.RED + " for help.");
            }
        }
    }
}
