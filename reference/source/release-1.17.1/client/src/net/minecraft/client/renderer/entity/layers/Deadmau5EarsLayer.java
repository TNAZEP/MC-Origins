package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.util.Mth;

public class Deadmau5EarsLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   public Deadmau5EarsLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> var1) {
      super(â˜ƒ);
   }

   public void render(
      PoseStack var1, MultiBufferSource var2, int var3, AbstractClientPlayer var4, float var5, float var6, float var7, float var8, float var9, float var10
   ) {
      if ("deadmau5".equals(â˜ƒ.getName().getString()) && â˜ƒ.isSkinLoaded() && !â˜ƒ.isInvisible()) {
         VertexConsumer â˜ƒ = â˜ƒ.getBuffer(RenderType.entitySolid(â˜ƒ.getSkinTextureLocation()));
         int â˜ƒx = LivingEntityRenderer.getOverlayCoords(â˜ƒ, 0.0F);

         for(int â˜ƒxx = 0; â˜ƒxx < 2; ++â˜ƒxx) {
            float â˜ƒxxx = Mth.lerp(â˜ƒ, â˜ƒ.yRotO, â˜ƒ.getYRot()) - Mth.lerp(â˜ƒ, â˜ƒ.yBodyRotO, â˜ƒ.yBodyRot);
            float â˜ƒxxxx = Mth.lerp(â˜ƒ, â˜ƒ.xRotO, â˜ƒ.getXRot());
            â˜ƒ.pushPose();
            â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒxxx));
            â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒxxxx));
            â˜ƒ.translate((double)(0.375F * (float)(â˜ƒxx * 2 - 1)), 0.0, 0.0);
            â˜ƒ.translate(0.0, -0.375, 0.0);
            â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(-â˜ƒxxxx));
            â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(-â˜ƒxxx));
            float â˜ƒxxxxx = 1.3333334F;
            â˜ƒ.scale(1.3333334F, 1.3333334F, 1.3333334F);
            this.getParentModel().renderEars(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx);
            â˜ƒ.popPose();
         }
      }
   }
}
