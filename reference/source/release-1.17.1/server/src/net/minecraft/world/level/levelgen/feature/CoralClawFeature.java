package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CoralClawFeature extends CoralFeature {
   public CoralClawFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   protected boolean placeFeature(LevelAccessor var1, Random var2, BlockPos var3, BlockState var4) {
      if (!this.placeCoralBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
         return false;
      } else {
         Direction â˜ƒ = Direction.Plane.HORIZONTAL.getRandomDirection(â˜ƒ);
         int â˜ƒx = â˜ƒ.nextInt(2) + 2;
         List<Direction> â˜ƒxx = Lists.<Direction>newArrayList(â˜ƒ, â˜ƒ.getClockWise(), â˜ƒ.getCounterClockWise());
         Collections.shuffle(â˜ƒxx, â˜ƒ);

         for(Direction â˜ƒxxx : â˜ƒxx.subList(0, â˜ƒx)) {
            BlockPos.MutableBlockPos â˜ƒxxxxxx = â˜ƒ.mutable();
            int â˜ƒxxxxxxx = â˜ƒ.nextInt(2) + 1;
            â˜ƒxxxxxx.move(â˜ƒxxx);
            int â˜ƒxxxx;
            Direction â˜ƒxxxxx;
            if (â˜ƒxxx == â˜ƒ) {
               â˜ƒxxxxx = â˜ƒ;
               â˜ƒxxxx = â˜ƒ.nextInt(3) + 2;
            } else {
               â˜ƒxxxxxx.move(Direction.UP);
               Direction[] â˜ƒxxxx = new Direction[]{â˜ƒxxx, Direction.UP};
               â˜ƒxxxxx = Util.getRandom(â˜ƒxxxx, â˜ƒ);
               â˜ƒxxxx = â˜ƒ.nextInt(3) + 3;
            }

            for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxxxxxxx && this.placeCoralBlock(â˜ƒ, â˜ƒ, â˜ƒxxxxxx, â˜ƒ); ++â˜ƒxxxx) {
               â˜ƒxxxxxx.move(â˜ƒxxxxx);
            }

            â˜ƒxxxxxx.move(â˜ƒxxxxx.getOpposite());
            â˜ƒxxxxxx.move(Direction.UP);

            for(int â˜ƒxxxx = 0; â˜ƒxxxx < â˜ƒxxxx; ++â˜ƒxxxx) {
               â˜ƒxxxxxx.move(â˜ƒ);
               if (!this.placeCoralBlock(â˜ƒ, â˜ƒ, â˜ƒxxxxxx, â˜ƒ)) {
                  break;
               }

               if (â˜ƒ.nextFloat() < 0.25F) {
                  â˜ƒxxxxxx.move(Direction.UP);
               }
            }
         }

         return true;
      }
   }
}
