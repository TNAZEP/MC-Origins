package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.IronGolemCrackinessLayer;
import net.minecraft.client.renderer.entity.layers.IronGolemFlowerLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.IronGolem;

public class IronGolemRenderer extends MobRenderer<IronGolem, IronGolemModel<IronGolem>> {
   private static final ResourceLocation GOLEM_LOCATION = new ResourceLocation("textures/entity/iron_golem/iron_golem.png");

   public IronGolemRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new IronGolemModel<>(â˜ƒ.bakeLayer(ModelLayers.IRON_GOLEM)), 0.7F);
      this.addLayer(new IronGolemCrackinessLayer(this));
      this.addLayer(new IronGolemFlowerLayer(this));
   }

   public ResourceLocation getTextureLocation(IronGolem var1) {
      return GOLEM_LOCATION;
   }

   protected void setupRotations(IronGolem var1, PoseStack var2, float var3, float var4, float var5) {
      super.setupRotations(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (!((double)â˜ƒ.animationSpeed < 0.01)) {
         float â˜ƒ = 13.0F;
         float â˜ƒx = â˜ƒ.animationPosition - â˜ƒ.animationSpeed * (1.0F - â˜ƒ) + 6.0F;
         float â˜ƒxx = (Math.abs(â˜ƒx % 13.0F - 6.5F) - 3.25F) / 3.25F;
         â˜ƒ.mulPose(Vector3f.ZP.rotationDegrees(6.5F * â˜ƒxx));
      }
   }
}
