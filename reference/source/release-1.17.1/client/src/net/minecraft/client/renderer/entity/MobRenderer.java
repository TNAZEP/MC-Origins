package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

public abstract class MobRenderer<T extends Mob, M extends EntityModel<T>> extends LivingEntityRenderer<T, M> {
   public static final int LEASH_RENDER_STEPS = 24;

   public MobRenderer(EntityRendererProvider.Context var1, M var2, float var3) {
      super(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   protected boolean shouldShowName(T var1) {
      return super.shouldShowName(â˜ƒ) && (â˜ƒ.shouldShowName() || â˜ƒ.hasCustomName() && â˜ƒ == this.entityRenderDispatcher.crosshairPickEntity);
   }

   public boolean shouldRender(T var1, Frustum var2, double var3, double var5, double var7) {
      if (super.shouldRender(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else {
         Entity â˜ƒ = â˜ƒ.getLeashHolder();
         return â˜ƒ != null ? â˜ƒ.isVisible(â˜ƒ.getBoundingBoxForCulling()) : false;
      }
   }

   public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      Entity â˜ƒ = â˜ƒ.getLeashHolder();
      if (â˜ƒ != null) {
         this.renderLeash(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private <E extends Entity> void renderLeash(T var1, float var2, PoseStack var3, MultiBufferSource var4, E var5) {
      â˜ƒ.pushPose();
      Vec3 â˜ƒ = â˜ƒ.getRopeHoldPosition(â˜ƒ);
      double â˜ƒx = (double)(Mth.lerp(â˜ƒ, â˜ƒ.yBodyRot, â˜ƒ.yBodyRotO) * (float) (Math.PI / 180.0)) + (Math.PI / 2);
      Vec3 â˜ƒxx = â˜ƒ.getLeashOffset();
      double â˜ƒxxx = Math.cos(â˜ƒx) * â˜ƒxx.z + Math.sin(â˜ƒx) * â˜ƒxx.x;
      double â˜ƒxxxx = Math.sin(â˜ƒx) * â˜ƒxx.z - Math.cos(â˜ƒx) * â˜ƒxx.x;
      double â˜ƒxxxxx = Mth.lerp((double)â˜ƒ, â˜ƒ.xo, â˜ƒ.getX()) + â˜ƒxxx;
      double â˜ƒxxxxxx = Mth.lerp((double)â˜ƒ, â˜ƒ.yo, â˜ƒ.getY()) + â˜ƒxx.y;
      double â˜ƒxxxxxxx = Mth.lerp((double)â˜ƒ, â˜ƒ.zo, â˜ƒ.getZ()) + â˜ƒxxxx;
      â˜ƒ.translate(â˜ƒxxx, â˜ƒxx.y, â˜ƒxxxx);
      float â˜ƒxxxxxxxx = (float)(â˜ƒ.x - â˜ƒxxxxx);
      float â˜ƒxxxxxxxxx = (float)(â˜ƒ.y - â˜ƒxxxxxx);
      float â˜ƒxxxxxxxxxx = (float)(â˜ƒ.z - â˜ƒxxxxxxx);
      float â˜ƒxxxxxxxxxxx = 0.025F;
      VertexConsumer â˜ƒxxxxxxxxxxxx = â˜ƒ.getBuffer(RenderType.leash());
      Matrix4f â˜ƒxxxxxxxxxxxxx = â˜ƒ.last().pose();
      float â˜ƒxxxxxxxxxxxxxx = Mth.fastInvSqrt(â˜ƒxxxxxxxx * â˜ƒxxxxxxxx + â˜ƒxxxxxxxxxx * â˜ƒxxxxxxxxxx) * 0.025F / 2.0F;
      float â˜ƒxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxx;
      float â˜ƒxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxx * â˜ƒxxxxxxxxxxxxxx;
      BlockPos â˜ƒxxxxxxxxxxxxxxxxx = new BlockPos(â˜ƒ.getEyePosition(â˜ƒ));
      BlockPos â˜ƒxxxxxxxxxxxxxxxxxx = new BlockPos(â˜ƒ.getEyePosition(â˜ƒ));
      int â˜ƒxxxxxxxxxxxxxxxxxxx = this.getBlockLightLevel(â˜ƒ, â˜ƒxxxxxxxxxxxxxxxxx);
      int â˜ƒxxxxxxxxxxxxxxxxxxxx = this.entityRenderDispatcher.getRenderer(â˜ƒ).getBlockLightLevel(â˜ƒ, â˜ƒxxxxxxxxxxxxxxxxxx);
      int â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.level.getBrightness(LightLayer.SKY, â˜ƒxxxxxxxxxxxxxxxxx);
      int â˜ƒxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.level.getBrightness(LightLayer.SKY, â˜ƒxxxxxxxxxxxxxxxxxx);

      for(int â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = 0; â˜ƒxxxxxxxxxxxxxxxxxxxxxxx <= 24; ++â˜ƒxxxxxxxxxxxxxxxxxxxxxxx) {
         addVertexPair(
            â˜ƒxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxx,
            â˜ƒxxxxxxxx,
            â˜ƒxxxxxxxxx,
            â˜ƒxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
            0.025F,
            0.025F,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxx,
            false
         );
      }

      for(int â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = 24; â˜ƒxxxxxxxxxxxxxxxxxxxxxxx >= 0; --â˜ƒxxxxxxxxxxxxxxxxxxxxxxx) {
         addVertexPair(
            â˜ƒxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxx,
            â˜ƒxxxxxxxx,
            â˜ƒxxxxxxxxx,
            â˜ƒxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
            0.025F,
            0.0F,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxx,
            true
         );
      }

      â˜ƒ.popPose();
   }

   private static void addVertexPair(
      VertexConsumer var0,
      Matrix4f var1,
      float var2,
      float var3,
      float var4,
      int var5,
      int var6,
      int var7,
      int var8,
      float var9,
      float var10,
      float var11,
      float var12,
      int var13,
      boolean var14
   ) {
      float â˜ƒ = (float)â˜ƒ / 24.0F;
      int â˜ƒx = (int)Mth.lerp(â˜ƒ, (float)â˜ƒ, (float)â˜ƒ);
      int â˜ƒxx = (int)Mth.lerp(â˜ƒ, (float)â˜ƒ, (float)â˜ƒ);
      int â˜ƒxxx = LightTexture.pack(â˜ƒx, â˜ƒxx);
      float â˜ƒxxxx = â˜ƒ % 2 == (â˜ƒ ? 1 : 0) ? 0.7F : 1.0F;
      float â˜ƒxxxxx = 0.5F * â˜ƒxxxx;
      float â˜ƒxxxxxx = 0.4F * â˜ƒxxxx;
      float â˜ƒxxxxxxx = 0.3F * â˜ƒxxxx;
      float â˜ƒxxxxxxxx = â˜ƒ * â˜ƒ;
      float â˜ƒxxxxxxxxx = â˜ƒ > 0.0F ? â˜ƒ * â˜ƒ * â˜ƒ : â˜ƒ - â˜ƒ * (1.0F - â˜ƒ) * (1.0F - â˜ƒ);
      float â˜ƒxxxxxxxxxx = â˜ƒ * â˜ƒ;
      â˜ƒ.vertex(â˜ƒ, â˜ƒxxxxxxxx - â˜ƒ, â˜ƒxxxxxxxxx + â˜ƒ, â˜ƒxxxxxxxxxx + â˜ƒ).color(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, 1.0F).uv2(â˜ƒxxx).endVertex();
      â˜ƒ.vertex(â˜ƒ, â˜ƒxxxxxxxx + â˜ƒ, â˜ƒxxxxxxxxx + â˜ƒ - â˜ƒ, â˜ƒxxxxxxxxxx - â˜ƒ).color(â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, 1.0F).uv2(â˜ƒxxx).endVertex();
   }
}
