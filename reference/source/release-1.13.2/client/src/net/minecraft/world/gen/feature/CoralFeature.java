package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockCoralWallFanDead;
import net.minecraft.block.BlockSeaPickle;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public abstract class CoralFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      IBlockState ☃ = BlockTags.field_205598_B.func_205596_a(☃).func_176223_P();
      return this.func_204623_a(☃, ☃, ☃, ☃);
   }

   protected abstract boolean func_204623_a(IWorld var1, Random var2, BlockPos var3, IBlockState var4);

   protected boolean func_204624_b(IWorld var1, Random var2, BlockPos var3, IBlockState var4) {
      BlockPos ☃ = ☃.func_177984_a();
      IBlockState ☃x = ☃.func_180495_p(☃);
      if ((☃x.func_177230_c() == Blocks.field_150355_j || ☃x.func_203425_a(BlockTags.field_204116_z))
         && ☃.func_180495_p(☃).func_177230_c() == Blocks.field_150355_j) {
         ☃.func_180501_a(☃, ☃, 3);
         if (☃.nextFloat() < 0.25F) {
            ☃.func_180501_a(☃, BlockTags.field_204116_z.func_205596_a(☃).func_176223_P(), 2);
         } else if (☃.nextFloat() < 0.05F) {
            ☃.func_180501_a(☃, Blocks.field_204913_jW.func_176223_P().func_206870_a(BlockSeaPickle.field_204902_a, Integer.valueOf(☃.nextInt(4) + 1)), 2);
         }

         for(EnumFacing ☃xx : EnumFacing.Plane.HORIZONTAL) {
            if (☃.nextFloat() < 0.2F) {
               BlockPos ☃xxx = ☃.func_177972_a(☃xx);
               if (☃.func_180495_p(☃xxx).func_177230_c() == Blocks.field_150355_j) {
                  IBlockState ☃xxxx = BlockTags.field_211922_B.func_205596_a(☃).func_176223_P().func_206870_a(BlockCoralWallFanDead.field_211884_b, ☃xx);
                  ☃.func_180501_a(☃xxx, ☃xxxx, 2);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
