package net.minecraft.world;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Random;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.dimension.DimensionType;

public class Teleporter {
   private static final BlockPortal field_196236_a = (BlockPortal)Blocks.field_150427_aO;
   private final WorldServer field_85192_a;
   private final Random field_77187_a;
   private final Long2ObjectMap<Teleporter.PortalPosition> field_85191_c = new Long2ObjectOpenHashMap<>(4096);

   public Teleporter(WorldServer var1) {
      this.field_85192_a = ☃;
      this.field_77187_a = new Random(☃.func_72905_C());
   }

   public void func_180266_a(Entity var1, float var2) {
      if (this.field_85192_a.field_73011_w.func_186058_p() != DimensionType.THE_END) {
         if (!this.func_180620_b(☃, ☃)) {
            this.func_85188_a(☃);
            this.func_180620_b(☃, ☃);
         }
      } else {
         int ☃ = MathHelper.func_76128_c(☃.field_70165_t);
         int ☃x = MathHelper.func_76128_c(☃.field_70163_u) - 1;
         int ☃xx = MathHelper.func_76128_c(☃.field_70161_v);
         int ☃xxx = 1;
         int ☃xxxx = 0;

         for(int ☃xxxxx = -2; ☃xxxxx <= 2; ++☃xxxxx) {
            for(int ☃xxxxxx = -2; ☃xxxxxx <= 2; ++☃xxxxxx) {
               for(int ☃xxxxxxx = -1; ☃xxxxxxx < 3; ++☃xxxxxxx) {
                  int ☃xxxxxxxx = ☃ + ☃xxxxxx * 1 + ☃xxxxx * 0;
                  int ☃xxxxxxxxx = ☃x + ☃xxxxxxx;
                  int ☃xxxxxxxxxx = ☃xx + ☃xxxxxx * 0 - ☃xxxxx * 1;
                  boolean ☃xxxxxxxxxxx = ☃xxxxxxx < 0;
                  this.field_85192_a
                     .func_175656_a(
                        new BlockPos(☃xxxxxxxx, ☃xxxxxxxxx, ☃xxxxxxxxxx),
                        ☃xxxxxxxxxxx ? Blocks.field_150343_Z.func_176223_P() : Blocks.field_150350_a.func_176223_P()
                     );
               }
            }
         }

         ☃.func_70012_b((double)☃, (double)☃x, (double)☃xx, ☃.field_70177_z, 0.0F);
         ☃.field_70159_w = 0.0;
         ☃.field_70181_x = 0.0;
         ☃.field_70179_y = 0.0;
      }
   }

   public boolean func_180620_b(Entity var1, float var2) {
      int ☃ = 128;
      double ☃x = -1.0;
      int ☃xx = MathHelper.func_76128_c(☃.field_70165_t);
      int ☃xxx = MathHelper.func_76128_c(☃.field_70161_v);
      boolean ☃xxxx = true;
      BlockPos ☃xxxxx = BlockPos.field_177992_a;
      long ☃xxxxxx = ChunkPos.func_77272_a(☃xx, ☃xxx);
      if (this.field_85191_c.containsKey(☃xxxxxx)) {
         Teleporter.PortalPosition ☃xxxxxxx = this.field_85191_c.get(☃xxxxxx);
         ☃x = 0.0;
         ☃xxxxx = ☃xxxxxxx;
         ☃xxxxxxx.field_85087_d = this.field_85192_a.func_82737_E();
         ☃xxxx = false;
      } else {
         BlockPos ☃ = new BlockPos(☃);

         for(int ☃x = -128; ☃x <= 128; ++☃x) {
            BlockPos ☃;
            for(int ☃xx = -128; ☃xx <= 128; ++☃xx) {
               for(BlockPos ☃xxx = ☃.func_177982_a(☃x, this.field_85192_a.func_72940_L() - 1 - ☃.func_177956_o(), ☃xx); ☃xxx.func_177956_o() >= 0; ☃xxx = ☃) {
                  ☃ = ☃xxx.func_177977_b();
                  if (this.field_85192_a.func_180495_p(☃xxx).func_177230_c() == field_196236_a) {
                     for(☃ = ☃xxx.func_177977_b(); this.field_85192_a.func_180495_p(☃).func_177230_c() == field_196236_a; ☃ = ☃.func_177977_b()) {
                        ☃xxx = ☃;
                     }

                     double ☃xxxx = ☃xxx.func_177951_i(☃);
                     if (☃x < 0.0 || ☃xxxx < ☃x) {
                        ☃x = ☃xxxx;
                        ☃xxxxx = ☃xxx;
                     }
                  }
               }
            }
         }
      }

      if (☃x >= 0.0) {
         if (☃xxxx) {
            this.field_85191_c.put(☃xxxxxx, new Teleporter.PortalPosition(☃xxxxx, this.field_85192_a.func_82737_E()));
         }

         double ☃ = (double)☃xxxxx.func_177958_n() + 0.5;
         double ☃x = (double)☃xxxxx.func_177952_p() + 0.5;
         BlockPattern.PatternHelper ☃xx = field_196236_a.func_181089_f(this.field_85192_a, ☃xxxxx);
         boolean ☃xxx = ☃xx.func_177669_b().func_176746_e().func_176743_c() == EnumFacing.AxisDirection.NEGATIVE;
         double ☃xxxx = ☃xx.func_177669_b().func_176740_k() == EnumFacing.Axis.X
            ? (double)☃xx.func_181117_a().func_177952_p()
            : (double)☃xx.func_181117_a().func_177958_n();
         double ☃xxxxx = (double)(☃xx.func_181117_a().func_177956_o() + 1) - ☃.func_181014_aG().field_72448_b * (double)☃xx.func_181119_e();
         if (☃xxx) {
            ++☃xxxx;
         }

         if (☃xx.func_177669_b().func_176740_k() == EnumFacing.Axis.X) {
            ☃x = ☃xxxx
               + (1.0 - ☃.func_181014_aG().field_72450_a)
                  * (double)☃xx.func_181118_d()
                  * (double)☃xx.func_177669_b().func_176746_e().func_176743_c().func_179524_a();
         } else {
            ☃ = ☃xxxx
               + (1.0 - ☃.func_181014_aG().field_72450_a)
                  * (double)☃xx.func_181118_d()
                  * (double)☃xx.func_177669_b().func_176746_e().func_176743_c().func_179524_a();
         }

         float ☃ = 0.0F;
         float ☃x = 0.0F;
         float ☃xx = 0.0F;
         float ☃xxx = 0.0F;
         if (☃xx.func_177669_b().func_176734_d() == ☃.func_181012_aH()) {
            ☃ = 1.0F;
            ☃x = 1.0F;
         } else if (☃xx.func_177669_b().func_176734_d() == ☃.func_181012_aH().func_176734_d()) {
            ☃ = -1.0F;
            ☃x = -1.0F;
         } else if (☃xx.func_177669_b().func_176734_d() == ☃.func_181012_aH().func_176746_e()) {
            ☃xx = 1.0F;
            ☃xxx = -1.0F;
         } else {
            ☃xx = -1.0F;
            ☃xxx = 1.0F;
         }

         double ☃ = ☃.field_70159_w;
         double ☃x = ☃.field_70179_y;
         ☃.field_70159_w = ☃ * (double)☃ + ☃x * (double)☃xxx;
         ☃.field_70179_y = ☃ * (double)☃xx + ☃x * (double)☃x;
         ☃.field_70177_z = ☃ - (float)(☃.func_181012_aH().func_176734_d().func_176736_b() * 90) + (float)(☃xx.func_177669_b().func_176736_b() * 90);
         if (☃ instanceof EntityPlayerMP) {
            ((EntityPlayerMP)☃).field_71135_a.func_147364_a(☃, ☃xxxxx, ☃x, ☃.field_70177_z, ☃.field_70125_A);
            ((EntityPlayerMP)☃).field_71135_a.func_184342_d();
         } else {
            ☃.func_70012_b(☃, ☃xxxxx, ☃x, ☃.field_70177_z, ☃.field_70125_A);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean func_85188_a(Entity var1) {
      int ☃ = 16;
      double ☃x = -1.0;
      int ☃xx = MathHelper.func_76128_c(☃.field_70165_t);
      int ☃xxx = MathHelper.func_76128_c(☃.field_70163_u);
      int ☃xxxx = MathHelper.func_76128_c(☃.field_70161_v);
      int ☃xxxxx = ☃xx;
      int ☃xxxxxx = ☃xxx;
      int ☃xxxxxxx = ☃xxxx;
      int ☃xxxxxxxx = 0;
      int ☃xxxxxxxxx = this.field_77187_a.nextInt(4);
      BlockPos.MutableBlockPos ☃xxxxxxxxxx = new BlockPos.MutableBlockPos();

      for(int ☃xxxxxxxxxxx = ☃xx - 16; ☃xxxxxxxxxxx <= ☃xx + 16; ++☃xxxxxxxxxxx) {
         double ☃xxxxxxxxxxxx = (double)☃xxxxxxxxxxx + 0.5 - ☃.field_70165_t;

         for(int ☃xxxxxxxxxxxxx = ☃xxxx - 16; ☃xxxxxxxxxxxxx <= ☃xxxx + 16; ++☃xxxxxxxxxxxxx) {
            double ☃xxxxxxxxxxxxxx = (double)☃xxxxxxxxxxxxx + 0.5 - ☃.field_70161_v;

            label279:
            for(int ☃xxxxxxxxxxxxxxx = this.field_85192_a.func_72940_L() - 1; ☃xxxxxxxxxxxxxxx >= 0; --☃xxxxxxxxxxxxxxx) {
               if (this.field_85192_a.func_175623_d(☃xxxxxxxxxx.func_181079_c(☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxx))) {
                  while(☃xxxxxxxxxxxxxxx > 0 && this.field_85192_a.func_175623_d(☃xxxxxxxxxx.func_181079_c(☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxx - 1, ☃xxxxxxxxxxxxx))) {
                     --☃xxxxxxxxxxxxxxx;
                  }

                  for(int ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxxx; ☃xxxxxxxxxxxxxxxx < ☃xxxxxxxxx + 4; ++☃xxxxxxxxxxxxxxxx) {
                     int ☃xxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxx % 2;
                     int ☃xxxxxxxxxxxxxxxxxx = 1 - ☃xxxxxxxxxxxxxxxxx;
                     if (☃xxxxxxxxxxxxxxxx % 4 >= 2) {
                        ☃xxxxxxxxxxxxxxxxx = -☃xxxxxxxxxxxxxxxxx;
                        ☃xxxxxxxxxxxxxxxxxx = -☃xxxxxxxxxxxxxxxxxx;
                     }

                     for(int ☃xxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxx < 3; ++☃xxxxxxxxxxxxxxxxx) {
                        for(int ☃xxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxx < 4; ++☃xxxxxxxxxxxxxxxxxx) {
                           for(int ☃xxxxxxxxxxxxxxxxxxx = -1; ☃xxxxxxxxxxxxxxxxxxx < 4; ++☃xxxxxxxxxxxxxxxxxxx) {
                              int ☃xxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxx
                                 + (☃xxxxxxxxxxxxxxxxxx - 1) * ☃xxxxxxxxxxxxxxxxx
                                 + ☃xxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxx;
                              int ☃xxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxx;
                              int ☃xxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx
                                 + (☃xxxxxxxxxxxxxxxxxx - 1) * ☃xxxxxxxxxxxxxxxxxx
                                 - ☃xxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxx;
                              ☃xxxxxxxxxx.func_181079_c(☃xxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxx);
                              if (☃xxxxxxxxxxxxxxxxxxx < 0 && !this.field_85192_a.func_180495_p(☃xxxxxxxxxx).func_185904_a().func_76220_a()
                                 || ☃xxxxxxxxxxxxxxxxxxx >= 0 && !this.field_85192_a.func_175623_d(☃xxxxxxxxxx)) {
                                 continue label279;
                              }
                           }
                        }
                     }

                     double ☃xxxxxxxxxxxxxxxxx = (double)☃xxxxxxxxxxxxxxx + 0.5 - ☃.field_70163_u;
                     double ☃xxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxx * ☃xxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxx;
                     if (☃x < 0.0 || ☃xxxxxxxxxxxxxxxxxx < ☃x) {
                        ☃x = ☃xxxxxxxxxxxxxxxxxx;
                        ☃xxxxx = ☃xxxxxxxxxxx;
                        ☃xxxxxx = ☃xxxxxxxxxxxxxxx;
                        ☃xxxxxxx = ☃xxxxxxxxxxxxx;
                        ☃xxxxxxxx = ☃xxxxxxxxxxxxxxxx % 4;
                     }
                  }
               }
            }
         }
      }

      if (☃x < 0.0) {
         for(int ☃xxxxxxxxxxx = ☃xx - 16; ☃xxxxxxxxxxx <= ☃xx + 16; ++☃xxxxxxxxxxx) {
            double ☃xxxxxxxxxxxx = (double)☃xxxxxxxxxxx + 0.5 - ☃.field_70165_t;

            for(int ☃xxxxxxxxxxxxx = ☃xxxx - 16; ☃xxxxxxxxxxxxx <= ☃xxxx + 16; ++☃xxxxxxxxxxxxx) {
               double ☃xxxxxxxxxxxxxx = (double)☃xxxxxxxxxxxxx + 0.5 - ☃.field_70161_v;

               label216:
               for(int ☃xxxxxxxxxxxxxxx = this.field_85192_a.func_72940_L() - 1; ☃xxxxxxxxxxxxxxx >= 0; --☃xxxxxxxxxxxxxxx) {
                  if (this.field_85192_a.func_175623_d(☃xxxxxxxxxx.func_181079_c(☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxx))) {
                     while(
                        ☃xxxxxxxxxxxxxxx > 0 && this.field_85192_a.func_175623_d(☃xxxxxxxxxx.func_181079_c(☃xxxxxxxxxxx, ☃xxxxxxxxxxxxxxx - 1, ☃xxxxxxxxxxxxx))
                     ) {
                        --☃xxxxxxxxxxxxxxx;
                     }

                     for(int ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxxx; ☃xxxxxxxxxxxxxxxx < ☃xxxxxxxxx + 2; ++☃xxxxxxxxxxxxxxxx) {
                        int ☃xxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxx % 2;
                        int ☃xxxxxxxxxxxxxxxxxx = 1 - ☃xxxxxxxxxxxxxxxxx;

                        for(int ☃xxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxx < 4; ++☃xxxxxxxxxxxxxxxxxxx) {
                           for(int ☃xxxxxxxxxxxxxxxxxxxx = -1; ☃xxxxxxxxxxxxxxxxxxxx < 4; ++☃xxxxxxxxxxxxxxxxxxxx) {
                              int ☃xxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxx + (☃xxxxxxxxxxxxxxxxxxx - 1) * ☃xxxxxxxxxxxxxxxxx;
                              int ☃xxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxx + ☃xxxxxxxxxxxxxxxxxxxx;
                              int ☃xxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx + (☃xxxxxxxxxxxxxxxxxxx - 1) * ☃xxxxxxxxxxxxxxxxxx;
                              ☃xxxxxxxxxx.func_181079_c(☃xxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxxxxxxxx);
                              if (☃xxxxxxxxxxxxxxxxxxxx < 0 && !this.field_85192_a.func_180495_p(☃xxxxxxxxxx).func_185904_a().func_76220_a()
                                 || ☃xxxxxxxxxxxxxxxxxxxx >= 0 && !this.field_85192_a.func_175623_d(☃xxxxxxxxxx)) {
                                 continue label216;
                              }
                           }
                        }

                        double ☃xxxxxxxxxxxxxxxxxxx = (double)☃xxxxxxxxxxxxxxx + 0.5 - ☃.field_70163_u;
                        double ☃xxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxx * ☃xxxxxxxxxxxx
                           + ☃xxxxxxxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxxxxxxx
                           + ☃xxxxxxxxxxxxxx * ☃xxxxxxxxxxxxxx;
                        if (☃x < 0.0 || ☃xxxxxxxxxxxxxxxxxxxx < ☃x) {
                           ☃x = ☃xxxxxxxxxxxxxxxxxxxx;
                           ☃xxxxx = ☃xxxxxxxxxxx;
                           ☃xxxxxx = ☃xxxxxxxxxxxxxxx;
                           ☃xxxxxxx = ☃xxxxxxxxxxxxx;
                           ☃xxxxxxxx = ☃xxxxxxxxxxxxxxxx % 2;
                        }
                     }
                  }
               }
            }
         }
      }

      int ☃xxxxxxxxxxx = ☃xxxxx;
      int ☃xxxxxxxxxxxx = ☃xxxxxx;
      int ☃xxxxxxxxxxxxx = ☃xxxxxxx;
      int ☃xxxxxxxxxxxxxx = ☃xxxxxxxx % 2;
      int ☃xxxxxxxxxxxxxxx = 1 - ☃xxxxxxxxxxxxxx;
      if (☃xxxxxxxx % 4 >= 2) {
         ☃xxxxxxxxxxxxxx = -☃xxxxxxxxxxxxxx;
         ☃xxxxxxxxxxxxxxx = -☃xxxxxxxxxxxxxxx;
      }

      if (☃x < 0.0) {
         ☃xxxxxx = MathHelper.func_76125_a(☃xxxxxx, 70, this.field_85192_a.func_72940_L() - 10);
         ☃xxxxxxxxxxxx = ☃xxxxxx;

         for(int ☃xxxxxxxxxxx = -1; ☃xxxxxxxxxxx <= 1; ++☃xxxxxxxxxxx) {
            for(int ☃xxxxxxxxxxxx = 1; ☃xxxxxxxxxxxx < 3; ++☃xxxxxxxxxxxx) {
               for(int ☃xxxxxxxxxxxxx = -1; ☃xxxxxxxxxxxxx < 3; ++☃xxxxxxxxxxxxx) {
                  int ☃xxxxxxxxxxxxxx = ☃xxxxxxxxxxx + (☃xxxxxxxxxxxx - 1) * ☃xxxxxxxxxxxxxx + ☃xxxxxxxxxxx * ☃xxxxxxxxxxxxxxx;
                  int ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxx + ☃xxxxxxxxxxxxx;
                  int ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx + (☃xxxxxxxxxxxx - 1) * ☃xxxxxxxxxxxxxxx - ☃xxxxxxxxxxx * ☃xxxxxxxxxxxxxx;
                  boolean ☃xxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxx < 0;
                  ☃xxxxxxxxxx.func_181079_c(☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx);
                  this.field_85192_a
                     .func_175656_a(☃xxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx ? Blocks.field_150343_Z.func_176223_P() : Blocks.field_150350_a.func_176223_P());
               }
            }
         }
      }

      for(int ☃xxxxxxxxxxx = -1; ☃xxxxxxxxxxx < 3; ++☃xxxxxxxxxxx) {
         for(int ☃xxxxxxxxxxxx = -1; ☃xxxxxxxxxxxx < 4; ++☃xxxxxxxxxxxx) {
            if (☃xxxxxxxxxxx == -1 || ☃xxxxxxxxxxx == 2 || ☃xxxxxxxxxxxx == -1 || ☃xxxxxxxxxxxx == 3) {
               ☃xxxxxxxxxx.func_181079_c(
                  ☃xxxxxxxxxxx + ☃xxxxxxxxxxx * ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxx + ☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxx + ☃xxxxxxxxxxx * ☃xxxxxxxxxxxxxxx
               );
               this.field_85192_a.func_180501_a(☃xxxxxxxxxx, Blocks.field_150343_Z.func_176223_P(), 3);
            }
         }
      }

      IBlockState ☃xxxxxxxxxxx = field_196236_a.func_176223_P()
         .func_206870_a(BlockPortal.field_176550_a, ☃xxxxxxxxxxxxxx == 0 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);

      for(int ☃xxxxxxxxxxxx = 0; ☃xxxxxxxxxxxx < 2; ++☃xxxxxxxxxxxx) {
         for(int ☃xxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxx < 3; ++☃xxxxxxxxxxxxx) {
            ☃xxxxxxxxxx.func_181079_c(
               ☃xxxxxxxxxxx + ☃xxxxxxxxxxxx * ☃xxxxxxxxxxxxxx, ☃xxxxxxxxxxxx + ☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxxx + ☃xxxxxxxxxxxx * ☃xxxxxxxxxxxxxxx
            );
            this.field_85192_a.func_180501_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, 18);
         }
      }

      return true;
   }

   public void func_85189_a(long var1) {
      if (☃ % 100L == 0L) {
         long ☃ = ☃ - 300L;
         ObjectIterator<Teleporter.PortalPosition> ☃x = this.field_85191_c.values().iterator();

         while(☃x.hasNext()) {
            Teleporter.PortalPosition ☃xx = (Teleporter.PortalPosition)☃x.next();
            if (☃xx == null || ☃xx.field_85087_d < ☃) {
               ☃x.remove();
            }
         }
      }
   }

   public class PortalPosition extends BlockPos {
      public long field_85087_d;

      public PortalPosition(BlockPos var2, long var3) {
         super(☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p());
         this.field_85087_d = ☃;
      }
   }
}
