package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.GuardianModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GuardianRenderer extends MobRenderer<Guardian, GuardianModel> {
   private static final ResourceLocation GUARDIAN_LOCATION = new ResourceLocation("textures/entity/guardian.png");
   private static final ResourceLocation GUARDIAN_BEAM_LOCATION = new ResourceLocation("textures/entity/guardian_beam.png");
   private static final RenderType BEAM_RENDER_TYPE = RenderType.entityCutoutNoCull(GUARDIAN_BEAM_LOCATION);

   public GuardianRenderer(EntityRendererProvider.Context var1) {
      this(â˜ƒ, 0.5F, ModelLayers.GUARDIAN);
   }

   protected GuardianRenderer(EntityRendererProvider.Context var1, float var2, ModelLayerLocation var3) {
      super(â˜ƒ, new GuardianModel(â˜ƒ.bakeLayer(â˜ƒ)), â˜ƒ);
   }

   public boolean shouldRender(Guardian var1, Frustum var2, double var3, double var5, double var7) {
      if (super.shouldRender(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
         return true;
      } else {
         if (â˜ƒ.hasActiveAttackTarget()) {
            LivingEntity â˜ƒ = â˜ƒ.getActiveAttackTarget();
            if (â˜ƒ != null) {
               Vec3 â˜ƒx = this.getPosition(â˜ƒ, (double)â˜ƒ.getBbHeight() * 0.5, 1.0F);
               Vec3 â˜ƒxx = this.getPosition(â˜ƒ, (double)â˜ƒ.getEyeHeight(), 1.0F);
               return â˜ƒ.isVisible(new AABB(â˜ƒxx.x, â˜ƒxx.y, â˜ƒxx.z, â˜ƒx.x, â˜ƒx.y, â˜ƒx.z));
            }
         }

         return false;
      }
   }

   private Vec3 getPosition(LivingEntity var1, double var2, float var4) {
      double â˜ƒ = Mth.lerp((double)â˜ƒ, â˜ƒ.xOld, â˜ƒ.getX());
      double â˜ƒx = Mth.lerp((double)â˜ƒ, â˜ƒ.yOld, â˜ƒ.getY()) + â˜ƒ;
      double â˜ƒxx = Mth.lerp((double)â˜ƒ, â˜ƒ.zOld, â˜ƒ.getZ());
      return new Vec3(â˜ƒ, â˜ƒx, â˜ƒxx);
   }

   public void render(Guardian var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      LivingEntity â˜ƒ = â˜ƒ.getActiveAttackTarget();
      if (â˜ƒ != null) {
         float â˜ƒx = â˜ƒ.getAttackAnimationScale(â˜ƒ);
         float â˜ƒxx = (float)â˜ƒ.level.getGameTime() + â˜ƒ;
         float â˜ƒxxx = â˜ƒxx * 0.5F % 1.0F;
         float â˜ƒxxxx = â˜ƒ.getEyeHeight();
         â˜ƒ.pushPose();
         â˜ƒ.translate(0.0, (double)â˜ƒxxxx, 0.0);
         Vec3 â˜ƒxxxxx = this.getPosition(â˜ƒ, (double)â˜ƒ.getBbHeight() * 0.5, â˜ƒ);
         Vec3 â˜ƒxxxxxx = this.getPosition(â˜ƒ, (double)â˜ƒxxxx, â˜ƒ);
         Vec3 â˜ƒxxxxxxx = â˜ƒxxxxx.subtract(â˜ƒxxxxxx);
         float â˜ƒxxxxxxxx = (float)(â˜ƒxxxxxxx.length() + 1.0);
         â˜ƒxxxxxxx = â˜ƒxxxxxxx.normalize();
         float â˜ƒxxxxxxxxx = (float)Math.acos(â˜ƒxxxxxxx.y);
         float â˜ƒxxxxxxxxxx = (float)Math.atan2(â˜ƒxxxxxxx.z, â˜ƒxxxxxxx.x);
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(((float) (Math.PI / 2) - â˜ƒxxxxxxxxxx) * (180.0F / (float)Math.PI)));
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒxxxxxxxxx * (180.0F / (float)Math.PI)));
         int â˜ƒxxxxxxxxxxx = 1;
         float â˜ƒxxxxxxxxxxxx = â˜ƒxx * 0.05F * -1.5F;
         float â˜ƒxxxxxxxxxxxxx = â˜ƒx * â˜ƒx;
         int â˜ƒxxxxxxxxxxxxxx = 64 + (int)(â˜ƒxxxxxxxxxxxxx * 191.0F);
         int â˜ƒxxxxxxxxxxxxxxx = 32 + (int)(â˜ƒxxxxxxxxxxxxx * 191.0F);
         int â˜ƒxxxxxxxxxxxxxxxx = 128 - (int)(â˜ƒxxxxxxxxxxxxx * 64.0F);
         float â˜ƒxxxxxxxxxxxxxxxxx = 0.2F;
         float â˜ƒxxxxxxxxxxxxxxxxxx = 0.282F;
         float â˜ƒxxxxxxxxxxxxxxxxxxx = Mth.cos(â˜ƒxxxxxxxxxxxx + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxx = Mth.sin(â˜ƒxxxxxxxxxxxx + (float) (Math.PI * 3.0 / 4.0)) * 0.282F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxx = Mth.cos(â˜ƒxxxxxxxxxxxx + (float) (Math.PI / 4)) * 0.282F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxx = Mth.sin(â˜ƒxxxxxxxxxxxx + (float) (Math.PI / 4)) * 0.282F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = Mth.cos(â˜ƒxxxxxxxxxxxx + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = Mth.sin(â˜ƒxxxxxxxxxxxx + ((float) Math.PI * 5.0F / 4.0F)) * 0.282F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.cos(â˜ƒxxxxxxxxxxxx + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.sin(â˜ƒxxxxxxxxxxxx + ((float) Math.PI * 7.0F / 4.0F)) * 0.282F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.cos(â˜ƒxxxxxxxxxxxx + (float) Math.PI) * 0.2F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.sin(â˜ƒxxxxxxxxxxxx + (float) Math.PI) * 0.2F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.cos(â˜ƒxxxxxxxxxxxx + 0.0F) * 0.2F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.sin(â˜ƒxxxxxxxxxxxx + 0.0F) * 0.2F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.cos(â˜ƒxxxxxxxxxxxx + (float) (Math.PI / 2)) * 0.2F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.sin(â˜ƒxxxxxxxxxxxx + (float) (Math.PI / 2)) * 0.2F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.cos(â˜ƒxxxxxxxxxxxx + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Mth.sin(â˜ƒxxxxxxxxxxxx + (float) (Math.PI * 3.0 / 2.0)) * 0.2F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.4999F;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = -1.0F + â˜ƒxxx;
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxx * 2.5F + â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
         VertexConsumer â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.getBuffer(BEAM_RENDER_TYPE);
         PoseStack.Pose â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒ.last();
         Matrix4f â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.pose();
         Matrix3f â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.normal();
         vertex(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            0.4999F,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         vertex(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            0.0F,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            0.4999F,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         vertex(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            0.0F,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            0.0F,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         vertex(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            0.0F,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         vertex(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            0.4999F,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         vertex(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            0.0F,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            0.4999F,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         vertex(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            0.0F,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            0.0F,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         vertex(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            0.0F,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         float â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0F;
         if (â˜ƒ.tickCount % 2 == 0) {
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.5F;
         }

         vertex(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            0.5F,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F
         );
         vertex(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            1.0F,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F
         );
         vertex(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            1.0F,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         vertex(
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxx,
            â˜ƒxxxxxxxxxxxxxxxx,
            0.5F,
            â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
         â˜ƒ.popPose();
      }
   }

   private static void vertex(
      VertexConsumer var0, Matrix4f var1, Matrix3f var2, float var3, float var4, float var5, int var6, int var7, int var8, float var9, float var10
   ) {
      â˜ƒ.vertex(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)
         .color(â˜ƒ, â˜ƒ, â˜ƒ, 255)
         .uv(â˜ƒ, â˜ƒ)
         .overlayCoords(OverlayTexture.NO_OVERLAY)
         .uv2(15728880)
         .normal(â˜ƒ, 0.0F, 1.0F, 0.0F)
         .endVertex();
   }

   public ResourceLocation getTextureLocation(Guardian var1) {
      return GUARDIAN_LOCATION;
   }
}
