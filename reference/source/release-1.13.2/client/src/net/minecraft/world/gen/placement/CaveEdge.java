package net.minecraft.world.gen.placement;

import java.util.BitSet;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class CaveEdge extends BasePlacement<CaveEdgeConfig> {
   public <C extends IFeatureConfig> boolean func_201491_a_(
      IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, CaveEdgeConfig var5, Feature<C> var6, C var7
   ) {
      IChunk ☃ = ☃.func_205771_y(☃);
      ChunkPos ☃x = ☃.func_76632_l();
      BitSet ☃xx = ☃.func_205749_a(☃.field_206928_a);

      for(int ☃xxx = 0; ☃xxx < ☃xx.length(); ++☃xxx) {
         if (☃xx.get(☃xxx) && ☃.nextFloat() < ☃.field_206929_b) {
            int ☃xxxx = ☃xxx & 15;
            int ☃xxxxx = ☃xxx >> 4 & 15;
            int ☃xxxxxx = ☃xxx >> 8;
            ☃.func_212245_a(☃, ☃, ☃, new BlockPos(☃x.func_180334_c() + ☃xxxx, ☃xxxxxx, ☃x.func_180333_d() + ☃xxxxx), ☃);
         }
      }

      return true;
   }
}
