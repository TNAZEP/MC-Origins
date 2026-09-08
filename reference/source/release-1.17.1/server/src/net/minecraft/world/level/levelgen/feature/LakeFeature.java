package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.BaseStoneSource;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.material.Material;

public class LakeFeature extends Feature<BlockStateConfiguration> {
   private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();

   public LakeFeature(Codec<BlockStateConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<BlockStateConfiguration> var1) {
      BlockPos â˜ƒ = â˜ƒ.origin();
      WorldGenLevel â˜ƒx = â˜ƒ.level();
      Random â˜ƒxx = â˜ƒ.random();
      BlockStateConfiguration â˜ƒxxx = â˜ƒ.config();

      while(â˜ƒ.getY() > â˜ƒx.getMinBuildHeight() + 5 && â˜ƒx.isEmptyBlock(â˜ƒ)) {
         â˜ƒ = â˜ƒ.below();
      }

      if (â˜ƒ.getY() <= â˜ƒx.getMinBuildHeight() + 4) {
         return false;
      } else {
         â˜ƒ = â˜ƒ.below(4);
         if (â˜ƒx.startsForFeature(SectionPos.of(â˜ƒ), StructureFeature.VILLAGE).findAny().isPresent()) {
            return false;
         } else {
            boolean[] â˜ƒxxxx = new boolean[2048];
            int â˜ƒxxxxx = â˜ƒxx.nextInt(4) + 4;

            for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒxxxxx; ++â˜ƒxxxxxx) {
               double â˜ƒxxxxxxx = â˜ƒxx.nextDouble() * 6.0 + 3.0;
               double â˜ƒxxxxxxxx = â˜ƒxx.nextDouble() * 4.0 + 2.0;
               double â˜ƒxxxxxxxxx = â˜ƒxx.nextDouble() * 6.0 + 3.0;
               double â˜ƒxxxxxxxxxx = â˜ƒxx.nextDouble() * (16.0 - â˜ƒxxxxxxx - 2.0) + 1.0 + â˜ƒxxxxxxx / 2.0;
               double â˜ƒxxxxxxxxxxx = â˜ƒxx.nextDouble() * (8.0 - â˜ƒxxxxxxxx - 4.0) + 2.0 + â˜ƒxxxxxxxx / 2.0;
               double â˜ƒxxxxxxxxxxxx = â˜ƒxx.nextDouble() * (16.0 - â˜ƒxxxxxxxxx - 2.0) + 1.0 + â˜ƒxxxxxxxxx / 2.0;

               for(int â˜ƒxxxxxxxxxxxxx = 1; â˜ƒxxxxxxxxxxxxx < 15; ++â˜ƒxxxxxxxxxxxxx) {
                  for(int â˜ƒxxxxxxxxxxxxxx = 1; â˜ƒxxxxxxxxxxxxxx < 15; ++â˜ƒxxxxxxxxxxxxxx) {
                     for(int â˜ƒxxxxxxxxxxxxxxx = 1; â˜ƒxxxxxxxxxxxxxxx < 7; ++â˜ƒxxxxxxxxxxxxxxx) {
                        double â˜ƒxxxxxxxxxxxxxxxx = ((double)â˜ƒxxxxxxxxxxxxx - â˜ƒxxxxxxxxxx) / (â˜ƒxxxxxxx / 2.0);
                        double â˜ƒxxxxxxxxxxxxxxxxx = ((double)â˜ƒxxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxx) / (â˜ƒxxxxxxxx / 2.0);
                        double â˜ƒxxxxxxxxxxxxxxxxxx = ((double)â˜ƒxxxxxxxxxxxxxx - â˜ƒxxxxxxxxxxxx) / (â˜ƒxxxxxxxxx / 2.0);
                        double â˜ƒxxxxxxxxxxxxxxxxxxx = â˜ƒxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxx
                           + â˜ƒxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxx
                           + â˜ƒxxxxxxxxxxxxxxxxxx * â˜ƒxxxxxxxxxxxxxxxxxx;
                        if (â˜ƒxxxxxxxxxxxxxxxxxxx < 1.0) {
                           â˜ƒxxxx[(â˜ƒxxxxxxxxxxxxx * 16 + â˜ƒxxxxxxxxxxxxxx) * 8 + â˜ƒxxxxxxxxxxxxxxx] = true;
                        }
                     }
                  }
               }
            }

            for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 16; ++â˜ƒxxxxxx) {
               for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < 16; ++â˜ƒxxxxxxx) {
                  for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < 8; ++â˜ƒxxxxxxxx) {
                     boolean â˜ƒxxxxxxxxx = !â˜ƒxxxx[(â˜ƒxxxxxx * 16 + â˜ƒxxxxxxx) * 8 + â˜ƒxxxxxxxx]
                        && (
                           â˜ƒxxxxxx < 15 && â˜ƒxxxx[((â˜ƒxxxxxx + 1) * 16 + â˜ƒxxxxxxx) * 8 + â˜ƒxxxxxxxx]
                              || â˜ƒxxxxxx > 0 && â˜ƒxxxx[((â˜ƒxxxxxx - 1) * 16 + â˜ƒxxxxxxx) * 8 + â˜ƒxxxxxxxx]
                              || â˜ƒxxxxxxx < 15 && â˜ƒxxxx[(â˜ƒxxxxxx * 16 + â˜ƒxxxxxxx + 1) * 8 + â˜ƒxxxxxxxx]
                              || â˜ƒxxxxxxx > 0 && â˜ƒxxxx[(â˜ƒxxxxxx * 16 + (â˜ƒxxxxxxx - 1)) * 8 + â˜ƒxxxxxxxx]
                              || â˜ƒxxxxxxxx < 7 && â˜ƒxxxx[(â˜ƒxxxxxx * 16 + â˜ƒxxxxxxx) * 8 + â˜ƒxxxxxxxx + 1]
                              || â˜ƒxxxxxxxx > 0 && â˜ƒxxxx[(â˜ƒxxxxxx * 16 + â˜ƒxxxxxxx) * 8 + (â˜ƒxxxxxxxx - 1)]
                        );
                     if (â˜ƒxxxxxxxxx) {
                        Material â˜ƒxxxxxxxxxx = â˜ƒx.getBlockState(â˜ƒ.offset(â˜ƒxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxx)).getMaterial();
                        if (â˜ƒxxxxxxxx >= 4 && â˜ƒxxxxxxxxxx.isLiquid()) {
                           return false;
                        }

                        if (â˜ƒxxxxxxxx < 4 && !â˜ƒxxxxxxxxxx.isSolid() && â˜ƒx.getBlockState(â˜ƒ.offset(â˜ƒxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxx)) != â˜ƒxxx.state) {
                           return false;
                        }
                     }
                  }
               }
            }

            for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 16; ++â˜ƒxxxxxx) {
               for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < 16; ++â˜ƒxxxxxxx) {
                  for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < 8; ++â˜ƒxxxxxxxx) {
                     if (â˜ƒxxxx[(â˜ƒxxxxxx * 16 + â˜ƒxxxxxxx) * 8 + â˜ƒxxxxxxxx]) {
                        BlockPos â˜ƒxxxxxxxxx = â˜ƒ.offset(â˜ƒxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxx);
                        boolean â˜ƒxxxxxxxxxx = â˜ƒxxxxxxxx >= 4;
                        â˜ƒx.setBlock(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxxx ? AIR : â˜ƒxxx.state, 2);
                        if (â˜ƒxxxxxxxxxx) {
                           â˜ƒx.getBlockTicks().scheduleTick(â˜ƒxxxxxxxxx, AIR.getBlock(), 0);
                           this.markAboveForPostProcessing(â˜ƒx, â˜ƒxxxxxxxxx);
                        }
                     }
                  }
               }
            }

            for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 16; ++â˜ƒxxxxxx) {
               for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < 16; ++â˜ƒxxxxxxx) {
                  for(int â˜ƒxxxxxxxx = 4; â˜ƒxxxxxxxx < 8; ++â˜ƒxxxxxxxx) {
                     if (â˜ƒxxxx[(â˜ƒxxxxxx * 16 + â˜ƒxxxxxxx) * 8 + â˜ƒxxxxxxxx]) {
                        BlockPos â˜ƒxxxxxxxxx = â˜ƒ.offset(â˜ƒxxxxxx, â˜ƒxxxxxxxx - 1, â˜ƒxxxxxxx);
                        if (isDirt(â˜ƒx.getBlockState(â˜ƒxxxxxxxxx)) && â˜ƒx.getBrightness(LightLayer.SKY, â˜ƒ.offset(â˜ƒxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxx)) > 0) {
                           Biome â˜ƒxxxxxxxxxx = â˜ƒx.getBiome(â˜ƒxxxxxxxxx);
                           if (â˜ƒxxxxxxxxxx.getGenerationSettings().getSurfaceBuilderConfig().getTopMaterial().is(Blocks.MYCELIUM)) {
                              â˜ƒx.setBlock(â˜ƒxxxxxxxxx, Blocks.MYCELIUM.defaultBlockState(), 2);
                           } else {
                              â˜ƒx.setBlock(â˜ƒxxxxxxxxx, Blocks.GRASS_BLOCK.defaultBlockState(), 2);
                           }
                        }
                     }
                  }
               }
            }

            if (â˜ƒxxx.state.getMaterial() == Material.LAVA) {
               BaseStoneSource â˜ƒxxxxxx = â˜ƒ.chunkGenerator().getBaseStoneSource();

               for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < 16; ++â˜ƒxxxxxxx) {
                  for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < 16; ++â˜ƒxxxxxxxx) {
                     for(int â˜ƒxxxxxxxxx = 0; â˜ƒxxxxxxxxx < 8; ++â˜ƒxxxxxxxxx) {
                        boolean â˜ƒxxxxxxxxxx = !â˜ƒxxxx[(â˜ƒxxxxxxx * 16 + â˜ƒxxxxxxxx) * 8 + â˜ƒxxxxxxxxx]
                           && (
                              â˜ƒxxxxxxx < 15 && â˜ƒxxxx[((â˜ƒxxxxxxx + 1) * 16 + â˜ƒxxxxxxxx) * 8 + â˜ƒxxxxxxxxx]
                                 || â˜ƒxxxxxxx > 0 && â˜ƒxxxx[((â˜ƒxxxxxxx - 1) * 16 + â˜ƒxxxxxxxx) * 8 + â˜ƒxxxxxxxxx]
                                 || â˜ƒxxxxxxxx < 15 && â˜ƒxxxx[(â˜ƒxxxxxxx * 16 + â˜ƒxxxxxxxx + 1) * 8 + â˜ƒxxxxxxxxx]
                                 || â˜ƒxxxxxxxx > 0 && â˜ƒxxxx[(â˜ƒxxxxxxx * 16 + (â˜ƒxxxxxxxx - 1)) * 8 + â˜ƒxxxxxxxxx]
                                 || â˜ƒxxxxxxxxx < 7 && â˜ƒxxxx[(â˜ƒxxxxxxx * 16 + â˜ƒxxxxxxxx) * 8 + â˜ƒxxxxxxxxx + 1]
                                 || â˜ƒxxxxxxxxx > 0 && â˜ƒxxxx[(â˜ƒxxxxxxx * 16 + â˜ƒxxxxxxxx) * 8 + (â˜ƒxxxxxxxxx - 1)]
                           );
                        if (â˜ƒxxxxxxxxxx && (â˜ƒxxxxxxxxx < 4 || â˜ƒxx.nextInt(2) != 0)) {
                           BlockState â˜ƒxxxxxxxxxxx = â˜ƒx.getBlockState(â˜ƒ.offset(â˜ƒxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxx));
                           if (â˜ƒxxxxxxxxxxx.getMaterial().isSolid() && !â˜ƒxxxxxxxxxxx.is(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE)) {
                              BlockPos â˜ƒxxxxxxxxxxxx = â˜ƒ.offset(â˜ƒxxxxxxx, â˜ƒxxxxxxxxx, â˜ƒxxxxxxxx);
                              â˜ƒx.setBlock(â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxx.getBaseBlock(â˜ƒxxxxxxxxxxxx), 2);
                              this.markAboveForPostProcessing(â˜ƒx, â˜ƒxxxxxxxxxxxx);
                           }
                        }
                     }
                  }
               }
            }

            if (â˜ƒxxx.state.getMaterial() == Material.WATER) {
               for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < 16; ++â˜ƒxxxxxx) {
                  for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx < 16; ++â˜ƒxxxxxxx) {
                     int â˜ƒxxxxxxxx = 4;
                     BlockPos â˜ƒxxxxxxxxx = â˜ƒ.offset(â˜ƒxxxxxx, 4, â˜ƒxxxxxxx);
                     if (â˜ƒx.getBiome(â˜ƒxxxxxxxxx).shouldFreeze(â˜ƒx, â˜ƒxxxxxxxxx, false)) {
                        â˜ƒx.setBlock(â˜ƒxxxxxxxxx, Blocks.ICE.defaultBlockState(), 2);
                     }
                  }
               }
            }

            return true;
         }
      }
   }
}
