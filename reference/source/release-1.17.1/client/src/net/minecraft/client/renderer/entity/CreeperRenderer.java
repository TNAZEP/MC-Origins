package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.CreeperPowerLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Creeper;

public class CreeperRenderer extends MobRenderer<Creeper, CreeperModel<Creeper>> {
   private static final ResourceLocation CREEPER_LOCATION = new ResourceLocation("textures/entity/creeper/creeper.png");

   public CreeperRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new CreeperModel<>(â˜ƒ.bakeLayer(ModelLayers.CREEPER)), 0.5F);
      this.addLayer(new CreeperPowerLayer(this, â˜ƒ.getModelSet()));
   }

   protected void scale(Creeper var1, PoseStack var2, float var3) {
      float â˜ƒ = â˜ƒ.getSwelling(â˜ƒ);
      float â˜ƒx = 1.0F + Mth.sin(â˜ƒ * 100.0F) * â˜ƒ * 0.01F;
      â˜ƒ = Mth.clamp(â˜ƒ, 0.0F, 1.0F);
      â˜ƒ *= â˜ƒ;
      â˜ƒ *= â˜ƒ;
      float â˜ƒxx = (1.0F + â˜ƒ * 0.4F) * â˜ƒx;
      float â˜ƒxxx = (1.0F + â˜ƒ * 0.1F) / â˜ƒx;
      â˜ƒ.scale(â˜ƒxx, â˜ƒxxx, â˜ƒxx);
   }

   protected float getWhiteOverlayProgress(Creeper var1, float var2) {
      float â˜ƒ = â˜ƒ.getSwelling(â˜ƒ);
      return (int)(â˜ƒ * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(â˜ƒ, 0.5F, 1.0F);
   }

   public ResourceLocation getTextureLocation(Creeper var1) {
      return CREEPER_LOCATION;
   }
}
