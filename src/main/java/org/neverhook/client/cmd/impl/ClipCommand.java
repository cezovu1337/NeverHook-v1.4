package org.neverhook.client.cmd.impl;

import net.minecraft.client.Minecraft;
import org.neverhook.client.cmd.CommandAbstract;
import org.neverhook.client.helpers.misc.ChatHelper;

public class ClipCommand extends CommandAbstract {

    Minecraft mc = Minecraft.getInstance();

    public ClipCommand() {
        super("clip", "clip | hclip", "§6.clip | (hclip) + | - §3<value> | down", "clip", "hclip");
    }

    @Override
    public void execute(String... args) {
        if (args.length > 1 && mc.player != null) {
            if (args[0].equalsIgnoreCase("clip")) {
                try {
                    if (args[1].equals("down")) {
                        mc.player.setPos(mc.player.getX(), -2, mc.player.getZ());
                    }
                    if (args[1].equals("+")) {
                        mc.player.setPos(mc.player.getX(), mc.player.getY() + Double.parseDouble(args[2]), mc.player.getZ());
                    }
                    if (args[1].equals("-")) {
                        mc.player.setPos(mc.player.getX(), mc.player.getY() - Double.parseDouble(args[2]), mc.player.getZ());
                    }
                } catch (Exception ignored) {
                }
            }
            if (args[0].equalsIgnoreCase("hclip")) {
                double x = mc.player.getX();
                double y = mc.player.getY();
                double z = mc.player.getZ();
                double yaw = mc.player.getYRot() * 0.017453292;
                try {
                    if (args[1].equals("+")) {
                        mc.player.setPos(x - Math.sin(yaw) * Double.parseDouble(args[2]), y, z + Math.cos(yaw) * Double.parseDouble(args[2]));
                    }
                    if (args[1].equals("-")) {
                        mc.player.setPos(x + Math.sin(yaw) * Double.parseDouble(args[2]), y, z - Math.cos(yaw) * Double.parseDouble(args[2]));
                    }
                } catch (Exception ignored) {
                }
            }
        } else {
            ChatHelper.addChatMessage(getUsage());
        }
    }
}
