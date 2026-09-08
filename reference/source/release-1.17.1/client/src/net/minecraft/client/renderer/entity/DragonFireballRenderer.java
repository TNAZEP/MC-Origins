package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.DragonFireball;

public class DragonFireballRenderer extends EntityRenderer<DragonFireball> {
   private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png");
   private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(TEXTURE_LOCATION);

   public DragonFireballRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
   }

   protected int getBlockLightLevel(DragonFireball var1, BlockPos var2) {
      return 15;
   }

   public void render(DragonFireball var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      â˜ƒ.pushPose();
      â˜ƒ.scale(2.0F, 2.0F, 2.0F);
      â˜ƒ.mulPose(this.entityRenderDispatcher.cameraOrientation());
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F));
      PoseStack.Pose â˜ƒ = â˜ƒ.last();
      Matrix4f â˜ƒx = â˜ƒ.pose();
      Matrix3f â˜ƒxx = â˜ƒ.normal();
      VertexConsumer â˜ƒxxx = â˜ƒ.getBuffer(RENDER_TYPE);
      vertex(â˜ƒxxx, â˜ƒx, â˜ƒxx, â˜ƒ, 0.0F, 0, 0, 1);
      vertex(â˜ƒxxx, â˜ƒx, â˜ƒxx, â˜ƒ, 1.0F, 0, 1, 1);
      vertex(â˜ƒxxx, â˜ƒx, â˜ƒxx, â˜ƒ, 1.0F, 1, 1, 0);
      vertex(â˜ƒxxx, â˜ƒx, â˜ƒxx, â˜ƒ, 0.0F, 1, 0, 0);
      â˜ƒ.popPose();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static void vertex(VertexConsumer var0, Matrix4f var1, Matrix3f var2, int var3, float var4, int var5, int var6, int var7) {
      â˜ƒ.vertex(â˜ƒ, â˜ƒ - 0.5F, (float)â˜ƒ - 0.25F, 0.0F)
         .color(255, 255, 255, 255)
         .uv((float)â˜ƒ, (float)â˜ƒ)
         .overlayCoords(OverlayTexture.NO_OVERLAY)
         .uv2(â˜ƒ)
         .normal(â˜ƒ, 0.0F, 1.0F, 0.0F)
         .endVertex();
   }

   public ResourceLocation getTextureLocation(DragonFireball var1) {
      return TEXTURE_LOCATION;
   }
}
