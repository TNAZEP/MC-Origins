package net.minecraft.world.gen;

import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;

public class ChunkGeneratorEnd extends AbstractChunkGenerator<EndGenSettings> {
   protected static final IBlockState field_185965_b = Blocks.field_150350_a.func_176223_P();
   private final NoiseGeneratorOctaves field_185969_i;
   private final NoiseGeneratorOctaves field_185970_j;
   private final NoiseGeneratorOctaves field_185971_k;
   private final NoiseGeneratorOctaves field_73214_a;
   private final NoiseGeneratorOctaves field_73212_b;
   private final NoiseGeneratorPerlin field_205478_l;
   private final BlockPos field_191061_n;
   private final EndGenSettings field_202116_l;
   private final IBlockState field_205479_o;
   private final IBlockState field_205477_p;

   public ChunkGeneratorEnd(IWorld var1, BiomeProvider var2, EndGenSettings var3) {
      super(☃, ☃);
      this.field_202116_l = ☃;
      this.field_205479_o = this.field_202116_l.func_205532_l();
      this.field_205477_p = this.field_202116_l.func_205533_m();
      this.field_191061_n = ☃.func_205539_n();
      SharedSeedRandom ☃ = new SharedSeedRandom(this.field_202096_b);
      this.field_185969_i = new NoiseGeneratorOctaves(☃, 16);
      this.field_185970_j = new NoiseGeneratorOctaves(☃, 16);
      this.field_185971_k = new NoiseGeneratorOctaves(☃, 8);
      this.field_73214_a = new NoiseGeneratorOctaves(☃, 10);
      this.field_73212_b = new NoiseGeneratorOctaves(☃, 16);
      ☃.func_202423_a(262);
      this.field_205478_l = new NoiseGeneratorPerlin(new SharedSeedRandom(this.field_202096_b), 4);
   }

   public void func_202114_a(int var1, int var2, IChunk var3) {
      int ☃ = 2;
      int ☃x = 3;
      int ☃xx = 33;
      int ☃xxx = 3;
      double[] ☃xxxx = this.func_202113_a(☃ * 2, 0, ☃ * 2, 3, 33, 3);
      BlockPos.MutableBlockPos ☃xxxxx = new BlockPos.MutableBlockPos();

      for(int ☃xxxxxx = 0; ☃xxxxxx < 2; ++☃xxxxxx) {
         for(int ☃xxxxxxx = 0; ☃xxxxxxx < 2; ++☃xxxxxxx) {
            for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < 32; ++☃xxxxxxxx) {
               double ☃xxxxxxxxx = 0.25;
               double ☃xxxxxxxxxx = ☃xxxx[((☃xxxxxx + 0) * 3 + ☃xxxxxxx + 0) * 33 + ☃xxxxxxxx + 0];
               double ☃xxxxxxxxxxx = ☃xxxx[((☃xxxxxx + 0) * 3 + ☃xxxxxxx + 1) * 33 + ☃xxxxxxxx + 0];
               double ☃xxxxxxxxxxxx = ☃xxxx[((☃xxxxxx + 1) * 3 + ☃xxxxxxx + 0) * 33 + ☃xxxxxxxx + 0];
               double ☃xxxxxxxxxxxxx = ☃xxxx[((☃xxxxxx + 1) * 3 + ☃xxxxxxx + 1) * 33 + ☃xxxxxxxx + 0];
               double ☃xxxxxxxxxxxxxx = (☃xxxx[((☃xxxxxx + 0) * 3 + ☃xxxxxxx + 0) * 33 + ☃xxxxxxxx + 1] - ☃xxxxxxxxxx) * 0.25;
               double ☃xxxxxxxxxxxxxxx = (☃xxxx[((☃xxxxxx + 0) * 3 + ☃xxxxxxx + 1) * 33 + ☃xxxxxxxx + 1] - ☃xxxxxxxxxxx) * 0.25;
               double ☃xxxxxxxxxxxxxxxx = (☃xxxx[((☃xxxxxx + 1) * 3 + ☃xxxxxxx + 0) * 33 + ☃xxxxxxxx + 1] - ☃xxxxxxxxxxxx) * 0.25;
               double ☃xxxxxxxxxxxxxxxxx = (☃xxxx[((☃xxxxxx + 1) * 3 + ☃xxxxxxx + 1) * 33 + ☃xxxxxxxx + 1] - ☃xxxxxxxxxxxxx) * 0.25;

               for(int ☃xxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxx < 4; ++☃xxxxxxxxxxxxxxxxxx) {
                  double ☃xxxxxxxxxxxxxxxxxxx = 0.125;
                  double ☃xxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxx;
                  double ☃xxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxx;
                  double ☃xxxxxxxxxxxxxxxxxxxxxx = (☃xxxxxxxxxxxx - ☃xxxxxxxxxx) * 0.125;
                  double ☃xxxxxxxxxxxxxxxxxxxxxxx = (☃xxxxxxxxxxxxx - ☃xxxxxxxxxxx) * 0.125;

                  for(int ☃xxxxxxxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxxxxxxx < 8; ++☃xxxxxxxxxxxxxxxxxxxxxxxx) {
                     double ☃xxxxxxxxxxxxxxxxxxxxxxxxx = 0.125;
                     double ☃xxxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxx;
                     double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx = (☃xxxxxxxxxxxxxxxxxxxxx - ☃xxxxxxxxxxxxxxxxxxxx) * 0.125;

                     for(int ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx < 8; ++☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                        IBlockState ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx = field_185965_b;
                        if (☃xxxxxxxxxxxxxxxxxxxxxxxxxx > 0.0) {
                           ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.field_205479_o;
                        }

                        int ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxx + ☃xxxxxx * 8;
                        int ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxx + ☃xxxxxxxx * 4;
                        int ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx + ☃xxxxxxx * 8;
                        ☃.func_177436_a(
                           ☃xxxxx.func_181079_c(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx),
                           ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                           false
                        );
                        ☃xxxxxxxxxxxxxxxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx;
                     }

                     ☃xxxxxxxxxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxxxxxxx;
                     ☃xxxxxxxxxxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxxxxxxxx;
                  }

                  ☃xxxxxxxxxx += ☃xxxxxxxxxxxxxx;
                  ☃xxxxxxxxxxx += ☃xxxxxxxxxxxxxxx;
                  ☃xxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxx;
                  ☃xxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxx;
               }
            }
         }
      }
   }

   @Override
   public void func_202088_a(IChunk var1) {
      ChunkPos ☃ = ☃.func_76632_l();
      int ☃x = ☃.field_77276_a;
      int ☃xx = ☃.field_77275_b;
      SharedSeedRandom ☃xxx = new SharedSeedRandom();
      ☃xxx.func_202422_a(☃x, ☃xx);
      Biome[] ☃xxxx = this.field_202097_c.func_201539_b(☃x * 16, ☃xx * 16, 16, 16);
      ☃.func_201577_a(☃xxxx);
      this.func_202114_a(☃x, ☃xx, ☃);
      this.func_205471_a(☃, ☃xxxx, ☃xxx, 0);
      ☃.func_201588_a(Heightmap.Type.WORLD_SURFACE_WG, Heightmap.Type.OCEAN_FLOOR_WG);
      ☃.func_201574_a(ChunkStatus.BASE);
   }

   private double[] func_202113_a(int var1, int var2, int var3, int var4, int var5, int var6) {
      double[] ☃ = new double[☃ * ☃ * ☃];
      double ☃x = 684.412;
      double ☃xx = 684.412;
      ☃x *= 2.0;
      double[] ☃xxx = this.field_185971_k.func_202647_a(☃, ☃, ☃, ☃, ☃, ☃, ☃x / 80.0, 4.277575000000001, ☃x / 80.0);
      double[] ☃xxxx = this.field_185969_i.func_202647_a(☃, ☃, ☃, ☃, ☃, ☃, ☃x, 684.412, ☃x);
      double[] ☃xxxxx = this.field_185970_j.func_202647_a(☃, ☃, ☃, ☃, ☃, ☃, ☃x, 684.412, ☃x);
      int ☃xxxxxx = ☃ / 2;
      int ☃xxxxxxx = ☃ / 2;
      int ☃xxxxxxxx = 0;

      for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < ☃; ++☃xxxxxxxxx) {
         for(int ☃xxxxxxxxxx = 0; ☃xxxxxxxxxx < ☃; ++☃xxxxxxxxxx) {
            float ☃xxxxxxxxxxx = this.field_202097_c.func_201536_c(☃xxxxxx, ☃xxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx);

            for(int ☃xxxxxxxxxxxx = 0; ☃xxxxxxxxxxxx < ☃; ++☃xxxxxxxxxxxx) {
               double ☃xxxxxxxxxxxxxx = ☃xxxx[☃xxxxxxxx] / 512.0;
               double ☃xxxxxxxxxxxxxxx = ☃xxxxx[☃xxxxxxxx] / 512.0;
               double ☃xxxxxxxxxxxxxxxx = (☃xxx[☃xxxxxxxx] / 10.0 + 1.0) / 2.0;
               double ☃xxxxxxxxxxxxx;
               if (☃xxxxxxxxxxxxxxxx < 0.0) {
                  ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxxxx;
               } else if (☃xxxxxxxxxxxxxxxx > 1.0) {
                  ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxx;
               } else {
                  ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxxxx + (☃xxxxxxxxxxxxxxx - ☃xxxxxxxxxxxxxx) * ☃xxxxxxxxxxxxxxxx;
               }

               ☃xxxxxxxxxxxxx -= 8.0;
               ☃xxxxxxxxxxxxx += (double)☃xxxxxxxxxxx;
               int ☃xxxxxxxxxxxxx = 2;
               if (☃xxxxxxxxxxxx > ☃ / 2 - ☃xxxxxxxxxxxxx) {
                  double ☃xxxxxxxxxxxxxx = (double)((float)(☃xxxxxxxxxxxx - (☃ / 2 - ☃xxxxxxxxxxxxx)) / 64.0F);
                  ☃xxxxxxxxxxxxxx = MathHelper.func_151237_a(☃xxxxxxxxxxxxxx, 0.0, 1.0);
                  ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxxx * (1.0 - ☃xxxxxxxxxxxxxx) - 3000.0 * ☃xxxxxxxxxxxxxx;
               }

               int var36 = 8;
               if (☃xxxxxxxxxxxx < var36) {
                  double ☃xxxxxxxxxxxxx = (double)((float)(var36 - ☃xxxxxxxxxxxx) / ((float)var36 - 1.0F));
                  ☃xxxxxxxxxxxxx = ☃xxxxxxxxxxxxx * (1.0 - ☃xxxxxxxxxxxxx) - 30.0 * ☃xxxxxxxxxxxxx;
               }

               ☃[☃xxxxxxxx] = ☃xxxxxxxxxxxxx;
               ++☃xxxxxxxx;
            }
         }
      }

      return ☃;
   }

   @Override
   public void func_202093_c(WorldGenRegion var1) {
   }

   @Override
   public List<Biome.SpawnListEntry> func_177458_a(EnumCreatureType var1, BlockPos var2) {
      return this.field_202095_a.func_180494_b(☃).func_76747_a(☃);
   }

   public BlockPos func_202112_d() {
      return this.field_191061_n;
   }

   @Override
   public int func_203222_a(World var1, boolean var2, boolean var3) {
      return 0;
   }

   public EndGenSettings func_201496_a_() {
      return this.field_202116_l;
   }

   @Override
   public double[] func_205473_a(int var1, int var2) {
      double ☃ = 0.03125;
      return this.field_205478_l.func_202644_a((double)(☃ << 4), (double)(☃ << 4), 16, 16, 0.0625, 0.0625, 1.0);
   }

   @Override
   public int func_205470_d() {
      return 50;
   }
}
