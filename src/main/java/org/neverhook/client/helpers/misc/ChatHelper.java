package org.neverhook.client.helpers.misc;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.neverhook.client.helpers.Helper;

public class ChatHelper implements Helper {

    public static String chatPrefix = "\2477[" + ChatFormatting.RED + "N" + ChatFormatting.WHITE + "ever" + ChatFormatting.RED + "H" + ChatFormatting.WHITE + "ook" + ChatFormatting.RESET + "\2477] " + ChatFormatting.RESET;

    public static void addChatMessage(String message) {
        if (mc.gui != null && mc.gui.getChat() != null) {
            mc.gui.getChat().addMessage(Component.literal(chatPrefix + message));
        }
    }
}
