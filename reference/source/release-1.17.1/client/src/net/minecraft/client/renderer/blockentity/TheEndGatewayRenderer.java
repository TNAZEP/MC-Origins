package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;

public class TheEndGatewayRenderer extends TheEndPortalRenderer<TheEndGatewayBlockEntity> {
   private static final ResourceLocation BEAM_LOCATION = new ResourceLocation("textures/entity/end_gateway_beam.png");

   public TheEndGatewayRenderer(BlockEntityRendererProvider.Context var1) {
      super(â˜ƒ);
   }

   public void render(TheEndGatewayBlockEntity var1, float var2, PoseStack var3, MultiBufferSource var4, int var5, int var6) {
      if (â˜ƒ.isSpawning() || â˜ƒ.isCoolingDown()) {
         float â˜ƒ = â˜ƒ.isSpawning() ? â˜ƒ.getSpawnPercent(â˜ƒ) : â˜ƒ.getCooldownPercent(â˜ƒ);
         double â˜ƒx = â˜ƒ.isSpawning() ? (double)â˜ƒ.getLevel().getMaxBuildHeight() : 50.0;
         â˜ƒ = Mth.sin(â˜ƒ * (float) Math.PI);
         int â˜ƒxx = Mth.floor((double)â˜ƒ * â˜ƒx);
         float[] â˜ƒxxx = â˜ƒ.isSpawning() ? DyeColor.MAGENTA.getTextureDiffuseColors() : DyeColor.PURPLE.getTextureDiffuseColors();
         long â˜ƒxxxx = â˜ƒ.getLevel().getGameTime();
         BeaconRenderer.renderBeaconBeam(â˜ƒ, â˜ƒ, BEAM_LOCATION, â˜ƒ, â˜ƒ, â˜ƒxxxx, -â˜ƒxx, â˜ƒxx * 2, â˜ƒxxx, 0.15F, 0.175F);
      }

      super.render(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   protected float getOffsetUp() {
      return 1.0F;
   }

   @Override
   protected float getOffsetDown() {
      return 0.0F;
   }

   @Override
   protected RenderType renderType() {
      return RenderType.endGateway();
   }

   @Override
   public int getViewDistance() {
      return 256;
   }
}
