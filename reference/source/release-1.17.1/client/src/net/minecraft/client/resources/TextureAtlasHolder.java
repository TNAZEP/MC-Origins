package net.minecraft.client.resources;

import java.util.stream.Stream;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public abstract class TextureAtlasHolder extends SimplePreparableReloadListener<TextureAtlas.Preparations> implements AutoCloseable {
   private final TextureAtlas textureAtlas;
   private final String prefix;

   public TextureAtlasHolder(TextureManager var1, ResourceLocation var2, String var3) {
      this.prefix = â˜ƒ;
      this.textureAtlas = new TextureAtlas(â˜ƒ);
      â˜ƒ.register(this.textureAtlas.location(), this.textureAtlas);
   }

   protected abstract Stream<ResourceLocation> getResourcesToLoad();

   protected TextureAtlasSprite getSprite(ResourceLocation var1) {
      return this.textureAtlas.getSprite(this.resolveLocation(â˜ƒ));
   }

   private ResourceLocation resolveLocation(ResourceLocation var1) {
      return new ResourceLocation(â˜ƒ.getNamespace(), this.prefix + "/" + â˜ƒ.getPath());
   }

   protected TextureAtlas.Preparations prepare(ResourceManager var1, ProfilerFiller var2) {
      â˜ƒ.startTick();
      â˜ƒ.push("stitching");
      TextureAtlas.Preparations â˜ƒ = this.textureAtlas.prepareToStitch(â˜ƒ, this.getResourcesToLoad().map(this::resolveLocation), â˜ƒ, 0);
      â˜ƒ.pop();
      â˜ƒ.endTick();
      return â˜ƒ;
   }

   protected void apply(TextureAtlas.Preparations var1, ResourceManager var2, ProfilerFiller var3) {
      â˜ƒ.startTick();
      â˜ƒ.push("upload");
      this.textureAtlas.reload(â˜ƒ);
      â˜ƒ.pop();
      â˜ƒ.endTick();
   }

   public void close() {
      this.textureAtlas.clearTextureData();
   }
}
