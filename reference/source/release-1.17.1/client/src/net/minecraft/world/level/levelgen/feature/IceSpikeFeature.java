package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class IceSpikeFeature extends Feature<NoneFeatureConfiguration> {
   public IceSpikeFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
      BlockPos â˜ƒ = â˜ƒ.origin();
      Random â˜ƒx = â˜ƒ.random();
      WorldGenLevel â˜ƒxx = â˜ƒ.level();

      while(â˜ƒxx.isEmptyBlock(â˜ƒ) && â˜ƒ.getY() > â˜ƒxx.getMinBuildHeight() + 2) {
         â˜ƒ = â˜ƒ.below();
      }

      if (!â˜ƒxx.getBlockState(â˜ƒ).is(Blocks.SNOW_BLOCK)) {
         return false;
      } else {
         â˜ƒ = â˜ƒ.above(â˜ƒx.nextInt(4));
         int â˜ƒxxx = â˜ƒx.nextInt(4) + 7;
         int â˜ƒxxxx = â˜ƒxxx / 4 + â˜ƒx.nextInt(2);
         if (â˜ƒxxxx > 1 && â˜ƒx.nextInt(60) == 0) {
            â˜ƒ = â˜ƒ.above(10 + â˜ƒx.nextInt(30));
         }

         for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxxx; ++â˜ƒxxx) {
            float â˜ƒxxxx = (1.0F - (float)â˜ƒxxx / (float)â˜ƒxxx) * (float)â˜ƒxxxx;
            int â˜ƒxxxxx = Mth.ceil(â˜ƒxxxx);

            for(int â˜ƒxxxxxx = -â˜ƒxxxxx; â˜ƒxxxxxx <= â˜ƒxxxxx; ++â˜ƒxxxxxx) {
               float â˜ƒxxxxxxx = (float)Mth.abs(â˜ƒxxxxxx) - 0.25F;

               for(int â˜ƒxxxxxxxx = -â˜ƒxxxxx; â˜ƒxxxxxxxx <= â˜ƒxxxxx; ++â˜ƒxxxxxxxx) {
                  float â˜ƒxxxxxxxxx = (float)Mth.abs(â˜ƒxxxxxxxx) - 0.25F;
                  if ((â˜ƒxxxxxx == 0 && â˜ƒxxxxxxxx == 0 || !(â˜ƒxxxxxxx * â˜ƒxxxxxxx + â˜ƒxxxxxxxxx * â˜ƒxxxxxxxxx > â˜ƒxxxx * â˜ƒxxxx))
                     && (â˜ƒxxxxxx != -â˜ƒxxxxx && â˜ƒxxxxxx != â˜ƒxxxxx && â˜ƒxxxxxxxx != -â˜ƒxxxxx && â˜ƒxxxxxxxx != â˜ƒxxxxx || !(â˜ƒx.nextFloat() > 0.75F))
                     )
                   {
                     BlockState â˜ƒxxxxxxxxxx = â˜ƒxx.getBlockState(â˜ƒ.offset(â˜ƒxxxxxx, â˜ƒxxx, â˜ƒxxxxxxxx));
                     if (â˜ƒxxxxxxxxxx.isAir() || isDirt(â˜ƒxxxxxxxxxx) || â˜ƒxxxxxxxxxx.is(Blocks.SNOW_BLOCK) || â˜ƒxxxxxxxxxx.is(Blocks.ICE)) {
                        this.setBlock(â˜ƒxx, â˜ƒ.offset(â˜ƒxxxxxx, â˜ƒxxx, â˜ƒxxxxxxxx), Blocks.PACKED_ICE.defaultBlockState());
                     }

                     if (â˜ƒxxx != 0 && â˜ƒxxxxx > 1) {
                        â˜ƒxxxxxxxxxx = â˜ƒxx.getBlockState(â˜ƒ.offset(â˜ƒxxxxxx, -â˜ƒxxx, â˜ƒxxxxxxxx));
                        if (â˜ƒxxxxxxxxxx.isAir() || isDirt(â˜ƒxxxxxxxxxx) || â˜ƒxxxxxxxxxx.is(Blocks.SNOW_BLOCK) || â˜ƒxxxxxxxxxx.is(Blocks.ICE)) {
                           this.setBlock(â˜ƒxx, â˜ƒ.offset(â˜ƒxxxxxx, -â˜ƒxxx, â˜ƒxxxxxxxx), Blocks.PACKED_ICE.defaultBlockState());
                        }
                     }
                  }
               }
            }
         }

         int â˜ƒxxx = â˜ƒxxxx - 1;
         if (â˜ƒxxx < 0) {
            â˜ƒxxx = 0;
         } else if (â˜ƒxxx > 1) {
            â˜ƒxxx = 1;
         }

         for(int â˜ƒxxx = -â˜ƒxxx; â˜ƒxxx <= â˜ƒxxx; ++â˜ƒxxx) {
            for(int â˜ƒxxxx = -â˜ƒxxx; â˜ƒxxxx <= â˜ƒxxx; ++â˜ƒxxxx) {
               BlockPos â˜ƒxxxxx = â˜ƒ.offset(â˜ƒxxx, -1, â˜ƒxxxx);
               int â˜ƒxxxxxx = 50;
               if (Math.abs(â˜ƒxxx) == 1 && Math.abs(â˜ƒxxxx) == 1) {
                  â˜ƒxxxxxx = â˜ƒx.nextInt(5);
               }

               while(â˜ƒxxxxx.getY() > 50) {
                  BlockState â˜ƒxxxxx = â˜ƒxx.getBlockState(â˜ƒxxxxx);
                  if (!â˜ƒxxxxx.isAir() && !isDirt(â˜ƒxxxxx) && !â˜ƒxxxxx.is(Blocks.SNOW_BLOCK) && !â˜ƒxxxxx.is(Blocks.ICE) && !â˜ƒxxxxx.is(Blocks.PACKED_ICE)) {
                     break;
                  }

                  this.setBlock(â˜ƒxx, â˜ƒxxxxx, Blocks.PACKED_ICE.defaultBlockState());
                  â˜ƒxxxxx = â˜ƒxxxxx.below();
                  if (--â˜ƒxxxxxx <= 0) {
                     â˜ƒxxxxx = â˜ƒxxxxx.below(â˜ƒx.nextInt(5) + 1);
                     â˜ƒxxxxxx = â˜ƒx.nextInt(5);
                  }
               }
            }
         }

         return true;
      }
   }
}
