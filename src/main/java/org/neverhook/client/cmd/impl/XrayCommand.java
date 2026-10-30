package org.neverhook.client.cmd.impl;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import org.neverhook.client.NeverHook;
import org.neverhook.client.cmd.CommandAbstract;
import org.neverhook.client.feature.impl.visual.XRay;
import org.neverhook.client.helpers.misc.ChatHelper;
import org.neverhook.client.ui.notification.NotificationManager;
import org.neverhook.client.ui.notification.NotificationType;

import java.util.ArrayList;

public class XrayCommand extends CommandAbstract {

    public static ArrayList<Integer> blockIDS = new ArrayList<>();

    public XrayCommand() {
        super("xray", "xray", "§6.xray" + ChatFormatting.LIGHT_PURPLE + " add " + "§3<blockId> | §6.xray" + ChatFormatting.LIGHT_PURPLE + " remove " + "§3<blockId> | §6.xray" + ChatFormatting.LIGHT_PURPLE + " list | §6.xray" + ChatFormatting.LIGHT_PURPLE + " clear", "xray");
    }

    @Override
    public void execute(String... arguments) {
        if (arguments.length >= 2) {
            if (!NeverHook.instance.featureManager.getFeatureByClass(XRay.class).getState()) {
                ChatHelper.addChatMessage(ChatFormatting.RED + "Error " + ChatFormatting.WHITE + "please enable XRay module!");
                NotificationManager.publicity("XrayManager", ChatFormatting.RED + "Error " + ChatFormatting.WHITE + "please enable XRay module!", 4, NotificationType.SUCCESS);
                return;
            }
            if (arguments[0].equalsIgnoreCase("xray")) {
                if (arguments[1].equalsIgnoreCase("add") && arguments.length >= 3) {
                    if (!arguments[2].isEmpty()) {
                        int id = Integer.parseInt(arguments[2]);
                        if (!blockIDS.contains(id)) {
                            blockIDS.add(id);
                            Block b = BuiltInRegistries.BLOCK.byId(id);
                            String name = b != null ? b.getName().getString() : "Unknown";
                            ChatHelper.addChatMessage(ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "added block: " + ChatFormatting.RED + "\"" + name + "\"");
                            NotificationManager.publicity("XrayManager", ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "added block: " + ChatFormatting.RED + "\"" + name + "\"", 4, NotificationType.SUCCESS);
                        } else {
                            ChatHelper.addChatMessage(ChatFormatting.RED + "Error " + ChatFormatting.WHITE + "this block already have in list!");
                            NotificationManager.publicity("XrayManager", ChatFormatting.RED + "Error " + ChatFormatting.WHITE + "this block already have in list!", 4, NotificationType.SUCCESS);
                        }
                    }
                } else if (arguments[1].equalsIgnoreCase("remove") && arguments.length >= 3) {
                    int id = Integer.parseInt(arguments[2]);
                    if (blockIDS.contains(id)) {
                        blockIDS.remove((Integer) id);
                        ChatHelper.addChatMessage(ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "removed block by id " + id);
                        NotificationManager.publicity("XrayManager", ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "removed block by id " + id, 4, NotificationType.SUCCESS);
                    } else {
                        ChatHelper.addChatMessage(ChatFormatting.RED + "Error " + ChatFormatting.WHITE + "this block doesn't contains in your list!");
                        NotificationManager.publicity("XrayManager", ChatFormatting.RED + "Error " + ChatFormatting.WHITE + "this block doesn't contains in your list!", 4, NotificationType.SUCCESS);
                    }
                } else if (arguments[1].equalsIgnoreCase("list")) {
                    if (!blockIDS.isEmpty()) {
                        for (Integer integer : blockIDS) {
                            Block b = BuiltInRegistries.BLOCK.byId(integer);
                            String name = b != null ? b.getName().getString() : "Unknown";
                            ChatHelper.addChatMessage(ChatFormatting.RED + name + ChatFormatting.LIGHT_PURPLE + " ID: " + integer);
                        }
                    } else {
                        ChatHelper.addChatMessage(ChatFormatting.RED + "Error " + ChatFormatting.WHITE + "your block list is empty!");
                        NotificationManager.publicity("XrayManager", ChatFormatting.RED + "Error " + ChatFormatting.WHITE + "your block list is empty!", 4, NotificationType.SUCCESS);
                    }
                } else if (arguments[1].equalsIgnoreCase("clear")) {
                    if (blockIDS.isEmpty()) {
                        ChatHelper.addChatMessage(ChatFormatting.RED + "Error " + ChatFormatting.WHITE + "your block list is empty!");
                        NotificationManager.publicity("XrayManager", ChatFormatting.RED + "Error " + ChatFormatting.WHITE + "your block list is empty!", 4, NotificationType.SUCCESS);
                    } else {
                        blockIDS.clear();
                        ChatHelper.addChatMessage(ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "clear block list!");
                        NotificationManager.publicity("XrayManager", ChatFormatting.GREEN + "Successfully " + ChatFormatting.WHITE + "clear block list!", 4, NotificationType.SUCCESS);
                    }
                }
            }
        } else {
            ChatHelper.addChatMessage(getUsage());
        }
    }
}
