package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EndIslandFeature extends Feature<NoneFeatureConfiguration> {
   public EndIslandFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> var1) {
      WorldGenLevel â˜ƒ = â˜ƒ.level();
      Random â˜ƒx = â˜ƒ.random();
      BlockPos â˜ƒxx = â˜ƒ.origin();
      float â˜ƒxxx = (float)(â˜ƒx.nextInt(3) + 4);

      for(int â˜ƒxxxx = 0; â˜ƒxxx > 0.5F; --â˜ƒxxxx) {
         for(int â˜ƒxxxxx = Mth.floor(-â˜ƒxxx); â˜ƒxxxxx <= Mth.ceil(â˜ƒxxx); ++â˜ƒxxxxx) {
            for(int â˜ƒxxxxxx = Mth.floor(-â˜ƒxxx); â˜ƒxxxxxx <= Mth.ceil(â˜ƒxxx); ++â˜ƒxxxxxx) {
               if ((float)(â˜ƒxxxxx * â˜ƒxxxxx + â˜ƒxxxxxx * â˜ƒxxxxxx) <= (â˜ƒxxx + 1.0F) * (â˜ƒxxx + 1.0F)) {
                  this.setBlock(â˜ƒ, â˜ƒxx.offset(â˜ƒxxxxx, â˜ƒxxxx, â˜ƒxxxxxx), Blocks.END_STONE.defaultBlockState());
               }
            }
         }

         â˜ƒxxx = (float)((double)â˜ƒxxx - ((double)â˜ƒx.nextInt(2) + 0.5));
      }

      return true;
   }
}
