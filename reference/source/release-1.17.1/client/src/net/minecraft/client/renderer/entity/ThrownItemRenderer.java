package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;

public class ThrownItemRenderer<T extends Entity & ItemSupplier> extends EntityRenderer<T> {
   private static final float MIN_CAMERA_DISTANCE_SQUARED = 12.25F;
   private final ItemRenderer itemRenderer;
   private final float scale;
   private final boolean fullBright;

   public ThrownItemRenderer(EntityRendererProvider.Context var1, float var2, boolean var3) {
      super(â˜ƒ);
      this.itemRenderer = â˜ƒ.getItemRenderer();
      this.scale = â˜ƒ;
      this.fullBright = â˜ƒ;
   }

   public ThrownItemRenderer(EntityRendererProvider.Context var1) {
      this(â˜ƒ, 1.0F, false);
   }

   @Override
   protected int getBlockLightLevel(T var1, BlockPos var2) {
      return this.fullBright ? 15 : super.getBlockLightLevel(â˜ƒ, â˜ƒ);
   }

   @Override
   public void render(T var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      if (â˜ƒ.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(â˜ƒ) < 12.25)) {
         â˜ƒ.pushPose();
         â˜ƒ.scale(this.scale, this.scale, this.scale);
         â˜ƒ.mulPose(this.entityRenderDispatcher.cameraOrientation());
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F));
         this.itemRenderer.renderStatic(â˜ƒ.getItem(), ItemTransforms.TransformType.GROUND, â˜ƒ, OverlayTexture.NO_OVERLAY, â˜ƒ, â˜ƒ, â˜ƒ.getId());
         â˜ƒ.popPose();
         super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public ResourceLocation getTextureLocation(Entity var1) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}
