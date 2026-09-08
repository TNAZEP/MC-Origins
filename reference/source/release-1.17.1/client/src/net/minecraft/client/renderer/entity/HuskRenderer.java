package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

public class HuskRenderer extends ZombieRenderer {
   private static final ResourceLocation HUSK_LOCATION = new ResourceLocation("textures/entity/zombie/husk.png");

   public HuskRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, ModelLayers.HUSK, ModelLayers.HUSK_INNER_ARMOR, ModelLayers.HUSK_OUTER_ARMOR);
   }

   protected void scale(Zombie var1, PoseStack var2, float var3) {
      float â˜ƒ = 1.0625F;
      â˜ƒ.scale(1.0625F, 1.0625F, 1.0625F);
      super.scale(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public ResourceLocation getTextureLocation(Zombie var1) {
      return HUSK_LOCATION;
   }
}
