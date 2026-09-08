package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;

public class SlimeOuterLayer<T extends LivingEntity> extends RenderLayer<T, SlimeModel<T>> {
   private final EntityModel<T> model;

   public SlimeOuterLayer(RenderLayerParent<T, SlimeModel<T>> var1, EntityModelSet var2) {
      super(â˜ƒ);
      this.model = new SlimeModel<>(â˜ƒ.bakeLayer(ModelLayers.SLIME_OUTER));
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      Minecraft â˜ƒ = Minecraft.getInstance();
      boolean â˜ƒx = â˜ƒ.shouldEntityAppearGlowing(â˜ƒ) && â˜ƒ.isInvisible();
      if (!â˜ƒ.isInvisible() || â˜ƒx) {
         VertexConsumer â˜ƒxx;
         if (â˜ƒx) {
            â˜ƒxx = â˜ƒ.getBuffer(RenderType.outline(this.getTextureLocation(â˜ƒ)));
         } else {
            â˜ƒxx = â˜ƒ.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(â˜ƒ)));
         }

         this.getParentModel().copyPropertiesTo(this.model);
         this.model.prepareMobModel(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.model.setupAnim(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         this.model.renderToBuffer(â˜ƒ, â˜ƒxx, â˜ƒ, LivingEntityRenderer.getOverlayCoords(â˜ƒ, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
      }
   }
}
