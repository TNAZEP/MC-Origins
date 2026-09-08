package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MinecartRenderer<T extends AbstractMinecart> extends EntityRenderer<T> {
   private static final ResourceLocation MINECART_LOCATION = new ResourceLocation("textures/entity/minecart.png");
   protected final EntityModel<T> model;

   public MinecartRenderer(EntityRendererProvider.Context var1, ModelLayerLocation var2) {
      super(â˜ƒ);
      this.shadowRadius = 0.7F;
      this.model = new MinecartModel<>(â˜ƒ.bakeLayer(â˜ƒ));
   }

   public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.pushPose();
      long â˜ƒ = (long)â˜ƒ.getId() * 493286711L;
      â˜ƒ = â˜ƒ * â˜ƒ * 4392167121L + â˜ƒ * 98761L;
      float â˜ƒx = (((float)(â˜ƒ >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float â˜ƒxx = (((float)(â˜ƒ >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float â˜ƒxxx = (((float)(â˜ƒ >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      â˜ƒ.translate((double)â˜ƒx, (double)â˜ƒxx, (double)â˜ƒxxx);
      double â˜ƒxxxx = Mth.lerp((double)â˜ƒ, â˜ƒ.xOld, â˜ƒ.getX());
      double â˜ƒxxxxx = Mth.lerp((double)â˜ƒ, â˜ƒ.yOld, â˜ƒ.getY());
      double â˜ƒxxxxxx = Mth.lerp((double)â˜ƒ, â˜ƒ.zOld, â˜ƒ.getZ());
      double â˜ƒxxxxxxx = 0.3F;
      Vec3 â˜ƒxxxxxxxx = â˜ƒ.getPos(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx);
      float â˜ƒxxxxxxxxx = Mth.lerp(â˜ƒ, â˜ƒ.xRotO, â˜ƒ.getXRot());
      if (â˜ƒxxxxxxxx != null) {
         Vec3 â˜ƒxxxxxxxxxx = â˜ƒ.getPosOffs(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, 0.3F);
         Vec3 â˜ƒxxxxxxxxxxx = â˜ƒ.getPosOffs(â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, -0.3F);
         if (â˜ƒxxxxxxxxxx == null) {
            â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxx;
         }

         if (â˜ƒxxxxxxxxxxx == null) {
            â˜ƒxxxxxxxxxxx = â˜ƒxxxxxxxx;
         }

         â˜ƒ.translate(â˜ƒxxxxxxxx.x - â˜ƒxxxx, (â˜ƒxxxxxxxxxx.y + â˜ƒxxxxxxxxxxx.y) / 2.0 - â˜ƒxxxxx, â˜ƒxxxxxxxx.z - â˜ƒxxxxxx);
         Vec3 â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxxxx.add(-â˜ƒxxxxxxxxxx.x, -â˜ƒxxxxxxxxxx.y, -â˜ƒxxxxxxxxxx.z);
         if (â˜ƒxxxxxxxxxx.length() != 0.0) {
            â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxxxx.normalize();
            â˜ƒ = (float)(Math.atan2(â˜ƒxxxxxxxxxx.z, â˜ƒxxxxxxxxxx.x) * 180.0 / Math.PI);
            â˜ƒxxxxxxxxx = (float)(Math.atan(â˜ƒxxxxxxxxxx.y) * 73.0);
         }
      }

      â˜ƒ.translate(0.0, 0.375, 0.0);
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F - â˜ƒ));
      â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(-â˜ƒxxxxxxxxx));
      float â˜ƒ = (float)â˜ƒ.getHurtTime() - â˜ƒ;
      float â˜ƒx = â˜ƒ.getDamage() - â˜ƒ;
      if (â˜ƒx < 0.0F) {
         â˜ƒx = 0.0F;
      }

      if (â˜ƒ > 0.0F) {
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(Mth.sin(â˜ƒ) * â˜ƒ * â˜ƒx / 10.0F * (float)â˜ƒ.getHurtDir()));
      }

      int â˜ƒ = â˜ƒ.getDisplayOffset();
      BlockState â˜ƒx = â˜ƒ.getDisplayBlockState();
      if (â˜ƒx.getRenderShape() != RenderShape.INVISIBLE) {
         â˜ƒ.pushPose();
         float â˜ƒxx = 0.75F;
         â˜ƒ.scale(0.75F, 0.75F, 0.75F);
         â˜ƒ.translate(-0.5, (double)((float)(â˜ƒ - 8) / 16.0F), 0.5);
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(90.0F));
         this.renderMinecartContents(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, â˜ƒ, â˜ƒ);
         â˜ƒ.popPose();
      }

      â˜ƒ.scale(-1.0F, -1.0F, 1.0F);
      this.model.setupAnim(â˜ƒ, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
      VertexConsumer â˜ƒ = â˜ƒ.getBuffer(this.model.renderType(this.getTextureLocation(â˜ƒ)));
      this.model.renderToBuffer(â˜ƒ, â˜ƒ, â˜ƒ, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
      â˜ƒ.popPose();
   }

   public ResourceLocation getTextureLocation(T var1) {
      return MINECART_LOCATION;
   }

   protected void renderMinecartContents(T var1, float var2, BlockState var3, PoseStack var4, MultiBufferSource var5, int var6) {
      Minecraft.getInstance().getBlockRenderer().renderSingleBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, OverlayTexture.NO_OVERLAY);
   }
}
