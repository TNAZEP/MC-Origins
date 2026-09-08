package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.FoxHeldItemLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Fox;

public class FoxRenderer extends MobRenderer<Fox, FoxModel<Fox>> {
   private static final ResourceLocation RED_FOX_TEXTURE = new ResourceLocation("textures/entity/fox/fox.png");
   private static final ResourceLocation RED_FOX_SLEEP_TEXTURE = new ResourceLocation("textures/entity/fox/fox_sleep.png");
   private static final ResourceLocation SNOW_FOX_TEXTURE = new ResourceLocation("textures/entity/fox/snow_fox.png");
   private static final ResourceLocation SNOW_FOX_SLEEP_TEXTURE = new ResourceLocation("textures/entity/fox/snow_fox_sleep.png");

   public FoxRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new FoxModel<>(â˜ƒ.bakeLayer(ModelLayers.FOX)), 0.4F);
      this.addLayer(new FoxHeldItemLayer(this));
   }

   protected void setupRotations(Fox var1, PoseStack var2, float var3, float var4, float var5) {
      super.setupRotations(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.isPouncing() || â˜ƒ.isFaceplanted()) {
         float â˜ƒ = -Mth.lerp(â˜ƒ, â˜ƒ.xRotO, â˜ƒ.getXRot());
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒ));
      }
   }

   public ResourceLocation getTextureLocation(Fox var1) {
      if (â˜ƒ.getFoxType() == Fox.Type.RED) {
         return â˜ƒ.isSleeping() ? RED_FOX_SLEEP_TEXTURE : RED_FOX_TEXTURE;
      } else {
         return â˜ƒ.isSleeping() ? SNOW_FOX_SLEEP_TEXTURE : SNOW_FOX_TEXTURE;
      }
   }
}
