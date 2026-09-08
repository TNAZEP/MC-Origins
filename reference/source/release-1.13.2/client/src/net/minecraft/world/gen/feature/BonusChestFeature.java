package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.loot.LootTableList;

public class BonusChestFeature extends Feature<NoFeatureConfig> {
   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      for(IBlockState ☃ = ☃.func_180495_p(☃); (☃.func_196958_f() || ☃.func_203425_a(BlockTags.field_206952_E)) && ☃.func_177956_o() > 1; ☃ = ☃.func_180495_p(☃)) {
         ☃ = ☃.func_177977_b();
      }

      if (☃.func_177956_o() < 1) {
         return false;
      } else {
         ☃ = ☃.func_177984_a();

         for(int ☃ = 0; ☃ < 4; ++☃) {
            BlockPos ☃x = ☃.func_177982_a(☃.nextInt(4) - ☃.nextInt(4), ☃.nextInt(3) - ☃.nextInt(3), ☃.nextInt(4) - ☃.nextInt(4));
            if (☃.func_175623_d(☃x) && ☃.func_180495_p(☃x.func_177977_b()).func_185896_q()) {
               ☃.func_180501_a(☃x, Blocks.field_150486_ae.func_176223_P(), 2);
               TileEntityLockableLoot.func_195479_a(☃, ☃, ☃x, LootTableList.field_186420_b);
               BlockPos ☃xx = ☃x.func_177974_f();
               BlockPos ☃xxx = ☃x.func_177976_e();
               BlockPos ☃xxxx = ☃x.func_177978_c();
               BlockPos ☃xxxxx = ☃x.func_177968_d();
               if (☃.func_175623_d(☃xxx) && ☃.func_180495_p(☃xxx.func_177977_b()).func_185896_q()) {
                  ☃.func_180501_a(☃xxx, Blocks.field_150478_aa.func_176223_P(), 2);
               }

               if (☃.func_175623_d(☃xx) && ☃.func_180495_p(☃xx.func_177977_b()).func_185896_q()) {
                  ☃.func_180501_a(☃xx, Blocks.field_150478_aa.func_176223_P(), 2);
               }

               if (☃.func_175623_d(☃xxxx) && ☃.func_180495_p(☃xxxx.func_177977_b()).func_185896_q()) {
                  ☃.func_180501_a(☃xxxx, Blocks.field_150478_aa.func_176223_P(), 2);
               }

               if (☃.func_175623_d(☃xxxxx) && ☃.func_180495_p(☃xxxxx.func_177977_b()).func_185896_q()) {
                  ☃.func_180501_a(☃xxxxx, Blocks.field_150478_aa.func_176223_P(), 2);
               }

               return true;
            }
         }

         return false;
      }
   }
}
