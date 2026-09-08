package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.StriderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Strider;

public class StriderRenderer extends MobRenderer<Strider, StriderModel<Strider>> {
   private static final ResourceLocation STRIDER_LOCATION = new ResourceLocation("textures/entity/strider/strider.png");
   private static final ResourceLocation COLD_LOCATION = new ResourceLocation("textures/entity/strider/strider_cold.png");

   public StriderRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new StriderModel<>(â˜ƒ.bakeLayer(ModelLayers.STRIDER)), 0.5F);
      this.addLayer(
         new SaddleLayer<>(
            this, new StriderModel<>(â˜ƒ.bakeLayer(ModelLayers.STRIDER_SADDLE)), new ResourceLocation("textures/entity/strider/strider_saddle.png")
         )
      );
   }

   public ResourceLocation getTextureLocation(Strider var1) {
      return â˜ƒ.isSuffocating() ? COLD_LOCATION : STRIDER_LOCATION;
   }

   protected void scale(Strider var1, PoseStack var2, float var3) {
      if (â˜ƒ.isBaby()) {
         â˜ƒ.scale(0.5F, 0.5F, 0.5F);
         this.shadowRadius = 0.25F;
      } else {
         this.shadowRadius = 0.5F;
      }
   }

   protected boolean isShaking(Strider var1) {
      return super.isShaking(â˜ƒ) || â˜ƒ.isSuffocating();
   }
}
