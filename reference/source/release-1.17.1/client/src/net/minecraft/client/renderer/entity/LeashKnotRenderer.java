package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.LeashKnotModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;

public class LeashKnotRenderer extends EntityRenderer<LeashFenceKnotEntity> {
   private static final ResourceLocation KNOT_LOCATION = new ResourceLocation("textures/entity/lead_knot.png");
   private final LeashKnotModel<LeashFenceKnotEntity> model;

   public LeashKnotRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
      this.model = new LeashKnotModel<>(â˜ƒ.bakeLayer(ModelLayers.LEASH_KNOT));
   }

   public void render(LeashFenceKnotEntity var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      â˜ƒ.pushPose();
      â˜ƒ.scale(-1.0F, -1.0F, 1.0F);
      this.model.setupAnim(â˜ƒ, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      VertexConsumer â˜ƒ = â˜ƒ.getBuffer(this.model.renderType(KNOT_LOCATION));
      this.model.renderToBuffer(â˜ƒ, â˜ƒ, â˜ƒ, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
      â˜ƒ.popPose();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public ResourceLocation getTextureLocation(LeashFenceKnotEntity var1) {
      return KNOT_LOCATION;
   }
}
