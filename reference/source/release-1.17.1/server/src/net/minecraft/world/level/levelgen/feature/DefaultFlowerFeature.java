package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

public class DefaultFlowerFeature extends AbstractFlowerFeature<RandomPatchConfiguration> {
   public DefaultFlowerFeature(Codec<RandomPatchConfiguration> var1) {
      super(â˜ƒ);
   }

   public boolean isValid(LevelAccessor var1, BlockPos var2, RandomPatchConfiguration var3) {
      return !â˜ƒ.blacklist.contains(â˜ƒ.getBlockState(â˜ƒ));
   }

   public int getCount(RandomPatchConfiguration var1) {
      return â˜ƒ.tries;
   }

   public BlockPos getPos(Random var1, BlockPos var2, RandomPatchConfiguration var3) {
      return â˜ƒ.offset(
         â˜ƒ.nextInt(â˜ƒ.xspread) - â˜ƒ.nextInt(â˜ƒ.xspread),
         â˜ƒ.nextInt(â˜ƒ.yspread) - â˜ƒ.nextInt(â˜ƒ.yspread),
         â˜ƒ.nextInt(â˜ƒ.zspread) - â˜ƒ.nextInt(â˜ƒ.zspread)
      );
   }

   public BlockState getRandomFlower(Random var1, BlockPos var2, RandomPatchConfiguration var3) {
      return â˜ƒ.stateProvider.getState(â˜ƒ, â˜ƒ);
   }
}
