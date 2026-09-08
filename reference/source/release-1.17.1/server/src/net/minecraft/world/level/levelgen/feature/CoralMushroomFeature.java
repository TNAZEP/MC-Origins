package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CoralMushroomFeature extends CoralFeature {
   public CoralMushroomFeature(Codec<NoneFeatureConfiguration> var1) {
      super(â˜ƒ);
   }

   @Override
   protected boolean placeFeature(LevelAccessor var1, Random var2, BlockPos var3, BlockState var4) {
      int â˜ƒ = â˜ƒ.nextInt(3) + 3;
      int â˜ƒx = â˜ƒ.nextInt(3) + 3;
      int â˜ƒxx = â˜ƒ.nextInt(3) + 3;
      int â˜ƒxxx = â˜ƒ.nextInt(3) + 1;
      BlockPos.MutableBlockPos â˜ƒxxxx = â˜ƒ.mutable();

      for(int â˜ƒxxxxx = 0; â˜ƒxxxxx <= â˜ƒx; ++â˜ƒxxxxx) {
         for(int â˜ƒxxxxxx = 0; â˜ƒxxxxxx <= â˜ƒ; ++â˜ƒxxxxxx) {
            for(int â˜ƒxxxxxxx = 0; â˜ƒxxxxxxx <= â˜ƒxx; ++â˜ƒxxxxxxx) {
               â˜ƒxxxx.set(â˜ƒxxxxx + â˜ƒ.getX(), â˜ƒxxxxxx + â˜ƒ.getY(), â˜ƒxxxxxxx + â˜ƒ.getZ());
               â˜ƒxxxx.move(Direction.DOWN, â˜ƒxxx);
               if ((â˜ƒxxxxx != 0 && â˜ƒxxxxx != â˜ƒx || â˜ƒxxxxxx != 0 && â˜ƒxxxxxx != â˜ƒ)
                  && (â˜ƒxxxxxxx != 0 && â˜ƒxxxxxxx != â˜ƒxx || â˜ƒxxxxxx != 0 && â˜ƒxxxxxx != â˜ƒ)
                  && (â˜ƒxxxxx != 0 && â˜ƒxxxxx != â˜ƒx || â˜ƒxxxxxxx != 0 && â˜ƒxxxxxxx != â˜ƒxx)
                  && (â˜ƒxxxxx == 0 || â˜ƒxxxxx == â˜ƒx || â˜ƒxxxxxx == 0 || â˜ƒxxxxxx == â˜ƒ || â˜ƒxxxxxxx == 0 || â˜ƒxxxxxxx == â˜ƒxx)
                  && !(â˜ƒ.nextFloat() < 0.1F)
                  && !this.placeCoralBlock(â˜ƒ, â˜ƒ, â˜ƒxxxx, â˜ƒ)) {
               }
            }
         }
      }

      return true;
   }
}
