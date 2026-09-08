package com.mojang.blaze3d.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProgramManager {
   private static final Logger LOGGER = LogManager.getLogger();

   public static void glUseProgram(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GlStateManager._glUseProgram(â˜ƒ);
   }

   public static void releaseProgram(Shader var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      â˜ƒ.getFragmentProgram().close();
      â˜ƒ.getVertexProgram().close();
      GlStateManager.glDeleteProgram(â˜ƒ.getId());
   }

   public static int createProgram() throws IOException {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      int â˜ƒ = GlStateManager.glCreateProgram();
      if (â˜ƒ <= 0) {
         throw new IOException("Could not create shader program (returned program ID " + â˜ƒ + ")");
      } else {
         return â˜ƒ;
      }
   }

   public static void linkShader(Shader var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      â˜ƒ.attachToProgram();
      GlStateManager.glLinkProgram(â˜ƒ.getId());
      int â˜ƒ = GlStateManager.glGetProgrami(â˜ƒ.getId(), 35714);
      if (â˜ƒ == 0) {
         LOGGER.warn(
            "Error encountered when linking program containing VS {} and FS {}. Log output:",
            â˜ƒ.getVertexProgram().getName(),
            â˜ƒ.getFragmentProgram().getName()
         );
         LOGGER.warn(GlStateManager.glGetProgramInfoLog(â˜ƒ.getId(), 32768));
      }
   }
}
