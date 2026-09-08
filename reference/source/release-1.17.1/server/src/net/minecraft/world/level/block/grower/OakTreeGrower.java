package net.minecraft.world.level.block.grower;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.data.worldgen.Features;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class OakTreeGrower extends AbstractTreeGrower {
   @Nullable
   @Override
   protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(Random var1, boolean var2) {
      if (â˜ƒ.nextInt(10) == 0) {
         return â˜ƒ ? Features.FANCY_OAK_BEES_005 : Features.FANCY_OAK;
      } else {
         return â˜ƒ ? Features.OAK_BEES_005 : Features.OAK;
      }
   }
}
