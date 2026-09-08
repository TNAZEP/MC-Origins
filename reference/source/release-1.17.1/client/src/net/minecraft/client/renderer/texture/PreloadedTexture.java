package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public class PreloadedTexture extends SimpleTexture {
   @Nullable
   private CompletableFuture<SimpleTexture.TextureImage> future;

   public PreloadedTexture(ResourceManager var1, ResourceLocation var2, Executor var3) {
      super(â˜ƒ);
      this.future = CompletableFuture.supplyAsync(() -> SimpleTexture.TextureImage.load(â˜ƒ, â˜ƒ), â˜ƒ);
   }

   @Override
   protected SimpleTexture.TextureImage getTextureImage(ResourceManager var1) {
      if (this.future != null) {
         SimpleTexture.TextureImage â˜ƒ = (SimpleTexture.TextureImage)this.future.join();
         this.future = null;
         return â˜ƒ;
      } else {
         return SimpleTexture.TextureImage.load(â˜ƒ, this.location);
      }
   }

   public CompletableFuture<Void> getFuture() {
      return this.future == null ? CompletableFuture.completedFuture(null) : this.future.thenApply(var0 -> null);
   }

   @Override
   public void reset(TextureManager var1, ResourceManager var2, ResourceLocation var3, Executor var4) {
      this.future = CompletableFuture.supplyAsync(() -> SimpleTexture.TextureImage.load(â˜ƒ, this.location), Util.backgroundExecutor());
      this.future.thenRunAsync(() -> â˜ƒ.register(this.location, this), executor(â˜ƒ));
   }

   private static Executor executor(Executor var0) {
      return var1 -> â˜ƒ.execute(() -> RenderSystem.recordRenderCall(var1::run));
   }
}
