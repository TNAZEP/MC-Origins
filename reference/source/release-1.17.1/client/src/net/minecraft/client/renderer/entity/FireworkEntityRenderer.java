package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;

public class FireworkEntityRenderer extends EntityRenderer<FireworkRocketEntity> {
   private final ItemRenderer itemRenderer;

   public FireworkEntityRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ);
      this.itemRenderer = â˜ƒ.getItemRenderer();
   }

   public void render(FireworkRocketEntity var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      â˜ƒ.pushPose();
      â˜ƒ.mulPose(this.entityRenderDispatcher.cameraOrientation());
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F));
      if (â˜ƒ.isShotAtAngle()) {
         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
         â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(180.0F));
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(90.0F));
      }

      this.itemRenderer.renderStatic(â˜ƒ.getItem(), ItemTransforms.TransformType.GROUND, â˜ƒ, OverlayTexture.NO_OVERLAY, â˜ƒ, â˜ƒ, â˜ƒ.getId());
      â˜ƒ.popPose();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public ResourceLocation getTextureLocation(FireworkRocketEntity var1) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}
