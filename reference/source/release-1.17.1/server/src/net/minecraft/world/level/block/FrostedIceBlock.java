package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class FrostedIceBlock extends IceBlock {
   public static final int MAX_AGE = 3;
   public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
   private static final int NEIGHBORS_TO_AGE = 4;
   private static final int NEIGHBORS_TO_MELT = 2;

   public FrostedIceBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      this.tick(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if ((â˜ƒ.nextInt(3) == 0 || this.fewerNeigboursThan(â˜ƒ, â˜ƒ, 4))
         && â˜ƒ.getMaxLocalRawBrightness(â˜ƒ) > 11 - â˜ƒ.getValue(AGE) - â˜ƒ.getLightBlock(â˜ƒ, â˜ƒ)
         && this.slightlyMelt(â˜ƒ, â˜ƒ, â˜ƒ)) {
         BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();

         for(Direction â˜ƒx : Direction.values()) {
            â˜ƒ.setWithOffset(â˜ƒ, â˜ƒx);
            BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ);
            if (â˜ƒxx.is(this) && !this.slightlyMelt(â˜ƒxx, â˜ƒ, â˜ƒ)) {
               â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, Mth.nextInt(â˜ƒ, 20, 40));
            }
         }
      } else {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, Mth.nextInt(â˜ƒ, 20, 40));
      }
   }

   private boolean slightlyMelt(BlockState var1, Level var2, BlockPos var3) {
      int â˜ƒ = â˜ƒ.getValue(AGE);
      if (â˜ƒ < 3) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(AGE, Integer.valueOf(â˜ƒ + 1)), 2);
         return false;
      } else {
         this.melt(â˜ƒ, â˜ƒ, â˜ƒ);
         return true;
      }
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      if (â˜ƒ.defaultBlockState().is(this) && this.fewerNeigboursThan(â˜ƒ, â˜ƒ, 2)) {
         this.melt(â˜ƒ, â˜ƒ, â˜ƒ);
      }

      super.neighborChanged(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private boolean fewerNeigboursThan(BlockGetter var1, BlockPos var2, int var3) {
      int â˜ƒ = 0;
      BlockPos.MutableBlockPos â˜ƒx = new BlockPos.MutableBlockPos();

      for(Direction â˜ƒxx : Direction.values()) {
         â˜ƒx.setWithOffset(â˜ƒ, â˜ƒxx);
         if (â˜ƒ.getBlockState(â˜ƒx).is(this)) {
            if (++â˜ƒ >= â˜ƒ) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(AGE);
   }

   @Override
   public ItemStack getCloneItemStack(BlockGetter var1, BlockPos var2, BlockState var3) {
      return ItemStack.EMPTY;
   }
}
