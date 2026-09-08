package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.SheepFurLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;

public class SheepRenderer extends MobRenderer<Sheep, SheepModel<Sheep>> {
   private static final ResourceLocation SHEEP_LOCATION = new ResourceLocation("textures/entity/sheep/sheep.png");

   public SheepRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new SheepModel<>(â˜ƒ.bakeLayer(ModelLayers.SHEEP)), 0.7F);
      this.addLayer(new SheepFurLayer(this, â˜ƒ.getModelSet()));
   }

   public ResourceLocation getTextureLocation(Sheep var1) {
      return SHEEP_LOCATION;
   }
}
