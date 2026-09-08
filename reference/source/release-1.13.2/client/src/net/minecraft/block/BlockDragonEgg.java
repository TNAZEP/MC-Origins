package net.minecraft.block;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Particles;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReaderBase;
import net.minecraft.world.World;

public class BlockDragonEgg extends BlockFalling {
   protected static final VoxelShape field_196444_a = Block.func_208617_a(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   public BlockDragonEgg(Block.Properties var1) {
      super(☃);
   }

   @Override
   public VoxelShape func_196244_b(IBlockState var1, IBlockReader var2, BlockPos var3) {
      return field_196444_a;
   }

   @Override
   public boolean func_196250_a(
      IBlockState var1, World var2, BlockPos var3, EntityPlayer var4, EnumHand var5, EnumFacing var6, float var7, float var8, float var9
   ) {
      this.func_196443_d(☃, ☃, ☃);
      return true;
   }

   @Override
   public void func_196270_a(IBlockState var1, World var2, BlockPos var3, EntityPlayer var4) {
      this.func_196443_d(☃, ☃, ☃);
   }

   private void func_196443_d(IBlockState var1, World var2, BlockPos var3) {
      for(int ☃ = 0; ☃ < 1000; ++☃) {
         BlockPos ☃x = ☃.func_177982_a(
            ☃.field_73012_v.nextInt(16) - ☃.field_73012_v.nextInt(16),
            ☃.field_73012_v.nextInt(8) - ☃.field_73012_v.nextInt(8),
            ☃.field_73012_v.nextInt(16) - ☃.field_73012_v.nextInt(16)
         );
         if (☃.func_180495_p(☃x).func_196958_f()) {
            if (☃.field_72995_K) {
               for(int ☃xx = 0; ☃xx < 128; ++☃xx) {
                  double ☃xxx = ☃.field_73012_v.nextDouble();
                  float ☃xxxx = (☃.field_73012_v.nextFloat() - 0.5F) * 0.2F;
                  float ☃xxxxx = (☃.field_73012_v.nextFloat() - 0.5F) * 0.2F;
                  float ☃xxxxxx = (☃.field_73012_v.nextFloat() - 0.5F) * 0.2F;
                  double ☃xxxxxxx = (double)☃x.func_177958_n()
                     + (double)(☃.func_177958_n() - ☃x.func_177958_n()) * ☃xxx
                     + (☃.field_73012_v.nextDouble() - 0.5)
                     + 0.5;
                  double ☃xxxxxxxx = (double)☃x.func_177956_o() + (double)(☃.func_177956_o() - ☃x.func_177956_o()) * ☃xxx + ☃.field_73012_v.nextDouble() - 0.5;
                  double ☃xxxxxxxxx = (double)☃x.func_177952_p()
                     + (double)(☃.func_177952_p() - ☃x.func_177952_p()) * ☃xxx
                     + (☃.field_73012_v.nextDouble() - 0.5)
                     + 0.5;
                  ☃.func_195594_a(Particles.field_197599_J, ☃xxxxxxx, ☃xxxxxxxx, ☃xxxxxxxxx, (double)☃xxxx, (double)☃xxxxx, (double)☃xxxxxx);
               }
            } else {
               ☃.func_180501_a(☃x, ☃, 2);
               ☃.func_175698_g(☃);
            }

            return;
         }
      }
   }

   @Override
   public int func_149738_a(IWorldReaderBase var1) {
      return 5;
   }

   @Override
   public boolean func_149686_d(IBlockState var1) {
      return false;
   }

   @Override
   public BlockFaceShape func_193383_a(IBlockReader var1, IBlockState var2, BlockPos var3, EnumFacing var4) {
      return BlockFaceShape.UNDEFINED;
   }

   @Override
   public boolean func_196266_a(IBlockState var1, IBlockReader var2, BlockPos var3, PathType var4) {
      return false;
   }
}
