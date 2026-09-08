package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.DrownedOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Zombie;

public class DrownedRenderer extends AbstractZombieRenderer<Drowned, DrownedModel<Drowned>> {
   private static final ResourceLocation DROWNED_LOCATION = new ResourceLocation("textures/entity/zombie/drowned.png");

   public DrownedRenderer(EntityRendererProvider.Context var1) {
      super(
         â˜ƒ,
         new DrownedModel<>(â˜ƒ.bakeLayer(ModelLayers.DROWNED)),
         new DrownedModel<>(â˜ƒ.bakeLayer(ModelLayers.DROWNED_INNER_ARMOR)),
         new DrownedModel<>(â˜ƒ.bakeLayer(ModelLayers.DROWNED_OUTER_ARMOR))
      );
      this.addLayer(new DrownedOuterLayer<>(this, â˜ƒ.getModelSet()));
   }

   @Override
   public ResourceLocation getTextureLocation(Zombie var1) {
      return DROWNED_LOCATION;
   }

   protected void setupRotations(Drowned var1, PoseStack var2, float var3, float var4, float var5) {
      super.setupRotations(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      float â˜ƒ = â˜ƒ.getSwimAmount(â˜ƒ);
      if (â˜ƒ > 0.0F) {
         â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(Mth.lerp(â˜ƒ, â˜ƒ.getXRot(), -10.0F - â˜ƒ.getXRot())));
      }
   }
}
