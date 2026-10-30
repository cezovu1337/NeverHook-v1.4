package org.neverhook.client.cmd.impl;

import net.minecraft.ChatFormatting;
import org.neverhook.client.cmd.CommandAbstract;
import org.neverhook.client.helpers.misc.ChatHelper;

public class HelpCommand extends CommandAbstract {

    public HelpCommand() {
        super("help", "help", ".help", "help");
    }

    @Override
    public void execute(String... args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                ChatHelper.addChatMessage(ChatFormatting.RED + "All Commands:");
                ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + ".bind " + ChatFormatting.GRAY + "<add|remove|clear>");
                ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + ".macro " + ChatFormatting.GRAY + "<add|remove|clear|list>");
                ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + ".clip " + ChatFormatting.GRAY + "<+|-|down> <value>");
                ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + ".hclip " + ChatFormatting.GRAY + "<+|- > <value>");
                ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + ".friend " + ChatFormatting.GRAY + "<add|del|list|clear>");
                ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + ".config " + ChatFormatting.GRAY + "<save|load|delete>");
                ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + ".xray " + ChatFormatting.GRAY + "<add|remove|list|clear>");
                ChatHelper.addChatMessage(ChatFormatting.DARK_AQUA + ".aes " + ChatFormatting.GRAY + "<encrypt|decrypt>");
            }
        } else {
            ChatHelper.addChatMessage(this.getUsage());
        }
    }
}
