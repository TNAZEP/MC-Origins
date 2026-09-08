package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Random;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CarriedBlockLayer;
import net.minecraft.client.renderer.entity.layers.EnderEyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EndermanRenderer extends MobRenderer<EnderMan, EndermanModel<EnderMan>> {
   private static final ResourceLocation ENDERMAN_LOCATION = new ResourceLocation("textures/entity/enderman/enderman.png");
   private final Random random = new Random();

   public EndermanRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new EndermanModel<>(â˜ƒ.bakeLayer(ModelLayers.ENDERMAN)), 0.5F);
      this.addLayer(new EnderEyesLayer<>(this));
      this.addLayer(new CarriedBlockLayer(this));
   }

   public void render(EnderMan var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      BlockState â˜ƒ = â˜ƒ.getCarriedBlock();
      EndermanModel<EnderMan> â˜ƒx = this.getModel();
      â˜ƒx.carrying = â˜ƒ != null;
      â˜ƒx.creepy = â˜ƒ.isCreepy();
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public Vec3 getRenderOffset(EnderMan var1, float var2) {
      if (â˜ƒ.isCreepy()) {
         double â˜ƒ = 0.02;
         return new Vec3(this.random.nextGaussian() * 0.02, 0.0, this.random.nextGaussian() * 0.02);
      } else {
         return super.getRenderOffset(â˜ƒ, â˜ƒ);
      }
   }

   public ResourceLocation getTextureLocation(EnderMan var1) {
      return ENDERMAN_LOCATION;
   }
}
