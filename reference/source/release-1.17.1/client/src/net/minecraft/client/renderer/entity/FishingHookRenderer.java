package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class FishingHookRenderer extends EntityRenderer<FishingHook> {
   private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/fishing_hook.png");
   private static final RenderType RENDER_TYPE = RenderType.entityCutout(TEXTURE_LOCATION);
   private static final double VIEW_BOBBING_SCALE = 960.0;

   public FishingHookRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
   }

   public void render(FishingHook var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      Player â˜ƒ = â˜ƒ.getPlayerOwner();
      if (â˜ƒ != null) {
         â˜ƒ.pushPose();
         â˜ƒ.pushPose();
         â˜ƒ.scale(0.5F, 0.5F, 0.5F);
         â˜ƒ.mulPose(this.entityRenderDispatcher.cameraOrientation());
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F));
         PoseStack.Pose â˜ƒx = â˜ƒ.last();
         Matrix4f â˜ƒxx = â˜ƒx.pose();
         Matrix3f â˜ƒxxx = â˜ƒx.normal();
         VertexConsumer â˜ƒxxxx = â˜ƒ.getBuffer(RENDER_TYPE);
         vertex(â˜ƒxxxx, â˜ƒxx, â˜ƒxxx, â˜ƒ, 0.0F, 0, 0, 1);
         vertex(â˜ƒxxxx, â˜ƒxx, â˜ƒxxx, â˜ƒ, 1.0F, 0, 1, 1);
         vertex(â˜ƒxxxx, â˜ƒxx, â˜ƒxxx, â˜ƒ, 1.0F, 1, 1, 0);
         vertex(â˜ƒxxxx, â˜ƒxx, â˜ƒxxx, â˜ƒ, 0.0F, 1, 0, 0);
         â˜ƒ.popPose();
         int â˜ƒxxxxx = â˜ƒ.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
         ItemStack â˜ƒxxxxxx = â˜ƒ.getMainHandItem();
         if (!â˜ƒxxxxxx.is(Items.FISHING_ROD)) {
            â˜ƒxxxxx = -â˜ƒxxxxx;
         }

         float â˜ƒxxxxx = â˜ƒ.getAttackAnim(â˜ƒ);
         float â˜ƒxxxxxx = Mth.sin(Mth.sqrt(â˜ƒxxxxx) * (float) Math.PI);
         float â˜ƒxxxxxxx = Mth.lerp(â˜ƒ, â˜ƒ.yBodyRotO, â˜ƒ.yBodyRot) * (float) (Math.PI / 180.0);
         double â˜ƒxxxxxxxx = (double)Mth.sin(â˜ƒxxxxxxx);
         double â˜ƒxxxxxxxxx = (double)Mth.cos(â˜ƒxxxxxxx);
         double â˜ƒxxxxxxxxxx = (double)â˜ƒxxxxx * 0.35;
         double â˜ƒxxxxxxxxxxx = 0.8;
         double â˜ƒx;
         double â˜ƒxx;
         double â˜ƒxxx;
         float â˜ƒxxxx;
         if ((this.entityRenderDispatcher.options == null || this.entityRenderDispatcher.options.getCameraType().isFirstPerson())
            && â˜ƒ == Minecraft.getInstance().player) {
            double â˜ƒxxxxxxxxxxxx = 960.0 / this.entityRenderDispatcher.options.fov;
            Vec3 â˜ƒxxxxxxxxxxxxx = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane((float)â˜ƒxxxxx * 0.525F, -0.1F);
            â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx.scale(â˜ƒxxxxxxxxxxxx);
            â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx.yRot(â˜ƒxxxxxx * 0.5F);
            â˜ƒxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx.xRot(-â˜ƒxxxxxx * 0.7F);
            â˜ƒx = Mth.lerp((double)â˜ƒ, â˜ƒ.xo, â˜ƒ.getX()) + â˜ƒxxxxxxxxxxxxx.x;
            â˜ƒxx = Mth.lerp((double)â˜ƒ, â˜ƒ.yo, â˜ƒ.getY()) + â˜ƒxxxxxxxxxxxxx.y;
            â˜ƒxxx = Mth.lerp((double)â˜ƒ, â˜ƒ.zo, â˜ƒ.getZ()) + â˜ƒxxxxxxxxxxxxx.z;
            â˜ƒxxxx = â˜ƒ.getEyeHeight();
         } else {
            â˜ƒx = Mth.lerp((double)â˜ƒ, â˜ƒ.xo, â˜ƒ.getX()) - â˜ƒxxxxxxxxx * â˜ƒxxxxxxxxxx - â˜ƒxxxxxxxx * 0.8;
            â˜ƒxx = â˜ƒ.yo + (double)â˜ƒ.getEyeHeight() + (â˜ƒ.getY() - â˜ƒ.yo) * (double)â˜ƒ - 0.45;
            â˜ƒxxx = Mth.lerp((double)â˜ƒ, â˜ƒ.zo, â˜ƒ.getZ()) - â˜ƒxxxxxxxx * â˜ƒxxxxxxxxxx + â˜ƒxxxxxxxxx * 0.8;
            â˜ƒxxxx = â˜ƒ.isCrouching() ? -0.1875F : 0.0F;
         }

         double â˜ƒx = Mth.lerp((double)â˜ƒ, â˜ƒ.xo, â˜ƒ.getX());
         double â˜ƒxx = Mth.lerp((double)â˜ƒ, â˜ƒ.yo, â˜ƒ.getY()) + 0.25;
         double â˜ƒxxx = Mth.lerp((double)â˜ƒ, â˜ƒ.zo, â˜ƒ.getZ());
         float â˜ƒxxxx = (float)(â˜ƒx - â˜ƒx);
         float â˜ƒxxxxx = (float)(â˜ƒxx - â˜ƒxx) + â˜ƒxxxx;
         float â˜ƒxxxxxx = (float)(â˜ƒxxx - â˜ƒxxx);
         VertexConsumer â˜ƒxxxxxxx = â˜ƒ.getBuffer(RenderType.lineStrip());
         PoseStack.Pose â˜ƒxxxxxxxx = â˜ƒ.last();
         int â˜ƒxxxxxxxxx = 16;

         for(int â˜ƒxxxxxxxxxx = 0; â˜ƒxxxxxxxxxx <= 16; ++â˜ƒxxxxxxxxxx) {
            stringVertex(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx, fraction(â˜ƒxxxxxxxxxx, 16), fraction(â˜ƒxxxxxxxxxx + 1, 16));
         }

         â˜ƒ.popPose();
         super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private static float fraction(int var0, int var1) {
      return (float)â˜ƒ / (float)â˜ƒ;
   }

   private static void vertex(VertexConsumer var0, Matrix4f var1, Matrix3f var2, int var3, float var4, int var5, int var6, int var7) {
      â˜ƒ.vertex(â˜ƒ, â˜ƒ - 0.5F, (float)â˜ƒ - 0.5F, 0.0F)
         .color(255, 255, 255, 255)
         .uv((float)â˜ƒ, (float)â˜ƒ)
         .overlayCoords(OverlayTexture.NO_OVERLAY)
         .uv2(â˜ƒ)
         .normal(â˜ƒ, 0.0F, 1.0F, 0.0F)
         .endVertex();
   }

   private static void stringVertex(float var0, float var1, float var2, VertexConsumer var3, PoseStack.Pose var4, float var5, float var6) {
      float â˜ƒ = â˜ƒ * â˜ƒ;
      float â˜ƒx = â˜ƒ * (â˜ƒ * â˜ƒ + â˜ƒ) * 0.5F + 0.25F;
      float â˜ƒxx = â˜ƒ * â˜ƒ;
      float â˜ƒxxx = â˜ƒ * â˜ƒ - â˜ƒ;
      float â˜ƒxxxx = â˜ƒ * (â˜ƒ * â˜ƒ + â˜ƒ) * 0.5F + 0.25F - â˜ƒx;
      float â˜ƒxxxxx = â˜ƒ * â˜ƒ - â˜ƒxx;
      float â˜ƒxxxxxx = Mth.sqrt(â˜ƒxxx * â˜ƒxxx + â˜ƒxxxx * â˜ƒxxxx + â˜ƒxxxxx * â˜ƒxxxxx);
      â˜ƒxxx /= â˜ƒxxxxxx;
      â˜ƒxxxx /= â˜ƒxxxxxx;
      â˜ƒxxxxx /= â˜ƒxxxxxx;
      â˜ƒ.vertex(â˜ƒ.pose(), â˜ƒ, â˜ƒx, â˜ƒxx).color(0, 0, 0, 255).normal(â˜ƒ.normal(), â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx).endVertex();
   }

   public ResourceLocation getTextureLocation(FishingHook var1) {
      return TEXTURE_LOCATION;
   }
}
