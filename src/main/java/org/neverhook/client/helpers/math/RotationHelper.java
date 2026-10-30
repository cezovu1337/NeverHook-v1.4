package org.neverhook.client.helpers.math;

import org.neverhook.client.helpers.Helper;

public class RotationHelper implements Helper {
    public static class Rotation {
        public static boolean isReady = false;
        public static float packetPitch;
        public static float packetYaw;
        public static float lastPacketPitch;
        public static float lastPacketYaw;
        public static float renderPacketYaw;
        public static float lastRenderPacketYaw;
        public static float bodyYaw;
        public static float lastBodyYaw;
        public static int rotationCounter;
    }
}
