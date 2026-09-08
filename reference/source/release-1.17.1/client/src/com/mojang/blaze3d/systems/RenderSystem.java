package com.mojang.blaze3d.systems;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.pipeline.RenderCall;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallbackI;

@DontObfuscate
public class RenderSystem {
   static final Logger LOGGER = LogManager.getLogger();
   private static final ConcurrentLinkedQueue<RenderCall> recordingQueue = Queues.newConcurrentLinkedQueue();
   private static final Tesselator RENDER_THREAD_TESSELATOR = new Tesselator();
   private static final int MINIMUM_ATLAS_TEXTURE_SIZE = 1024;
   private static boolean isReplayingQueue;
   @Nullable
   private static Thread gameThread;
   @Nullable
   private static Thread renderThread;
   private static int MAX_SUPPORTED_TEXTURE_SIZE = -1;
   private static boolean isInInit;
   private static double lastDrawTime = Double.MIN_VALUE;
   private static final RenderSystem.AutoStorageIndexBuffer sharedSequential = new RenderSystem.AutoStorageIndexBuffer(1, 1, IntConsumer::accept);
   private static final RenderSystem.AutoStorageIndexBuffer sharedSequentialQuad = new RenderSystem.AutoStorageIndexBuffer(4, 6, (var0, var1) -> {
      var0.accept(var1 + 0);
      var0.accept(var1 + 1);
      var0.accept(var1 + 2);
      var0.accept(var1 + 2);
      var0.accept(var1 + 3);
      var0.accept(var1 + 0);
   });
   private static final RenderSystem.AutoStorageIndexBuffer sharedSequentialLines = new RenderSystem.AutoStorageIndexBuffer(4, 6, (var0, var1) -> {
      var0.accept(var1 + 0);
      var0.accept(var1 + 1);
      var0.accept(var1 + 2);
      var0.accept(var1 + 3);
      var0.accept(var1 + 2);
      var0.accept(var1 + 1);
   });
   private static Matrix4f projectionMatrix = new Matrix4f();
   private static Matrix4f savedProjectionMatrix = new Matrix4f();
   private static PoseStack modelViewStack = new PoseStack();
   private static Matrix4f modelViewMatrix = new Matrix4f();
   private static Matrix4f textureMatrix = new Matrix4f();
   private static final int[] shaderTextures = new int[12];
   private static final float[] shaderColor = new float[]{1.0F, 1.0F, 1.0F, 1.0F};
   private static float shaderFogStart;
   private static float shaderFogEnd = 1.0F;
   private static final float[] shaderFogColor = new float[]{0.0F, 0.0F, 0.0F, 0.0F};
   private static final Vector3f[] shaderLightDirections = new Vector3f[2];
   private static float shaderGameTime;
   private static float shaderLineWidth = 1.0F;
   @Nullable
   private static ShaderInstance shader;

   public static void initRenderThread() {
      if (renderThread == null && gameThread != Thread.currentThread()) {
         renderThread = Thread.currentThread();
      } else {
         throw new IllegalStateException("Could not initialize render thread");
      }
   }

   public static boolean isOnRenderThread() {
      return Thread.currentThread() == renderThread;
   }

   public static boolean isOnRenderThreadOrInit() {
      return isInInit || isOnRenderThread();
   }

   public static void initGameThread(boolean var0) {
      boolean â˜ƒ = renderThread == Thread.currentThread();
      if (gameThread == null && renderThread != null && â˜ƒ != â˜ƒ) {
         gameThread = Thread.currentThread();
      } else {
         throw new IllegalStateException("Could not initialize tick thread");
      }
   }

   public static boolean isOnGameThread() {
      return true;
   }

   public static boolean isOnGameThreadOrInit() {
      return isInInit || isOnGameThread();
   }

   public static void assertThread(Supplier<Boolean> var0) {
      if (!â˜ƒ.get()) {
         throw new IllegalStateException("Rendersystem called from wrong thread");
      }
   }

   public static boolean isInInitPhase() {
      return true;
   }

   public static void recordRenderCall(RenderCall var0) {
      recordingQueue.add(â˜ƒ);
   }

   public static void flipFrame(long var0) {
      GLFW.glfwPollEvents();
      replayQueue();
      Tesselator.getInstance().getBuilder().clear();
      GLFW.glfwSwapBuffers(â˜ƒ);
      GLFW.glfwPollEvents();
   }

   public static void replayQueue() {
      isReplayingQueue = true;

      while(!recordingQueue.isEmpty()) {
         RenderCall â˜ƒ = (RenderCall)recordingQueue.poll();
         â˜ƒ.execute();
      }

      isReplayingQueue = false;
   }

   public static void limitDisplayFPS(int var0) {
      double â˜ƒ = lastDrawTime + 1.0 / (double)â˜ƒ;

      double â˜ƒ;
      for(â˜ƒ = GLFW.glfwGetTime(); â˜ƒ < â˜ƒ; â˜ƒ = GLFW.glfwGetTime()) {
         GLFW.glfwWaitEventsTimeout(â˜ƒ - â˜ƒ);
      }

      lastDrawTime = â˜ƒ;
   }

   public static void disableDepthTest() {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._disableDepthTest();
   }

   public static void enableDepthTest() {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      GlStateManager._enableDepthTest();
   }

   public static void enableScissor(int var0, int var1, int var2, int var3) {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      GlStateManager._enableScissorTest();
      GlStateManager._scissorBox(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void disableScissor() {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      GlStateManager._disableScissorTest();
   }

   public static void depthFunc(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._depthFunc(â˜ƒ);
   }

   public static void depthMask(boolean var0) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._depthMask(â˜ƒ);
   }

   public static void enableBlend() {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._enableBlend();
   }

   public static void disableBlend() {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._disableBlend();
   }

   public static void blendFunc(GlStateManager.SourceFactor var0, GlStateManager.DestFactor var1) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._blendFunc(â˜ƒ.value, â˜ƒ.value);
   }

   public static void blendFunc(int var0, int var1) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._blendFunc(â˜ƒ, â˜ƒ);
   }

   public static void blendFuncSeparate(
      GlStateManager.SourceFactor var0, GlStateManager.DestFactor var1, GlStateManager.SourceFactor var2, GlStateManager.DestFactor var3
   ) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._blendFuncSeparate(â˜ƒ.value, â˜ƒ.value, â˜ƒ.value, â˜ƒ.value);
   }

   public static void blendFuncSeparate(int var0, int var1, int var2, int var3) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._blendFuncSeparate(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void blendEquation(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._blendEquation(â˜ƒ);
   }

   public static void enableCull() {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._enableCull();
   }

   public static void disableCull() {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._disableCull();
   }

   public static void polygonMode(int var0, int var1) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._polygonMode(â˜ƒ, â˜ƒ);
   }

   public static void enablePolygonOffset() {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._enablePolygonOffset();
   }

   public static void disablePolygonOffset() {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._disablePolygonOffset();
   }

   public static void polygonOffset(float var0, float var1) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._polygonOffset(â˜ƒ, â˜ƒ);
   }

   public static void enableColorLogicOp() {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._enableColorLogicOp();
   }

   public static void disableColorLogicOp() {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._disableColorLogicOp();
   }

   public static void logicOp(GlStateManager.LogicOp var0) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._logicOp(â˜ƒ.value);
   }

   public static void activeTexture(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._activeTexture(â˜ƒ);
   }

   public static void enableTexture() {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._enableTexture();
   }

   public static void disableTexture() {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._disableTexture();
   }

   public static void texParameter(int var0, int var1, int var2) {
      GlStateManager._texParameter(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void deleteTexture(int var0) {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      GlStateManager._deleteTexture(â˜ƒ);
   }

   public static void bindTextureForSetup(int var0) {
      bindTexture(â˜ƒ);
   }

   public static void bindTexture(int var0) {
      GlStateManager._bindTexture(â˜ƒ);
   }

   public static void viewport(int var0, int var1, int var2, int var3) {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      GlStateManager._viewport(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void colorMask(boolean var0, boolean var1, boolean var2, boolean var3) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._colorMask(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void stencilFunc(int var0, int var1, int var2) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._stencilFunc(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void stencilMask(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._stencilMask(â˜ƒ);
   }

   public static void stencilOp(int var0, int var1, int var2) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._stencilOp(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void clearDepth(double var0) {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      GlStateManager._clearDepth(â˜ƒ);
   }

   public static void clearColor(float var0, float var1, float var2, float var3) {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      GlStateManager._clearColor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void clearStencil(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._clearStencil(â˜ƒ);
   }

   public static void clear(int var0, boolean var1) {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      GlStateManager._clear(â˜ƒ, â˜ƒ);
   }

   public static void setShaderFogStart(float var0) {
      assertThread(RenderSystem::isOnGameThread);
      _setShaderFogStart(â˜ƒ);
   }

   private static void _setShaderFogStart(float var0) {
      shaderFogStart = â˜ƒ;
   }

   public static float getShaderFogStart() {
      assertThread(RenderSystem::isOnRenderThread);
      return shaderFogStart;
   }

   public static void setShaderFogEnd(float var0) {
      assertThread(RenderSystem::isOnGameThread);
      _setShaderFogEnd(â˜ƒ);
   }

   private static void _setShaderFogEnd(float var0) {
      shaderFogEnd = â˜ƒ;
   }

   public static float getShaderFogEnd() {
      assertThread(RenderSystem::isOnRenderThread);
      return shaderFogEnd;
   }

   public static void setShaderFogColor(float var0, float var1, float var2, float var3) {
      assertThread(RenderSystem::isOnGameThread);
      _setShaderFogColor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void setShaderFogColor(float var0, float var1, float var2) {
      setShaderFogColor(â˜ƒ, â˜ƒ, â˜ƒ, 1.0F);
   }

   private static void _setShaderFogColor(float var0, float var1, float var2, float var3) {
      shaderFogColor[0] = â˜ƒ;
      shaderFogColor[1] = â˜ƒ;
      shaderFogColor[2] = â˜ƒ;
      shaderFogColor[3] = â˜ƒ;
   }

   public static float[] getShaderFogColor() {
      assertThread(RenderSystem::isOnRenderThread);
      return shaderFogColor;
   }

   public static void setShaderLights(Vector3f var0, Vector3f var1) {
      assertThread(RenderSystem::isOnGameThread);
      _setShaderLights(â˜ƒ, â˜ƒ);
   }

   public static void _setShaderLights(Vector3f var0, Vector3f var1) {
      shaderLightDirections[0] = â˜ƒ;
      shaderLightDirections[1] = â˜ƒ;
   }

   public static void setupShaderLights(ShaderInstance var0) {
      assertThread(RenderSystem::isOnRenderThread);
      if (â˜ƒ.LIGHT0_DIRECTION != null) {
         â˜ƒ.LIGHT0_DIRECTION.set(shaderLightDirections[0]);
      }

      if (â˜ƒ.LIGHT1_DIRECTION != null) {
         â˜ƒ.LIGHT1_DIRECTION.set(shaderLightDirections[1]);
      }
   }

   public static void setShaderColor(float var0, float var1, float var2, float var3) {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> _setShaderColor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ));
      } else {
         _setShaderColor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private static void _setShaderColor(float var0, float var1, float var2, float var3) {
      shaderColor[0] = â˜ƒ;
      shaderColor[1] = â˜ƒ;
      shaderColor[2] = â˜ƒ;
      shaderColor[3] = â˜ƒ;
   }

   public static float[] getShaderColor() {
      assertThread(RenderSystem::isOnRenderThread);
      return shaderColor;
   }

   public static void drawElements(int var0, int var1, int var2) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._drawElements(â˜ƒ, â˜ƒ, â˜ƒ, 0L);
   }

   public static void lineWidth(float var0) {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> shaderLineWidth = â˜ƒ);
      } else {
         shaderLineWidth = â˜ƒ;
      }
   }

   public static float getShaderLineWidth() {
      assertThread(RenderSystem::isOnRenderThread);
      return shaderLineWidth;
   }

   public static void pixelStore(int var0, int var1) {
      assertThread(RenderSystem::isOnGameThreadOrInit);
      GlStateManager._pixelStore(â˜ƒ, â˜ƒ);
   }

   public static void readPixels(int var0, int var1, int var2, int var3, int var4, int var5, ByteBuffer var6) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._readPixels(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void getString(int var0, Consumer<String> var1) {
      assertThread(RenderSystem::isOnGameThread);
      â˜ƒ.accept(GlStateManager._getString(â˜ƒ));
   }

   public static String getBackendDescription() {
      assertThread(RenderSystem::isInInitPhase);
      return String.format("LWJGL version %s", GLX._getLWJGLVersion());
   }

   public static String getApiDescription() {
      assertThread(RenderSystem::isInInitPhase);
      return GLX.getOpenGLVersionString();
   }

   public static LongSupplier initBackendSystem() {
      assertThread(RenderSystem::isInInitPhase);
      return GLX._initGlfw();
   }

   public static void initRenderer(int var0, boolean var1) {
      assertThread(RenderSystem::isInInitPhase);
      GLX._init(â˜ƒ, â˜ƒ);
   }

   public static void setErrorCallback(GLFWErrorCallbackI var0) {
      assertThread(RenderSystem::isInInitPhase);
      GLX._setGlfwErrorCallback(â˜ƒ);
   }

   public static void renderCrosshair(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      GLX._renderCrosshair(â˜ƒ, true, true, true);
   }

   public static String getCapsString() {
      assertThread(RenderSystem::isOnGameThread);
      return "Using framebuffer using OpenGL 3.2";
   }

   public static void setupDefaultState(int var0, int var1, int var2, int var3) {
      assertThread(RenderSystem::isInInitPhase);
      GlStateManager._enableTexture();
      GlStateManager._clearDepth(1.0);
      GlStateManager._enableDepthTest();
      GlStateManager._depthFunc(515);
      projectionMatrix.setIdentity();
      savedProjectionMatrix.setIdentity();
      modelViewMatrix.setIdentity();
      textureMatrix.setIdentity();
      GlStateManager._viewport(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static int maxSupportedTextureSize() {
      if (MAX_SUPPORTED_TEXTURE_SIZE == -1) {
         assertThread(RenderSystem::isOnRenderThreadOrInit);
         int â˜ƒ = GlStateManager._getInteger(3379);

         for(int â˜ƒx = Math.max(32768, â˜ƒ); â˜ƒx >= 1024; â˜ƒx >>= 1) {
            GlStateManager._texImage2D(32868, 0, 6408, â˜ƒx, â˜ƒx, 0, 6408, 5121, null);
            int â˜ƒxx = GlStateManager._getTexLevelParameter(32868, 0, 4096);
            if (â˜ƒxx != 0) {
               MAX_SUPPORTED_TEXTURE_SIZE = â˜ƒx;
               return â˜ƒx;
            }
         }

         MAX_SUPPORTED_TEXTURE_SIZE = Math.max(â˜ƒ, 1024);
         LOGGER.info("Failed to determine maximum texture size by probing, trying GL_MAX_TEXTURE_SIZE = {}", MAX_SUPPORTED_TEXTURE_SIZE);
      }

      return MAX_SUPPORTED_TEXTURE_SIZE;
   }

   public static void glBindBuffer(int var0, IntSupplier var1) {
      GlStateManager._glBindBuffer(â˜ƒ, â˜ƒ.getAsInt());
   }

   public static void glBindVertexArray(Supplier<Integer> var0) {
      GlStateManager._glBindVertexArray(â˜ƒ.get());
   }

   public static void glBufferData(int var0, ByteBuffer var1, int var2) {
      assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager._glBufferData(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void glDeleteBuffers(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._glDeleteBuffers(â˜ƒ);
   }

   public static void glDeleteVertexArrays(int var0) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._glDeleteVertexArrays(â˜ƒ);
   }

   public static void glUniform1i(int var0, int var1) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._glUniform1i(â˜ƒ, â˜ƒ);
   }

   public static void glUniform1(int var0, IntBuffer var1) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._glUniform1(â˜ƒ, â˜ƒ);
   }

   public static void glUniform2(int var0, IntBuffer var1) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._glUniform2(â˜ƒ, â˜ƒ);
   }

   public static void glUniform3(int var0, IntBuffer var1) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._glUniform3(â˜ƒ, â˜ƒ);
   }

   public static void glUniform4(int var0, IntBuffer var1) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._glUniform4(â˜ƒ, â˜ƒ);
   }

   public static void glUniform1(int var0, FloatBuffer var1) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._glUniform1(â˜ƒ, â˜ƒ);
   }

   public static void glUniform2(int var0, FloatBuffer var1) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._glUniform2(â˜ƒ, â˜ƒ);
   }

   public static void glUniform3(int var0, FloatBuffer var1) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._glUniform3(â˜ƒ, â˜ƒ);
   }

   public static void glUniform4(int var0, FloatBuffer var1) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._glUniform4(â˜ƒ, â˜ƒ);
   }

   public static void glUniformMatrix2(int var0, boolean var1, FloatBuffer var2) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._glUniformMatrix2(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void glUniformMatrix3(int var0, boolean var1, FloatBuffer var2) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._glUniformMatrix3(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void glUniformMatrix4(int var0, boolean var1, FloatBuffer var2) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager._glUniformMatrix4(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void setupOverlayColor(IntSupplier var0, int var1) {
      assertThread(RenderSystem::isOnGameThread);
      int â˜ƒ = â˜ƒ.getAsInt();
      setShaderTexture(1, â˜ƒ);
   }

   public static void teardownOverlayColor() {
      assertThread(RenderSystem::isOnGameThread);
      setShaderTexture(1, 0);
   }

   public static void setupLevelDiffuseLighting(Vector3f var0, Vector3f var1, Matrix4f var2) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager.setupLevelDiffuseLighting(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void setupGuiFlatDiffuseLighting(Vector3f var0, Vector3f var1) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager.setupGuiFlatDiffuseLighting(â˜ƒ, â˜ƒ);
   }

   public static void setupGui3DDiffuseLighting(Vector3f var0, Vector3f var1) {
      assertThread(RenderSystem::isOnGameThread);
      GlStateManager.setupGui3DDiffuseLighting(â˜ƒ, â˜ƒ);
   }

   public static void beginInitialization() {
      isInInit = true;
   }

   public static void finishInitialization() {
      isInInit = false;
      if (!recordingQueue.isEmpty()) {
         replayQueue();
      }

      if (!recordingQueue.isEmpty()) {
         throw new IllegalStateException("Recorded to render queue during initialization");
      }
   }

   public static void glGenBuffers(Consumer<Integer> var0) {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> â˜ƒ.accept(GlStateManager._glGenBuffers()));
      } else {
         â˜ƒ.accept(GlStateManager._glGenBuffers());
      }
   }

   public static void glGenVertexArrays(Consumer<Integer> var0) {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> â˜ƒ.accept(GlStateManager._glGenVertexArrays()));
      } else {
         â˜ƒ.accept(GlStateManager._glGenVertexArrays());
      }
   }

   public static Tesselator renderThreadTesselator() {
      assertThread(RenderSystem::isOnRenderThread);
      return RENDER_THREAD_TESSELATOR;
   }

   public static void defaultBlendFunc() {
      blendFuncSeparate(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
   }

   @Deprecated
   public static void runAsFancy(Runnable var0) {
      boolean â˜ƒ = Minecraft.useShaderTransparency();
      if (!â˜ƒ) {
         â˜ƒ.run();
      } else {
         Options â˜ƒ = Minecraft.getInstance().options;
         GraphicsStatus â˜ƒx = â˜ƒ.graphicsMode;
         â˜ƒ.graphicsMode = GraphicsStatus.FANCY;
         â˜ƒ.run();
         â˜ƒ.graphicsMode = â˜ƒx;
      }
   }

   public static void setShader(Supplier<ShaderInstance> var0) {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> shader = (ShaderInstance)â˜ƒ.get());
      } else {
         shader = (ShaderInstance)â˜ƒ.get();
      }
   }

   @Nullable
   public static ShaderInstance getShader() {
      assertThread(RenderSystem::isOnRenderThread);
      return shader;
   }

   public static int getTextureId(int var0) {
      return GlStateManager._getTextureId(â˜ƒ);
   }

   public static void setShaderTexture(int var0, ResourceLocation var1) {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> _setShaderTexture(â˜ƒ, â˜ƒ));
      } else {
         _setShaderTexture(â˜ƒ, â˜ƒ);
      }
   }

   public static void _setShaderTexture(int var0, ResourceLocation var1) {
      if (â˜ƒ >= 0 && â˜ƒ < shaderTextures.length) {
         TextureManager â˜ƒ = Minecraft.getInstance().getTextureManager();
         AbstractTexture â˜ƒx = â˜ƒ.getTexture(â˜ƒ);
         shaderTextures[â˜ƒ] = â˜ƒx.getId();
      }
   }

   public static void setShaderTexture(int var0, int var1) {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> _setShaderTexture(â˜ƒ, â˜ƒ));
      } else {
         _setShaderTexture(â˜ƒ, â˜ƒ);
      }
   }

   public static void _setShaderTexture(int var0, int var1) {
      if (â˜ƒ >= 0 && â˜ƒ < shaderTextures.length) {
         shaderTextures[â˜ƒ] = â˜ƒ;
      }
   }

   public static int getShaderTexture(int var0) {
      assertThread(RenderSystem::isOnRenderThread);
      return â˜ƒ >= 0 && â˜ƒ < shaderTextures.length ? shaderTextures[â˜ƒ] : 0;
   }

   public static void setProjectionMatrix(Matrix4f var0) {
      Matrix4f â˜ƒ = â˜ƒ.copy();
      if (!isOnRenderThread()) {
         recordRenderCall(() -> projectionMatrix = â˜ƒ);
      } else {
         projectionMatrix = â˜ƒ;
      }
   }

   public static void setTextureMatrix(Matrix4f var0) {
      Matrix4f â˜ƒ = â˜ƒ.copy();
      if (!isOnRenderThread()) {
         recordRenderCall(() -> textureMatrix = â˜ƒ);
      } else {
         textureMatrix = â˜ƒ;
      }
   }

   public static void resetTextureMatrix() {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> textureMatrix.setIdentity());
      } else {
         textureMatrix.setIdentity();
      }
   }

   public static void applyModelViewMatrix() {
      Matrix4f â˜ƒ = modelViewStack.last().pose().copy();
      if (!isOnRenderThread()) {
         recordRenderCall(() -> modelViewMatrix = â˜ƒ);
      } else {
         modelViewMatrix = â˜ƒ;
      }
   }

   public static void backupProjectionMatrix() {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> _backupProjectionMatrix());
      } else {
         _backupProjectionMatrix();
      }
   }

   private static void _backupProjectionMatrix() {
      savedProjectionMatrix = projectionMatrix;
   }

   public static void restoreProjectionMatrix() {
      if (!isOnRenderThread()) {
         recordRenderCall(() -> _restoreProjectionMatrix());
      } else {
         _restoreProjectionMatrix();
      }
   }

   private static void _restoreProjectionMatrix() {
      projectionMatrix = savedProjectionMatrix;
   }

   public static Matrix4f getProjectionMatrix() {
      assertThread(RenderSystem::isOnRenderThread);
      return projectionMatrix;
   }

   public static Matrix4f getModelViewMatrix() {
      assertThread(RenderSystem::isOnRenderThread);
      return modelViewMatrix;
   }

   public static PoseStack getModelViewStack() {
      return modelViewStack;
   }

   public static Matrix4f getTextureMatrix() {
      assertThread(RenderSystem::isOnRenderThread);
      return textureMatrix;
   }

   public static RenderSystem.AutoStorageIndexBuffer getSequentialBuffer(VertexFormat.Mode var0, int var1) {
      assertThread(RenderSystem::isOnRenderThread);
      RenderSystem.AutoStorageIndexBuffer â˜ƒ;
      if (â˜ƒ == VertexFormat.Mode.QUADS) {
         â˜ƒ = sharedSequentialQuad;
      } else if (â˜ƒ == VertexFormat.Mode.LINES) {
         â˜ƒ = sharedSequentialLines;
      } else {
         â˜ƒ = sharedSequential;
      }

      â˜ƒ.ensureStorage(â˜ƒ);
      return â˜ƒ;
   }

   public static void setShaderGameTime(long var0, float var2) {
      float â˜ƒ = ((float)(â˜ƒ % 24000L) + â˜ƒ) / 24000.0F;
      if (!isOnRenderThread()) {
         recordRenderCall(() -> shaderGameTime = â˜ƒ);
      } else {
         shaderGameTime = â˜ƒ;
      }
   }

   public static float getShaderGameTime() {
      assertThread(RenderSystem::isOnRenderThread);
      return shaderGameTime;
   }

   static {
      projectionMatrix.setIdentity();
      savedProjectionMatrix.setIdentity();
      modelViewMatrix.setIdentity();
      textureMatrix.setIdentity();
   }

   public static final class AutoStorageIndexBuffer {
      private final int vertexStride;
      private final int indexStride;
      private final RenderSystem.AutoStorageIndexBuffer.IndexGenerator generator;
      private int name;
      private VertexFormat.IndexType type = VertexFormat.IndexType.BYTE;
      private int indexCount;

      AutoStorageIndexBuffer(int var1, int var2, RenderSystem.AutoStorageIndexBuffer.IndexGenerator var3) {
         this.vertexStride = â˜ƒ;
         this.indexStride = â˜ƒ;
         this.generator = â˜ƒ;
      }

      void ensureStorage(int var1) {
         if (â˜ƒ > this.indexCount) {
            â˜ƒ = Mth.roundToward(â˜ƒ * 2, this.indexStride);
            RenderSystem.LOGGER.debug("Growing IndexBuffer: Old limit {}, new limit {}.", this.indexCount, â˜ƒ);
            if (this.name == 0) {
               this.name = GlStateManager._glGenBuffers();
            }

            VertexFormat.IndexType â˜ƒ = VertexFormat.IndexType.least(â˜ƒ);
            int â˜ƒx = Mth.roundToward(â˜ƒ * â˜ƒ.bytes, 4);
            GlStateManager._glBindBuffer(34963, this.name);
            GlStateManager._glBufferData(34963, (long)â˜ƒx, 35048);
            ByteBuffer â˜ƒxx = GlStateManager._glMapBuffer(34963, 35001);
            if (â˜ƒxx == null) {
               throw new RuntimeException("Failed to map GL buffer");
            } else {
               this.type = â˜ƒ;
               it.unimi.dsi.fastutil.ints.IntConsumer â˜ƒ = this.intConsumer(â˜ƒxx);

               for(int â˜ƒx = 0; â˜ƒx < â˜ƒ; â˜ƒx += this.indexStride) {
                  this.generator.accept(â˜ƒ, â˜ƒx * this.vertexStride / this.indexStride);
               }

               GlStateManager._glUnmapBuffer(34963);
               GlStateManager._glBindBuffer(34963, 0);
               this.indexCount = â˜ƒ;
               BufferUploader.invalidateElementArrayBufferBinding();
            }
         }
      }

      private it.unimi.dsi.fastutil.ints.IntConsumer intConsumer(ByteBuffer var1) {
         switch(this.type) {
            case BYTE:
               return var1x -> â˜ƒ.put((byte)var1x);
            case SHORT:
               return var1x -> â˜ƒ.putShort((short)var1x);
            case INT:
            default:
               return â˜ƒ::putInt;
         }
      }

      public int name() {
         return this.name;
      }

      public VertexFormat.IndexType type() {
         return this.type;
      }

      interface IndexGenerator {
         void accept(it.unimi.dsi.fastutil.ints.IntConsumer var1, int var2);
      }
   }
}
