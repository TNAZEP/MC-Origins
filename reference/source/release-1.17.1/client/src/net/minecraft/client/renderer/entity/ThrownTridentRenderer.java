package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.ThrownTrident;

public class ThrownTridentRenderer extends EntityRenderer<ThrownTrident> {
   public static final ResourceLocation TRIDENT_LOCATION = new ResourceLocation("textures/entity/trident.png");
   private final TridentModel model;

   public ThrownTridentRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
      this.model = new TridentModel(â˜ƒ.bakeLayer(ModelLayers.TRIDENT));
   }

   public void render(ThrownTrident var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      â˜ƒ.pushPose();
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(â˜ƒ, â˜ƒ.yRotO, â˜ƒ.getYRot()) - 90.0F));
      â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(â˜ƒ, â˜ƒ.xRotO, â˜ƒ.getXRot()) + 90.0F));
      VertexConsumer â˜ƒ = ItemRenderer.getFoilBufferDirect(â˜ƒ, this.model.renderType(this.getTextureLocation(â˜ƒ)), false, â˜ƒ.isFoil());
      this.model.renderToBuffer(â˜ƒ, â˜ƒ, â˜ƒ, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
      â˜ƒ.popPose();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public ResourceLocation getTextureLocation(ThrownTrident var1) {
      return TRIDENT_LOCATION;
   }
}
