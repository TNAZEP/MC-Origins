package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class BeeStingerLayer<T extends LivingEntity, M extends PlayerModel<T>> extends StuckInBodyLayer<T, M> {
   private static final ResourceLocation BEE_STINGER_LOCATION = new ResourceLocation("textures/entity/bee/bee_stinger.png");

   public BeeStingerLayer(LivingEntityRenderer<T, M> var1) {
      super(â˜ƒ);
   }

   @Override
   protected int numStuck(T var1) {
      return â˜ƒ.getStingerCount();
   }

   @Override
   protected void renderStuckItem(PoseStack var1, MultiBufferSource var2, int var3, Entity var4, float var5, float var6, float var7, float var8) {
      float â˜ƒ = Mth.sqrt(â˜ƒ * â˜ƒ + â˜ƒ * â˜ƒ);
      float â˜ƒx = (float)(Math.atan2((double)â˜ƒ, (double)â˜ƒ) * 180.0F / (float)Math.PI);
      float â˜ƒxx = (float)(Math.atan2((double)â˜ƒ, (double)â˜ƒ) * 180.0F / (float)Math.PI);
      â˜ƒ.translate(0.0, 0.0, 0.0);
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒx - 90.0F));
      â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(â˜ƒxx));
      float â˜ƒxxx = 0.0F;
      float â˜ƒxxxx = 0.125F;
      float â˜ƒxxxxx = 0.0F;
      float â˜ƒxxxxxx = 0.0625F;
      float â˜ƒxxxxxxx = 0.03125F;
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(45.0F));
      â˜ƒ.scale(0.03125F, 0.03125F, 0.03125F);
      â˜ƒ.translate(2.5, 0.0, 0.0);
      VertexConsumer â˜ƒxxxxxxxx = â˜ƒ.getBuffer(RenderType.entityCutoutNoCull(BEE_STINGER_LOCATION));

      for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < 4; ++â˜ƒxxxxxxxxx) {
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(90.0F));
         PoseStack.Pose â˜ƒxxxxxxxxxx = â˜ƒ.last();
         Matrix4f â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxxxx.pose();
         Matrix3f â˜ƒxxxxxxxxxxxx = â˜ƒxxxxxxxxxx.normal();
         vertex(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, -4.5F, -1, 0.0F, 0.0F, â˜ƒ);
         vertex(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, 4.5F, -1, 0.125F, 0.0F, â˜ƒ);
         vertex(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, 4.5F, 1, 0.125F, 0.0625F, â˜ƒ);
         vertex(â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxxx, -4.5F, 1, 0.0F, 0.0625F, â˜ƒ);
      }
   }

   private static void vertex(VertexConsumer var0, Matrix4f var1, Matrix3f var2, float var3, int var4, float var5, float var6, int var7) {
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, (float)â˜ƒ, 0.0F)
         .color(255, 255, 255, 255)
         .uv(â˜ƒ, â˜ƒ)
         .overlayCoords(OverlayTexture.NO_OVERLAY)
         .uv2(â˜ƒ)
         .normal(â˜ƒ, 0.0F, 1.0F, 0.0F)
         .endVertex();
   }
}
