package org.neverhook.client.helpers.player;

import org.neverhook.client.helpers.Helper;

public class MovementHelper implements Helper {

    public static boolean isMoving() {
        if (mc.player == null || mc.player.input == null) return false;
        return mc.player.input.getMoveVector().x != 0 || mc.player.input.getMoveVector().y != 0;
    }
}
