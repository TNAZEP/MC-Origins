package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Chicken;

public class ChickenRenderer extends MobRenderer<Chicken, ChickenModel<Chicken>> {
   private static final ResourceLocation CHICKEN_LOCATION = new ResourceLocation("textures/entity/chicken.png");

   public ChickenRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new ChickenModel<>(â˜ƒ.bakeLayer(ModelLayers.CHICKEN)), 0.3F);
   }

   public ResourceLocation getTextureLocation(Chicken var1) {
      return CHICKEN_LOCATION;
   }

   protected float getBob(Chicken var1, float var2) {
      float â˜ƒ = Mth.lerp(â˜ƒ, â˜ƒ.oFlap, â˜ƒ.flap);
      float â˜ƒx = Mth.lerp(â˜ƒ, â˜ƒ.oFlapSpeed, â˜ƒ.flapSpeed);
      return (Mth.sin(â˜ƒ) + 1.0F) * â˜ƒx;
   }
}
