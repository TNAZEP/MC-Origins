package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Parrot;

public class ParrotRenderer extends MobRenderer<Parrot, ParrotModel> {
   public static final ResourceLocation[] PARROT_LOCATIONS = new ResourceLocation[]{
      new ResourceLocation("textures/entity/parrot/parrot_red_blue.png"),
      new ResourceLocation("textures/entity/parrot/parrot_blue.png"),
      new ResourceLocation("textures/entity/parrot/parrot_green.png"),
      new ResourceLocation("textures/entity/parrot/parrot_yellow_blue.png"),
      new ResourceLocation("textures/entity/parrot/parrot_grey.png")
   };

   public ParrotRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new ParrotModel(â˜ƒ.bakeLayer(ModelLayers.PARROT)), 0.3F);
   }

   public ResourceLocation getTextureLocation(Parrot var1) {
      return PARROT_LOCATIONS[â˜ƒ.getVariant()];
   }

   public float getBob(Parrot var1, float var2) {
      float â˜ƒ = Mth.lerp(â˜ƒ, â˜ƒ.oFlap, â˜ƒ.flap);
      float â˜ƒx = Mth.lerp(â˜ƒ, â˜ƒ.oFlapSpeed, â˜ƒ.flapSpeed);
      return (Mth.sin(â˜ƒ) + 1.0F) * â˜ƒx;
   }
}
