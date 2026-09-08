package com.mojang.blaze3d.platform;

import com.google.common.base.Charsets;
import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL20C;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

@DontObfuscate
public class GlStateManager {
   public static final int TEXTURE_COUNT = 12;
   private static final GlStateManager.BlendState BLEND = new GlStateManager.BlendState();
   private static final GlStateManager.DepthState DEPTH = new GlStateManager.DepthState();
   private static final GlStateManager.CullState CULL = new GlStateManager.CullState();
   private static final GlStateManager.PolygonOffsetState POLY_OFFSET = new GlStateManager.PolygonOffsetState();
   private static final GlStateManager.ColorLogicState COLOR_LOGIC = new GlStateManager.ColorLogicState();
   private static final GlStateManager.StencilState STENCIL = new GlStateManager.StencilState();
   private static final GlStateManager.ScissorState SCISSOR = new GlStateManager.ScissorState();
   private static int activeTexture;
   private static final GlStateManager.TextureState[] TEXTURES = (GlStateManager.TextureState[])IntStream.range(0, 12)
      .mapToObj(var0 -> new GlStateManager.TextureState())
      .toArray(var0 -> new GlStateManager.TextureState[var0]);
   private static final GlStateManager.ColorMask COLOR_MASK = new GlStateManager.ColorMask();

   public static void _disableScissorTest() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      SCISSOR.mode.disable();
   }

   public static void _enableScissorTest() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      SCISSOR.mode.enable();
   }

   public static void _scissorBox(int var0, int var1, int var2, int var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL20.glScissor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _disableDepthTest() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      DEPTH.mode.disable();
   }

   public static void _enableDepthTest() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      DEPTH.mode.enable();
   }

   public static void _depthFunc(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (â˜ƒ != DEPTH.func) {
         DEPTH.func = â˜ƒ;
         GL11.glDepthFunc(â˜ƒ);
      }
   }

   public static void _depthMask(boolean var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (â˜ƒ != DEPTH.mask) {
         DEPTH.mask = â˜ƒ;
         GL11.glDepthMask(â˜ƒ);
      }
   }

   public static void _disableBlend() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      BLEND.mode.disable();
   }

   public static void _enableBlend() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      BLEND.mode.enable();
   }

   public static void _blendFunc(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (â˜ƒ != BLEND.srcRgb || â˜ƒ != BLEND.dstRgb) {
         BLEND.srcRgb = â˜ƒ;
         BLEND.dstRgb = â˜ƒ;
         GL11.glBlendFunc(â˜ƒ, â˜ƒ);
      }
   }

   public static void _blendFuncSeparate(int var0, int var1, int var2, int var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (â˜ƒ != BLEND.srcRgb || â˜ƒ != BLEND.dstRgb || â˜ƒ != BLEND.srcAlpha || â˜ƒ != BLEND.dstAlpha) {
         BLEND.srcRgb = â˜ƒ;
         BLEND.dstRgb = â˜ƒ;
         BLEND.srcAlpha = â˜ƒ;
         BLEND.dstAlpha = â˜ƒ;
         glBlendFuncSeparate(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public static void _blendEquation(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL14.glBlendEquation(â˜ƒ);
   }

   public static int glGetProgrami(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetProgrami(â˜ƒ, â˜ƒ);
   }

   public static void glAttachShader(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glAttachShader(â˜ƒ, â˜ƒ);
   }

   public static void glDeleteShader(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glDeleteShader(â˜ƒ);
   }

   public static int glCreateShader(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glCreateShader(â˜ƒ);
   }

   public static void glShaderSource(int var0, List<String> var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      StringBuilder â˜ƒ = new StringBuilder();

      for(String â˜ƒx : â˜ƒ) {
         â˜ƒ.append(â˜ƒx);
      }

      byte[] â˜ƒx = â˜ƒ.toString().getBytes(Charsets.UTF_8);
      ByteBuffer â˜ƒxx = MemoryUtil.memAlloc(â˜ƒx.length + 1);
      â˜ƒxx.put(â˜ƒx);
      â˜ƒxx.put((byte)0);
      â˜ƒxx.flip();

      try (MemoryStack â˜ƒxxx = MemoryStack.stackPush()) {
         PointerBuffer â˜ƒxxxx = â˜ƒxxx.mallocPointer(1);
         â˜ƒxxxx.put(â˜ƒxx);
         GL20C.nglShaderSource(â˜ƒ, 1, â˜ƒxxxx.address0(), 0L);
      } finally {
         MemoryUtil.memFree(â˜ƒxx);
      }
   }

   public static void glCompileShader(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glCompileShader(â˜ƒ);
   }

   public static int glGetShaderi(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetShaderi(â˜ƒ, â˜ƒ);
   }

   public static void _glUseProgram(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUseProgram(â˜ƒ);
   }

   public static int glCreateProgram() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glCreateProgram();
   }

   public static void glDeleteProgram(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glDeleteProgram(â˜ƒ);
   }

   public static void glLinkProgram(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glLinkProgram(â˜ƒ);
   }

   public static int _glGetUniformLocation(int var0, CharSequence var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetUniformLocation(â˜ƒ, â˜ƒ);
   }

   public static void _glUniform1(int var0, IntBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform1iv(â˜ƒ, â˜ƒ);
   }

   public static void _glUniform1i(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform1i(â˜ƒ, â˜ƒ);
   }

   public static void _glUniform1(int var0, FloatBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform1fv(â˜ƒ, â˜ƒ);
   }

   public static void _glUniform2(int var0, IntBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform2iv(â˜ƒ, â˜ƒ);
   }

   public static void _glUniform2(int var0, FloatBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform2fv(â˜ƒ, â˜ƒ);
   }

   public static void _glUniform3(int var0, IntBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform3iv(â˜ƒ, â˜ƒ);
   }

   public static void _glUniform3(int var0, FloatBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform3fv(â˜ƒ, â˜ƒ);
   }

   public static void _glUniform4(int var0, IntBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform4iv(â˜ƒ, â˜ƒ);
   }

   public static void _glUniform4(int var0, FloatBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform4fv(â˜ƒ, â˜ƒ);
   }

   public static void _glUniformMatrix2(int var0, boolean var1, FloatBuffer var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniformMatrix2fv(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _glUniformMatrix3(int var0, boolean var1, FloatBuffer var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniformMatrix3fv(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _glUniformMatrix4(int var0, boolean var1, FloatBuffer var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniformMatrix4fv(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static int _glGetAttribLocation(int var0, CharSequence var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetAttribLocation(â˜ƒ, â˜ƒ);
   }

   public static void _glBindAttribLocation(int var0, int var1, CharSequence var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glBindAttribLocation(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static int _glGenBuffers() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      return GL15.glGenBuffers();
   }

   public static int _glGenVertexArrays() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      return GL30.glGenVertexArrays();
   }

   public static void _glBindBuffer(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL15.glBindBuffer(â˜ƒ, â˜ƒ);
   }

   public static void _glBindVertexArray(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL30.glBindVertexArray(â˜ƒ);
   }

   public static void _glBufferData(int var0, ByteBuffer var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL15.glBufferData(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _glBufferData(int var0, long var1, int var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL15.glBufferData(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   public static ByteBuffer _glMapBuffer(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      return GL15.glMapBuffer(â˜ƒ, â˜ƒ);
   }

   public static void _glUnmapBuffer(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL15.glUnmapBuffer(â˜ƒ);
   }

   public static void _glDeleteBuffers(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL15.glDeleteBuffers(â˜ƒ);
   }

   public static void _glCopyTexSubImage2D(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL20.glCopyTexSubImage2D(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _glDeleteVertexArrays(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL30.glDeleteVertexArrays(â˜ƒ);
   }

   public static void _glBindFramebuffer(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL30.glBindFramebuffer(â˜ƒ, â˜ƒ);
   }

   public static void _glBlitFrameBuffer(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL30.glBlitFramebuffer(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _glBindRenderbuffer(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL30.glBindRenderbuffer(â˜ƒ, â˜ƒ);
   }

   public static void _glDeleteRenderbuffers(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL30.glDeleteRenderbuffers(â˜ƒ);
   }

   public static void _glDeleteFramebuffers(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL30.glDeleteFramebuffers(â˜ƒ);
   }

   public static int glGenFramebuffers() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      return GL30.glGenFramebuffers();
   }

   public static int glGenRenderbuffers() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      return GL30.glGenRenderbuffers();
   }

   public static void _glRenderbufferStorage(int var0, int var1, int var2, int var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL30.glRenderbufferStorage(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _glFramebufferRenderbuffer(int var0, int var1, int var2, int var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL30.glFramebufferRenderbuffer(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static int glCheckFramebufferStatus(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      return GL30.glCheckFramebufferStatus(â˜ƒ);
   }

   public static void _glFramebufferTexture2D(int var0, int var1, int var2, int var3, int var4) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL30.glFramebufferTexture2D(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static int getBoundFramebuffer() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return _getInteger(36006);
   }

   public static void glActiveTexture(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL13.glActiveTexture(â˜ƒ);
   }

   public static void glBlendFuncSeparate(int var0, int var1, int var2, int var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL14.glBlendFuncSeparate(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static String glGetShaderInfoLog(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetShaderInfoLog(â˜ƒ, â˜ƒ);
   }

   public static String glGetProgramInfoLog(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetProgramInfoLog(â˜ƒ, â˜ƒ);
   }

   public static void setupLevelDiffuseLighting(Vector3f var0, Vector3f var1, Matrix4f var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      Vector4f â˜ƒ = new Vector4f(â˜ƒ);
      â˜ƒ.transform(â˜ƒ);
      Vector4f â˜ƒx = new Vector4f(â˜ƒ);
      â˜ƒx.transform(â˜ƒ);
      RenderSystem.setShaderLights(new Vector3f(â˜ƒ), new Vector3f(â˜ƒx));
   }

   public static void setupGuiFlatDiffuseLighting(Vector3f var0, Vector3f var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      Matrix4f â˜ƒ = new Matrix4f();
      â˜ƒ.setIdentity();
      â˜ƒ.multiply(Matrix4f.createScaleMatrix(1.0F, -1.0F, 1.0F));
      â˜ƒ.multiply(Vector3f.YP.rotationDegrees(-22.5F));
      â˜ƒ.multiply(Vector3f.XP.rotationDegrees(135.0F));
      setupLevelDiffuseLighting(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void setupGui3DDiffuseLighting(Vector3f var0, Vector3f var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      Matrix4f â˜ƒ = new Matrix4f();
      â˜ƒ.setIdentity();
      â˜ƒ.multiply(Vector3f.YP.rotationDegrees(62.0F));
      â˜ƒ.multiply(Vector3f.XP.rotationDegrees(185.5F));
      â˜ƒ.multiply(Vector3f.YP.rotationDegrees(-22.5F));
      â˜ƒ.multiply(Vector3f.XP.rotationDegrees(135.0F));
      setupLevelDiffuseLighting(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _enableCull() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      CULL.enable.enable();
   }

   public static void _disableCull() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      CULL.enable.disable();
   }

   public static void _polygonMode(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPolygonMode(â˜ƒ, â˜ƒ);
   }

   public static void _enablePolygonOffset() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      POLY_OFFSET.fill.enable();
   }

   public static void _disablePolygonOffset() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      POLY_OFFSET.fill.disable();
   }

   public static void _polygonOffset(float var0, float var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (â˜ƒ != POLY_OFFSET.factor || â˜ƒ != POLY_OFFSET.units) {
         POLY_OFFSET.factor = â˜ƒ;
         POLY_OFFSET.units = â˜ƒ;
         GL11.glPolygonOffset(â˜ƒ, â˜ƒ);
      }
   }

   public static void _enableColorLogicOp() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      COLOR_LOGIC.enable.enable();
   }

   public static void _disableColorLogicOp() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      COLOR_LOGIC.enable.disable();
   }

   public static void _logicOp(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (â˜ƒ != COLOR_LOGIC.op) {
         COLOR_LOGIC.op = â˜ƒ;
         GL11.glLogicOp(â˜ƒ);
      }
   }

   public static void _activeTexture(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (activeTexture != â˜ƒ - 33984) {
         activeTexture = â˜ƒ - 33984;
         glActiveTexture(â˜ƒ);
      }
   }

   public static void _enableTexture() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      TEXTURES[activeTexture].enable = true;
   }

   public static void _disableTexture() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      TEXTURES[activeTexture].enable = false;
   }

   public static void _texParameter(int var0, int var1, float var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glTexParameterf(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _texParameter(int var0, int var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glTexParameteri(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static int _getTexLevelParameter(int var0, int var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      return GL11.glGetTexLevelParameteri(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static int _genTexture() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      return GL11.glGenTextures();
   }

   public static void _genTextures(int[] var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glGenTextures(â˜ƒ);
   }

   public static void _deleteTexture(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glDeleteTextures(â˜ƒ);

      for(GlStateManager.TextureState â˜ƒ : TEXTURES) {
         if (â˜ƒ.binding == â˜ƒ) {
            â˜ƒ.binding = -1;
         }
      }
   }

   public static void _deleteTextures(int[] var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);

      for(GlStateManager.TextureState â˜ƒ : TEXTURES) {
         for(int â˜ƒx : â˜ƒ) {
            if (â˜ƒ.binding == â˜ƒx) {
               â˜ƒ.binding = -1;
            }
         }
      }

      GL11.glDeleteTextures(â˜ƒ);
   }

   public static void _bindTexture(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (â˜ƒ != TEXTURES[activeTexture].binding) {
         TEXTURES[activeTexture].binding = â˜ƒ;
         GL11.glBindTexture(3553, â˜ƒ);
      }
   }

   public static int _getTextureId(int var0) {
      return â˜ƒ >= 0 && â˜ƒ < 12 && TEXTURES[â˜ƒ].enable ? TEXTURES[â˜ƒ].binding : 0;
   }

   public static int _getActiveTexture() {
      return activeTexture + 33984;
   }

   public static void _texImage2D(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, @Nullable IntBuffer var8) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glTexImage2D(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _texSubImage2D(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, long var8) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glTexSubImage2D(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _getTexImage(int var0, int var1, int var2, int var3, long var4) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glGetTexImage(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _viewport(int var0, int var1, int var2, int var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GlStateManager.Viewport.INSTANCE.x = â˜ƒ;
      GlStateManager.Viewport.INSTANCE.y = â˜ƒ;
      GlStateManager.Viewport.INSTANCE.width = â˜ƒ;
      GlStateManager.Viewport.INSTANCE.height = â˜ƒ;
      GL11.glViewport(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _colorMask(boolean var0, boolean var1, boolean var2, boolean var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (â˜ƒ != COLOR_MASK.red || â˜ƒ != COLOR_MASK.green || â˜ƒ != COLOR_MASK.blue || â˜ƒ != COLOR_MASK.alpha) {
         COLOR_MASK.red = â˜ƒ;
         COLOR_MASK.green = â˜ƒ;
         COLOR_MASK.blue = â˜ƒ;
         COLOR_MASK.alpha = â˜ƒ;
         GL11.glColorMask(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public static void _stencilFunc(int var0, int var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (â˜ƒ != STENCIL.func.func || â˜ƒ != STENCIL.func.ref || â˜ƒ != STENCIL.func.mask) {
         STENCIL.func.func = â˜ƒ;
         STENCIL.func.ref = â˜ƒ;
         STENCIL.func.mask = â˜ƒ;
         GL11.glStencilFunc(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public static void _stencilMask(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (â˜ƒ != STENCIL.mask) {
         STENCIL.mask = â˜ƒ;
         GL11.glStencilMask(â˜ƒ);
      }
   }

   public static void _stencilOp(int var0, int var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (â˜ƒ != STENCIL.fail || â˜ƒ != STENCIL.zfail || â˜ƒ != STENCIL.zpass) {
         STENCIL.fail = â˜ƒ;
         STENCIL.zfail = â˜ƒ;
         STENCIL.zpass = â˜ƒ;
         GL11.glStencilOp(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public static void _clearDepth(double var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glClearDepth(â˜ƒ);
   }

   public static void _clearColor(float var0, float var1, float var2, float var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glClearColor(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _clearStencil(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glClearStencil(â˜ƒ);
   }

   public static void _clear(int var0, boolean var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glClear(â˜ƒ);
      if (â˜ƒ) {
         _getError();
      }
   }

   public static void _glDrawPixels(int var0, int var1, int var2, int var3, long var4) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glDrawPixels(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _vertexAttribPointer(int var0, int var1, int var2, boolean var3, int var4, long var5) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glVertexAttribPointer(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _vertexAttribIPointer(int var0, int var1, int var2, int var3, long var4) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL30.glVertexAttribIPointer(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _enableVertexAttribArray(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glEnableVertexAttribArray(â˜ƒ);
   }

   public static void _disableVertexAttribArray(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glDisableVertexAttribArray(â˜ƒ);
   }

   public static void _drawElements(int var0, int var1, int var2, long var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glDrawElements(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _pixelStore(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glPixelStorei(â˜ƒ, â˜ƒ);
   }

   public static void _readPixels(int var0, int var1, int var2, int var3, int var4, int var5, ByteBuffer var6) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glReadPixels(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void _readPixels(int var0, int var1, int var2, int var3, int var4, int var5, long var6) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glReadPixels(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static int _getError() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL11.glGetError();
   }

   public static String _getString(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL11.glGetString(â˜ƒ);
   }

   public static int _getInteger(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      return GL11.glGetInteger(â˜ƒ);
   }

   static class BlendState {
      public final GlStateManager.BooleanState mode = new GlStateManager.BooleanState(3042);
      public int srcRgb = 1;
      public int dstRgb = 0;
      public int srcAlpha = 1;
      public int dstAlpha = 0;
   }

   static class BooleanState {
      private final int state;
      private boolean enabled;

      public BooleanState(int var1) {
         this.state = â˜ƒ;
      }

      public void disable() {
         this.setEnabled(false);
      }

      public void enable() {
         this.setEnabled(true);
      }

      public void setEnabled(boolean var1) {
         RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
         if (â˜ƒ != this.enabled) {
            this.enabled = â˜ƒ;
            if (â˜ƒ) {
               GL11.glEnable(this.state);
            } else {
               GL11.glDisable(this.state);
            }
         }
      }
   }

   static class ColorLogicState {
      public final GlStateManager.BooleanState enable = new GlStateManager.BooleanState(3058);
      public int op = 5379;
   }

   static class ColorMask {
      public boolean red = true;
      public boolean green = true;
      public boolean blue = true;
      public boolean alpha = true;
   }

   static class CullState {
      public final GlStateManager.BooleanState enable = new GlStateManager.BooleanState(2884);
      public int mode = 1029;
   }

   static class DepthState {
      public final GlStateManager.BooleanState mode = new GlStateManager.BooleanState(2929);
      public boolean mask = true;
      public int func = 513;
   }

   @DontObfuscate
   public static enum DestFactor {
      CONSTANT_ALPHA(32771),
      CONSTANT_COLOR(32769),
      DST_ALPHA(772),
      DST_COLOR(774),
      ONE(1),
      ONE_MINUS_CONSTANT_ALPHA(32772),
      ONE_MINUS_CONSTANT_COLOR(32770),
      ONE_MINUS_DST_ALPHA(773),
      ONE_MINUS_DST_COLOR(775),
      ONE_MINUS_SRC_ALPHA(771),
      ONE_MINUS_SRC_COLOR(769),
      SRC_ALPHA(770),
      SRC_COLOR(768),
      ZERO(0);

      public final int value;

      private DestFactor(int var3) {
         this.value = â˜ƒ;
      }
   }

   public static enum LogicOp {
      AND(5377),
      AND_INVERTED(5380),
      AND_REVERSE(5378),
      CLEAR(5376),
      COPY(5379),
      COPY_INVERTED(5388),
      EQUIV(5385),
      INVERT(5386),
      NAND(5390),
      NOOP(5381),
      NOR(5384),
      OR(5383),
      OR_INVERTED(5389),
      OR_REVERSE(5387),
      SET(5391),
      XOR(5382);

      public final int value;

      private LogicOp(int var3) {
         this.value = â˜ƒ;
      }
   }

   static class PolygonOffsetState {
      public final GlStateManager.BooleanState fill = new GlStateManager.BooleanState(32823);
      public final GlStateManager.BooleanState line = new GlStateManager.BooleanState(10754);
      public float factor;
      public float units;
   }

   static class ScissorState {
      public final GlStateManager.BooleanState mode = new GlStateManager.BooleanState(3089);
   }

   @DontObfuscate
   public static enum SourceFactor {
      CONSTANT_ALPHA(32771),
      CONSTANT_COLOR(32769),
      DST_ALPHA(772),
      DST_COLOR(774),
      ONE(1),
      ONE_MINUS_CONSTANT_ALPHA(32772),
      ONE_MINUS_CONSTANT_COLOR(32770),
      ONE_MINUS_DST_ALPHA(773),
      ONE_MINUS_DST_COLOR(775),
      ONE_MINUS_SRC_ALPHA(771),
      ONE_MINUS_SRC_COLOR(769),
      SRC_ALPHA(770),
      SRC_ALPHA_SATURATE(776),
      SRC_COLOR(768),
      ZERO(0);

      public final int value;

      private SourceFactor(int var3) {
         this.value = â˜ƒ;
      }
   }

   static class StencilFunc {
      public int func = 519;
      public int ref;
      public int mask = -1;
   }

   static class StencilState {
      public final GlStateManager.StencilFunc func = new GlStateManager.StencilFunc();
      public int mask = -1;
      public int fail = 7680;
      public int zfail = 7680;
      public int zpass = 7680;
   }

   static class TextureState {
      public boolean enable;
      public int binding;
   }

   public static enum Viewport {
      INSTANCE;

      protected int x;
      protected int y;
      protected int width;
      protected int height;

      public static int x() {
         return INSTANCE.x;
      }

      public static int y() {
         return INSTANCE.y;
      }

      public static int width() {
         return INSTANCE.width;
      }

      public static int height() {
         return INSTANCE.height;
      }
   }
}
