package com.mojang.blaze3d.platform;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import net.minecraft.client.renderer.GameRenderer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

@DontObfuscate
public class GLX {
   private static final Logger LOGGER = LogManager.getLogger();
   private static String cpuInfo;

   public static String getOpenGLVersionString() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GLFW.glfwGetCurrentContext() == 0L
         ? "NO CONTEXT"
         : GlStateManager._getString(7937) + " GL version " + GlStateManager._getString(7938) + ", " + GlStateManager._getString(7936);
   }

   public static int _getRefreshRate(Window var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      long â˜ƒ = GLFW.glfwGetWindowMonitor(â˜ƒ.getWindow());
      if (â˜ƒ == 0L) {
         â˜ƒ = GLFW.glfwGetPrimaryMonitor();
      }

      GLFWVidMode â˜ƒ = â˜ƒ == 0L ? null : GLFW.glfwGetVideoMode(â˜ƒ);
      return â˜ƒ == null ? 0 : â˜ƒ.refreshRate();
   }

   public static String _getLWJGLVersion() {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      return Version.getVersion();
   }

   public static LongSupplier _initGlfw() {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      Window.checkGlfwError((var0x, var1x) -> {
         throw new IllegalStateException(String.format("GLFW error before init: [0x%X]%s", var0x, var1x));
      });
      List<String> â˜ƒ = Lists.newArrayList();
      GLFWErrorCallback â˜ƒx = GLFW.glfwSetErrorCallback((var1x, var2x) -> â˜ƒ.add(String.format("GLFW error during init: [0x%X]%s", var1x, var2x)));
      if (!GLFW.glfwInit()) {
         throw new IllegalStateException("Failed to initialize GLFW, errors: " + Joiner.on(",").join(â˜ƒ));
      } else {
         LongSupplier â˜ƒ = () -> (long)(GLFW.glfwGetTime() * 1.0E9);

         for(String â˜ƒx : â˜ƒ) {
            LOGGER.error("GLFW error collected during initialization: {}", â˜ƒx);
         }

         RenderSystem.setErrorCallback(â˜ƒx);
         return â˜ƒ;
      }
   }

   public static void _setGlfwErrorCallback(GLFWErrorCallbackI var0) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      GLFWErrorCallback â˜ƒ = GLFW.glfwSetErrorCallback(â˜ƒ);
      if (â˜ƒ != null) {
         â˜ƒ.free();
      }
   }

   public static boolean _shouldClose(Window var0) {
      return GLFW.glfwWindowShouldClose(â˜ƒ.getWindow());
   }

   public static void _init(int var0, boolean var1) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);

      try {
         CentralProcessor â˜ƒ = new SystemInfo().getHardware().getProcessor();
         cpuInfo = String.format("%dx %s", â˜ƒ.getLogicalProcessorCount(), â˜ƒ.getProcessorIdentifier().getName()).replaceAll("\\s+", " ");
      } catch (Throwable var3) {
      }

      GlDebug.enableDebugCallback(â˜ƒ, â˜ƒ);
   }

   public static String _getCpuInfo() {
      return cpuInfo == null ? "<unknown>" : cpuInfo;
   }

   public static void _renderCrosshair(int var0, boolean var1, boolean var2, boolean var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GlStateManager._disableTexture();
      GlStateManager._depthMask(false);
      GlStateManager._disableCull();
      RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
      Tesselator â˜ƒ = RenderSystem.renderThreadTesselator();
      BufferBuilder â˜ƒx = â˜ƒ.getBuilder();
      RenderSystem.lineWidth(4.0F);
      â˜ƒx.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
      if (â˜ƒ) {
         â˜ƒx.vertex(0.0, 0.0, 0.0).color(0, 0, 0, 255).normal(1.0F, 0.0F, 0.0F).endVertex();
         â˜ƒx.vertex((double)â˜ƒ, 0.0, 0.0).color(0, 0, 0, 255).normal(1.0F, 0.0F, 0.0F).endVertex();
      }

      if (â˜ƒ) {
         â˜ƒx.vertex(0.0, 0.0, 0.0).color(0, 0, 0, 255).normal(0.0F, 1.0F, 0.0F).endVertex();
         â˜ƒx.vertex(0.0, (double)â˜ƒ, 0.0).color(0, 0, 0, 255).normal(0.0F, 1.0F, 0.0F).endVertex();
      }

      if (â˜ƒ) {
         â˜ƒx.vertex(0.0, 0.0, 0.0).color(0, 0, 0, 255).normal(0.0F, 0.0F, 1.0F).endVertex();
         â˜ƒx.vertex(0.0, 0.0, (double)â˜ƒ).color(0, 0, 0, 255).normal(0.0F, 0.0F, 1.0F).endVertex();
      }

      â˜ƒ.end();
      RenderSystem.lineWidth(2.0F);
      â˜ƒx.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);
      if (â˜ƒ) {
         â˜ƒx.vertex(0.0, 0.0, 0.0).color(255, 0, 0, 255).normal(1.0F, 0.0F, 0.0F).endVertex();
         â˜ƒx.vertex((double)â˜ƒ, 0.0, 0.0).color(255, 0, 0, 255).normal(1.0F, 0.0F, 0.0F).endVertex();
      }

      if (â˜ƒ) {
         â˜ƒx.vertex(0.0, 0.0, 0.0).color(0, 255, 0, 255).normal(0.0F, 1.0F, 0.0F).endVertex();
         â˜ƒx.vertex(0.0, (double)â˜ƒ, 0.0).color(0, 255, 0, 255).normal(0.0F, 1.0F, 0.0F).endVertex();
      }

      if (â˜ƒ) {
         â˜ƒx.vertex(0.0, 0.0, 0.0).color(127, 127, 255, 255).normal(0.0F, 0.0F, 1.0F).endVertex();
         â˜ƒx.vertex(0.0, 0.0, (double)â˜ƒ).color(127, 127, 255, 255).normal(0.0F, 0.0F, 1.0F).endVertex();
      }

      â˜ƒ.end();
      RenderSystem.lineWidth(1.0F);
      GlStateManager._enableCull();
      GlStateManager._depthMask(true);
      GlStateManager._enableTexture();
   }

   public static <T> T make(Supplier<T> var0) {
      return (T)â˜ƒ.get();
   }

   public static <T> T make(T var0, Consumer<T> var1) {
      â˜ƒ.accept(â˜ƒ);
      return â˜ƒ;
   }
}
