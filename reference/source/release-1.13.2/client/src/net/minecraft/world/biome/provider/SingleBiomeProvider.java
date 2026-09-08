package net.minecraft.world.biome.provider;

import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.structure.Structure;

public class SingleBiomeProvider extends BiomeProvider {
   private final Biome field_76947_d;

   public SingleBiomeProvider(SingleBiomeProviderSettings var1) {
      this.field_76947_d = ☃.func_205437_a();
   }

   @Override
   public Biome func_180300_a(BlockPos var1, @Nullable Biome var2) {
      return this.field_76947_d;
   }

   @Override
   public Biome[] func_201535_a(int var1, int var2, int var3, int var4) {
      return this.func_201539_b(☃, ☃, ☃, ☃);
   }

   @Override
   public Biome[] func_201537_a(int var1, int var2, int var3, int var4, boolean var5) {
      Biome[] ☃ = new Biome[☃ * ☃];
      Arrays.fill(☃, 0, ☃ * ☃, this.field_76947_d);
      return ☃;
   }

   @Nullable
   @Override
   public BlockPos func_180630_a(int var1, int var2, int var3, List<Biome> var4, Random var5) {
      return ☃.contains(this.field_76947_d) ? new BlockPos(☃ - ☃ + ☃.nextInt(☃ * 2 + 1), 0, ☃ - ☃ + ☃.nextInt(☃ * 2 + 1)) : null;
   }

   @Override
   public boolean func_205004_a(Structure<?> var1) {
      return this.field_205005_a.computeIfAbsent(☃, this.field_76947_d::func_201858_a);
   }

   @Override
   public Set<IBlockState> func_205706_b() {
      if (this.field_205707_b.isEmpty()) {
         this.field_205707_b.add(this.field_76947_d.func_203944_q().func_204108_a());
      }

      return this.field_205707_b;
   }

   @Override
   public Set<Biome> func_201538_a(int var1, int var2, int var3) {
      return Sets.<Biome>newHashSet(this.field_76947_d);
   }
}
