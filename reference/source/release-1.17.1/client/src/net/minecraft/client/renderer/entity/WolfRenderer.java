package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.WolfCollarLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

public class WolfRenderer extends MobRenderer<Wolf, WolfModel<Wolf>> {
   private static final ResourceLocation WOLF_LOCATION = new ResourceLocation("textures/entity/wolf/wolf.png");
   private static final ResourceLocation WOLF_TAME_LOCATION = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
   private static final ResourceLocation WOLF_ANGRY_LOCATION = new ResourceLocation("textures/entity/wolf/wolf_angry.png");

   public WolfRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new WolfModel<>(â˜ƒ.bakeLayer(ModelLayers.WOLF)), 0.5F);
      this.addLayer(new WolfCollarLayer(this));
   }

   protected float getBob(Wolf var1, float var2) {
      return â˜ƒ.getTailAngle();
   }

   public void render(Wolf var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      if (â˜ƒ.isWet()) {
         float â˜ƒ = â˜ƒ.getWetShade(â˜ƒ);
         this.model.setColor(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.isWet()) {
         this.model.setColor(1.0F, 1.0F, 1.0F);
      }
   }

   public ResourceLocation getTextureLocation(Wolf var1) {
      if (â˜ƒ.isTame()) {
         return WOLF_TAME_LOCATION;
      } else {
         return â˜ƒ.isAngry() ? WOLF_ANGRY_LOCATION : WOLF_LOCATION;
      }
   }
}
