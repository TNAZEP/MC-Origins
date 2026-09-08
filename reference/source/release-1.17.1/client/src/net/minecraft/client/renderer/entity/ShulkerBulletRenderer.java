package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.ShulkerBulletModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.ShulkerBullet;

public class ShulkerBulletRenderer extends EntityRenderer<ShulkerBullet> {
   private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/shulker/spark.png");
   private static final RenderType RENDER_TYPE = RenderType.entityTranslucent(TEXTURE_LOCATION);
   private final ShulkerBulletModel<ShulkerBullet> model;

   public ShulkerBulletRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
      this.model = new ShulkerBulletModel<>(â˜ƒ.bakeLayer(ModelLayers.SHULKER_BULLET));
   }

   protected int getBlockLightLevel(ShulkerBullet var1, BlockPos var2) {
      return 15;
   }

   public void render(ShulkerBullet var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      â˜ƒ.pushPose();
      float â˜ƒ = Mth.rotlerp(â˜ƒ.yRotO, â˜ƒ.getYRot(), â˜ƒ);
      float â˜ƒx = Mth.lerp(â˜ƒ, â˜ƒ.xRotO, â˜ƒ.getXRot());
      float â˜ƒxx = (float)â˜ƒ.tickCount + â˜ƒ;
      â˜ƒ.translate(0.0, 0.15F, 0.0);
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(Mth.sin(â˜ƒxx * 0.1F) * 180.0F));
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(Mth.cos(â˜ƒxx * 0.1F) * 180.0F));
      â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(Mth.sin(â˜ƒxx * 0.15F) * 360.0F));
      â˜ƒ.scale(-0.5F, -0.5F, 0.5F);
      this.model.setupAnim(â˜ƒ, 0.0F, 0.0F, 0.0F, â˜ƒ, â˜ƒx);
      VertexConsumer â˜ƒxxx = â˜ƒ.getBuffer(this.model.renderType(TEXTURE_LOCATION));
      this.model.renderToBuffer(â˜ƒ, â˜ƒxxx, â˜ƒ, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
      â˜ƒ.scale(1.5F, 1.5F, 1.5F);
      VertexConsumer â˜ƒxxxx = â˜ƒ.getBuffer(RENDER_TYPE);
      this.model.renderToBuffer(â˜ƒ, â˜ƒxxxx, â˜ƒ, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.15F);
      â˜ƒ.popPose();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public ResourceLocation getTextureLocation(ShulkerBullet var1) {
      return TEXTURE_LOCATION;
   }
}
