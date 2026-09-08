package com.mojang.blaze3d.shaders;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.preprocessor.GlslPreprocessor;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Program {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int MAX_LOG_LENGTH = 32768;
   private final Program.Type type;
   private final String name;
   private int id;

   protected Program(Program.Type var1, int var2, String var3) {
      this.type = â˜ƒ;
      this.id = â˜ƒ;
      this.name = â˜ƒ;
   }

   public void attachToShader(Shader var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GlStateManager.glAttachShader(â˜ƒ.getId(), this.getId());
   }

   public void close() {
      if (this.id != -1) {
         RenderSystem.assertThread(RenderSystem::isOnRenderThread);
         GlStateManager.glDeleteShader(this.id);
         this.id = -1;
         this.type.getPrograms().remove(this.name);
      }
   }

   public String getName() {
      return this.name;
   }

   public static Program compileShader(Program.Type var0, String var1, InputStream var2, String var3, GlslPreprocessor var4) throws IOException {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      int â˜ƒ = compileShaderInternal(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      Program â˜ƒx = new Program(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.getPrograms().put(â˜ƒ, â˜ƒx);
      return â˜ƒx;
   }

   protected static int compileShaderInternal(Program.Type var0, String var1, InputStream var2, String var3, GlslPreprocessor var4) throws IOException {
      String â˜ƒ = TextureUtil.readResourceAsString(â˜ƒ);
      if (â˜ƒ == null) {
         throw new IOException("Could not load program " + â˜ƒ.getName());
      } else {
         int â˜ƒ = GlStateManager.glCreateShader(â˜ƒ.getGlType());
         GlStateManager.glShaderSource(â˜ƒ, â˜ƒ.process(â˜ƒ));
         GlStateManager.glCompileShader(â˜ƒ);
         if (GlStateManager.glGetShaderi(â˜ƒ, 35713) == 0) {
            String â˜ƒx = StringUtils.trim(GlStateManager.glGetShaderInfoLog(â˜ƒ, 32768));
            throw new IOException("Couldn't compile " + â˜ƒ.getName() + " program (" + â˜ƒ + ", " + â˜ƒ + ") : " + â˜ƒx);
         } else {
            return â˜ƒ;
         }
      }
   }

   private static Program createProgram(Program.Type var0, String var1, int var2) {
      return new Program(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected int getId() {
      return this.id;
   }

   public static enum Type {
      VERTEX("vertex", ".vsh", 35633),
      FRAGMENT("fragment", ".fsh", 35632);

      private final String name;
      private final String extension;
      private final int glType;
      private final Map<String, Program> programs = Maps.newHashMap();

      private Type(String var3, String var4, int var5) {
         this.name = â˜ƒ;
         this.extension = â˜ƒ;
         this.glType = â˜ƒ;
      }

      public String getName() {
         return this.name;
      }

      public String getExtension() {
         return this.extension;
      }

      int getGlType() {
         return this.glType;
      }

      public Map<String, Program> getPrograms() {
         return this.programs;
      }
   }
}
