package net.minecraft.util.math;

import net.minecraft.util.Mth;

public class MathHelper {

    public static float sin(float value) {
        return Mth.sin(value);
    }

    public static float cos(float value) {
        return Mth.cos(value);
    }

    public static float sqrt(float value) {
        return Mth.sqrt(value);
    }

    public static float sqrt(double value) {
        return (float) Math.sqrt(value);
    }

    public static int floor(float value) {
        return Mth.floor(value);
    }

    public static int floor(double value) {
        return Mth.floor(value);
    }

    public static int ceil(float value) {
        return Mth.ceil(value);
    }

    public static int ceil(double value) {
        return Mth.ceil(value);
    }

    public static int clamp(int num, int min, int max) {
        return Mth.clamp(num, min, max);
    }

    public static float clamp(float num, float min, float max) {
        return Mth.clamp(num, min, max);
    }

    public static double clamp(double num, double min, double max) {
        return Mth.clamp(num, min, max);
    }

    public static float wrapDegrees(float value) {
        return Mth.wrapDegrees(value);
    }

    public static double wrapDegrees(double value) {
        return Mth.wrapDegrees(value);
    }
}
