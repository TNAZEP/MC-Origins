package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;

public class BuddingAmethystBlock extends AmethystBlock {
   public static final int GROWTH_CHANCE = 5;
   private static final Direction[] DIRECTIONS = Direction.values();

   public BuddingAmethystBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public PushReaction getPistonPushReaction(BlockState var1) {
      return PushReaction.DESTROY;
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.nextInt(5) == 0) {
         Direction â˜ƒ = DIRECTIONS[â˜ƒ.nextInt(DIRECTIONS.length)];
         BlockPos â˜ƒx = â˜ƒ.relative(â˜ƒ);
         BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
         Block â˜ƒxxx = null;
         if (canClusterGrowAtState(â˜ƒxx)) {
            â˜ƒxxx = Blocks.SMALL_AMETHYST_BUD;
         } else if (â˜ƒxx.is(Blocks.SMALL_AMETHYST_BUD) && â˜ƒxx.getValue(AmethystClusterBlock.FACING) == â˜ƒ) {
            â˜ƒxxx = Blocks.MEDIUM_AMETHYST_BUD;
         } else if (â˜ƒxx.is(Blocks.MEDIUM_AMETHYST_BUD) && â˜ƒxx.getValue(AmethystClusterBlock.FACING) == â˜ƒ) {
            â˜ƒxxx = Blocks.LARGE_AMETHYST_BUD;
         } else if (â˜ƒxx.is(Blocks.LARGE_AMETHYST_BUD) && â˜ƒxx.getValue(AmethystClusterBlock.FACING) == â˜ƒ) {
            â˜ƒxxx = Blocks.AMETHYST_CLUSTER;
         }

         if (â˜ƒxxx != null) {
            BlockState â˜ƒ = â˜ƒxxx.defaultBlockState()
               .setValue(AmethystClusterBlock.FACING, â˜ƒ)
               .setValue(AmethystClusterBlock.WATERLOGGED, Boolean.valueOf(â˜ƒxx.getFluidState().getType() == Fluids.WATER));
            â˜ƒ.setBlockAndUpdate(â˜ƒx, â˜ƒ);
         }
      }
   }

   public static boolean canClusterGrowAtState(BlockState var0) {
      return â˜ƒ.isAir() || â˜ƒ.is(Blocks.WATER) && â˜ƒ.getFluidState().getAmount() == 8;
   }
}
