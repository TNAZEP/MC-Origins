package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class SkeletonRenderer extends HumanoidMobRenderer<AbstractSkeleton, SkeletonModel<AbstractSkeleton>> {
   private static final ResourceLocation SKELETON_LOCATION = new ResourceLocation("textures/entity/skeleton/skeleton.png");

   public SkeletonRenderer(EntityRendererProvider.Context var1) {
      this(â˜ƒ, ModelLayers.SKELETON, ModelLayers.SKELETON_INNER_ARMOR, ModelLayers.SKELETON_OUTER_ARMOR);
   }

   public SkeletonRenderer(EntityRendererProvider.Context var1, ModelLayerLocation var2, ModelLayerLocation var3, ModelLayerLocation var4) {
      super(â˜ƒ, new SkeletonModel<>(â˜ƒ.bakeLayer(â˜ƒ)), 0.5F);
      this.addLayer(new HumanoidArmorLayer<>(this, new SkeletonModel(â˜ƒ.bakeLayer(â˜ƒ)), new SkeletonModel(â˜ƒ.bakeLayer(â˜ƒ))));
   }

   public ResourceLocation getTextureLocation(AbstractSkeleton var1) {
      return SKELETON_LOCATION;
   }

   protected boolean isShaking(AbstractSkeleton var1) {
      return â˜ƒ.isShaking();
   }
}
