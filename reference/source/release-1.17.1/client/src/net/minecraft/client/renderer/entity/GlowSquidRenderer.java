package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.SquidModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.GlowSquid;

public class GlowSquidRenderer extends SquidRenderer<GlowSquid> {
   private static final ResourceLocation GLOW_SQUID_LOCATION = new ResourceLocation("textures/entity/squid/glow_squid.png");

   public GlowSquidRenderer(EntityRendererProvider.Context var1, SquidModel<GlowSquid> var2) {
      super(â˜ƒ, â˜ƒ);
   }

   public ResourceLocation getTextureLocation(GlowSquid var1) {
      return GLOW_SQUID_LOCATION;
   }

   protected int getBlockLightLevel(GlowSquid var1, BlockPos var2) {
      int â˜ƒ = (int)Mth.clampedLerp(0.0F, 15.0F, 1.0F - (float)â˜ƒ.getDarkTicksRemaining() / 10.0F);
      return â˜ƒ == 15 ? 15 : Math.max(â˜ƒ, super.getBlockLightLevel(â˜ƒ, â˜ƒ));
   }
}
