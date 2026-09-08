package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

public class WolfCollarLayer extends RenderLayer<Wolf, WolfModel<Wolf>> {
   private static final ResourceLocation WOLF_COLLAR_LOCATION = new ResourceLocation("textures/entity/wolf/wolf_collar.png");

   public WolfCollarLayer(RenderLayerParent<Wolf, WolfModel<Wolf>> var1) {
      super(â˜ƒ);
   }

   public void render(PoseStack var1, MultiBufferSource var2, int var3, Wolf var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      if (â˜ƒ.isTame() && !â˜ƒ.isInvisible()) {
         float[] â˜ƒ = â˜ƒ.getCollarColor().getTextureDiffuseColors();
         renderColoredCutoutModel(this.getParentModel(), WOLF_COLLAR_LOCATION, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ[0], â˜ƒ[1], â˜ƒ[2]);
      }
   }
}
