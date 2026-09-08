package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.CodModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Cod;

public class CodRenderer extends MobRenderer<Cod, CodModel<Cod>> {
   private static final ResourceLocation COD_LOCATION = new ResourceLocation("textures/entity/fish/cod.png");

   public CodRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new CodModel<>(â˜ƒ.bakeLayer(ModelLayers.COD)), 0.3F);
   }

   public ResourceLocation getTextureLocation(Cod var1) {
      return COD_LOCATION;
   }

   protected void setupRotations(Cod var1, PoseStack var2, float var3, float var4, float var5) {
      super.setupRotations(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      float â˜ƒ = 4.3F * Mth.sin(0.6F * â˜ƒ);
      â˜ƒ.mulPose(Vector3f.YP.rotationDegrees(â˜ƒ));
      if (!â˜ƒ.isInWater()) {
         â˜ƒ.translate(0.1F, 0.1F, -0.1F);
         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
      }
   }
}
