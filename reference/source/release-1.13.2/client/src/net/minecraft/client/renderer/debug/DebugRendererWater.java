package net.minecraft.client.renderer.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;

public class DebugRendererWater implements DebugRenderer.IDebugRenderer {
   private final Minecraft field_188288_a;
   private EntityPlayer field_190062_b;
   private double field_190063_c;
   private double field_190064_d;
   private double field_190065_e;

   public DebugRendererWater(Minecraft var1) {
      this.field_188288_a = ☃;
   }

   @Override
   public void func_190060_a(float var1, long var2) {
      this.field_190062_b = this.field_188288_a.field_71439_g;
      this.field_190063_c = this.field_190062_b.field_70142_S + (this.field_190062_b.field_70165_t - this.field_190062_b.field_70142_S) * (double)☃;
      this.field_190064_d = this.field_190062_b.field_70137_T + (this.field_190062_b.field_70163_u - this.field_190062_b.field_70137_T) * (double)☃;
      this.field_190065_e = this.field_190062_b.field_70136_U + (this.field_190062_b.field_70161_v - this.field_190062_b.field_70136_U) * (double)☃;
      BlockPos ☃ = this.field_188288_a.field_71439_g.func_180425_c();
      IWorldReaderBase ☃x = this.field_188288_a.field_71439_g.field_70170_p;
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_179131_c(0.0F, 1.0F, 0.0F, 0.75F);
      GlStateManager.func_179090_x();
      GlStateManager.func_187441_d(6.0F);

      for(BlockPos ☃xx : BlockPos.func_177980_a(☃.func_177982_a(-10, -10, -10), ☃.func_177982_a(10, 10, 10))) {
         IFluidState ☃xxx = ☃x.func_204610_c(☃xx);
         if (☃xxx.func_206884_a(FluidTags.field_206959_a)) {
            double ☃xxxx = (double)((float)☃xx.func_177956_o() + ☃xxx.func_206885_f());
            WorldRenderer.func_189696_b(
               new AxisAlignedBB(
                     (double)((float)☃xx.func_177958_n() + 0.01F),
                     (double)((float)☃xx.func_177956_o() + 0.01F),
                     (double)((float)☃xx.func_177952_p() + 0.01F),
                     (double)((float)☃xx.func_177958_n() + 0.99F),
                     ☃xxxx,
                     (double)((float)☃xx.func_177952_p() + 0.99F)
                  )
                  .func_72317_d(-this.field_190063_c, -this.field_190064_d, -this.field_190065_e),
               1.0F,
               1.0F,
               1.0F,
               0.2F
            );
         }
      }

      for(BlockPos ☃xx : BlockPos.func_177980_a(☃.func_177982_a(-10, -10, -10), ☃.func_177982_a(10, 10, 10))) {
         IFluidState ☃xxx = ☃x.func_204610_c(☃xx);
         if (☃xxx.func_206884_a(FluidTags.field_206959_a)) {
            DebugRenderer.func_190076_a(
               String.valueOf(☃xxx.func_206882_g()),
               (double)☃xx.func_177958_n() + 0.5,
               (double)((float)☃xx.func_177956_o() + ☃xxx.func_206885_f()),
               (double)☃xx.func_177952_p() + 0.5,
               ☃,
               -16777216
            );
         }
      }

      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }
}
