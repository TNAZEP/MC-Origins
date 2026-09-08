package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.WitchModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.WitchItemLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Witch;

public class WitchRenderer extends MobRenderer<Witch, WitchModel<Witch>> {
   private static final ResourceLocation WITCH_LOCATION = new ResourceLocation("textures/entity/witch.png");

   public WitchRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new WitchModel<>(â˜ƒ.bakeLayer(ModelLayers.WITCH)), 0.5F);
      this.addLayer(new WitchItemLayer<>(this));
   }

   public void render(Witch var1, float var2, float var3, PoseStack var4, MultiBufferSource var5, int var6) {
      this.model.setHoldingItem(!â˜ƒ.getMainHandItem().isEmpty());
      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public ResourceLocation getTextureLocation(Witch var1) {
      return WITCH_LOCATION;
   }

   protected void scale(Witch var1, PoseStack var2, float var3) {
      float â˜ƒ = 0.9375F;
      â˜ƒ.scale(0.9375F, 0.9375F, 0.9375F);
   }
}
