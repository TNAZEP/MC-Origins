package net.minecraft.client.renderer.debug;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class DebugRendererSolidFace implements DebugRenderer.IDebugRenderer {
   private final Minecraft field_193851_a;

   public DebugRendererSolidFace(Minecraft var1) {
      this.field_193851_a = ☃;
   }

   @Override
   public void func_190060_a(float var1, long var2) {
      EntityPlayer ☃ = this.field_193851_a.field_71439_g;
      double ☃x = ☃.field_70142_S + (☃.field_70165_t - ☃.field_70142_S) * (double)☃;
      double ☃xx = ☃.field_70137_T + (☃.field_70163_u - ☃.field_70137_T) * (double)☃;
      double ☃xxx = ☃.field_70136_U + (☃.field_70161_v - ☃.field_70136_U) * (double)☃;
      IBlockReader ☃xxxx = this.field_193851_a.field_71439_g.field_70170_p;
      Iterable<BlockPos> ☃xxxxx = BlockPos.func_191532_a(
         MathHelper.func_76128_c(☃.field_70165_t - 6.0),
         MathHelper.func_76128_c(☃.field_70163_u - 6.0),
         MathHelper.func_76128_c(☃.field_70161_v - 6.0),
         MathHelper.func_76128_c(☃.field_70165_t + 6.0),
         MathHelper.func_76128_c(☃.field_70163_u + 6.0),
         MathHelper.func_76128_c(☃.field_70161_v + 6.0)
      );
      GlStateManager.func_179147_l();
      GlStateManager.func_187428_a(
         GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO
      );
      GlStateManager.func_187441_d(2.0F);
      GlStateManager.func_179090_x();
      GlStateManager.func_179132_a(false);

      for(BlockPos ☃xxxxxx : ☃xxxxx) {
         IBlockState ☃xxxxxxx = ☃xxxx.func_180495_p(☃xxxxxx);
         if (☃xxxxxxx.func_177230_c() != Blocks.field_150350_a) {
            VoxelShape ☃xxxxxxxx = ☃xxxxxxx.func_196954_c(☃xxxx, ☃xxxxxx);

            for(AxisAlignedBB ☃xxxxxxxxx : ☃xxxxxxxx.func_197756_d()) {
               AxisAlignedBB ☃xxxxxxxxxx = ☃xxxxxxxxx.func_186670_a(☃xxxxxx).func_186662_g(0.002).func_72317_d(-☃x, -☃xx, -☃xxx);
               double ☃xxxxxxxxxxx = ☃xxxxxxxxxx.field_72340_a;
               double ☃xxxxxxxxxxxx = ☃xxxxxxxxxx.field_72338_b;
               double ☃xxxxxxxxxxxxx = ☃xxxxxxxxxx.field_72339_c;
               double ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxx.field_72336_d;
               double ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxx.field_72337_e;
               double ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxxxx.field_72334_f;
               float ☃xxxxxxxxxxxxxxxxx = 1.0F;
               float ☃xxxxxxxxxxxxxxxxxx = 0.0F;
               float ☃xxxxxxxxxxxxxxxxxxx = 0.0F;
               float ☃xxxxxxxxxxxxxxxxxxxx = 0.5F;
               if (☃xxxxxxx.func_193401_d(☃xxxx, ☃xxxxxx, EnumFacing.WEST) == BlockFaceShape.SOLID) {
                  Tessellator ☃xxxxxxxxxxxxxxxxxxxxx = Tessellator.func_178181_a();
                  BufferBuilder ☃xxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxx.func_178180_c();
                  ☃xxxxxxxxxxxxxxxxxxxxxx.func_181668_a(5, DefaultVertexFormats.field_181706_f);
                  ☃xxxxxxxxxxxxxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxxxxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxxxxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxxxxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx)
                     .func_181666_a(1.0F, 0.0F, 0.0F, 0.5F)
                     .func_181675_d();
                  ☃xxxxxxxxxxxxxxxxxxxxx.func_78381_a();
               }

               if (☃xxxxxxx.func_193401_d(☃xxxx, ☃xxxxxx, EnumFacing.SOUTH) == BlockFaceShape.SOLID) {
                  Tessellator ☃xxxxxxxxxx = Tessellator.func_178181_a();
                  BufferBuilder ☃xxxxxxxxxxx = ☃xxxxxxxxxx.func_178180_c();
                  ☃xxxxxxxxxxx.func_181668_a(5, DefaultVertexFormats.field_181706_f);
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxx.func_78381_a();
               }

               if (☃xxxxxxx.func_193401_d(☃xxxx, ☃xxxxxx, EnumFacing.EAST) == BlockFaceShape.SOLID) {
                  Tessellator ☃xxxxxxxxxx = Tessellator.func_178181_a();
                  BufferBuilder ☃xxxxxxxxxxx = ☃xxxxxxxxxx.func_178180_c();
                  ☃xxxxxxxxxxx.func_181668_a(5, DefaultVertexFormats.field_181706_f);
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxx.func_78381_a();
               }

               if (☃xxxxxxx.func_193401_d(☃xxxx, ☃xxxxxx, EnumFacing.NORTH) == BlockFaceShape.SOLID) {
                  Tessellator ☃xxxxxxxxxx = Tessellator.func_178181_a();
                  BufferBuilder ☃xxxxxxxxxxx = ☃xxxxxxxxxx.func_178180_c();
                  ☃xxxxxxxxxxx.func_181668_a(5, DefaultVertexFormats.field_181706_f);
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxx.func_78381_a();
               }

               if (☃xxxxxxx.func_193401_d(☃xxxx, ☃xxxxxx, EnumFacing.DOWN) == BlockFaceShape.SOLID) {
                  Tessellator ☃xxxxxxxxxx = Tessellator.func_178181_a();
                  BufferBuilder ☃xxxxxxxxxxx = ☃xxxxxxxxxx.func_178180_c();
                  ☃xxxxxxxxxxx.func_181668_a(5, DefaultVertexFormats.field_181706_f);
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxx.func_78381_a();
               }

               if (☃xxxxxxx.func_193401_d(☃xxxx, ☃xxxxxx, EnumFacing.UP) == BlockFaceShape.SOLID) {
                  Tessellator ☃xxxxxxxxxx = Tessellator.func_178181_a();
                  BufferBuilder ☃xxxxxxxxxxx = ☃xxxxxxxxxx.func_178180_c();
                  ☃xxxxxxxxxxx.func_181668_a(5, DefaultVertexFormats.field_181706_f);
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxxx.func_181662_b(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx).func_181666_a(1.0F, 0.0F, 0.0F, 0.5F).func_181675_d();
                  ☃xxxxxxxxxx.func_78381_a();
               }
            }
         }
      }

      GlStateManager.func_179132_a(true);
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }
}
