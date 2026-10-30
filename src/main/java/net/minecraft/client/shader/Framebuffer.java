package net.minecraft.client.shader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class Framebuffer {
    public int framebufferWidth;
    public int framebufferHeight;
    public int framebufferTextureWidth;
    public int framebufferTextureHeight;
    public int framebufferObject = -1;
    public int framebufferTexture = -1;
    public int depthBuffer = -1;
    public boolean useDepth;

    private static int previousFbo = 0;

    public Framebuffer(int width, int height, boolean useDepth) {
        this.framebufferWidth = width;
        this.framebufferHeight = height;
        this.framebufferTextureWidth = width;
        this.framebufferTextureHeight = height;
        this.useDepth = useDepth;
        if (width > 0 && height > 0) {
            createBuffers(width, height);
        }
    }

    public void createBuffers(int width, int height) {
        this.framebufferWidth = width;
        this.framebufferHeight = height;
        this.framebufferTextureWidth = width;
        this.framebufferTextureHeight = height;

        if (framebufferObject == -1) {
            framebufferObject = GL30.glGenFramebuffers();
        }
        if (framebufferTexture == -1) {
            framebufferTexture = GL11.glGenTextures();
        }

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebufferTexture);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (java.nio.ByteBuffer) null);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebufferObject);
        GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, framebufferTexture, 0);

        if (useDepth) {
            if (depthBuffer == -1) {
                depthBuffer = GL30.glGenRenderbuffers();
            }
            GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthBuffer);
            GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL30.GL_DEPTH24_STENCIL8, width, height);
            GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL30.GL_RENDERBUFFER, depthBuffer);
            GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, 0);
        }

        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
    }

    public void createBindFramebuffer(int width, int height) {
        deleteFramebuffer();
        createBuffers(width, height);
        bindFramebuffer(true);
    }

    public void bindFramebuffer(boolean setViewport) {
        previousFbo = GL11.glGetInteger(GL30.GL_FRAMEBUFFER_BINDING);
        if (framebufferObject != -1) {
            GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, framebufferObject);
            if (setViewport) {
                GL11.glViewport(0, 0, framebufferWidth, framebufferHeight);
            }
        }
    }

    public void bindFramebufferTexture() {
        if (framebufferTexture != -1) {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebufferTexture);
        }
    }

    public void unbindFramebufferTexture() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    public void unbindFramebuffer() {
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, previousFbo);
    }

    public void framebufferClear() {
        bindFramebuffer(false);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | (useDepth ? GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT : 0));
        unbindFramebuffer();
    }

    public void deleteFramebuffer() {
        if (framebufferTexture != -1) {
            GL11.glDeleteTextures(framebufferTexture);
            framebufferTexture = -1;
        }
        if (depthBuffer != -1) {
            GL30.glDeleteRenderbuffers(depthBuffer);
            depthBuffer = -1;
        }
        if (framebufferObject != -1) {
            GL30.glDeleteFramebuffers(framebufferObject);
            framebufferObject = -1;
        }
    }
}
