package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LayerLightEngine;

public abstract class SpreadingSnowyDirtBlock extends SnowyDirtBlock {
   protected SpreadingSnowyDirtBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   private static boolean canBeGrass(BlockState var0, LevelReader var1, BlockPos var2) {
      BlockPos â˜ƒ = â˜ƒ.above();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      if (â˜ƒx.is(Blocks.SNOW) && â˜ƒx.getValue(SnowLayerBlock.LAYERS) == 1) {
         return true;
      } else if (â˜ƒx.getFluidState().getAmount() == 8) {
         return false;
      } else {
         int â˜ƒ = LayerLightEngine.getLightBlockInto(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ, Direction.UP, â˜ƒx.getLightBlock(â˜ƒ, â˜ƒ));
         return â˜ƒ < â˜ƒ.getMaxLightLevel();
      }
   }

   private static boolean canPropagate(BlockState var0, LevelReader var1, BlockPos var2) {
      BlockPos â˜ƒ = â˜ƒ.above();
      return canBeGrass(â˜ƒ, â˜ƒ, â˜ƒ) && !â˜ƒ.getFluidState(â˜ƒ).is(FluidTags.WATER);
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (!canBeGrass(â˜ƒ, â˜ƒ, â˜ƒ)) {
         â˜ƒ.setBlockAndUpdate(â˜ƒ, Blocks.DIRT.defaultBlockState());
      } else {
         if (â˜ƒ.getMaxLocalRawBrightness(â˜ƒ.above()) >= 9) {
            BlockState â˜ƒ = this.defaultBlockState();

            for(int â˜ƒx = 0; â˜ƒx < 4; ++â˜ƒx) {
               BlockPos â˜ƒxx = â˜ƒ.offset(â˜ƒ.nextInt(3) - 1, â˜ƒ.nextInt(5) - 3, â˜ƒ.nextInt(3) - 1);
               if (â˜ƒ.getBlockState(â˜ƒxx).is(Blocks.DIRT) && canPropagate(â˜ƒ, â˜ƒ, â˜ƒxx)) {
                  â˜ƒ.setBlockAndUpdate(â˜ƒxx, â˜ƒ.setValue(SNOWY, Boolean.valueOf(â˜ƒ.getBlockState(â˜ƒxx.above()).is(Blocks.SNOW))));
               }
            }
         }
      }
   }
}
