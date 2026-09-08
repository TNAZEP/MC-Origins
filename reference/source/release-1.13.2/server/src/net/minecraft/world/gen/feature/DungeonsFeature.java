package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.storage.loot.LootTableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DungeonsFeature extends Feature<NoFeatureConfig> {
   private static final Logger field_175918_a = LogManager.getLogger();
   private static final EntityType<?>[] field_175916_b = new EntityType[]{
      EntityType.field_200741_ag, EntityType.field_200725_aD, EntityType.field_200725_aD, EntityType.field_200748_an
   };
   private static final IBlockState field_205189_c = Blocks.field_201941_jj.func_176223_P();

   public boolean func_212245_a(IWorld var1, IChunkGenerator<? extends IChunkGenSettings> var2, Random var3, BlockPos var4, NoFeatureConfig var5) {
      int ☃ = 3;
      int ☃x = ☃.nextInt(2) + 2;
      int ☃xx = -☃x - 1;
      int ☃xxx = ☃x + 1;
      int ☃xxxx = -1;
      int ☃xxxxx = 4;
      int ☃xxxxxx = ☃.nextInt(2) + 2;
      int ☃xxxxxxx = -☃xxxxxx - 1;
      int ☃xxxxxxxx = ☃xxxxxx + 1;
      int ☃xxxxxxxxx = 0;

      for(int ☃xxxxxxxxxx = ☃xx; ☃xxxxxxxxxx <= ☃xxx; ++☃xxxxxxxxxx) {
         for(int ☃xxxxxxxxxxx = -1; ☃xxxxxxxxxxx <= 4; ++☃xxxxxxxxxxx) {
            for(int ☃xxxxxxxxxxxx = ☃xxxxxxx; ☃xxxxxxxxxxxx <= ☃xxxxxxxx; ++☃xxxxxxxxxxxx) {
               BlockPos ☃xxxxxxxxxxxxx = ☃.func_177982_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx);
               Material ☃xxxxxxxxxxxxxx = ☃.func_180495_p(☃xxxxxxxxxxxxx).func_185904_a();
               boolean ☃xxxxxxxxxxxxxxx = ☃xxxxxxxxxxxxxx.func_76220_a();
               if (☃xxxxxxxxxxx == -1 && !☃xxxxxxxxxxxxxxx) {
                  return false;
               }

               if (☃xxxxxxxxxxx == 4 && !☃xxxxxxxxxxxxxxx) {
                  return false;
               }

               if ((☃xxxxxxxxxx == ☃xx || ☃xxxxxxxxxx == ☃xxx || ☃xxxxxxxxxxxx == ☃xxxxxxx || ☃xxxxxxxxxxxx == ☃xxxxxxxx)
                  && ☃xxxxxxxxxxx == 0
                  && ☃.func_175623_d(☃xxxxxxxxxxxxx)
                  && ☃.func_175623_d(☃xxxxxxxxxxxxx.func_177984_a())) {
                  ++☃xxxxxxxxx;
               }
            }
         }
      }

      if (☃xxxxxxxxx >= 1 && ☃xxxxxxxxx <= 5) {
         for(int ☃xxxxxxxxxx = ☃xx; ☃xxxxxxxxxx <= ☃xxx; ++☃xxxxxxxxxx) {
            for(int ☃xxxxxxxxxxx = 3; ☃xxxxxxxxxxx >= -1; --☃xxxxxxxxxxx) {
               for(int ☃xxxxxxxxxxxx = ☃xxxxxxx; ☃xxxxxxxxxxxx <= ☃xxxxxxxx; ++☃xxxxxxxxxxxx) {
                  BlockPos ☃xxxxxxxxxxxxx = ☃.func_177982_a(☃xxxxxxxxxx, ☃xxxxxxxxxxx, ☃xxxxxxxxxxxx);
                  if (☃xxxxxxxxxx != ☃xx
                     && ☃xxxxxxxxxxx != -1
                     && ☃xxxxxxxxxxxx != ☃xxxxxxx
                     && ☃xxxxxxxxxx != ☃xxx
                     && ☃xxxxxxxxxxx != 4
                     && ☃xxxxxxxxxxxx != ☃xxxxxxxx) {
                     if (☃.func_180495_p(☃xxxxxxxxxxxxx).func_177230_c() != Blocks.field_150486_ae) {
                        ☃.func_180501_a(☃xxxxxxxxxxxxx, field_205189_c, 2);
                     }
                  } else if (☃xxxxxxxxxxxxx.func_177956_o() >= 0 && !☃.func_180495_p(☃xxxxxxxxxxxxx.func_177977_b()).func_185904_a().func_76220_a()) {
                     ☃.func_180501_a(☃xxxxxxxxxxxxx, field_205189_c, 2);
                  } else if (☃.func_180495_p(☃xxxxxxxxxxxxx).func_185904_a().func_76220_a()
                     && ☃.func_180495_p(☃xxxxxxxxxxxxx).func_177230_c() != Blocks.field_150486_ae) {
                     if (☃xxxxxxxxxxx == -1 && ☃.nextInt(4) != 0) {
                        ☃.func_180501_a(☃xxxxxxxxxxxxx, Blocks.field_150341_Y.func_176223_P(), 2);
                     } else {
                        ☃.func_180501_a(☃xxxxxxxxxxxxx, Blocks.field_150347_e.func_176223_P(), 2);
                     }
                  }
               }
            }
         }

         for(int ☃xxxxxxxxxx = 0; ☃xxxxxxxxxx < 2; ++☃xxxxxxxxxx) {
            for(int ☃xxxxxxxxxxx = 0; ☃xxxxxxxxxxx < 3; ++☃xxxxxxxxxxx) {
               int ☃xxxxxxxxxxxx = ☃.func_177958_n() + ☃.nextInt(☃x * 2 + 1) - ☃x;
               int ☃xxxxxxxxxxxxx = ☃.func_177956_o();
               int ☃xxxxxxxxxxxxxx = ☃.func_177952_p() + ☃.nextInt(☃xxxxxx * 2 + 1) - ☃xxxxxx;
               BlockPos ☃xxxxxxxxxxxxxxx = new BlockPos(☃xxxxxxxxxxxx, ☃xxxxxxxxxxxxx, ☃xxxxxxxxxxxxxx);
               if (☃.func_175623_d(☃xxxxxxxxxxxxxxx)) {
                  int ☃xxxxxxxxxxxxxxxx = 0;

                  for(EnumFacing ☃xxxxxxxxxxxxxxxxx : EnumFacing.Plane.HORIZONTAL) {
                     if (☃.func_180495_p(☃xxxxxxxxxxxxxxx.func_177972_a(☃xxxxxxxxxxxxxxxxx)).func_185904_a().func_76220_a()) {
                        ++☃xxxxxxxxxxxxxxxx;
                     }
                  }

                  if (☃xxxxxxxxxxxxxxxx == 1) {
                     ☃.func_180501_a(☃xxxxxxxxxxxxxxx, StructurePiece.func_197528_a(☃, ☃xxxxxxxxxxxxxxx, Blocks.field_150486_ae.func_176223_P()), 2);
                     TileEntityLockableLoot.func_195479_a(☃, ☃, ☃xxxxxxxxxxxxxxx, LootTableList.field_186422_d);
                     break;
                  }
               }
            }
         }

         ☃.func_180501_a(☃, Blocks.field_150474_ac.func_176223_P(), 2);
         TileEntity ☃xxxxxxxxxx = ☃.func_175625_s(☃);
         if (☃xxxxxxxxxx instanceof TileEntityMobSpawner) {
            ((TileEntityMobSpawner)☃xxxxxxxxxx).func_145881_a().func_200876_a(this.func_201043_a(☃));
         } else {
            field_175918_a.error("Failed to fetch mob spawner entity at ({}, {}, {})", ☃.func_177958_n(), ☃.func_177956_o(), ☃.func_177952_p());
         }

         return true;
      } else {
         return false;
      }
   }

   private EntityType<?> func_201043_a(Random var1) {
      return field_175916_b[☃.nextInt(field_175916_b.length)];
   }
}
