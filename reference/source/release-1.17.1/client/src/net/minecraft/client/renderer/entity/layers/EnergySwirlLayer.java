package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PowerableMob;

public abstract class EnergySwirlLayer<T extends Entity & PowerableMob, M extends EntityModel<T>> extends RenderLayer<T, M> {
   public EnergySwirlLayer(RenderLayerParent<T, M> var1) {
      super(â˜ƒ);
   }

   @Override
   public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      if (â˜ƒ.isPowered()) {
         float â˜ƒ = (float)â˜ƒ.tickCount + â˜ƒ;
         EntityModel<T> â˜ƒx = this.model();
         â˜ƒx.prepareMobModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.getParentModel().copyPropertiesTo(â˜ƒx);
         VertexConsumer â˜ƒxx = â˜ƒ.getBuffer(RenderType.energySwirl(this.getTextureLocation(), this.xOffset(â˜ƒ) % 1.0F, â˜ƒ * 0.01F % 1.0F));
         â˜ƒx.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒx.renderToBuffer(â˜ƒ, â˜ƒxx, â˜ƒ, OverlayTexture.NO_OVERLAY, 0.5F, 0.5F, 0.5F, 1.0F);
      }
   }

   protected abstract float xOffset(float var1);

   protected abstract ResourceLocation getTextureLocation();

   protected abstract EntityModel<T> model();
}
