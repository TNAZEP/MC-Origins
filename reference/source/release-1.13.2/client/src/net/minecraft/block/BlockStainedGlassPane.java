package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStainedGlassPane extends BlockGlassPane {
   private final EnumDyeColor field_196420_C;

   public BlockStainedGlassPane(EnumDyeColor var1, Block.Properties var2) {
      super(☃);
      this.field_196420_C = ☃;
      this.func_180632_j(
         this.field_176227_L
            .func_177621_b()
            .func_206870_a(field_196409_a, Boolean.valueOf(false))
            .func_206870_a(field_196411_b, Boolean.valueOf(false))
            .func_206870_a(field_196413_c, Boolean.valueOf(false))
            .func_206870_a(field_196414_y, Boolean.valueOf(false))
            .func_206870_a(field_204514_u, Boolean.valueOf(false))
      );
   }

   public EnumDyeColor func_196419_d() {
      return this.field_196420_C;
   }

   @Override
   public BlockRenderLayer func_180664_k() {
      return BlockRenderLayer.TRANSLUCENT;
   }

   @Override
   public void func_196259_b(IBlockState var1, World var2, BlockPos var3, IBlockState var4) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         if (!☃.field_72995_K) {
            BlockBeacon.func_176450_d(☃, ☃);
         }
      }
   }

   @Override
   public void func_196243_a(IBlockState var1, World var2, BlockPos var3, IBlockState var4, boolean var5) {
      if (☃.func_177230_c() != ☃.func_177230_c()) {
         if (!☃.field_72995_K) {
            BlockBeacon.func_176450_d(☃, ☃);
         }
      }
   }
}
