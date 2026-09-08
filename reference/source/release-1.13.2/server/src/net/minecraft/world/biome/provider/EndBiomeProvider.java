package net.minecraft.world.biome.provider;

import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.NoiseGeneratorSimplex;
import net.minecraft.world.gen.feature.structure.Structure;

public class EndBiomeProvider extends BiomeProvider {
   private final NoiseGeneratorSimplex field_201546_a;
   private final SharedSeedRandom field_201547_b;
   private final Biome[] field_205009_d = new Biome[]{
      Biomes.field_76779_k, Biomes.field_201938_R, Biomes.field_201937_Q, Biomes.field_201936_P, Biomes.field_201939_S
   };

   public EndBiomeProvider(EndBiomeProviderSettings var1) {
      this.field_201547_b = new SharedSeedRandom(☃.func_205445_a());
      this.field_201547_b.func_202423_a(17292);
      this.field_201546_a = new NoiseGeneratorSimplex(this.field_201547_b);
   }

   @Nullable
   @Override
   public Biome func_180300_a(BlockPos var1, @Nullable Biome var2) {
      return this.func_201545_a(☃.func_177958_n() >> 4, ☃.func_177952_p() >> 4);
   }

   private Biome func_201545_a(int var1, int var2) {
      if ((long)☃ * (long)☃ + (long)☃ * (long)☃ <= 4096L) {
         return Biomes.field_76779_k;
      } else {
         float ☃ = this.func_201536_c(☃, ☃, 1, 1);
         if (☃ > 40.0F) {
            return Biomes.field_201938_R;
         } else if (☃ >= 0.0F) {
            return Biomes.field_201937_Q;
         } else {
            return ☃ < -20.0F ? Biomes.field_201936_P : Biomes.field_201939_S;
         }
      }
   }

   @Override
   public Biome[] func_201535_a(int var1, int var2, int var3, int var4) {
      return this.func_201539_b(☃, ☃, ☃, ☃);
   }

   @Override
   public Biome[] func_201537_a(int var1, int var2, int var3, int var4, boolean var5) {
      Biome[] ☃ = new Biome[☃ * ☃];
      Long2ObjectMap<Biome> ☃x = new Long2ObjectOpenHashMap<>();

      for(int ☃xx = 0; ☃xx < ☃; ++☃xx) {
         for(int ☃xxx = 0; ☃xxx < ☃; ++☃xxx) {
            int ☃xxxx = ☃xx + ☃ >> 4;
            int ☃xxxxx = ☃xxx + ☃ >> 4;
            long ☃xxxxxx = ChunkPos.func_77272_a(☃xxxx, ☃xxxxx);
            Biome ☃xxxxxxx = ☃x.get(☃xxxxxx);
            if (☃xxxxxxx == null) {
               ☃xxxxxxx = this.func_201545_a(☃xxxx, ☃xxxxx);
               ☃x.put(☃xxxxxx, ☃xxxxxxx);
            }

            ☃[☃xx + ☃xxx * ☃] = ☃xxxxxxx;
         }
      }

      return ☃;
   }

   @Override
   public Set<Biome> func_201538_a(int var1, int var2, int var3) {
      int ☃ = ☃ - ☃ >> 2;
      int ☃x = ☃ - ☃ >> 2;
      int ☃xx = ☃ + ☃ >> 2;
      int ☃xxx = ☃ + ☃ >> 2;
      int ☃xxxx = ☃xx - ☃ + 1;
      int ☃xxxxx = ☃xxx - ☃x + 1;
      return Sets.<Biome>newHashSet(this.func_201539_b(☃, ☃x, ☃xxxx, ☃xxxxx));
   }

   @Nullable
   @Override
   public BlockPos func_180630_a(int var1, int var2, int var3, List<Biome> var4, Random var5) {
      int ☃ = ☃ - ☃ >> 2;
      int ☃x = ☃ - ☃ >> 2;
      int ☃xx = ☃ + ☃ >> 2;
      int ☃xxx = ☃ + ☃ >> 2;
      int ☃xxxx = ☃xx - ☃ + 1;
      int ☃xxxxx = ☃xxx - ☃x + 1;
      Biome[] ☃xxxxxx = this.func_201539_b(☃, ☃x, ☃xxxx, ☃xxxxx);
      BlockPos ☃xxxxxxx = null;
      int ☃xxxxxxxx = 0;

      for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < ☃xxxx * ☃xxxxx; ++☃xxxxxxxxx) {
         int ☃xxxxxxxxxx = ☃ + ☃xxxxxxxxx % ☃xxxx << 2;
         int ☃xxxxxxxxxxx = ☃x + ☃xxxxxxxxx / ☃xxxx << 2;
         if (☃.contains(☃xxxxxx[☃xxxxxxxxx])) {
            if (☃xxxxxxx == null || ☃.nextInt(☃xxxxxxxx + 1) == 0) {
               ☃xxxxxxx = new BlockPos(☃xxxxxxxxxx, 0, ☃xxxxxxxxxxx);
            }

            ++☃xxxxxxxx;
         }
      }

      return ☃xxxxxxx;
   }

   @Override
   public float func_201536_c(int var1, int var2, int var3, int var4) {
      float ☃ = (float)(☃ * 2 + ☃);
      float ☃x = (float)(☃ * 2 + ☃);
      float ☃xx = 100.0F - MathHelper.func_76129_c(☃ * ☃ + ☃x * ☃x) * 8.0F;
      ☃xx = MathHelper.func_76131_a(☃xx, -100.0F, 80.0F);

      for(int ☃xxx = -12; ☃xxx <= 12; ++☃xxx) {
         for(int ☃xxxx = -12; ☃xxxx <= 12; ++☃xxxx) {
            long ☃xxxxx = (long)(☃ + ☃xxx);
            long ☃xxxxxx = (long)(☃ + ☃xxxx);
            if (☃xxxxx * ☃xxxxx + ☃xxxxxx * ☃xxxxxx > 4096L && this.field_201546_a.func_151605_a((double)☃xxxxx, (double)☃xxxxxx) < -0.9F) {
               float ☃xxxxxxx = (MathHelper.func_76135_e((float)☃xxxxx) * 3439.0F + MathHelper.func_76135_e((float)☃xxxxxx) * 147.0F) % 13.0F + 9.0F;
               ☃ = (float)(☃ - ☃xxx * 2);
               ☃x = (float)(☃ - ☃xxxx * 2);
               float ☃xxxxxxxx = 100.0F - MathHelper.func_76129_c(☃ * ☃ + ☃x * ☃x) * ☃xxxxxxx;
               ☃xxxxxxxx = MathHelper.func_76131_a(☃xxxxxxxx, -100.0F, 80.0F);
               ☃xx = Math.max(☃xx, ☃xxxxxxxx);
            }
         }
      }

      return ☃xx;
   }

   @Override
   public boolean func_205004_a(Structure<?> var1) {
      return this.field_205005_a.computeIfAbsent(☃, var1x -> {
         for(Biome ☃ : this.field_205009_d) {
            if (☃.func_201858_a(var1x)) {
               return true;
            }
         }

         return false;
      });
   }

   @Override
   public Set<IBlockState> func_205706_b() {
      if (this.field_205707_b.isEmpty()) {
         for(Biome ☃ : this.field_205009_d) {
            this.field_205707_b.add(☃.func_203944_q().func_204108_a());
         }
      }

      return this.field_205707_b;
   }
}
