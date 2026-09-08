package com.mojang.blaze3d.shaders;

import com.mojang.blaze3d.preprocessor.GlslPreprocessor;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;

public class EffectProgram extends Program {
   private static final GlslPreprocessor PREPROCESSOR = new GlslPreprocessor() {
      @Override
      public String applyImport(boolean var1, String var2) {
         return "#error Import statement not supported";
      }
   };
   private int references;

   private EffectProgram(Program.Type var1, int var2, String var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void attachToEffect(Effect var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      ++this.references;
      this.attachToShader(â˜ƒ);
   }

   @Override
   public void close() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      --this.references;
      if (this.references <= 0) {
         super.close();
      }
   }

   public static EffectProgram compileShader(Program.Type var0, String var1, InputStream var2, String var3) throws IOException {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      int â˜ƒ = compileShaderInternal(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, PREPROCESSOR);
      EffectProgram â˜ƒx = new EffectProgram(â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.getPrograms().put(â˜ƒ, â˜ƒx);
      return â˜ƒx;
   }
}
