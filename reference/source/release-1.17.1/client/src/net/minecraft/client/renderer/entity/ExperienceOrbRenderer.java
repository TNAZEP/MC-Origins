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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ExperienceOrb;

public class ExperienceOrbRenderer extends EntityRenderer<ExperienceOrb> {
   private static final ResourceLocation EXPERIENCE_ORB_LOCATION = new ResourceLocation("textures/entity/experience_orb.png");
   private static final RenderType RENDER_TYPE = RenderType.itemEntityTranslucentCull(EXPERIENCE_ORB_LOCATION);

   public ExperienceOrbRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
      this.shadowRadius = 0.15F;
      this.shadowStrength = 0.75F;
   }

   protected int getBlockLightLevel(ExperienceOrb var1, BlockPos var2) {
      return Mth.clamp(super.getBlockLightLevel(â˜ƒ, â˜ƒ) + 7, 0, 15);
   }

   public void render(ExperienceOrb var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      â˜ƒ.pushPose();
      int â˜ƒ = â˜ƒ.getIcon();
      float â˜ƒx = (float)(â˜ƒ % 4 * 16 + 0) / 64.0F;
      float â˜ƒxx = (float)(â˜ƒ % 4 * 16 + 16) / 64.0F;
      float â˜ƒxxx = (float)(â˜ƒ / 4 * 16 + 0) / 64.0F;
      float â˜ƒxxxx = (float)(â˜ƒ / 4 * 16 + 16) / 64.0F;
      float â˜ƒxxxxx = 1.0F;
      float â˜ƒxxxxxx = 0.5F;
      float â˜ƒxxxxxxx = 0.25F;
      float â˜ƒxxxxxxxx = 255.0F;
      float â˜ƒxxxxxxxxx = ((float)â˜ƒ.tickCount + â˜ƒ) / 2.0F;
      int â˜ƒxxxxxxxxxx = (int)((Mth.sin(â˜ƒxxxxxxxxx + 0.0F) + 1.0F) * 0.5F * 255.0F);
      int â˜ƒxxxxxxxxxxx = 255;
      int â˜ƒxxxxxxxxxxxx = (int)((Mth.sin(â˜ƒxxxxxxxxx + (float) (Math.PI * 4.0 / 3.0)) + 1.0F) * 0.1F * 255.0F);
      â˜ƒ.translate(0.0, 0.1F, 0.0);
      â˜ƒ.mulPose(this.entityRenderDispatcher.cameraOrientation());
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F));
      float â˜ƒxxxxxxxxxxxxx = 0.3F;
      â˜ƒ.scale(0.3F, 0.3F, 0.3F);
      VertexConsumer â˜ƒxxxxxxxxxxxxxx = â˜ƒ.getBuffer(RENDER_TYPE);
      PoseStack.Pose â˜ƒxxxxxxxxxxxxxxx = â˜ƒ.last();
      Matrix4f â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx.pose();
      Matrix3f â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxx.normal();
      vertex(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, -0.5F, -0.25F, â˜ƒxxxxxxxxxx, 255, â˜ƒxxxxxxxxxxxx, â˜ƒx, â˜ƒxxxx, â˜ƒ);
      vertex(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, 0.5F, -0.25F, â˜ƒxxxxxxxxxx, 255, â˜ƒxxxxxxxxxxxx, â˜ƒxx, â˜ƒxxxx, â˜ƒ);
      vertex(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, 0.5F, 0.75F, â˜ƒxxxxxxxxxx, 255, â˜ƒxxxxxxxxxxxx, â˜ƒxx, â˜ƒxxx, â˜ƒ);
      vertex(â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, -0.5F, 0.75F, â˜ƒxxxxxxxxxx, 255, â˜ƒxxxxxxxxxxxx, â˜ƒx, â˜ƒxxx, â˜ƒ);
      â˜ƒ.popPose();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static void vertex(
      VertexConsumer var0, Matrix4f var1, Matrix3f var2, float var3, float var4, int var5, int var6, int var7, float var8, float var9, int var10
   ) {
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ, 0.0F)
         .color(â˜ƒ, â˜ƒ, â˜ƒ, 128)
         .uv(â˜ƒ, â˜ƒ)
         .overlayCoords(OverlayTexture.NO_OVERLAY)
         .uv2(â˜ƒ)
         .normal(â˜ƒ, 0.0F, 1.0F, 0.0F)
         .endVertex();
   }

   public ResourceLocation getTextureLocation(ExperienceOrb var1) {
      return EXPERIENCE_ORB_LOCATION;
   }
}
