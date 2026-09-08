package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.AbstractArrow;

public abstract class ArrowRenderer<T extends AbstractArrow> extends EntityRenderer<T> {
   public ArrowRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
   }

   public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      â˜ƒ.pushPose();
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(â˜ƒ, â˜ƒ.yRotO, â˜ƒ.getYRot()) - 90.0F));
      â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(â˜ƒ, â˜ƒ.xRotO, â˜ƒ.getXRot())));
      int â˜ƒ = 0;
      float â˜ƒx = 0.0F;
      float â˜ƒxx = 0.5F;
      float â˜ƒxxx = 0.0F;
      float â˜ƒxxxx = 0.15625F;
      float â˜ƒxxxxx = 0.0F;
      float â˜ƒxxxxxx = 0.15625F;
      float â˜ƒxxxxxxx = 0.15625F;
      float â˜ƒxxxxxxxx = 0.3125F;
      float â˜ƒxxxxxxxxx = 0.05625F;
      float â˜ƒxxxxxxxxxx = (float)â˜ƒ.shakeTime - â˜ƒ;
      if (â˜ƒxxxxxxxxxx > 0.0F) {
         float â˜ƒxxxxxxxxxxx = -Mth.sin(â˜ƒxxxxxxxxxx * 3.0F) * â˜ƒxxxxxxxxxx;
         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(â˜ƒxxxxxxxxxxx));
      }

      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(45.0F));
      â˜ƒ.scale(0.05625F, 0.05625F, 0.05625F);
      â˜ƒ.translate(-4.0, 0.0, 0.0);
      VertexConsumer â˜ƒ = â˜ƒ.getBuffer(RenderType.entityCutout(this.getTextureLocation(â˜ƒ)));
      PoseStack.Pose â˜ƒx = â˜ƒ.last();
      Matrix4f â˜ƒxx = â˜ƒx.pose();
      Matrix3f â˜ƒxxx = â˜ƒx.normal();
      this.vertex(â˜ƒxx, â˜ƒxxx, â˜ƒ, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, â˜ƒ);
      this.vertex(â˜ƒxx, â˜ƒxxx, â˜ƒ, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, â˜ƒ);
      this.vertex(â˜ƒxx, â˜ƒxxx, â˜ƒ, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, â˜ƒ);
      this.vertex(â˜ƒxx, â˜ƒxxx, â˜ƒ, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, â˜ƒ);
      this.vertex(â˜ƒxx, â˜ƒxxx, â˜ƒ, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, â˜ƒ);
      this.vertex(â˜ƒxx, â˜ƒxxx, â˜ƒ, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, â˜ƒ);
      this.vertex(â˜ƒxx, â˜ƒxxx, â˜ƒ, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, â˜ƒ);
      this.vertex(â˜ƒxx, â˜ƒxxx, â˜ƒ, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, â˜ƒ);

      for(int â˜ƒxxxx = 0; â˜ƒxxxx < 4; ++â˜ƒxxxx) {
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(90.0F));
         this.vertex(â˜ƒxx, â˜ƒxxx, â˜ƒ, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, â˜ƒ);
         this.vertex(â˜ƒxx, â˜ƒxxx, â˜ƒ, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, â˜ƒ);
         this.vertex(â˜ƒxx, â˜ƒxxx, â˜ƒ, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, â˜ƒ);
         this.vertex(â˜ƒxx, â˜ƒxxx, â˜ƒ, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, â˜ƒ);
      }

      â˜ƒ.popPose();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public void vertex(
      Matrix4f var1, Matrix3f var2, VertexConsumer var3, int var4, int var5, int var6, float var7, float var8, int var9, int var10, int var11, int var12
   ) {
      â˜ƒ.vertex(â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ)
         .color(255, 255, 255, 255)
         .uv(â˜ƒ, â˜ƒ)
         .overlayCoords(OverlayTexture.NO_OVERLAY)
         .uv2(â˜ƒ)
         .normal(â˜ƒ, (float)â˜ƒ, (float)â˜ƒ, (float)â˜ƒ)
         .endVertex();
   }
}
