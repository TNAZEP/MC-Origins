package net.minecraft.client.renderer.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumLightType;
import net.minecraft.world.IWorldReaderBase;

public class DebugRendererLight implements DebugRenderer.IDebugRenderer {
   private final Minecraft field_201728_a;

   public DebugRendererLight(Minecraft var1) {
      this.field_201728_a = ☃;
   }

   @Override
   public void func_190060_a(float var1, long var2) {
      EntityPlayer ☃ = this.field_201728_a.field_71439_g;
      IWorldReaderBase ☃x = this.field_201728_a.field_71441_e;
      double ☃xx = ☃.field_70142_S + (☃.field_70165_t - ☃.field_70142_S) * (double)☃;
      double ☃xxx = ☃.field_70137_T + (☃.field_70163_u - ☃.field_70137_T) * (double)☃;
      double ☃xxxx = ☃.field_70136_U + (☃.field_70161_v - ☃.field_70136_U) * (double)☃;
      GlStateManager.func_179094_E();
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_179090_x();
      BlockPos ☃xxxxx = new BlockPos(☃.field_70165_t, ☃.field_70163_u, ☃.field_70161_v);

      for(BlockPos ☃xxxxxx : BlockPos.func_177980_a(☃xxxxx.func_177982_a(-5, -5, -5), ☃xxxxx.func_177982_a(5, 5, 5))) {
         int ☃xxxxxxx = ☃x.func_175642_b(EnumLightType.SKY, ☃xxxxxx);
         float ☃xxxxxxxx = (float)(15 - ☃xxxxxxx) / 15.0F * 0.5F + 0.16F;
         int ☃xxxxxxxxx = MathHelper.func_181758_c(☃xxxxxxxx, 0.9F, 0.9F);
         if (☃xxxxxxx != 15) {
            DebugRenderer.func_190076_a(
               String.valueOf(☃xxxxxxx),
               (double)☃xxxxxx.func_177958_n() + 0.5,
               (double)☃xxxxxx.func_177956_o() + 0.25,
               (double)☃xxxxxx.func_177952_p() + 0.5,
               1.0F,
               ☃xxxxxxxxx
            );
         }
      }

      GlStateManager.func_179098_w();
      GlStateManager.func_179121_F();
   }
}
