package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.material.Material;

public class HugeFungusFeature extends Feature<HugeFungusConfiguration> {
   private static final float HUGE_PROBABILITY = 0.06F;

   public HugeFungusFeature(Codec<HugeFungusConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<HugeFungusConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      BlockPos â˜ƒx = â˜ƒ.origin();
      Random â˜ƒxx = â˜ƒ.random();
      ChunkGenerator â˜ƒxxx = â˜ƒ.chunkGenerator();
      HugeFungusConfiguration â˜ƒxxxx = â˜ƒ.config();
      Block â˜ƒxxxxx = â˜ƒxxxx.validBaseState.getBlock();
      BlockPos â˜ƒxxxxxx = null;
      BlockState â˜ƒxxxxxxx = â˜ƒ.getBlockState(â˜ƒx.below());
      if (â˜ƒxxxxxxx.is(â˜ƒxxxxx)) {
         â˜ƒxxxxxx = â˜ƒx;
      }

      if (â˜ƒxxxxxx == null) {
         return false;
      } else {
         int â˜ƒ = Mth.nextInt(â˜ƒxx, 4, 13);
         if (â˜ƒxx.nextInt(12) == 0) {
            â˜ƒ *= 2;
         }

         if (!â˜ƒxxxx.planted) {
            int â˜ƒ = â˜ƒxxx.getGenDepth();
            if (â˜ƒxxxxxx.getY() + â˜ƒ + 1 >= â˜ƒ) {
               return false;
            }
         }

         boolean â˜ƒ = !â˜ƒxxxx.planted && â˜ƒxx.nextFloat() < 0.06F;
         â˜ƒ.setBlock(â˜ƒx, Blocks.AIR.defaultBlockState(), 4);
         this.placeStem(â˜ƒ, â˜ƒxx, â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒ, â˜ƒ);
         this.placeHat(â˜ƒ, â˜ƒxx, â˜ƒxxxx, â˜ƒxxxxxx, â˜ƒ, â˜ƒ);
         return true;
      }
   }

   private static boolean isReplaceable(LevelAccessor var0, BlockPos var1, boolean var2) {
      return â˜ƒ.isStateAtPosition(â˜ƒ, var1x -> {
         Material â˜ƒ = var1x.getMaterial();
         return var1x.getMaterial().isReplaceable() || â˜ƒ && â˜ƒ == Material.PLANT;
      });
   }

   private void placeStem(LevelAccessor var1, Random var2, HugeFungusConfiguration var3, BlockPos var4, int var5, boolean var6) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();
      BlockState â˜ƒx = â˜ƒ.stemState;
      int â˜ƒxx = â˜ƒ ? 1 : 0;

      for(int â˜ƒxxx = -â˜ƒxx; â˜ƒxxx <= â˜ƒxx; ++â˜ƒxxx) {
         for(int â˜ƒxxxx = -â˜ƒxx; â˜ƒxxxx <= â˜ƒxx; ++â˜ƒxxxx) {
            boolean â˜ƒxxxxx = â˜ƒ && Mth.abs(â˜ƒxxx) == â˜ƒxx && Mth.abs(â˜ƒxxxx) == â˜ƒxx;

            for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < â˜ƒ; ++â˜ƒxxxxxx) {
               â˜ƒ.setWithOffset(â˜ƒ, â˜ƒxxx, â˜ƒxxxxxx, â˜ƒxxxx);
               if (isReplaceable(â˜ƒ, â˜ƒ, true)) {
                  if (â˜ƒ.planted) {
                     if (!â˜ƒ.getBlockState(â˜ƒ.below()).isAir()) {
                        â˜ƒ.destroyBlock(â˜ƒ, true);
                     }

                     â˜ƒ.setBlock(â˜ƒ, â˜ƒx, 3);
                  } else if (â˜ƒxxxxx) {
                     if (â˜ƒ.nextFloat() < 0.1F) {
                        this.setBlock(â˜ƒ, â˜ƒ, â˜ƒx);
                     }
                  } else {
                     this.setBlock(â˜ƒ, â˜ƒ, â˜ƒx);
                  }
               }
            }
         }
      }
   }

   private void placeHat(LevelAccessor var1, Random var2, HugeFungusConfiguration var3, BlockPos var4, int var5, boolean var6) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();
      boolean â˜ƒx = â˜ƒ.hatState.is(Blocks.NETHER_WART_BLOCK);
      int â˜ƒxx = Math.min(â˜ƒ.nextInt(1 + â˜ƒ / 3) + 5, â˜ƒ);
      int â˜ƒxxx = â˜ƒ - â˜ƒxx;

      for(int â˜ƒxxxx = â˜ƒxxx; â˜ƒxxxx <= â˜ƒ; ++â˜ƒxxxx) {
         int â˜ƒxxxxx = â˜ƒxxxx < â˜ƒ - â˜ƒ.nextInt(3) ? 2 : 1;
         if (â˜ƒxx > 8 && â˜ƒxxxx < â˜ƒxxx + 4) {
            â˜ƒxxxxx = 3;
         }

         if (â˜ƒ) {
            ++â˜ƒxxxxx;
         }

         for(int â˜ƒxxxxx = -â˜ƒxxxxx; â˜ƒxxxxx <= â˜ƒxxxxx; ++â˜ƒxxxxx) {
            for(int â˜ƒxxxxxx = -â˜ƒxxxxx; â˜ƒxxxxxx <= â˜ƒxxxxx; ++â˜ƒxxxxxx) {
               boolean â˜ƒxxxxxxx = â˜ƒxxxxx == -â˜ƒxxxxx || â˜ƒxxxxx == â˜ƒxxxxx;
               boolean â˜ƒxxxxxxxx = â˜ƒxxxxxx == -â˜ƒxxxxx || â˜ƒxxxxxx == â˜ƒxxxxx;
               boolean â˜ƒxxxxxxxxx = !â˜ƒxxxxxxx && !â˜ƒxxxxxxxx && â˜ƒxxxx != â˜ƒ;
               boolean â˜ƒxxxxxxxxxx = â˜ƒxxxxxxx && â˜ƒxxxxxxxx;
               boolean â˜ƒxxxxxxxxxxx = â˜ƒxxxx < â˜ƒxxx + 3;
               â˜ƒ.setWithOffset(â˜ƒ, â˜ƒxxxxx, â˜ƒxxxx, â˜ƒxxxxxx);
               if (isReplaceable(â˜ƒ, â˜ƒ, false)) {
                  if (â˜ƒ.planted && !â˜ƒ.getBlockState(â˜ƒ.below()).isAir()) {
                     â˜ƒ.destroyBlock(â˜ƒ, true);
                  }

                  if (â˜ƒxxxxxxxxxxx) {
                     if (!â˜ƒxxxxxxxxx) {
                        this.placeHatDropBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.hatState, â˜ƒx);
                     }
                  } else if (â˜ƒxxxxxxxxx) {
                     this.placeHatBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.1F, 0.2F, â˜ƒx ? 0.1F : 0.0F);
                  } else if (â˜ƒxxxxxxxxxx) {
                     this.placeHatBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 0.01F, 0.7F, â˜ƒx ? 0.083F : 0.0F);
                  } else {
                     this.placeHatBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, 5.0E-4F, 0.98F, â˜ƒx ? 0.07F : 0.0F);
                  }
               }
            }
         }
      }
   }

   private void placeHatBlock(LevelAccessor var1, Random var2, HugeFungusConfiguration var3, BlockPos.MutableBlockPos var4, float var5, float var6, float var7) {
      if (â˜ƒ.nextFloat() < â˜ƒ) {
         this.setBlock(â˜ƒ, â˜ƒ, â˜ƒ.decorState);
      } else if (â˜ƒ.nextFloat() < â˜ƒ) {
         this.setBlock(â˜ƒ, â˜ƒ, â˜ƒ.hatState);
         if (â˜ƒ.nextFloat() < â˜ƒ) {
            tryPlaceWeepingVines(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   private void placeHatDropBlock(LevelAccessor var1, Random var2, BlockPos var3, BlockState var4, boolean var5) {
      if (â˜ƒ.getBlockState(â˜ƒ.below()).is(â˜ƒ.getBlock())) {
         this.setBlock(â˜ƒ, â˜ƒ, â˜ƒ);
      } else if ((double)â˜ƒ.nextFloat() < 0.15) {
         this.setBlock(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ && â˜ƒ.nextInt(11) == 0) {
            tryPlaceWeepingVines(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   private static void tryPlaceWeepingVines(BlockPos var0, LevelAccessor var1, Random var2) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable().move(Direction.DOWN);
      if (â˜ƒ.isEmptyBlock(â˜ƒ)) {
         int â˜ƒx = Mth.nextInt(â˜ƒ, 1, 5);
         if (â˜ƒ.nextInt(7) == 0) {
            â˜ƒx *= 2;
         }

         int â˜ƒx = 23;
         int â˜ƒxx = 25;
         WeepingVinesFeature.placeWeepingVinesColumn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, 23, 25);
      }
   }
}
