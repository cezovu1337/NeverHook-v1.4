package org.neverhook.client.ui.shader;

import com.mojang.blaze3d.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.lwjgl.opengl.GL20;
import org.neverhook.client.helpers.Helper;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public abstract class Shader implements Helper {

    private int program;
    private Map<String, Integer> uniformsMap;

    public Shader(final String fragmentShader) {
        int vertexShaderID = 0;
        int fragmentShaderID = 0;
        try {
            var vRes = Minecraft.getInstance().getResourceManager().getResource(Identifier.withDefaultNamespace("neverhook/shaders/vertex.vert"));
            if (vRes.isPresent()) {
                try (InputStream stream = vRes.get().open()) {
                    String src = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
                    vertexShaderID = createShader(src, GL20.GL_VERTEX_SHADER);
                }
            }

            var fRes = Minecraft.getInstance().getResourceManager().getResource(Identifier.withDefaultNamespace("neverhook/shaders/fragment/" + fragmentShader));
            if (fRes.isPresent()) {
                try (InputStream stream = fRes.get().open()) {
                    String src = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
                    fragmentShaderID = createShader(src, GL20.GL_FRAGMENT_SHADER);
                }
            }
        } catch (Exception ignored) {
            return;
        }

        if (vertexShaderID == 0 || fragmentShaderID == 0) return;

        program = GL20.glCreateProgram();
        if (program == 0) return;

        GL20.glAttachShader(program, vertexShaderID);
        GL20.glAttachShader(program, fragmentShaderID);
        GL20.glLinkProgram(program);

        int status = GL20.glGetProgrami(program, GL20.GL_LINK_STATUS);
        if (status == 0) {
            GL20.glDeleteProgram(program);
            program = 0;
        }
    }

    public void startShader() {
        if (program <= 0) return;
        GlStateManager._glUseProgram(program);
        if (uniformsMap == null) {
            uniformsMap = new HashMap<>();
            setupUniforms();
        }
        updateUniforms();
    }

    public void stopShader() {
        if (program > 0) {
            GlStateManager._glUseProgram(0);
        }
    }

    public abstract void setupUniforms();

    public abstract void updateUniforms();

    private int createShader(String shaderSource, int shaderType) {
        int shader = GL20.glCreateShader(shaderType);
        if (shader == 0) return 0;
        GL20.glShaderSource(shader, shaderSource);
        GL20.glCompileShader(shader);
        int status = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
        if (status == 0) {
            GL20.glDeleteShader(shader);
            return 0;
        }
        return shader;
    }

    public void setUniform(final String uniformName, final int location) {
        if (uniformsMap != null) {
            uniformsMap.put(uniformName, location);
        }
    }

    public void setupUniform(final String uniformName) {
        if (program > 0) {
            setUniform(uniformName, GL20.glGetUniformLocation(program, uniformName));
        }
    }

    public int getUniform(final String uniformName) {
        return (uniformsMap != null && uniformsMap.containsKey(uniformName)) ? uniformsMap.get(uniformName) : -1;
    }
}
