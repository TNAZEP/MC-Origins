package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CoralTreeFeature extends CoralFeature {
   public CoralTreeFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   protected boolean placeFeature(LevelAccessor var1, Random var2, BlockPos var3, BlockState var4) {
      BlockPos.MutableBlockPos â˜ƒ = â˜ƒ.mutable();
      int â˜ƒx = â˜ƒ.nextInt(3) + 1;

      for(int â˜ƒxx = 0; â˜ƒxx < â˜ƒx; ++â˜ƒxx) {
         if (!this.placeCoralBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)) {
            return true;
         }

         â˜ƒ.move(Direction.UP);
      }

      BlockPos â˜ƒxx = â˜ƒ.immutable();
      int â˜ƒxxx = â˜ƒ.nextInt(3) + 2;
      List<Direction> â˜ƒxxxx = Lists.<Direction>newArrayList(Direction.Plane.HORIZONTAL);
      Collections.shuffle(â˜ƒxxxx, â˜ƒ);

      for(Direction â˜ƒxxxxx : â˜ƒxxxx.subList(0, â˜ƒxxx)) {
         â˜ƒ.set(â˜ƒxx);
         â˜ƒ.move(â˜ƒxxxxx);
         int â˜ƒxxxxxx = â˜ƒ.nextInt(5) + 2;
         int â˜ƒxxxxxxx = 0;

         for(int â˜ƒxxxxxxxx = 0; â˜ƒxxxxxxxx < â˜ƒxxxxxx && this.placeCoralBlock(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ); ++â˜ƒxxxxxxxx) {
            ++â˜ƒxxxxxxx;
            â˜ƒ.move(Direction.UP);
            if (â˜ƒxxxxxxxx == 0 || â˜ƒxxxxxxx >= 2 && â˜ƒ.nextFloat() < 0.25F) {
               â˜ƒ.move(â˜ƒxxxxx);
               â˜ƒxxxxxxx = 0;
            }
         }
      }

      return true;
   }
}
