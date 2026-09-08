package net.minecraft.world;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.fluid.IFluidState;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathType;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.Heightmap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class WorldEntitySpawner {
   private static final Logger field_209383_a = LogManager.getLogger();
   private static final int field_180268_a = (int)Math.pow(17.0, 2.0);
   private final Set<ChunkPos> field_77193_b = Sets.<ChunkPos>newHashSet();

   public int func_77192_a(WorldServer var1, boolean var2, boolean var3, boolean var4) {
      if (!☃ && !☃) {
         return 0;
      } else {
         this.field_77193_b.clear();
         int ☃ = 0;

         for(EntityPlayer ☃x : ☃.field_73010_i) {
            if (!☃x.func_175149_v()) {
               int ☃xx = MathHelper.func_76128_c(☃x.field_70165_t / 16.0);
               int ☃xxx = MathHelper.func_76128_c(☃x.field_70161_v / 16.0);
               int ☃xxxx = 8;

               for(int ☃xxxxx = -8; ☃xxxxx <= 8; ++☃xxxxx) {
                  for(int ☃xxxxxx = -8; ☃xxxxxx <= 8; ++☃xxxxxx) {
                     boolean ☃xxxxxxx = ☃xxxxx == -8 || ☃xxxxx == 8 || ☃xxxxxx == -8 || ☃xxxxxx == 8;
                     ChunkPos ☃xxxxxxxx = new ChunkPos(☃xxxxx + ☃xx, ☃xxxxxx + ☃xxx);
                     if (!this.field_77193_b.contains(☃xxxxxxxx)) {
                        ++☃;
                        if (!☃xxxxxxx && ☃.func_175723_af().func_177730_a(☃xxxxxxxx)) {
                           PlayerChunkMapEntry ☃xxxxxxxxx = ☃.func_184164_w().func_187301_b(☃xxxxxxxx.field_77276_a, ☃xxxxxxxx.field_77275_b);
                           if (☃xxxxxxxxx != null && ☃xxxxxxxxx.func_187274_e()) {
                              this.field_77193_b.add(☃xxxxxxxx);
                           }
                        }
                     }
                  }
               }
            }
         }

         int ☃x = 0;
         BlockPos ☃xx = ☃.func_175694_M();

         for(EnumCreatureType ☃xxx : EnumCreatureType.values()) {
            if ((!☃xxx.func_75599_d() || ☃) && (☃xxx.func_75599_d() || ☃) && (!☃xxx.func_82705_e() || ☃)) {
               int ☃xxxx = ☃xxx.func_75601_b() * ☃ / field_180268_a;
               int ☃xxxxx = ☃.func_72907_a(☃xxx.func_75598_a(), ☃xxxx);
               if (☃xxxxx <= ☃xxxx) {
                  BlockPos.MutableBlockPos ☃xxxxxx = new BlockPos.MutableBlockPos();

                  label152:
                  for(ChunkPos ☃xxxxxxx : this.field_77193_b) {
                     BlockPos ☃xxxxxxxx = func_180621_a(☃, ☃xxxxxxx.field_77276_a, ☃xxxxxxx.field_77275_b);
                     int ☃xxxxxxxxx = ☃xxxxxxxx.func_177958_n();
                     int ☃xxxxxxxxxx = ☃xxxxxxxx.func_177956_o();
                     int ☃xxxxxxxxxxx = ☃xxxxxxxx.func_177952_p();
                     IBlockState ☃xxxxxxxxxxxx = ☃.func_180495_p(☃xxxxxxxx);
                     if (!☃xxxxxxxxxxxx.func_185915_l()) {
                        int ☃xxxxxxxxxxxxx = 0;

                        for(int ☃xxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxx < 3; ++☃xxxxxxxxxxxxxx) {
                           int ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxx;
                           int ☃xxxxxxxxxxxxxxxx = ☃xxxxxxxxxx;
                           int ☃xxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxx;
                           int ☃xxxxxxxxxxxxxxxxxx = 6;
                           Biome.SpawnListEntry ☃xxxxxxxxxxxxxxxxxxx = null;
                           IEntityLivingData ☃xxxxxxxxxxxxxxxxxxxx = null;
                           int ☃xxxxxxxxxxxxxxxxxxxxx = MathHelper.func_76143_f(Math.random() * 4.0);
                           int ☃xxxxxxxxxxxxxxxxxxxxxx = 0;

                           for(int ☃xxxxxxxxxxxxxxxxxxxxxxx = 0; ☃xxxxxxxxxxxxxxxxxxxxxxx < ☃xxxxxxxxxxxxxxxxxxxxx; ++☃xxxxxxxxxxxxxxxxxxxxxxx) {
                              ☃xxxxxxxxxxxxxxx += ☃.field_73012_v.nextInt(6) - ☃.field_73012_v.nextInt(6);
                              ☃xxxxxxxxxxxxxxxx += ☃.field_73012_v.nextInt(1) - ☃.field_73012_v.nextInt(1);
                              ☃xxxxxxxxxxxxxxxxx += ☃.field_73012_v.nextInt(6) - ☃.field_73012_v.nextInt(6);
                              ☃xxxxxx.func_181079_c(☃xxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxx, ☃xxxxxxxxxxxxxxxxx);
                              float ☃xxxxxxxxxxxxxxxxxxxxxxxx = (float)☃xxxxxxxxxxxxxxx + 0.5F;
                              float ☃xxxxxxxxxxxxxxxxxxxxxxxxx = (float)☃xxxxxxxxxxxxxxxxx + 0.5F;
                              EntityPlayer ☃xxxxxxxxxxxxxxxxxxxxxxxxxx = ☃.func_212817_a(
                                 (double)☃xxxxxxxxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxxxxxxx, -1.0
                              );
                              if (☃xxxxxxxxxxxxxxxxxxxxxxxxxx != null) {
                                 double ☃xxxxxxxxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxxxxxxxxx.func_70092_e(
                                    (double)☃xxxxxxxxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxxxxxxx
                                 );
                                 if (!(☃xxxxxxxxxxxxxxxxxxxxxxxxxxx <= 576.0)
                                    && !(
                                       ☃xx.func_177954_c((double)☃xxxxxxxxxxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxxxxxxxxxxxxxx)
                                          < 576.0
                                    )) {
                                    if (☃xxxxxxxxxxxxxxxxxxx == null) {
                                       ☃xxxxxxxxxxxxxxxxxxx = ☃.func_175734_a(☃xxx, ☃xxxxxx);
                                       if (☃xxxxxxxxxxxxxxxxxxx == null) {
                                          break;
                                       }

                                       ☃xxxxxxxxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxxxxxxx.field_76301_c
                                          + ☃.field_73012_v.nextInt(1 + ☃xxxxxxxxxxxxxxxxxxx.field_76299_d - ☃xxxxxxxxxxxxxxxxxxx.field_76301_c);
                                    }

                                    if (☃.func_175732_a(☃xxx, ☃xxxxxxxxxxxxxxxxxxx, ☃xxxxxx)) {
                                       EntitySpawnPlacementRegistry.SpawnPlacementType ☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx = EntitySpawnPlacementRegistry.func_209344_a(
                                          ☃xxxxxxxxxxxxxxxxxxx.field_200702_b
                                       );
                                       if (☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx != null
                                          && func_209382_a(☃xxxxxxxxxxxxxxxxxxxxxxxxxxxx, ☃, ☃xxxxxx, ☃xxxxxxxxxxxxxxxxxxx.field_200702_b)) {
                                          EntityLiving ☃;
                                          try {
                                             ☃ = ☃xxxxxxxxxxxxxxxxxxx.field_200702_b.func_200721_a(☃);
                                          } catch (Exception var41) {
                                             field_209383_a.warn("Failed to create mob", var41);
                                             return ☃x;
                                          }

                                          ☃.func_70012_b(
                                             (double)☃xxxxxxxxxxxxxxxxxxxxxxxx,
                                             (double)☃xxxxxxxxxxxxxxxx,
                                             (double)☃xxxxxxxxxxxxxxxxxxxxxxxxx,
                                             ☃.field_73012_v.nextFloat() * 360.0F,
                                             0.0F
                                          );
                                          if ((☃xxxxxxxxxxxxxxxxxxxxxxxxxxx <= 16384.0 || !☃.func_70692_ba())
                                             && ☃.func_205020_a(☃, false)
                                             && ☃.func_205019_a(☃)) {
                                             ☃xxxxxxxxxxxxxxxxxxxx = ☃.func_204210_a(☃.func_175649_E(new BlockPos(☃)), ☃xxxxxxxxxxxxxxxxxxxx, null);
                                             if (☃.func_205019_a(☃)) {
                                                ++☃xxxxxxxxxxxxx;
                                                ++☃xxxxxxxxxxxxxxxxxxxxxx;
                                                ☃.func_72838_d(☃);
                                             } else {
                                                ☃.func_70106_y();
                                             }

                                             if (☃xxxxxxxxxxxxx >= ☃.func_70641_bl()) {
                                                continue label152;
                                             }

                                             if (☃.func_204209_c(☃xxxxxxxxxxxxxxxxxxxxxx)) {
                                                break;
                                             }
                                          }

                                          ☃x += ☃xxxxxxxxxxxxx;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         return ☃x;
      }
   }

   private static BlockPos func_180621_a(World var0, int var1, int var2) {
      Chunk ☃ = ☃.func_72964_e(☃, ☃);
      int ☃x = ☃ * 16 + ☃.field_73012_v.nextInt(16);
      int ☃xx = ☃ * 16 + ☃.field_73012_v.nextInt(16);
      int ☃xxx = ☃.func_201576_a(Heightmap.Type.LIGHT_BLOCKING, ☃x, ☃xx) + 1;
      int ☃xxxx = ☃.field_73012_v.nextInt(☃xxx + 1);
      return new BlockPos(☃x, ☃xxxx, ☃xx);
   }

   public static boolean func_206851_a(IBlockState var0, IFluidState var1) {
      if (☃.func_185898_k()) {
         return false;
      } else if (☃.func_185897_m()) {
         return false;
      } else if (!☃.func_206888_e()) {
         return false;
      } else {
         return !☃.func_203425_a(BlockTags.field_203437_y);
      }
   }

   public static boolean func_209382_a(
      EntitySpawnPlacementRegistry.SpawnPlacementType var0, IWorldReaderBase var1, BlockPos var2, @Nullable EntityType<? extends EntityLiving> var3
   ) {
      if (☃ != null && ☃.func_175723_af().func_177746_a(☃)) {
         IBlockState ☃ = ☃.func_180495_p(☃);
         IFluidState ☃x = ☃.func_204610_c(☃);
         switch(☃) {
            case IN_WATER:
               return ☃x.func_206884_a(FluidTags.field_206959_a)
                  && ☃.func_204610_c(☃.func_177977_b()).func_206884_a(FluidTags.field_206959_a)
                  && !☃.func_180495_p(☃.func_177984_a()).func_185915_l();
            case ON_GROUND:
            default:
               IBlockState ☃xx = ☃.func_180495_p(☃.func_177977_b());
               if (☃xx.func_185896_q() || ☃ != null && EntitySpawnPlacementRegistry.func_209345_a(☃, ☃xx)) {
                  Block ☃xxx = ☃xx.func_177230_c();
                  boolean ☃xxxx = ☃xxx != Blocks.field_150357_h && ☃xxx != Blocks.field_180401_cv;
                  return ☃xxxx && func_206851_a(☃, ☃x) && func_206851_a(☃.func_180495_p(☃.func_177984_a()), ☃.func_204610_c(☃.func_177984_a()));
               } else {
                  return false;
               }
         }
      } else {
         return false;
      }
   }

   public static void func_77191_a(IWorld var0, Biome var1, int var2, int var3, Random var4) {
      List<Biome.SpawnListEntry> ☃ = ☃.func_76747_a(EnumCreatureType.CREATURE);
      if (!☃.isEmpty()) {
         int ☃x = ☃ << 4;
         int ☃xx = ☃ << 4;

         while(☃.nextFloat() < ☃.func_76741_f()) {
            Biome.SpawnListEntry ☃xxx = WeightedRandom.func_76271_a(☃, ☃);
            int ☃xxxx = ☃xxx.field_76301_c + ☃.nextInt(1 + ☃xxx.field_76299_d - ☃xxx.field_76301_c);
            IEntityLivingData ☃xxxxx = null;
            int ☃xxxxxx = ☃x + ☃.nextInt(16);
            int ☃xxxxxxx = ☃xx + ☃.nextInt(16);
            int ☃xxxxxxxx = ☃xxxxxx;
            int ☃xxxxxxxxx = ☃xxxxxxx;

            for(int ☃xxxxxxxxxx = 0; ☃xxxxxxxxxx < ☃xxxx; ++☃xxxxxxxxxx) {
               boolean ☃xxxxxxxxxxx = false;

               for(int ☃xxxxxxxxxxxx = 0; !☃xxxxxxxxxxx && ☃xxxxxxxxxxxx < 4; ++☃xxxxxxxxxxxx) {
                  BlockPos ☃xxxxxxxxxxxxx = func_208498_a(☃, ☃xxx.field_200702_b, ☃xxxxxx, ☃xxxxxxx);
                  if (func_209382_a(EntitySpawnPlacementRegistry.SpawnPlacementType.ON_GROUND, ☃, ☃xxxxxxxxxxxxx, ☃xxx.field_200702_b)) {
                     EntityLiving ☃;
                     try {
                        ☃ = ☃xxx.field_200702_b.func_200721_a(☃.func_201672_e());
                     } catch (Exception var24) {
                        field_209383_a.warn("Failed to create mob", var24);
                        continue;
                     }

                     double ☃xxxxxxxxxxxxxx = MathHelper.func_151237_a(
                        (double)☃xxxxxx, (double)☃x + (double)☃.field_70130_N, (double)☃x + 16.0 - (double)☃.field_70130_N
                     );
                     double ☃xxxxxxxxxxxxxxx = MathHelper.func_151237_a(
                        (double)☃xxxxxxx, (double)☃xx + (double)☃.field_70130_N, (double)☃xx + 16.0 - (double)☃.field_70130_N
                     );
                     ☃.func_70012_b(☃xxxxxxxxxxxxxx, (double)☃xxxxxxxxxxxxx.func_177956_o(), ☃xxxxxxxxxxxxxxx, ☃.nextFloat() * 360.0F, 0.0F);
                     if (☃.func_205020_a(☃, false) && ☃.func_205019_a(☃)) {
                        ☃xxxxx = ☃.func_204210_a(☃.func_175649_E(new BlockPos(☃)), ☃xxxxx, null);
                        ☃.func_72838_d(☃);
                        ☃xxxxxxxxxxx = true;
                     }
                  }

                  ☃xxxxxx += ☃.nextInt(5) - ☃.nextInt(5);

                  for(☃xxxxxxx += ☃.nextInt(5) - ☃.nextInt(5);
                     ☃xxxxxx < ☃x || ☃xxxxxx >= ☃x + 16 || ☃xxxxxxx < ☃xx || ☃xxxxxxx >= ☃xx + 16;
                     ☃xxxxxxx = ☃xxxxxxxxx + ☃.nextInt(5) - ☃.nextInt(5)
                  ) {
                     ☃xxxxxx = ☃xxxxxxxx + ☃.nextInt(5) - ☃.nextInt(5);
                  }
               }
            }
         }
      }
   }

   private static BlockPos func_208498_a(IWorld var0, @Nullable EntityType<? extends EntityLiving> var1, int var2, int var3) {
      BlockPos ☃ = new BlockPos(☃, ☃.func_201676_a(EntitySpawnPlacementRegistry.func_209342_b(☃), ☃, ☃), ☃);
      BlockPos ☃x = ☃.func_177977_b();
      return ☃.func_180495_p(☃x).func_196957_g(☃, ☃x, PathType.LAND) ? ☃x : ☃;
   }
}
