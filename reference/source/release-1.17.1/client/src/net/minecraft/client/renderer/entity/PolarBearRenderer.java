package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PolarBearModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.PolarBear;

public class PolarBearRenderer extends MobRenderer<PolarBear, PolarBearModel<PolarBear>> {
   private static final ResourceLocation BEAR_LOCATION = new ResourceLocation("textures/entity/bear/polarbear.png");

   public PolarBearRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new PolarBearModel<>(â˜ƒ.bakeLayer(ModelLayers.POLAR_BEAR)), 0.9F);
   }

   public ResourceLocation getTextureLocation(PolarBear var1) {
      return BEAR_LOCATION;
   }

   protected void scale(PolarBear var1, PoseStack var2, float var3) {
      â˜ƒ.scale(1.2F, 1.2F, 1.2F);
      super.scale(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
