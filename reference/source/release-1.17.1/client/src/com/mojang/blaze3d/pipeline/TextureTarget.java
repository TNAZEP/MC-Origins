package com.mojang.blaze3d.pipeline;

import com.mojang.blaze3d.systems.RenderSystem;

public class TextureTarget extends RenderTarget {
   public TextureTarget(int var1, int var2, boolean var3, boolean var4) {
      super(â˜ƒ);
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.resize(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
