package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class GrowingPlantHeadBlock extends GrowingPlantBlock implements BonemealableBlock {
   public static final IntegerProperty AGE = BlockStateProperties.AGE_25;
   public static final int MAX_AGE = 25;
   private final double growPerTickProbability;

   protected GrowingPlantHeadBlock(BlockBehaviour.Properties var1, Direction var2, VoxelShape var3, boolean var4, double var5) {
      super(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      this.growPerTickProbability = â˜ƒ;
      this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
   }

   @Override
   public BlockState getStateForPlacement(LevelAccessor var1) {
      return this.defaultBlockState().setValue(AGE, Integer.valueOf(â˜ƒ.getRandom().nextInt(25)));
   }

   @Override
   public boolean isRandomlyTicking(BlockState var1) {
      return â˜ƒ.getValue(AGE) < 25;
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(AGE) < 25 && â˜ƒ.nextDouble() < this.growPerTickProbability) {
         BlockPos â˜ƒ = â˜ƒ.relative(this.growthDirection);
         if (this.canGrowInto(â˜ƒ.getBlockState(â˜ƒ))) {
            â˜ƒ.setBlockAndUpdate(â˜ƒ, this.getGrowIntoState(â˜ƒ, â˜ƒ.random));
         }
      }
   }

   protected BlockState getGrowIntoState(BlockState var1, Random var2) {
      return â˜ƒ.cycle(AGE);
   }

   protected BlockState updateBodyAfterConvertedFromHead(BlockState var1, BlockState var2) {
      return â˜ƒ;
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ == this.growthDirection.getOpposite() && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 1);
      }

      if (â˜ƒ != this.growthDirection || !â˜ƒ.is(this) && !â˜ƒ.is(this.getBodyBlock())) {
         if (this.scheduleFluidTicks) {
            â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
         }

         return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         return this.updateBodyAfterConvertedFromHead(â˜ƒ, this.getBodyBlock().defaultBlockState());
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(AGE);
   }

   @Override
   public boolean isValidBonemealTarget(BlockGetter var1, BlockPos var2, BlockState var3, boolean var4) {
      return this.canGrowInto(â˜ƒ.getBlockState(â˜ƒ.relative(this.growthDirection)));
   }

   @Override
   public boolean isBonemealSuccess(Level var1, Random var2, BlockPos var3, BlockState var4) {
      return true;
   }

   @Override
   public void performBonemeal(ServerLevel var1, Random var2, BlockPos var3, BlockState var4) {
      BlockPos â˜ƒ = â˜ƒ.relative(this.growthDirection);
      int â˜ƒx = Math.min(â˜ƒ.getValue(AGE) + 1, 25);
      int â˜ƒxx = this.getBlocksToGrowWhenBonemealed(â˜ƒ);

      for(int â˜ƒxxx = 0; â˜ƒxxx < â˜ƒxx && this.canGrowInto(â˜ƒ.getBlockState(â˜ƒ)); ++â˜ƒxxx) {
         â˜ƒ.setBlockAndUpdate(â˜ƒ, â˜ƒ.setValue(AGE, Integer.valueOf(â˜ƒx)));
         â˜ƒ = â˜ƒ.relative(this.growthDirection);
         â˜ƒx = Math.min(â˜ƒx + 1, 25);
      }
   }

   protected abstract int getBlocksToGrowWhenBonemealed(Random var1);

   protected abstract boolean canGrowInto(BlockState var1);

   @Override
   protected GrowingPlantHeadBlock getHeadBlock() {
      return this;
   }
}
