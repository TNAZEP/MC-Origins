package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.TileEntityStructure;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class TileEntityStructureRenderer extends TileEntityRenderer<TileEntityStructure> {
   public void func_199341_a(TileEntityStructure var1, double var2, double var4, double var6, float var8, int var9) {
      if (Minecraft.func_71410_x().field_71439_g.func_195070_dx() || Minecraft.func_71410_x().field_71439_g.func_175149_v()) {
         super.func_199341_a(☃, ☃, ☃, ☃, ☃, ☃);
         BlockPos ☃ = ☃.func_189711_e();
         BlockPos ☃x = ☃.func_189717_g();
         if (☃x.func_177958_n() >= 1 && ☃x.func_177956_o() >= 1 && ☃x.func_177952_p() >= 1) {
            if (☃.func_189700_k() == StructureMode.SAVE || ☃.func_189700_k() == StructureMode.LOAD) {
               double ☃xxxx = 0.01;
               double ☃xxxxx = (double)☃.func_177958_n();
               double ☃xxxxxx = (double)☃.func_177952_p();
               double ☃xxxxxxx = ☃ + (double)☃.func_177956_o() - 0.01;
               double ☃xxxxxxxx = ☃xxxxxxx + (double)☃x.func_177956_o() + 0.02;
               double ☃xx;
               double ☃xxx;
               switch(☃.func_189716_h()) {
                  case LEFT_RIGHT:
                     ☃xx = (double)☃x.func_177958_n() + 0.02;
                     ☃xxx = -((double)☃x.func_177952_p() + 0.02);
                     break;
                  case FRONT_BACK:
                     ☃xx = -((double)☃x.func_177958_n() + 0.02);
                     ☃xxx = (double)☃x.func_177952_p() + 0.02;
                     break;
                  default:
                     ☃xx = (double)☃x.func_177958_n() + 0.02;
                     ☃xxx = (double)☃x.func_177952_p() + 0.02;
               }

               double ☃xx;
               double ☃xxx;
               double ☃xxxx;
               double ☃xxxxx;
               switch(☃.func_189726_i()) {
                  case CLOCKWISE_90:
                     ☃xx = ☃ + (☃xxx < 0.0 ? ☃xxxxx - 0.01 : ☃xxxxx + 1.0 + 0.01);
                     ☃xxx = ☃ + (☃xx < 0.0 ? ☃xxxxxx + 1.0 + 0.01 : ☃xxxxxx - 0.01);
                     ☃xxxx = ☃xx - ☃xxx;
                     ☃xxxxx = ☃xxx + ☃xx;
                     break;
                  case CLOCKWISE_180:
                     ☃xx = ☃ + (☃xx < 0.0 ? ☃xxxxx - 0.01 : ☃xxxxx + 1.0 + 0.01);
                     ☃xxx = ☃ + (☃xxx < 0.0 ? ☃xxxxxx - 0.01 : ☃xxxxxx + 1.0 + 0.01);
                     ☃xxxx = ☃xx - ☃xx;
                     ☃xxxxx = ☃xxx - ☃xxx;
                     break;
                  case COUNTERCLOCKWISE_90:
                     ☃xx = ☃ + (☃xxx < 0.0 ? ☃xxxxx + 1.0 + 0.01 : ☃xxxxx - 0.01);
                     ☃xxx = ☃ + (☃xx < 0.0 ? ☃xxxxxx - 0.01 : ☃xxxxxx + 1.0 + 0.01);
                     ☃xxxx = ☃xx + ☃xxx;
                     ☃xxxxx = ☃xxx - ☃xx;
                     break;
                  default:
                     ☃xx = ☃ + (☃xx < 0.0 ? ☃xxxxx + 1.0 + 0.01 : ☃xxxxx - 0.01);
                     ☃xxx = ☃ + (☃xxx < 0.0 ? ☃xxxxxx + 1.0 + 0.01 : ☃xxxxxx - 0.01);
                     ☃xxxx = ☃xx + ☃xx;
                     ☃xxxxx = ☃xxx + ☃xxx;
               }

               int ☃xx = 255;
               int ☃xxx = 223;
               int ☃xxxx = 127;
               Tessellator ☃xxxxx = Tessellator.func_178181_a();
               BufferBuilder ☃xxxxxx = ☃xxxxx.func_178180_c();
               GlStateManager.func_179106_n();
               GlStateManager.func_179140_f();
               GlStateManager.func_179090_x();
               GlStateManager.func_179147_l();
               GlStateManager.func_187428_a(
                  GlStateManager.SourceFactor.SRC_ALPHA,
                  GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                  GlStateManager.SourceFactor.ONE,
                  GlStateManager.DestFactor.ZERO
               );
               this.func_190053_a(true);
               if (☃.func_189700_k() == StructureMode.SAVE || ☃.func_189721_I()) {
                  this.func_190055_a(☃xxxxx, ☃xxxxxx, ☃xx, ☃xxxxxxx, ☃xxx, ☃xxxx, ☃xxxxxxxx, ☃xxxxx, 255, 223, 127);
               }

               if (☃.func_189700_k() == StructureMode.SAVE && ☃.func_189707_H()) {
                  this.func_190054_a(☃, ☃, ☃, ☃, ☃, ☃xxxxx, ☃xxxxxx, true);
                  this.func_190054_a(☃, ☃, ☃, ☃, ☃, ☃xxxxx, ☃xxxxxx, false);
               }

               this.func_190053_a(false);
               GlStateManager.func_187441_d(1.0F);
               GlStateManager.func_179145_e();
               GlStateManager.func_179098_w();
               GlStateManager.func_179126_j();
               GlStateManager.func_179132_a(true);
               GlStateManager.func_179127_m();
            }
         }
      }
   }

   private void func_190054_a(
      TileEntityStructure var1, double var2, double var4, double var6, BlockPos var8, Tessellator var9, BufferBuilder var10, boolean var11
   ) {
      GlStateManager.func_187441_d(☃ ? 3.0F : 1.0F);
      ☃.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      IBlockReader ☃ = ☃.func_145831_w();
      BlockPos ☃x = ☃.func_174877_v();
      BlockPos ☃xx = ☃x.func_177971_a(☃);

      for(BlockPos ☃xxx : BlockPos.func_177980_a(☃xx, ☃xx.func_177971_a(☃.func_189717_g()).func_177982_a(-1, -1, -1))) {
         IBlockState ☃xxxx = ☃.func_180495_p(☃xxx);
         boolean ☃xxxxx = ☃xxxx.func_196958_f();
         boolean ☃xxxxxx = ☃xxxx.func_177230_c() == Blocks.field_189881_dj;
         if (☃xxxxx || ☃xxxxxx) {
            float ☃xxxxxxx = ☃xxxxx ? 0.05F : 0.0F;
            double ☃xxxxxxxx = (double)((float)(☃xxx.func_177958_n() - ☃x.func_177958_n()) + 0.45F) + ☃ - (double)☃xxxxxxx;
            double ☃xxxxxxxxx = (double)((float)(☃xxx.func_177956_o() - ☃x.func_177956_o()) + 0.45F) + ☃ - (double)☃xxxxxxx;
            double ☃xxxxxxxxxx = (double)((float)(☃xxx.func_177952_p() - ☃x.func_177952_p()) + 0.45F) + ☃ - (double)☃xxxxxxx;
            double ☃xxxxxxxxxxx = (double)((float)(☃xxx.func_177958_n() - ☃x.func_177958_n()) + 0.55F) + ☃ + (double)☃xxxxxxx;
            double ☃xxxxxxxxxxxx = (double)((float)(☃xxx.func_177956_o() - ☃x.func_177956_o()) + 0.55F) + ☃ + (double)☃xxxxxxx;
            double ☃xxxxxxxxxxxxx = (double)((float)(☃xxx.func_177952_p() - ☃x.func_177952_p()) + 0.55F) + ☃ + (double)☃xxxxxxx;
            if (☃) {
               WorldRenderer.func_189698_a(☃, ☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxx, 0.0F, 0.0F, 0.0F, 1.0F);
            } else if (☃xxxxx) {
               WorldRenderer.func_189698_a(☃, ☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxx, 0.5F, 0.5F, 1.0F, 1.0F);
            } else {
               WorldRenderer.func_189698_a(☃, ☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxx, 1.0F, 0.25F, 0.25F, 1.0F);
            }
         }
      }

      ☃.func_78381_a();
   }

   private void func_190055_a(
      Tessellator var1, BufferBuilder var2, double var3, double var5, double var7, double var9, double var11, double var13, int var15, int var16, int var17
   ) {
      GlStateManager.func_187441_d(2.0F);
      ☃.func_181668_a(3, DefaultVertexFormats.field_181706_f);
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a((float)☃, (float)☃, (float)☃, 0.0F).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181669_b(☃, ☃, ☃, ☃).func_181675_d();
      ☃.func_181662_b(☃, ☃, ☃).func_181666_a((float)☃, (float)☃, (float)☃, 0.0F).func_181675_d();
      ☃.func_78381_a();
      GlStateManager.func_187441_d(1.0F);
   }

   public boolean func_188185_a(TileEntityStructure var1) {
      return true;
   }
}
