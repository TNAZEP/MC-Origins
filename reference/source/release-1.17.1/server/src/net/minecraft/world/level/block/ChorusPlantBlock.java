package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class ChorusPlantBlock extends PipeBlock {
   protected ChorusPlantBlock(BlockBehaviour.Properties var1) {
      super(0.3125F, â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(NORTH, Boolean.valueOf(false))
            .setValue(EAST, Boolean.valueOf(false))
            .setValue(SOUTH, Boolean.valueOf(false))
            .setValue(WEST, Boolean.valueOf(false))
            .setValue(UP, Boolean.valueOf(false))
            .setValue(DOWN, Boolean.valueOf(false))
      );
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return this.getStateForPlacement(â˜ƒ.getLevel(), â˜ƒ.getClickedPos());
   }

   public BlockState getStateForPlacement(BlockGetter var1, BlockPos var2) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.below());
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ.above());
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒ.north());
      BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒ.east());
      BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒ.south());
      BlockState â˜ƒxxxxx = â˜ƒ.getBlockState(â˜ƒ.west());
      return this.defaultBlockState()
         .setValue(DOWN, Boolean.valueOf(â˜ƒ.is(this) || â˜ƒ.is(Blocks.CHORUS_FLOWER) || â˜ƒ.is(Blocks.END_STONE)))
         .setValue(UP, Boolean.valueOf(â˜ƒx.is(this) || â˜ƒx.is(Blocks.CHORUS_FLOWER)))
         .setValue(NORTH, Boolean.valueOf(â˜ƒxx.is(this) || â˜ƒxx.is(Blocks.CHORUS_FLOWER)))
         .setValue(EAST, Boolean.valueOf(â˜ƒxxx.is(this) || â˜ƒxxx.is(Blocks.CHORUS_FLOWER)))
         .setValue(SOUTH, Boolean.valueOf(â˜ƒxxxx.is(this) || â˜ƒxxxx.is(Blocks.CHORUS_FLOWER)))
         .setValue(WEST, Boolean.valueOf(â˜ƒxxxxx.is(this) || â˜ƒxxxxx.is(Blocks.CHORUS_FLOWER)));
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (!â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 1);
         return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         boolean â˜ƒ = â˜ƒ.is(this) || â˜ƒ.is(Blocks.CHORUS_FLOWER) || â˜ƒ == Direction.DOWN && â˜ƒ.is(Blocks.END_STONE);
         return â˜ƒ.setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ), Boolean.valueOf(â˜ƒ));
      }
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (!â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
         â˜ƒ.destroyBlock(â˜ƒ, true);
      }
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.below());
      boolean â˜ƒx = !â˜ƒ.getBlockState(â˜ƒ.above()).isAir() && !â˜ƒ.isAir();

      for(Direction â˜ƒxx : Direction.Plane.HORIZONTAL) {
         BlockPos â˜ƒxxx = â˜ƒ.relative(â˜ƒxx);
         BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒxxx);
         if (â˜ƒxxxx.is(this)) {
            if (â˜ƒx) {
               return false;
            }

            BlockState â˜ƒxxxxx = â˜ƒ.getBlockState(â˜ƒxxx.below());
            if (â˜ƒxxxxx.is(this) || â˜ƒxxxxx.is(Blocks.END_STONE)) {
               return true;
            }
         }
      }

      return â˜ƒ.is(this) || â˜ƒ.is(Blocks.END_STONE);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }
}
