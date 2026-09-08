package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.EvokerFangsModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.EvokerFangs;

public class EvokerFangsRenderer extends EntityRenderer<EvokerFangs> {
   private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/illager/evoker_fangs.png");
   private final EvokerFangsModel<EvokerFangs> model;

   public EvokerFangsRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
      this.model = new EvokerFangsModel<>(â˜ƒ.bakeLayer(ModelLayers.EVOKER_FANGS));
   }

   public void render(EvokerFangs var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      float â˜ƒ = â˜ƒ.getAnimationProgress(â˜ƒ);
      if (â˜ƒ != 0.0F) {
         float â˜ƒx = 2.0F;
         if (â˜ƒ > 0.9F) {
            â˜ƒx = (float)((double)â˜ƒx * ((1.0 - (double)â˜ƒ) / 0.1F));
         }

         â˜ƒ.pushPose();
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(90.0F - â˜ƒ.getYRot()));
         â˜ƒ.scale(-â˜ƒx, -â˜ƒx, â˜ƒx);
         float â˜ƒx = 0.03125F;
         â˜ƒ.translate(0.0, -0.626F, 0.0);
         â˜ƒ.scale(0.5F, 0.5F, 0.5F);
         this.model.setupAnim(â˜ƒ, â˜ƒ, 0.0F, 0.0F, â˜ƒ.getYRot(), â˜ƒ.getXRot());
         VertexConsumer â˜ƒxx = â˜ƒ.getBuffer(this.model.renderType(TEXTURE_LOCATION));
         this.model.renderToBuffer(â˜ƒ, â˜ƒxx, â˜ƒ, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
         â˜ƒ.popPose();
         super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public ResourceLocation getTextureLocation(EvokerFangs var1) {
      return TEXTURE_LOCATION;
   }
}
