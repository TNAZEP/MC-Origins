package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class OreFeature extends Feature<OreConfiguration> {
   public OreFeature(Codec<OreConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<OreConfiguration> var1) {
      Random â˜ƒ = â˜ƒ.random();
      BlockPos â˜ƒx = â˜ƒ.origin();
      WorldGenLevel â˜ƒxx = â˜ƒ.level();
      OreConfiguration â˜ƒxxx = â˜ƒ.config();
      float â˜ƒxxxx = â˜ƒ.nextFloat() * (float) Math.PI;
      float â˜ƒxxxxx = (float)â˜ƒxxx.size / 8.0F;
      int â˜ƒxxxxxx = Mth.ceil(((float)â˜ƒxxx.size / 16.0F * 2.0F + 1.0F) / 2.0F);
      double â˜ƒxxxxxxx = (double)â˜ƒx.getX() + Math.sin((double)â˜ƒxxxx) * (double)â˜ƒxxxxx;
      double â˜ƒxxxxxxxx = (double)â˜ƒx.getX() - Math.sin((double)â˜ƒxxxx) * (double)â˜ƒxxxxx;
      double â˜ƒxxxxxxxxx = (double)â˜ƒx.getZ() + Math.cos((double)â˜ƒxxxx) * (double)â˜ƒxxxxx;
      double â˜ƒxxxxxxxxxx = (double)â˜ƒx.getZ() - Math.cos((double)â˜ƒxxxx) * (double)â˜ƒxxxxx;
      int â˜ƒxxxxxxxxxxx = 2;
      double â˜ƒxxxxxxxxxxxx = (double)(â˜ƒx.getY() + â˜ƒ.nextInt(3) - 2);
      double â˜ƒxxxxxxxxxxxxx = (double)(â˜ƒx.getY() + â˜ƒ.nextInt(3) - 2);
      int â˜ƒxxxxxxxxxxxxxx = â˜ƒx.getX() - Mth.ceil(â˜ƒxxxxx) - â˜ƒxxxxxx;
      int â˜ƒxxxxxxxxxxxxxxx = â˜ƒx.getY() - 2 - â˜ƒxxxxxx;
      int â˜ƒxxxxxxxxxxxxxxxx = â˜ƒx.getZ() - Mth.ceil(â˜ƒxxxxx) - â˜ƒxxxxxx;
      int â˜ƒxxxxxxxxxxxxxxxxx = 2 * (Mth.ceil(â˜ƒxxxxx) + â˜ƒxxxxxx);
      int â˜ƒxxxxxxxxxxxxxxxxxx = 2 * (2 + â˜ƒxxxxxx);

      for(int â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxx; â˜ƒxxxxxxxxxxxxxxxxxxx <= â˜ƒxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxx; ++â˜ƒxxxxxxxxxxxxxxxxxxx) {
         for(int â˜ƒxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx;
            â˜ƒxxxxxxxxxxxxxxxxxxxx <= â˜ƒxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxx;
            ++â˜ƒxxxxxxxxxxxxxxxxxxxx
         ) {
            if (â˜ƒxxxxxxxxxxxxxxx <= â˜ƒxx.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxx)) {
               return this.doPlace(
                  â˜ƒxx,
                  â˜ƒ,
                  â˜ƒxxx,
                  â˜ƒxxxxxxx,
                  â˜ƒxxxxxxxx,
                  â˜ƒxxxxxxxxx,
                  â˜ƒxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxx,
                  â˜ƒxxxxxxxxxxxxxxxxxx
               );
            }
         }
      }

      return false;
   }

   protected boolean doPlace(
      WorldGenLevel var1,
      Random var2,
      OreConfiguration var3,
      double var4,
      double var6,
      double var8,
      double var10,
      double var12,
      double var14,
      int var16,
      int var17,
      int var18,
      int var19,
      int var20
   ) {
      int â˜ƒ = 0;
      BitSet â˜ƒx = new BitSet(â˜ƒ * â˜ƒ * â˜ƒ);
      BlockPos.MutableBlockPos â˜ƒxx = new BlockPos.MutableBlockPos();
      int â˜ƒxxx = â˜ƒ.size;
      double[] â˜ƒxxxx = new double[â˜ƒxxx * 4];

      for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxxx; ++â˜ƒxxxxx) {
         float â˜ƒxxxxxx = (float)â˜ƒxxxxx / (float)â˜ƒxxx;
         double â˜ƒxxxxxxx = Mth.lerp((double)â˜ƒxxxxxx, â˜ƒ, â˜ƒ);
         double â˜ƒxxxxxxxx = Mth.lerp((double)â˜ƒxxxxxx, â˜ƒ, â˜ƒ);
         double â˜ƒxxxxxxxxx = Mth.lerp((double)â˜ƒxxxxxx, â˜ƒ, â˜ƒ);
         double â˜ƒxxxxxxxxxx = â˜ƒ.nextDouble() * (double)â˜ƒxxx / 16.0;
         double â˜ƒxxxxxxxxxxx = ((double)(Mth.sin((float) Math.PI * â˜ƒxxxxxx) + 1.0F) * â˜ƒxxxxxxxxxx + 1.0) / 2.0;
         â˜ƒxxxx[â˜ƒxxxxx * 4 + 0] = â˜ƒxxxxxxx;
         â˜ƒxxxx[â˜ƒxxxxx * 4 + 1] = â˜ƒxxxxxxxx;
         â˜ƒxxxx[â˜ƒxxxxx * 4 + 2] = â˜ƒxxxxxxxxx;
         â˜ƒxxxx[â˜ƒxxxxx * 4 + 3] = â˜ƒxxxxxxxxxxx;
      }

      for(int â˜ƒxxxxx = 0; â˜ƒxxxxx < â˜ƒxxx - 1; ++â˜ƒxxxxx) {
         if (!(â˜ƒxxxx[â˜ƒxxxxx * 4 + 3] <= 0.0)) {
            for(int â˜ƒxxxxxx = â˜ƒxxxxx + 1; â˜ƒxxxxxx < â˜ƒxxx; ++â˜ƒxxxxxx) {
               if (!(â˜ƒxxxx[â˜ƒxxxxxx * 4 + 3] <= 0.0)) {
                  double â˜ƒxxxxxxx = â˜ƒxxxx[â˜ƒxxxxx * 4 + 0] - â˜ƒxxxx[â˜ƒxxxxxx * 4 + 0];
                  double â˜ƒxxxxxxxx = â˜ƒxxxx[â˜ƒxxxxx * 4 + 1] - â˜ƒxxxx[â˜ƒxxxxxx * 4 + 1];
                  double â˜ƒxxxxxxxxx = â˜ƒxxxx[â˜ƒxxxxx * 4 + 2] - â˜ƒxxxx[â˜ƒxxxxxx * 4 + 2];
                  double â˜ƒxxxxxxxxxx = â˜ƒxxxx[â˜ƒxxxxx * 4 + 3] - â˜ƒxxxx[â˜ƒxxxxxx * 4 + 3];
                  if (â˜ƒxxxxxxxxxx * â˜ƒxxxxxxxxxx > â˜ƒxxxxxxx * â˜ƒxxxxxxx + â˜ƒxxxxxxxx * â˜ƒxxxxxxxx + â˜ƒxxxxxxxxx * â˜ƒxxxxxxxxx) {
                     if (â˜ƒxxxxxxxxxx > 0.0) {
                        â˜ƒxxxx[â˜ƒxxxxxx * 4 + 3] = -1.0;
                     } else {
                        â˜ƒxxxx[â˜ƒxxxxx * 4 + 3] = -1.0;
                     }
                  }
               }
            }
         }
      }

      try (BulkSectionAccess â˜ƒxxxxx = new BulkSectionAccess(â˜ƒ)) {
         for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxx; ++â˜ƒxxxxxx) {
            double â˜ƒxxxxxxx = â˜ƒxxxx[â˜ƒxxxxxx * 4 + 3];
            if (!(â˜ƒxxxxxxx < 0.0)) {
               double â˜ƒxxxxxxxx = â˜ƒxxxx[â˜ƒxxxxxx * 4 + 0];
               double â˜ƒxxxxxxxxx = â˜ƒxxxx[â˜ƒxxxxxx * 4 + 1];
               double â˜ƒxxxxxxxxxx = â˜ƒxxxx[â˜ƒxxxxxx * 4 + 2];
               int â˜ƒxxxxxxxxxxx = Math.max(Mth.floor(â˜ƒxxxxxxxx - â˜ƒxxxxxxx), â˜ƒ);
               int â˜ƒxxxxxxxxxxxx = Math.max(Mth.floor(â˜ƒxxxxxxxxx - â˜ƒxxxxxxx), â˜ƒ);
               int â˜ƒxxxxxxxxxxxxx = Math.max(Mth.floor(â˜ƒxxxxxxxxxx - â˜ƒxxxxxxx), â˜ƒ);
               int â˜ƒxxxxxxxxxxxxxx = Math.max(Mth.floor(â˜ƒxxxxxxxx + â˜ƒxxxxxxx), â˜ƒxxxxxxxxxxx);
               int â˜ƒxxxxxxxxxxxxxxx = Math.max(Mth.floor(â˜ƒxxxxxxxxx + â˜ƒxxxxxxx), â˜ƒxxxxxxxxxxxx);
               int â˜ƒxxxxxxxxxxxxxxxx = Math.max(Mth.floor(â˜ƒxxxxxxxxxx + â˜ƒxxxxxxx), â˜ƒxxxxxxxxxxxxx);

               for(int â˜ƒxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxx; â˜ƒxxxxxxxxxxxxxxxxx <= â˜ƒxxxxxxxxxxxxxx; ++â˜ƒxxxxxxxxxxxxxxxxx) {
                  double â˜ƒxxxxxxxxxxxxxxxxxx = ((double)â˜ƒxxxxxxxxxxxxxxxxx + 0.5 - â˜ƒxxxxxxxx) / â˜ƒxxxxxxx;
                  if (â˜ƒxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxx < 1.0) {
                     for(int â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxx; â˜ƒxxxxxxxxxxxxxxxxxxx <= â˜ƒxxxxxxxxxxxxxxx; ++â˜ƒxxxxxxxxxxxxxxxxxxx) {
                        double â˜ƒxxxxxxxxxxxxxxxxxxxx = ((double)â˜ƒxxxxxxxxxxxxxxxxxxx + 0.5 - â˜ƒxxxxxxxxx) / â˜ƒxxxxxxx;
                        if (â˜ƒxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxx + â˜ƒxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxxx < 1.0) {
                           for(int â˜ƒxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxx; â˜ƒxxxxxxxxxxxxxxxxxxxxx <= â˜ƒxxxxxxxxxxxxxxxx; ++â˜ƒxxxxxxxxxxxxxxxxxxxxx) {
                              double â˜ƒxxxxxxxxxxxxxxxxxxxxxx = ((double)â˜ƒxxxxxxxxxxxxxxxxxxxxx + 0.5 - â˜ƒxxxxxxxxxx) / â˜ƒxxxxxxx;
                              if (â˜ƒxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxx
                                       + â˜ƒxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxxx
                                       + â˜ƒxxxxxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxxxxxx
                                    < 1.0
                                 && !â˜ƒ.isOutsideBuildHeight(â˜ƒxxxxxxxxxxxxxxxxxxx)) {
                                 int â˜ƒxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxx
                                    - â˜ƒ
                                    + (â˜ƒxxxxxxxxxxxxxxxxxxx - â˜ƒ) * â˜ƒ
                                    + (â˜ƒxxxxxxxxxxxxxxxxxxxxx - â˜ƒ) * â˜ƒ * â˜ƒ;
                                 if (!â˜ƒx.get(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx)) {
                                    â˜ƒx.set(â˜ƒxxxxxxxxxxxxxxxxxxxxxxx);
                                    â˜ƒxx.set(â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxx);
                                    if (â˜ƒ.ensureCanWrite(â˜ƒxx)) {
                                       LevelChunkSection â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxx.getSection(â˜ƒxx);
                                       if (â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx != LevelChunk.EMPTY_SECTION) {
                                          int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx = SectionPos.sectionRelative(â˜ƒxxxxxxxxxxxxxxxxx);
                                          int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx = SectionPos.sectionRelative(â˜ƒxxxxxxxxxxxxxxxxxxx);
                                          int â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx = SectionPos.sectionRelative(â˜ƒxxxxxxxxxxxxxxxxxxxxx);
                                          BlockState â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.getBlockState(
                                             â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                          );

                                          for(OreConfiguration.TargetBlockState â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx : â˜ƒ.targetStates) {
                                             if (canPlaceOre(
                                                â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxx::getBlockState, â˜ƒ, â˜ƒ, â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, â˜ƒxx
                                             )) {
                                                â˜ƒxxxxxxxxxxxxxxxxxxxxxxxx.setBlockState(
                                                   â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxx,
                                                   â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                                   â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                                   â˜ƒxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.state,
                                                   false
                                                );
                                                ++â˜ƒ;
                                                break;
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
            }
         }
      }

      return â˜ƒ > 0;
   }

   public static boolean canPlaceOre(
      BlockState var0,
      Function<BlockPos, BlockState> var1,
      Random var2,
      OreConfiguration var3,
      OreConfiguration.TargetBlockState var4,
      BlockPos.MutableBlockPos var5
   ) {
      if (!â˜ƒ.target.test(â˜ƒ, â˜ƒ)) {
         return false;
      } else if (shouldSkipAirCheck(â˜ƒ, â˜ƒ.discardChanceOnAirExposure)) {
         return true;
      } else {
         return !isAdjacentToAir(â˜ƒ, â˜ƒ);
      }
   }

   protected static boolean shouldSkipAirCheck(Random var0, float var1) {
      if (â˜ƒ <= 0.0F) {
         return true;
      } else if (â˜ƒ >= 1.0F) {
         return false;
      } else {
         return â˜ƒ.nextFloat() >= â˜ƒ;
      }
   }
}
