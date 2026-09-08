package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.LavaSlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.MagmaCube;

public class MagmaCubeRenderer extends MobRenderer<MagmaCube, LavaSlimeModel<MagmaCube>> {
   private static final ResourceLocation MAGMACUBE_LOCATION = new ResourceLocation("textures/entity/slime/magmacube.png");

   public MagmaCubeRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, new LavaSlimeModel<>(â˜ƒ.bakeLayer(ModelLayers.MAGMA_CUBE)), 0.25F);
   }

   protected int getBlockLightLevel(MagmaCube var1, BlockPos var2) {
      return 15;
   }

   public ResourceLocation getTextureLocation(MagmaCube var1) {
      return MAGMACUBE_LOCATION;
   }

   protected void scale(MagmaCube var1, PoseStack var2, float var3) {
      int â˜ƒ = â˜ƒ.getSize();
      float â˜ƒx = Mth.lerp(â˜ƒ, â˜ƒ.oSquish, â˜ƒ.squish) / ((float)â˜ƒ * 0.5F + 1.0F);
      float â˜ƒxx = 1.0F / (â˜ƒx + 1.0F);
      â˜ƒ.scale(â˜ƒxx * (float)â˜ƒ, 1.0F / â˜ƒxx * (float)â˜ƒ, â˜ƒxx * (float)â˜ƒ);
   }
}
