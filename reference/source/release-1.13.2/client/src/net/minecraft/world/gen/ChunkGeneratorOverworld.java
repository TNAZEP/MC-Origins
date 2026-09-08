package net.minecraft.world.gen;

import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.PhantomSpawner;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.SwampHutStructure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkGeneratorOverworld extends AbstractChunkGenerator<OverworldGenSettings> {
   private static final Logger field_202111_e = LogManager.getLogger();
   private final NoiseGeneratorOctaves field_185991_j;
   private final NoiseGeneratorOctaves field_185992_k;
   private final NoiseGeneratorOctaves field_185993_l;
   private final NoiseGeneratorPerlin field_185994_m;
   private final OverworldGenSettings field_186000_s;
   private final NoiseGeneratorOctaves field_185983_b;
   private final NoiseGeneratorOctaves field_185984_c;
   private final WorldType field_185997_p;
   private final float[] field_185999_r;
   private final PhantomSpawner field_203230_r = new PhantomSpawner();
   private final IBlockState field_205475_r;
   private final IBlockState field_205476_s;

   public ChunkGeneratorOverworld(IWorld var1, BiomeProvider var2, OverworldGenSettings var3) {
      super(☃, ☃);
      this.field_185997_p = ☃.func_72912_H().func_76067_t();
      SharedSeedRandom ☃ = new SharedSeedRandom(this.field_202096_b);
      this.field_185991_j = new NoiseGeneratorOctaves(☃, 16);
      this.field_185992_k = new NoiseGeneratorOctaves(☃, 16);
      this.field_185993_l = new NoiseGeneratorOctaves(☃, 8);
      this.field_185994_m = new NoiseGeneratorPerlin(☃, 4);
      this.field_185983_b = new NoiseGeneratorOctaves(☃, 10);
      this.field_185984_c = new NoiseGeneratorOctaves(☃, 16);
      this.field_185999_r = new float[25];

      for(int ☃x = -2; ☃x <= 2; ++☃x) {
         for(int ☃xx = -2; ☃xx <= 2; ++☃xx) {
            float ☃xxx = 10.0F / MathHelper.func_76129_c((float)(☃x * ☃x + ☃xx * ☃xx) + 0.2F);
            this.field_185999_r[☃x + 2 + (☃xx + 2) * 5] = ☃xxx;
         }
      }

      this.field_186000_s = ☃;
      this.field_205475_r = this.field_186000_s.func_205532_l();
      this.field_205476_s = this.field_186000_s.func_205533_m();
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
      this.func_185976_a(☃x, ☃xx, ☃);
      ☃.func_201588_a(Heightmap.Type.WORLD_SURFACE_WG, Heightmap.Type.OCEAN_FLOOR_WG);
      this.func_205471_a(☃, ☃xxxx, ☃xxx, this.field_202095_a.func_181545_F());
      this.func_205472_a(☃, ☃xxx);
      ☃.func_201588_a(Heightmap.Type.WORLD_SURFACE_WG, Heightmap.Type.OCEAN_FLOOR_WG);
      ☃.func_201574_a(ChunkStatus.BASE);
   }

   @Override
   public void func_202093_c(WorldGenRegion var1) {
      int ☃ = ☃.func_201679_a();
      int ☃x = ☃.func_201680_b();
      Biome ☃xx = ☃.func_72964_e(☃, ☃x).func_201590_e()[0];
      SharedSeedRandom ☃xxx = new SharedSeedRandom();
      ☃xxx.func_202424_a(☃.func_72905_C(), ☃ << 4, ☃x << 4);
      WorldEntitySpawner.func_77191_a(☃, ☃xx, ☃, ☃x, ☃xxx);
   }

   public void func_185976_a(int var1, int var2, IChunk var3) {
      Biome[] ☃ = this.field_202097_c.func_201535_a(☃.func_76632_l().field_77276_a * 4 - 2, ☃.func_76632_l().field_77275_b * 4 - 2, 10, 10);
      double[] ☃x = new double[825];
      this.func_202108_a(☃, ☃.func_76632_l().field_77276_a * 4, 0, ☃.func_76632_l().field_77275_b * 4, ☃x);
      BlockPos.MutableBlockPos ☃xx = new BlockPos.MutableBlockPos();

      for(int ☃xxx = 0; ☃xxx < 4; ++☃xxx) {
         int ☃xxxx = ☃xxx * 5;
         int ☃xxxxx = (☃xxx + 1) * 5;

         for(int ☃xxxxxx = 0; ☃xxxxxx < 4; ++☃xxxxxx) {
            int ☃xxxxxxx = (☃xxxx + ☃xxxxxx) * 33;
            int ☃xxxxxxxx = (☃xxxx + ☃xxxxxx + 1) * 33;
            int ☃xxxxxxxxx = (☃xxxxx + ☃xxxxxx) * 33;
            int ☃xxxxxxxxxx = (☃xxxxx + ☃xxxxxx + 1) * 33;

            for(int ☃xxxxxxxxxxx = 0; ☃xxxxxxxxxxx < 32; ++☃xxxxxxxxxxx) {
               double ☃xxxxxxxxxxxx = 0.125;
               double ☃xxxxxxxxxxxxx = ☃x[☃xxxxxxx + ☃xxxxxxxxxxx];
               double ☃xxxxxxxxxxxxxx = ☃x[☃xxxxxxxx + ☃xxxxxxxxxxx];
               double ☃xxxxxxxxxxxxxxx = ☃x[☃xxxxxxxxx + ☃xxxxxxxxxxx];
               double ☃xxxxxxxxxxxxxxxx = ☃x[☃xxxxxxxxxx + ☃xxxxxxxxxxx];
               double ☃xxxxxxxxxxxxxxxxx = (☃x[☃xxxxxxx + ☃xxxxxxxxxxx + 1] - ☃xxxxxxxxxxxxx) * 0.125;
               double ☃xxxxxxxxxxxxxxxxxx = (☃x[☃xxxxxxxx + ☃xxxxxxxxxxx + 1] - ☃xxxxxxxxxxxxxx) * 0.125;
               double ☃xxxxxxxxxxxxxxxxxxx = (☃x[☃xxxxxxxxx + ☃xxxxxxxxxxx + 1] - ☃xxxxxxxxxxxxxxx) * 0.125;
               double ☃xxxxxxxxxxxxxxxxxxxx = (☃x[☃xxxxxxxxxx + ☃xxxxxxxxxxx + 1] - ☃xxxxxxxxxxxxxxxx) * 0.125;

               for(int ☃xxxxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxxxx < 8; ++☃xxxxxxxxxxxxxxxxxxxxx) {
                  double ☃xxxxxxxxxxxxxxxxxxxxxx = 0.25;
                  double ☃xxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx;
                  double ☃xxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxx;
                  double ☃xxxxxxxxxxxxxxxxxxxxxxxxx = (☃xxxxxxxxxxxxxxx - ☃xxxxxxxxxxxxx) * 0.25;
                  double ☃xxxxxxxxxxxxxxxxxxxxxxxxxx = (☃xxxxxxxxxxxxxxxx - ☃xxxxxxxxxxxxxx) * 0.25;

                  for(int ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx < 4; ++☃xxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                     double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.25;
                     double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (☃xxxxxxxxxxxxxxxxxxxxxxxx - ☃xxxxxxxxxxxxxxxxxxxxxxx) * 0.25;
                     double var48 = ☃xxxxxxxxxxxxxxxxxxxxxxx - ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx;

                     for(int ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < 4; ++☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                        ☃xx.func_181079_c(
                           ☃xxx * 4 + ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxx * 8 + ☃xxxxxxxxxxxxxxxxxxxxx, ☃xxxxxx * 4 + ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                        );
                        if ((var48 += ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx) > 0.0) {
                           ☃.func_177436_a(☃xx, this.field_205475_r, false);
                        } else if (☃xxxxxxxxxxx * 8 + ☃xxxxxxxxxxxxxxxxxxxxx < this.field_186000_s.func_202197_m()) {
                           ☃.func_177436_a(☃xx, this.field_205476_s, false);
                        }
                     }

                     ☃xxxxxxxxxxxxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxxxxxxxxxx;
                     ☃xxxxxxxxxxxxxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxxxxxxxxxxx;
                  }

                  ☃xxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxx;
                  ☃xxxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxxx;
                  ☃xxxxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxxxx;
                  ☃xxxxxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxxxxx;
               }
            }
         }
      }
   }

   private void func_202108_a(Biome[] var1, int var2, int var3, int var4, double[] var5) {
      double[] ☃ = this.field_185984_c
         .func_202646_a(☃, ☃, 5, 5, this.field_186000_s.func_202193_n(), this.field_186000_s.func_202194_o(), this.field_186000_s.func_202189_p());
      float ☃x = this.field_186000_s.func_202195_q();
      float ☃xx = this.field_186000_s.func_202196_r();
      double[] ☃xxx = this.field_185993_l
         .func_202647_a(
            ☃,
            ☃,
            ☃,
            5,
            33,
            5,
            (double)(☃x / this.field_186000_s.func_202192_s()),
            (double)(☃xx / this.field_186000_s.func_202190_t()),
            (double)(☃x / this.field_186000_s.func_202191_u())
         );
      double[] ☃xxxx = this.field_185991_j.func_202647_a(☃, ☃, ☃, 5, 33, 5, (double)☃x, (double)☃xx, (double)☃x);
      double[] ☃xxxxx = this.field_185992_k.func_202647_a(☃, ☃, ☃, 5, 33, 5, (double)☃x, (double)☃xx, (double)☃x);
      int ☃xxxxxx = 0;
      int ☃xxxxxxx = 0;

      for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < 5; ++☃xxxxxxxx) {
         for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < 5; ++☃xxxxxxxxx) {
            float ☃xxxxxxxxxx = 0.0F;
            float ☃xxxxxxxxxxx = 0.0F;
            float ☃xxxxxxxxxxxx = 0.0F;
            int ☃xxxxxxxxxxxxx = 2;
            Biome ☃xxxxxxxxxxxxxx = ☃[☃xxxxxxxx + 2 + (☃xxxxxxxxx + 2) * 10];

            for(int ☃xxxxxxxxxxxxxxx = -2; ☃xxxxxxxxxxxxxxx <= 2; ++☃xxxxxxxxxxxxxxx) {
               for(int ☃xxxxxxxxxxxxxxxx = -2; ☃xxxxxxxxxxxxxxxx <= 2; ++☃xxxxxxxxxxxxxxxx) {
                  Biome ☃xxxxxxxxxxxxxxxxx = ☃[☃xxxxxxxx + ☃xxxxxxxxxxxxxxx + 2 + (☃xxxxxxxxx + ☃xxxxxxxxxxxxxxxx + 2) * 10];
                  float ☃xxxxxxxxxxxxxxxxxx = this.field_186000_s.func_202203_v() + ☃xxxxxxxxxxxxxxxxx.func_185355_j() * this.field_186000_s.func_202202_w();
                  float ☃xxxxxxxxxxxxxxxxxxx = this.field_186000_s.func_202204_x() + ☃xxxxxxxxxxxxxxxxx.func_185360_m() * this.field_186000_s.func_202205_y();
                  if (this.field_185997_p == WorldType.field_151360_e && ☃xxxxxxxxxxxxxxxxxx > 0.0F) {
                     ☃xxxxxxxxxxxxxxxxxx = 1.0F + ☃xxxxxxxxxxxxxxxxxx * 2.0F;
                     ☃xxxxxxxxxxxxxxxxxxx = 1.0F + ☃xxxxxxxxxxxxxxxxxxx * 4.0F;
                  }

                  float ☃xxxxxxxxxxxxxxxxx = this.field_185999_r[☃xxxxxxxxxxxxxxx + 2 + (☃xxxxxxxxxxxxxxxx + 2) * 5] / (☃xxxxxxxxxxxxxxxxxx + 2.0F);
                  if (☃xxxxxxxxxxxxxxxxx.func_185355_j() > ☃xxxxxxxxxxxxxx.func_185355_j()) {
                     ☃xxxxxxxxxxxxxxxxx /= 2.0F;
                  }

                  ☃xxxxxxxxxx += ☃xxxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxx;
                  ☃xxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxx;
                  ☃xxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxx;
               }
            }

            ☃xxxxxxxxxx /= ☃xxxxxxxxxxxx;
            ☃xxxxxxxxxxx /= ☃xxxxxxxxxxxx;
            ☃xxxxxxxxxx = ☃xxxxxxxxxx * 0.9F + 0.1F;
            ☃xxxxxxxxxxx = (☃xxxxxxxxxxx * 4.0F - 1.0F) / 8.0F;
            double ☃xxxxxxxxxxxxxxx = ☃[☃xxxxxxx] / 8000.0;
            if (☃xxxxxxxxxxxxxxx < 0.0) {
               ☃xxxxxxxxxxxxxxx = -☃xxxxxxxxxxxxxxx * 0.3;
            }

            ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxx * 3.0 - 2.0;
            if (☃xxxxxxxxxxxxxxx < 0.0) {
               ☃xxxxxxxxxxxxxxx /= 2.0;
               if (☃xxxxxxxxxxxxxxx < -1.0) {
                  ☃xxxxxxxxxxxxxxx = -1.0;
               }

               ☃xxxxxxxxxxxxxxx /= 1.4;
               ☃xxxxxxxxxxxxxxx /= 2.0;
            } else {
               if (☃xxxxxxxxxxxxxxx > 1.0) {
                  ☃xxxxxxxxxxxxxxx = 1.0;
               }

               ☃xxxxxxxxxxxxxxx /= 8.0;
            }

            ++☃xxxxxxx;
            double ☃xxxxxxxxxxxxxxx = (double)☃xxxxxxxxxxx;
            double ☃xxxxxxxxxxxxxxxx = (double)☃xxxxxxxxxx;
            ☃xxxxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxx * 0.2;
            ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxx * this.field_186000_s.func_202201_z() / 8.0;
            double ☃xxxxxxxxxxxxxxxxx = this.field_186000_s.func_202201_z() + ☃xxxxxxxxxxxxxxx * 4.0;

            for(int ☃xxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxx < 33; ++☃xxxxxxxxxxxxxxxxxx) {
               double ☃xxxxxxxxxxxxxxxxxxx = ((double)☃xxxxxxxxxxxxxxxxxx - ☃xxxxxxxxxxxxxxxxx)
                  * this.field_186000_s.func_202206_A()
                  * 128.0
                  / 256.0
                  / ☃xxxxxxxxxxxxxxxx;
               if (☃xxxxxxxxxxxxxxxxxxx < 0.0) {
                  ☃xxxxxxxxxxxxxxxxxxx *= 4.0;
               }

               double ☃xxxxxxxxxxxxxxxxxxx = ☃xxxx[☃xxxxxx] / this.field_186000_s.func_202207_B();
               double ☃xxxxxxxxxxxxxxxxxxxx = ☃xxxxx[☃xxxxxx] / this.field_186000_s.func_202208_C();
               double ☃xxxxxxxxxxxxxxxxxxxxx = (☃xxx[☃xxxxxx] / 10.0 + 1.0) / 2.0;
               double ☃xxxxxxxxxxxxxxxxxxxxxx = MathHelper.func_151238_b(☃xxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxx)
                  - ☃xxxxxxxxxxxxxxxxxxx;
               if (☃xxxxxxxxxxxxxxxxxx > 29) {
                  double ☃xxxxxxxxxxxxxxxxxxxxxxx = (double)((float)(☃xxxxxxxxxxxxxxxxxx - 29) / 3.0F);
                  ☃xxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxx * (1.0 - ☃xxxxxxxxxxxxxxxxxxxxxxx) - 10.0 * ☃xxxxxxxxxxxxxxxxxxxxxxx;
               }

               ☃[☃xxxxxx] = ☃xxxxxxxxxxxxxxxxxxxxxx;
               ++☃xxxxxx;
            }
         }
      }
   }

   @Override
   public List<Biome.SpawnListEntry> func_177458_a(EnumCreatureType var1, BlockPos var2) {
      Biome ☃ = this.field_202095_a.func_180494_b(☃);
      if (☃ == EnumCreatureType.MONSTER && ((SwampHutStructure)Feature.field_202334_l).func_202383_b(this.field_202095_a, ☃)) {
         return Feature.field_202334_l.func_202279_e();
      } else {
         return ☃ == EnumCreatureType.MONSTER && Feature.field_202336_n.func_175796_a(this.field_202095_a, ☃)
            ? Feature.field_202336_n.func_202279_e()
            : ☃.func_76747_a(☃);
      }
   }

   @Override
   public int func_203222_a(World var1, boolean var2, boolean var3) {
      int ☃ = 0;
      return ☃ + this.field_203230_r.func_203232_a(☃, ☃, ☃);
   }

   public OverworldGenSettings func_201496_a_() {
      return this.field_186000_s;
   }

   @Override
   public double[] func_205473_a(int var1, int var2) {
      double ☃ = 0.03125;
      return this.field_185994_m.func_202644_a((double)(☃ << 4), (double)(☃ << 4), 16, 16, 0.0625, 0.0625, 1.0);
   }

   @Override
   public int func_205470_d() {
      return this.field_202095_a.func_181545_F() + 1;
   }
}
