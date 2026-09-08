package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.SalmonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Salmon;

public class SalmonRenderer extends MobRenderer<Salmon, SalmonModel<Salmon>> {
   private static final ResourceLocation SALMON_LOCATION = new ResourceLocation("textures/entity/fish/salmon.png");

   public SalmonRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new SalmonModel<>(â˜ƒ.bakeLayer(ModelLayers.SALMON)), 0.4F);
   }

   public ResourceLocation getTextureLocation(Salmon var1) {
      return SALMON_LOCATION;
   }

   protected void setupRotations(Salmon var1, PoseStack var2, float var3, float var4, float var5) {
      super.setupRotations(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      float â˜ƒ = 1.0F;
      float â˜ƒx = 1.0F;
      if (!â˜ƒ.isInWater()) {
         â˜ƒ = 1.3F;
         â˜ƒx = 1.7F;
      }

      float â˜ƒ = â˜ƒ * 4.3F * Mth.sin(â˜ƒx * 0.6F * â˜ƒ);
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒ));
      â˜ƒ.translate(0.0, 0.0, -0.4F);
      if (!â˜ƒ.isInWater()) {
         â˜ƒ.translate(0.2F, 0.1F, 0.0);
         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
      }
   }
}
