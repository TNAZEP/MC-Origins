package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.level.block.state.BlockState;

public class TntMinecartRenderer extends MinecartRenderer<MinecartTNT> {
   public TntMinecartRenderer(EntityRendererProvider.Context var1) {
      super(â˜ƒ, ModelLayers.TNT_MINECART);
   }

   protected void renderMinecartContents(MinecartTNT var1, float var2, BlockState var3, PoseStack var4, MultiBufferSource var5, int var6) {
      int â˜ƒ = â˜ƒ.getFuse();
      if (â˜ƒ > -1 && (float)â˜ƒ - â˜ƒ + 1.0F < 10.0F) {
         float â˜ƒx = 1.0F - ((float)â˜ƒ - â˜ƒ + 1.0F) / 10.0F;
         â˜ƒx = Mth.clamp(â˜ƒx, 0.0F, 1.0F);
         â˜ƒx *= â˜ƒx;
         â˜ƒx *= â˜ƒx;
         float â˜ƒxx = 1.0F + â˜ƒx * 0.3F;
         â˜ƒ.scale(â˜ƒxx, â˜ƒxx, â˜ƒxx);
      }

      renderWhiteSolidBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ > -1 && â˜ƒ / 5 % 2 == 0);
   }

   public static void renderWhiteSolidBlock(BlockState var0, PoseStack var1, MultiBufferSource var2, int var3, boolean var4) {
      int â˜ƒ;
      if (â˜ƒ) {
         â˜ƒ = OverlayTexture.pack(OverlayTexture.u(1.0F), 10);
      } else {
         â˜ƒ = OverlayTexture.NO_OVERLAY;
      }

      Minecraft.getInstance().getBlockRenderer().renderSingleBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
