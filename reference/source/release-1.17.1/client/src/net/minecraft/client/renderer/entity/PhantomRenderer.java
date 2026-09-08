package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.PhantomModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.PhantomEyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Phantom;

public class PhantomRenderer extends MobRenderer<Phantom, PhantomModel<Phantom>> {
   private static final ResourceLocation PHANTOM_LOCATION = new ResourceLocation("textures/entity/phantom.png");

   public PhantomRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new PhantomModel<>(â˜ƒ.bakeLayer(ModelLayers.PHANTOM)), 0.75F);
      this.addLayer(new PhantomEyesLayer<>(this));
   }

   public ResourceLocation getTextureLocation(Phantom var1) {
      return PHANTOM_LOCATION;
   }

   protected void scale(Phantom var1, PoseStack var2, float var3) {
      int â˜ƒ = â˜ƒ.getPhantomSize();
      float â˜ƒx = 1.0F + 0.15F * (float)â˜ƒ;
      â˜ƒ.scale(â˜ƒx, â˜ƒx, â˜ƒx);
      â˜ƒ.translate(0.0, 1.3125, 0.1875);
   }

   protected void setupRotations(Phantom var1, PoseStack var2, float var3, float var4, float var5) {
      super.setupRotations(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      â˜ƒ.mulPose(Vector3f.XP.rotationDegrees(â˜ƒ.getXRot()));
   }
}
