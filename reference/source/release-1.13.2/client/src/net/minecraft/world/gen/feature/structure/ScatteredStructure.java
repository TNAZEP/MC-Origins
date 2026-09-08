package net.minecraft.world.gen.feature.structure;

import java.util.Random;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.IFeatureConfig;

public abstract class ScatteredStructure<C extends IFeatureConfig> extends Structure<C> {
   @Override
   protected ChunkPos func_211744_a(IChunkGenerator<?> var1, Random var2, int var3, int var4, int var5, int var6) {
      int ☃ = this.func_204030_a(☃);
      int ☃x = this.func_211745_b(☃);
      int ☃xx = ☃ + ☃ * ☃;
      int ☃xxx = ☃ + ☃ * ☃;
      int ☃xxxx = ☃xx < 0 ? ☃xx - ☃ + 1 : ☃xx;
      int ☃xxxxx = ☃xxx < 0 ? ☃xxx - ☃ + 1 : ☃xxx;
      int ☃xxxxxx = ☃xxxx / ☃;
      int ☃xxxxxxx = ☃xxxxx / ☃;
      ((SharedSeedRandom)☃).func_202427_a(☃.func_202089_c(), ☃xxxxxx, ☃xxxxxxx, this.func_202382_c());
      ☃xxxxxx *= ☃;
      ☃xxxxxxx *= ☃;
      ☃xxxxxx += ☃.nextInt(☃ - ☃x);
      ☃xxxxxxx += ☃.nextInt(☃ - ☃x);
      return new ChunkPos(☃xxxxxx, ☃xxxxxxx);
   }

   @Override
   protected boolean func_202372_a(IChunkGenerator<?> var1, Random var2, int var3, int var4) {
      ChunkPos ☃ = this.func_211744_a(☃, ☃, ☃, ☃, 0, 0);
      if (☃ == ☃.field_77276_a && ☃ == ☃.field_77275_b) {
         Biome ☃x = ☃.func_202090_b().func_180300_a(new BlockPos(☃ * 16 + 9, 0, ☃ * 16 + 9), null);
         if (☃.func_202094_a(☃x, this)) {
            return true;
         }
      }

      return false;
   }

   protected int func_204030_a(IChunkGenerator<?> var1) {
      return ☃.func_201496_a_().func_202177_g();
   }

   protected int func_211745_b(IChunkGenerator<?> var1) {
      return ☃.func_201496_a_().func_211731_i();
   }

   @Override
   protected boolean func_202365_a(IWorld var1) {
      return ☃.func_72912_H().func_76089_r();
   }

   @Override
   protected abstract StructureStart func_202369_a(IWorld var1, IChunkGenerator<?> var2, SharedSeedRandom var3, int var4, int var5);

   protected abstract int func_202382_c();
}
