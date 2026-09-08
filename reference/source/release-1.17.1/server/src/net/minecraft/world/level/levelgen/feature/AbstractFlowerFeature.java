package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public abstract class AbstractFlowerFeature<U extends FeatureConfiguration> extends Feature<U> {
   public AbstractFlowerFeature(Codec<U> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<U> var1) {
      Random â˜ƒ = â˜ƒ.random();
      BlockPos â˜ƒx = â˜ƒ.origin();
      WorldGenLevel â˜ƒxx = â˜ƒ.level();
      U â˜ƒxxx = â˜ƒ.config();
      BlockState â˜ƒxxxx = this.getRandomFlower(â˜ƒ, â˜ƒx, â˜ƒxxx);
      int â˜ƒxxxxx = 0;

      for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx < this.getCount(â˜ƒxxx); ++â˜ƒxxxxxx) {
         BlockPos â˜ƒxxxxxxx = this.getPos(â˜ƒ, â˜ƒx, â˜ƒxxx);
         if (â˜ƒxx.isEmptyBlock(â˜ƒxxxxxxx) && â˜ƒxxxx.canSurvive(â˜ƒxx, â˜ƒxxxxxxx) && this.isValid(â˜ƒxx, â˜ƒxxxxxxx, â˜ƒxxx)) {
            â˜ƒxx.setBlock(â˜ƒxxxxxxx, â˜ƒxxxx, 2);
            ++â˜ƒxxxxx;
         }
      }

      return â˜ƒxxxxx > 0;
   }

   public abstract boolean isValid(LevelAccessor var1, BlockPos var2, U var3);

   public abstract int getCount(U var1);

   public abstract BlockPos getPos(Random var1, BlockPos var2, U var3);

   public abstract BlockState getRandomFlower(Random var1, BlockPos var2, U var3);
}
