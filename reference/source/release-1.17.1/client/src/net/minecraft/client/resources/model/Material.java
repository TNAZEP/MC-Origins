package net.minecraft.client.resources.model;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;

public class Material {
   private final ResourceLocation atlasLocation;
   private final ResourceLocation texture;
   @Nullable
   private RenderType renderType;

   public Material(ResourceLocation var1, ResourceLocation var2) {
      this.atlasLocation = â˜ƒ;
      this.texture = â˜ƒ;
   }

   public ResourceLocation atlasLocation() {
      return this.atlasLocation;
   }

   public ResourceLocation texture() {
      return this.texture;
   }

   public TextureAtlasSprite sprite() {
      return (TextureAtlasSprite)Minecraft.getInstance().getTextureAtlas(this.atlasLocation()).apply(this.texture());
   }

   public RenderType renderType(Function<ResourceLocation, RenderType> var1) {
      if (this.renderType == null) {
         this.renderType = (RenderType)â˜ƒ.apply(this.atlasLocation);
      }

      return this.renderType;
   }

   public VertexConsumer buffer(MultiBufferSource var1, Function<ResourceLocation, RenderType> var2) {
      return this.sprite().wrap(â˜ƒ.getBuffer(this.renderType(â˜ƒ)));
   }

   public VertexConsumer buffer(MultiBufferSource var1, Function<ResourceLocation, RenderType> var2, boolean var3) {
      return this.sprite().wrap(ItemRenderer.getFoilBufferDirect(â˜ƒ, this.renderType(â˜ƒ), true, â˜ƒ));
   }

   public boolean equals(Object var1) {
      if (this == â˜ƒ) {
         return true;
      } else if (â˜ƒ != null && this.getClass() == â˜ƒ.getClass()) {
         Material â˜ƒ = (Material)â˜ƒ;
         return this.atlasLocation.equals(â˜ƒ.atlasLocation) && this.texture.equals(â˜ƒ.texture);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.atlasLocation, this.texture});
   }

   public String toString() {
      return "Material{atlasLocation=" + this.atlasLocation + ", texture=" + this.texture + "}";
   }
}
