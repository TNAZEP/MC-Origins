package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ObserverBlock extends DirectionalBlock {
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

   public ObserverBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.SOUTH).setValue(POWERED, Boolean.valueOf(false)));
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING, POWERED);
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      return â˜ƒ.setValue(FACING, â˜ƒ.rotate(â˜ƒ.getValue(FACING)));
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      return â˜ƒ.rotate(â˜ƒ.getRotation(â˜ƒ.getValue(FACING)));
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.getValue(POWERED)) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(false)), 2);
      } else {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(true)), 2);
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 2);
      }

      this.updateNeighborsInFront(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(FACING) == â˜ƒ && !â˜ƒ.getValue(POWERED)) {
         this.startSignal(â˜ƒ, â˜ƒ);
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void startSignal(LevelAccessor var1, BlockPos var2) {
      if (!â˜ƒ.isClientSide() && !â˜ƒ.getBlockTicks().hasScheduledTick(â˜ƒ, this)) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 2);
      }
   }

   protected void updateNeighborsInFront(Level var1, BlockPos var2, BlockState var3) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      BlockPos â˜ƒx = â˜ƒ.relative(â˜ƒ.getOpposite());
      â˜ƒ.neighborChanged(â˜ƒx, this, â˜ƒ);
      â˜ƒ.updateNeighborsAtExceptFromFacing(â˜ƒx, this, â˜ƒ);
   }

   @Override
   public boolean isSignalSource(BlockState var1) {
      return true;
   }

   @Override
   public int getDirectSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ.getSignal(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public int getSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ.getValue(POWERED) && â˜ƒ.getValue(FACING) == â˜ƒ ? 15 : 0;
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         if (!â˜ƒ.isClientSide() && â˜ƒ.getValue(POWERED) && !â˜ƒ.getBlockTicks().hasScheduledTick(â˜ƒ, this)) {
            BlockState â˜ƒ = â˜ƒ.setValue(POWERED, Boolean.valueOf(false));
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 18);
            this.updateNeighborsInFront(â˜ƒ, â˜ƒ, â˜ƒ);
         }
      }
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         if (!â˜ƒ.isClientSide && â˜ƒ.getValue(POWERED) && â˜ƒ.getBlockTicks().hasScheduledTick(â˜ƒ, this)) {
            this.updateNeighborsInFront(â˜ƒ, â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(false)));
         }
      }
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return this.defaultBlockState().setValue(FACING, â˜ƒ.getNearestLookingDirection().getOpposite().getOpposite());
   }
}
