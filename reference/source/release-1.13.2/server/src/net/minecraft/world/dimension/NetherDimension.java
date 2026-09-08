package net.minecraft.world.dimension;

import javax.annotation.Nullable;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NetherGenSettings;

public class NetherDimension extends Dimension {
   @Override
   public void func_76572_b() {
      this.field_76575_d = true;
      this.field_76576_e = true;
      this.field_191067_f = false;
   }

   @Override
   protected void func_76556_a() {
      float ☃ = 0.1F;

      for(int ☃x = 0; ☃x <= 15; ++☃x) {
         float ☃xx = 1.0F - (float)☃x / 15.0F;
         this.field_76573_f[☃x] = (1.0F - ☃xx) / (☃xx * 3.0F + 1.0F) * 0.9F + 0.1F;
      }
   }

   @Override
   public IChunkGenerator<?> func_186060_c() {
      NetherGenSettings ☃ = ChunkGeneratorType.field_206912_c.func_205483_a();
      ☃.func_205535_a(Blocks.field_150424_aL.func_176223_P());
      ☃.func_205534_b(Blocks.field_150353_l.func_176223_P());
      return ChunkGeneratorType.field_206912_c
         .create(
            this.field_76579_a,
            BiomeProviderType.field_205461_c.func_205457_a(BiomeProviderType.field_205461_c.func_205458_a().func_205436_a(Biomes.field_76778_j)),
            ☃
         );
   }

   @Override
   public boolean func_76569_d() {
      return false;
   }

   @Nullable
   @Override
   public BlockPos func_206920_a(ChunkPos var1, boolean var2) {
      return null;
   }

   @Nullable
   @Override
   public BlockPos func_206921_a(int var1, int var2, boolean var3) {
      return null;
   }

   @Override
   public float func_76563_a(long var1, float var3) {
      return 0.5F;
   }

   @Override
   public boolean func_76567_e() {
      return false;
   }

   @Override
   public WorldBorder func_177501_r() {
      return new WorldBorder() {
         @Override
         public double func_177731_f() {
            return super.func_177731_f() / 8.0;
         }

         @Override
         public double func_177721_g() {
            return super.func_177721_g() / 8.0;
         }
      };
   }

   @Override
   public DimensionType func_186058_p() {
      return DimensionType.NETHER;
   }
}
