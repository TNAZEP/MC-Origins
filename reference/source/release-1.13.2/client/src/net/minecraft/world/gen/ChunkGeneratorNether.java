package net.minecraft.world.gen;

import java.util.List;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.feature.Feature;

public class ChunkGeneratorNether extends AbstractChunkGenerator<NetherGenSettings> {
   protected static final IBlockState field_185940_a = Blocks.field_150350_a.func_176223_P();
   protected static final IBlockState field_185941_b = Blocks.field_150424_aL.func_176223_P();
   protected static final IBlockState field_185943_d = Blocks.field_150353_l.func_176223_P();
   private final NoiseGeneratorOctaves field_185957_u;
   private final NoiseGeneratorOctaves field_185958_v;
   private final NoiseGeneratorOctaves field_185959_w;
   private final NoiseGeneratorOctaves field_73177_m;
   private final NoiseGeneratorOctaves field_185946_g;
   private final NoiseGeneratorOctaves field_185947_h;
   private final NetherGenSettings field_202107_q;
   private final IBlockState field_205474_p;
   private final IBlockState field_205604_n;

   public ChunkGeneratorNether(World var1, BiomeProvider var2, NetherGenSettings var3) {
      super(☃, ☃);
      this.field_202107_q = ☃;
      this.field_205474_p = this.field_202107_q.func_205532_l();
      this.field_205604_n = this.field_202107_q.func_205533_m();
      SharedSeedRandom ☃ = new SharedSeedRandom(this.field_202096_b);
      this.field_185957_u = new NoiseGeneratorOctaves(☃, 16);
      this.field_185958_v = new NoiseGeneratorOctaves(☃, 16);
      this.field_185959_w = new NoiseGeneratorOctaves(☃, 8);
      ☃.func_202423_a(1048);
      this.field_73177_m = new NoiseGeneratorOctaves(☃, 4);
      this.field_185946_g = new NoiseGeneratorOctaves(☃, 10);
      this.field_185947_h = new NoiseGeneratorOctaves(☃, 16);
      ☃.func_181544_b(63);
   }

   public void func_185936_a(int var1, int var2, IChunk var3) {
      int ☃ = 4;
      int ☃x = this.field_202095_a.func_181545_F() / 2 + 1;
      int ☃xx = 5;
      int ☃xxx = 17;
      int ☃xxxx = 5;
      double[] ☃xxxxx = this.func_202104_a(☃ * 4, 0, ☃ * 4, 5, 17, 5);
      BlockPos.MutableBlockPos ☃xxxxxx = new BlockPos.MutableBlockPos();

      for(int ☃xxxxxxx = 0; ☃xxxxxxx < 4; ++☃xxxxxxx) {
         for(int ☃xxxxxxxx = 0; ☃xxxxxxxx < 4; ++☃xxxxxxxx) {
            for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < 16; ++☃xxxxxxxxx) {
               double ☃xxxxxxxxxx = 0.125;
               double ☃xxxxxxxxxxx = ☃xxxxx[((☃xxxxxxx + 0) * 5 + ☃xxxxxxxx + 0) * 17 + ☃xxxxxxxxx + 0];
               double ☃xxxxxxxxxxxx = ☃xxxxx[((☃xxxxxxx + 0) * 5 + ☃xxxxxxxx + 1) * 17 + ☃xxxxxxxxx + 0];
               double ☃xxxxxxxxxxxxx = ☃xxxxx[((☃xxxxxxx + 1) * 5 + ☃xxxxxxxx + 0) * 17 + ☃xxxxxxxxx + 0];
               double ☃xxxxxxxxxxxxxx = ☃xxxxx[((☃xxxxxxx + 1) * 5 + ☃xxxxxxxx + 1) * 17 + ☃xxxxxxxxx + 0];
               double ☃xxxxxxxxxxxxxxx = (☃xxxxx[((☃xxxxxxx + 0) * 5 + ☃xxxxxxxx + 0) * 17 + ☃xxxxxxxxx + 1] - ☃xxxxxxxxxxx) * 0.125;
               double ☃xxxxxxxxxxxxxxxx = (☃xxxxx[((☃xxxxxxx + 0) * 5 + ☃xxxxxxxx + 1) * 17 + ☃xxxxxxxxx + 1] - ☃xxxxxxxxxxxx) * 0.125;
               double ☃xxxxxxxxxxxxxxxxx = (☃xxxxx[((☃xxxxxxx + 1) * 5 + ☃xxxxxxxx + 0) * 17 + ☃xxxxxxxxx + 1] - ☃xxxxxxxxxxxxx) * 0.125;
               double ☃xxxxxxxxxxxxxxxxxx = (☃xxxxx[((☃xxxxxxx + 1) * 5 + ☃xxxxxxxx + 1) * 17 + ☃xxxxxxxxx + 1] - ☃xxxxxxxxxxxxxx) * 0.125;

               for(int ☃xxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxx < 8; ++☃xxxxxxxxxxxxxxxxxxx) {
                  double ☃xxxxxxxxxxxxxxxxxxxx = 0.25;
                  double ☃xxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxx;
                  double ☃xxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxx;
                  double ☃xxxxxxxxxxxxxxxxxxxxxxx = (☃xxxxxxxxxxxxx - ☃xxxxxxxxxxx) * 0.25;
                  double ☃xxxxxxxxxxxxxxxxxxxxxxxx = (☃xxxxxxxxxxxxxx - ☃xxxxxxxxxxxx) * 0.25;

                  for(int ☃xxxxxxxxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxxxxxxxx < 4; ++☃xxxxxxxxxxxxxxxxxxxxxxxxx) {
                     double ☃xxxxxxxxxxxxxxxxxxxxxxxxxx = 0.25;
                     double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxx;
                     double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx = (☃xxxxxxxxxxxxxxxxxxxxxx - ☃xxxxxxxxxxxxxxxxxxxxx) * 0.25;

                     for(int ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx < 4; ++☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                        IBlockState ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = field_185940_a;
                        if (☃xxxxxxxxx * 8 + ☃xxxxxxxxxxxxxxxxxxx < ☃x) {
                           ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.field_205604_n;
                        }

                        if (☃xxxxxxxxxxxxxxxxxxxxxxxxxxx > 0.0) {
                           ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.field_205474_p;
                        }

                        int ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxxx + ☃xxxxxxx * 4;
                        int ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxx + ☃xxxxxxxxx * 8;
                        int ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxx + ☃xxxxxxxx * 4;
                        ☃.func_177436_a(
                           ☃xxxxxx.func_181079_c(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx),
                           ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                           false
                        );
                        ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                     }

                     ☃xxxxxxxxxxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxxxxxxxx;
                     ☃xxxxxxxxxxxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxxxxxxxxx;
                  }

                  ☃xxxxxxxxxxx += ☃xxxxxxxxxxxxxxx;
                  ☃xxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxx;
                  ☃xxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxx;
                  ☃xxxxxxxxxxxxxx += ☃xxxxxxxxxxxxxxxxxx;
               }
            }
         }
      }
   }

   @Override
   protected void func_205472_a(IChunk var1, Random var2) {
      BlockPos.MutableBlockPos ☃ = new BlockPos.MutableBlockPos();
      int ☃x = ☃.func_76632_l().func_180334_c();
      int ☃xx = ☃.func_76632_l().func_180333_d();

      for(BlockPos ☃xxx : BlockPos.func_191532_a(☃x, 0, ☃xx, ☃x + 16, 0, ☃xx + 16)) {
         for(int ☃xxxx = 127; ☃xxxx > 122; --☃xxxx) {
            if (☃xxxx >= 127 - ☃.nextInt(5)) {
               ☃.func_177436_a(☃.func_181079_c(☃xxx.func_177958_n(), ☃xxxx, ☃xxx.func_177952_p()), Blocks.field_150357_h.func_176223_P(), false);
            }
         }

         for(int ☃xxxx = 4; ☃xxxx >= 0; --☃xxxx) {
            if (☃xxxx <= ☃.nextInt(5)) {
               ☃.func_177436_a(☃.func_181079_c(☃xxx.func_177958_n(), ☃xxxx, ☃xxx.func_177952_p()), Blocks.field_150357_h.func_176223_P(), false);
            }
         }
      }
   }

   @Override
   public double[] func_205473_a(int var1, int var2) {
      double ☃ = 0.03125;
      return this.field_73177_m.func_202647_a(☃ << 4, ☃ << 4, 0, 16, 16, 1, 0.0625, 0.0625, 0.0625);
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
      this.func_185936_a(☃x, ☃xx, ☃);
      this.func_205471_a(☃, ☃xxxx, ☃xxx, this.field_202095_a.func_181545_F());
      this.func_205472_a(☃, ☃xxx);
      ☃.func_201588_a(Heightmap.Type.WORLD_SURFACE_WG, Heightmap.Type.OCEAN_FLOOR_WG);
      ☃.func_201574_a(ChunkStatus.BASE);
   }

   private double[] func_202104_a(int var1, int var2, int var3, int var4, int var5, int var6) {
      double[] ☃ = new double[☃ * ☃ * ☃];
      double ☃x = 684.412;
      double ☃xx = 2053.236;
      double[] ☃xxx = this.field_185946_g.func_202647_a(☃, ☃, ☃, ☃, 1, ☃, 1.0, 0.0, 1.0);
      double[] ☃xxxx = this.field_185947_h.func_202647_a(☃, ☃, ☃, ☃, 1, ☃, 100.0, 0.0, 100.0);
      double[] ☃xxxxx = this.field_185959_w.func_202647_a(☃, ☃, ☃, ☃, ☃, ☃, 8.555150000000001, 34.2206, 8.555150000000001);
      double[] ☃xxxxxx = this.field_185957_u.func_202647_a(☃, ☃, ☃, ☃, ☃, ☃, 684.412, 2053.236, 684.412);
      double[] ☃xxxxxxx = this.field_185958_v.func_202647_a(☃, ☃, ☃, ☃, ☃, ☃, 684.412, 2053.236, 684.412);
      double[] ☃xxxxxxxx = new double[☃];

      for(int ☃xxxxxxxxx = 0; ☃xxxxxxxxx < ☃; ++☃xxxxxxxxx) {
         ☃xxxxxxxx[☃xxxxxxxxx] = Math.cos((double)☃xxxxxxxxx * Math.PI * 6.0 / (double)☃) * 2.0;
         double ☃xxxxxxxxxx = (double)☃xxxxxxxxx;
         if (☃xxxxxxxxx > ☃ / 2) {
            ☃xxxxxxxxxx = (double)(☃ - 1 - ☃xxxxxxxxx);
         }

         if (☃xxxxxxxxxx < 4.0) {
            ☃xxxxxxxxxx = 4.0 - ☃xxxxxxxxxx;
            ☃xxxxxxxx[☃xxxxxxxxx] -= ☃xxxxxxxxxx * ☃xxxxxxxxxx * ☃xxxxxxxxxx * 10.0;
         }
      }

      int ☃xxxxxxxxx = 0;

      for(int ☃xxxxxxxxxx = 0; ☃xxxxxxxxxx < ☃; ++☃xxxxxxxxxx) {
         for(int ☃xxxxxxxxxxx = 0; ☃xxxxxxxxxxx < ☃; ++☃xxxxxxxxxxx) {
            double ☃xxxxxxxxxxxx = 0.0;

            for(int ☃xxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxx < ☃; ++☃xxxxxxxxxxxxx) {
               double ☃xxxxxxxxxxxxxxx = ☃xxxxxxxx[☃xxxxxxxxxxxxx];
               double ☃xxxxxxxxxxxxxxxx = ☃xxxxxx[☃xxxxxxxxx] / 512.0;
               double ☃xxxxxxxxxxxxxxxxx = ☃xxxxxxx[☃xxxxxxxxx] / 512.0;
               double ☃xxxxxxxxxxxxxxxxxx = (☃xxxxx[☃xxxxxxxxx] / 10.0 + 1.0) / 2.0;
               double ☃xxxxxxxxxxxxxx;
               if (☃xxxxxxxxxxxxxxxxxx < 0.0) {
                  ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxx;
               } else if (☃xxxxxxxxxxxxxxxxxx > 1.0) {
                  ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxx;
               } else {
                  ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxx + (☃xxxxxxxxxxxxxxxxx - ☃xxxxxxxxxxxxxxxx) * ☃xxxxxxxxxxxxxxxxxx;
               }

               ☃xxxxxxxxxxxxxx -= ☃xxxxxxxxxxxxxxx;
               if (☃xxxxxxxxxxxxx > ☃ - 4) {
                  double ☃xxxxxxxxxxxxxx = (double)((float)(☃xxxxxxxxxxxxx - (☃ - 4)) / 3.0F);
                  ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxx * (1.0 - ☃xxxxxxxxxxxxxx) - 10.0 * ☃xxxxxxxxxxxxxx;
               }

               if ((double)☃xxxxxxxxxxxxx < 0.0) {
                  double ☃xxxxxxxxxxxxxx = (0.0 - (double)☃xxxxxxxxxxxxx) / 4.0;
                  ☃xxxxxxxxxxxxxx = MathHelper.func_151237_a(☃xxxxxxxxxxxxxx, 0.0, 1.0);
                  ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxx * (1.0 - ☃xxxxxxxxxxxxxx) - 10.0 * ☃xxxxxxxxxxxxxx;
               }

               ☃[☃xxxxxxxxx] = ☃xxxxxxxxxxxxxx;
               ++☃xxxxxxxxx;
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
      if (☃ == EnumCreatureType.MONSTER) {
         if (Feature.field_202337_o.func_202366_b(this.field_202095_a, ☃)) {
            return Feature.field_202337_o.func_202279_e();
         }

         if (Feature.field_202337_o.func_175796_a(this.field_202095_a, ☃)
            && this.field_202095_a.func_180495_p(☃.func_177977_b()).func_177230_c() == Blocks.field_196653_dH) {
            return Feature.field_202337_o.func_202279_e();
         }
      }

      Biome ☃ = this.field_202095_a.func_180494_b(☃);
      return ☃.func_76747_a(☃);
   }

   @Override
   public int func_203222_a(World var1, boolean var2, boolean var3) {
      return 0;
   }

   public NetherGenSettings func_201496_a_() {
      return this.field_202107_q;
   }

   @Override
   public int func_205470_d() {
      return this.field_202095_a.func_181545_F() + 1;
   }

   @Override
   public int func_207511_e() {
      return 128;
   }
}
