package org.neverhook.client.ui.shader;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public abstract class FramebufferShader extends Shader {

    private static RenderTarget framebuffer;

    protected float red, green, blue, alpha = 1F;
    protected float radius = 2F;
    protected float quality = 1F;

    public FramebufferShader(String fragmentShader) {
        super(fragmentShader);
    }

    public void renderShader(float partialTicks) {
    }

    public void stopRenderShader(Color color, float radius, float quality) {
        red = color.getRed() / 255F;
        green = color.getGreen() / 255F;
        blue = color.getBlue() / 255F;
        alpha = color.getAlpha() / 255F;
        this.radius = radius;
        this.quality = quality;

        startShader();
        stopShader();
    }

    public RenderTarget setupFrameBuffer(RenderTarget frameBuffer) {
        return frameBuffer;
    }

    public void drawFramebuffer(final RenderTarget framebuffer) {
        GlStateManager._glUseProgram(0);
    }
}
