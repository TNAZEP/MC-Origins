package net.minecraft.world.biome.provider;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.structure.Structure;

public class CheckerboardBiomeProvider extends BiomeProvider {
   private final Biome[] field_205320_b;
   private final int field_205321_c;

   public CheckerboardBiomeProvider(CheckerboardBiomeProviderSettings var1) {
      this.field_205320_b = ☃.func_205432_a();
      this.field_205321_c = ☃.func_205433_b() + 4;
   }

   @Override
   public Biome func_180300_a(BlockPos var1, @Nullable Biome var2) {
      return this.field_205320_b[Math.abs(
         ((☃.func_177958_n() >> this.field_205321_c) + (☃.func_177952_p() >> this.field_205321_c)) % this.field_205320_b.length
      )];
   }

   @Override
   public Biome[] func_201535_a(int var1, int var2, int var3, int var4) {
      return this.func_201539_b(☃, ☃, ☃, ☃);
   }

   @Override
   public Biome[] func_201537_a(int var1, int var2, int var3, int var4, boolean var5) {
      Biome[] ☃ = new Biome[☃ * ☃];

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         for(int ☃xx = 0; ☃xx < ☃; ++☃xx) {
            int ☃xxx = Math.abs(((☃ + ☃x >> this.field_205321_c) + (☃ + ☃xx >> this.field_205321_c)) % this.field_205320_b.length);
            Biome ☃xxxx = this.field_205320_b[☃xxx];
            ☃[☃x * ☃ + ☃xx] = ☃xxxx;
         }
      }

      return ☃;
   }

   @Nullable
   @Override
   public BlockPos func_180630_a(int var1, int var2, int var3, List<Biome> var4, Random var5) {
      return null;
   }

   @Override
   public boolean func_205004_a(Structure<?> var1) {
      return this.field_205005_a.computeIfAbsent(☃, var1x -> {
         for(Biome ☃ : this.field_205320_b) {
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
         for(Biome ☃ : this.field_205320_b) {
            this.field_205707_b.add(☃.func_203944_q().func_204108_a());
         }
      }

      return this.field_205707_b;
   }

   @Override
   public Set<Biome> func_201538_a(int var1, int var2, int var3) {
      return Sets.<Biome>newHashSet(this.field_205320_b);
   }
}
