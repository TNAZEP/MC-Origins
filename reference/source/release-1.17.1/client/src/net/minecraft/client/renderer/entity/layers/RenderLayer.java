package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public abstract class RenderLayer<T extends Entity, M extends EntityModel<T>> {
   private final RenderLayerParent<T, M> renderer;

   public RenderLayer(RenderLayerParent<T, M> var1) {
      this.renderer = â˜ƒ;
   }

   protected static <T extends LivingEntity> void coloredCutoutModelCopyLayerRender(
      EntityModel<T> var0,
      EntityModel<T> var1,
      ResourceLocation var2,
      PoseStack var3,
      MultiBufferSource var4,
      int var5,
      T var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12,
      float var13,
      float var14,
      float var15
   ) {
      if (!â˜ƒ.isInvisible()) {
         â˜ƒ.copyPropertiesTo(â˜ƒ);
         â˜ƒ.prepareMobModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         renderColoredCutoutModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   protected static <T extends LivingEntity> void renderColoredCutoutModel(
      EntityModel<T> var0, ResourceLocation var1, PoseStack var2, MultiBufferSource var3, int var4, T var5, float var6, float var7, float var8
   ) {
      VertexConsumer â˜ƒ = â˜ƒ.getBuffer(RenderType.entityCutoutNoCull(â˜ƒ));
      â˜ƒ.renderToBuffer(â˜ƒ, â˜ƒ, â˜ƒ, LivingEntityRenderer.getOverlayCoords(â˜ƒ, 0.0F), â˜ƒ, â˜ƒ, â˜ƒ, 1.0F);
   }

   public M getParentModel() {
      return this.renderer.getModel();
   }

   protected ResourceLocation getTextureLocation(T var1) {
      return this.renderer.getTextureLocation(â˜ƒ);
   }

   public abstract void render(
      PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10
   );
}
