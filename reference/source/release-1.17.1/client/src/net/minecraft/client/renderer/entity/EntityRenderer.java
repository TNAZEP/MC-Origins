package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class EntityRenderer<T extends Entity> {
   protected static final float NAMETAG_SCALE = 0.025F;
   protected final EntityRenderDispatcher entityRenderDispatcher;
   private final Font font;
   protected float shadowRadius;
   protected float shadowStrength = 1.0F;

   protected EntityRenderer(EntityRendererProvider.Context var1) {
      this.entityRenderDispatcher = â˜ƒ.getEntityRenderDispatcher();
      this.font = â˜ƒ.getFont();
   }

   public final int getPackedLightCoords(T var1, float var2) {
      BlockPos â˜ƒ = new BlockPos(â˜ƒ.getLightProbePosition(â˜ƒ));
      return LightTexture.pack(this.getBlockLightLevel(â˜ƒ, â˜ƒ), this.getSkyLightLevel(â˜ƒ, â˜ƒ));
   }

   protected int getSkyLightLevel(T var1, BlockPos var2) {
      return â˜ƒ.level.getBrightness(LightLayer.SKY, â˜ƒ);
   }

   protected int getBlockLightLevel(T var1, BlockPos var2) {
      return â˜ƒ.isOnFire() ? 15 : â˜ƒ.level.getBrightness(LightLayer.BLOCK, â˜ƒ);
   }

   public boolean shouldRender(T var1, Frustum var2, double var3, double var5, double var7) {
      if (!â˜ƒ.shouldRender(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return false;
      } else if (â˜ƒ.noCulling) {
         return true;
      } else {
         AABB â˜ƒ = â˜ƒ.getBoundingBoxForCulling().inflate(0.5);
         if (â˜ƒ.hasNaN() || â˜ƒ.getSize() == 0.0) {
            â˜ƒ = new AABB(â˜ƒ.getX() - 2.0, â˜ƒ.getY() - 2.0, â˜ƒ.getZ() - 2.0, â˜ƒ.getX() + 2.0, â˜ƒ.getY() + 2.0, â˜ƒ.getZ() + 2.0);
         }

         return â˜ƒ.isVisible(â˜ƒ);
      }
   }

   public Vec3 getRenderOffset(T var1, float var2) {
      return Vec3.ZERO;
   }

   public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      if (this.shouldShowName(â˜ƒ)) {
         this.renderNameTag(â˜ƒ, â˜ƒ.getDisplayName(), â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   protected boolean shouldShowName(T var1) {
      return â˜ƒ.shouldShowName() && â˜ƒ.hasCustomName();
   }

   public abstract ResourceLocation getTextureLocation(T var1);

   public Font getFont() {
      return this.font;
   }

   protected void renderNameTag(T var1, Component var2, PoseStack var3, MultiBufferSource var4, int var5) {
      double â˜ƒ = this.entityRenderDispatcher.distanceToSqr(â˜ƒ);
      if (!(â˜ƒ > 4096.0)) {
         boolean â˜ƒx = !â˜ƒ.isDiscrete();
         float â˜ƒxx = â˜ƒ.getBbHeight() + 0.5F;
         int â˜ƒxxx = "deadmau5".equals(â˜ƒ.getString()) ? -10 : 0;
         â˜ƒ.pushPose();
         â˜ƒ.translate(0.0, (double)â˜ƒxx, 0.0);
         â˜ƒ.mulPose(this.entityRenderDispatcher.cameraOrientation());
         â˜ƒ.scale(-0.025F, -0.025F, 0.025F);
         Matrix4f â˜ƒxxxx = â˜ƒ.last().pose();
         float â˜ƒxxxxx = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
         int â˜ƒxxxxxx = (int)(â˜ƒxxxxx * 255.0F) << 24;
         Font â˜ƒxxxxxxx = this.getFont();
         float â˜ƒxxxxxxxx = (float)(-â˜ƒxxxxxxx.width(â˜ƒ) / 2);
         â˜ƒxxxxxxx.drawInBatch(â˜ƒ, â˜ƒxxxxxxxx, (float)â˜ƒxxx, 553648127, false, â˜ƒxxxx, â˜ƒ, â˜ƒx, â˜ƒxxxxxx, â˜ƒ);
         if (â˜ƒx) {
            â˜ƒxxxxxxx.drawInBatch(â˜ƒ, â˜ƒxxxxxxxx, (float)â˜ƒxxx, -1, false, â˜ƒxxxx, â˜ƒ, false, 0, â˜ƒ);
         }

         â˜ƒ.popPose();
      }
   }
}
