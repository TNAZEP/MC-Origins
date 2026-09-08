package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;

public abstract class AbstractTreeFeature<T extends IFeatureConfig> extends Feature<T> {
   public AbstractTreeFeature(boolean var1) {
      super(☃);
   }

   protected boolean func_150523_a(Block var1) {
      IBlockState ☃ = ☃.func_176223_P();
      return ☃.func_196958_f()
         || ☃.func_203425_a(BlockTags.field_206952_E)
         || ☃ == Blocks.field_196658_i
         || Block.func_196245_f(☃)
         || ☃.func_203417_a(BlockTags.field_200031_h)
         || ☃.func_203417_a(BlockTags.field_200030_g)
         || ☃ == Blocks.field_150395_bd;
   }

   protected void func_175921_a(IWorld var1, BlockPos var2) {
      if (!Block.func_196245_f(☃.func_180495_p(☃).func_177230_c())) {
         this.func_202278_a(☃, ☃, Blocks.field_150346_d.func_176223_P());
      }
   }

   @Override
   protected void func_202278_a(IWorld var1, BlockPos var2, IBlockState var3) {
      this.func_208521_b(☃, ☃, ☃);
   }

   protected final void func_208520_a(Set<BlockPos> var1, IWorld var2, BlockPos var3, IBlockState var4) {
      this.func_208521_b(☃, ☃, ☃);
      if (BlockTags.field_200031_h.func_199685_a_(☃.func_177230_c())) {
         ☃.add(☃.func_185334_h());
      }
   }

   private void func_208521_b(IWorld var1, BlockPos var2, IBlockState var3) {
      if (this.field_76488_a) {
         ☃.func_180501_a(☃, ☃, 19);
      } else {
         ☃.func_180501_a(☃, ☃, 18);
      }
   }

   @Override
   public final boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, T var5) {
      Set<BlockPos> ☃ = Sets.<BlockPos>newHashSet();
      boolean ☃x = this.func_208519_a(☃, ☃, ☃, ☃);
      List<Set<BlockPos>> ☃xx = Lists.newArrayList();
      int ☃xxx = 6;

      for(int ☃xxxx = 0; ☃xxxx < 6; ++☃xxxx) {
         ☃xx.add(Sets.newHashSet());
      }

      try (BlockPos.PooledMutableBlockPos ☃xxxx = BlockPos.PooledMutableBlockPos.func_185346_s()) {
         if (☃x && !☃.isEmpty()) {
            for(BlockPos ☃xxxxx : Lists.newArrayList(☃)) {
               for(EnumFacing ☃xxxxxx : EnumFacing.values()) {
                  ☃xxxx.func_189533_g(☃xxxxx).func_189536_c(☃xxxxxx);
                  if (!☃.contains(☃xxxx)) {
                     IBlockState ☃xxxxxxx = ☃.func_180495_p(☃xxxx);
                     if (☃xxxxxxx.func_196959_b(BlockStateProperties.field_208514_aa)) {
                        ((Set)☃xx.get(0)).add(☃xxxx.func_185334_h());
                        this.func_208521_b(☃, ☃xxxx, ☃xxxxxxx.func_206870_a(BlockStateProperties.field_208514_aa, Integer.valueOf(1)));
                     }
                  }
               }
            }
         }

         for(int ☃xxxxx = 1; ☃xxxxx < 6; ++☃xxxxx) {
            Set<BlockPos> ☃xxxxxx = (Set)☃xx.get(☃xxxxx - 1);
            Set<BlockPos> ☃xxxxxxx = (Set)☃xx.get(☃xxxxx);

            for(BlockPos ☃xxxxxxxx : ☃xxxxxx) {
               for(EnumFacing ☃xxxxxxxxx : EnumFacing.values()) {
                  ☃xxxx.func_189533_g(☃xxxxxxxx).func_189536_c(☃xxxxxxxxx);
                  if (!☃xxxxxx.contains(☃xxxx) && !☃xxxxxxx.contains(☃xxxx)) {
                     IBlockState ☃xxxxxxxxxx = ☃.func_180495_p(☃xxxx);
                     if (☃xxxxxxxxxx.func_196959_b(BlockStateProperties.field_208514_aa)) {
                        int ☃xxxxxxxxxxx = ☃xxxxxxxxxx.func_177229_b(BlockStateProperties.field_208514_aa);
                        if (☃xxxxxxxxxxx > ☃xxxxx + 1) {
                           IBlockState ☃xxxxxxxxxxxx = ☃xxxxxxxxxx.func_206870_a(BlockStateProperties.field_208514_aa, Integer.valueOf(☃xxxxx + 1));
                           this.func_208521_b(☃, ☃xxxx, ☃xxxxxxxxxxxx);
                           ☃xxxxxxx.add(☃xxxx.func_185334_h());
                        }
                     }
                  }
               }
            }
         }
      }

      return ☃x;
   }

   protected abstract boolean func_208519_a(Set<BlockPos> var1, IWorld var2, Random var3, BlockPos var4);
}
