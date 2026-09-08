package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class IronBarsBlock extends CrossCollisionBlock {
   protected IronBarsBlock(BlockBehaviour.Properties var1) {
      super(1.0F, 1.0F, 16.0F, 16.0F, 16.0F, â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(NORTH, Boolean.valueOf(false))
            .setValue(EAST, Boolean.valueOf(false))
            .setValue(SOUTH, Boolean.valueOf(false))
            .setValue(WEST, Boolean.valueOf(false))
            .setValue(WATERLOGGED, Boolean.valueOf(false))
      );
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockGetter â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      FluidState â˜ƒxx = â˜ƒ.getLevel().getFluidState(â˜ƒ.getClickedPos());
      BlockPos â˜ƒxxx = â˜ƒx.north();
      BlockPos â˜ƒxxxx = â˜ƒx.south();
      BlockPos â˜ƒxxxxx = â˜ƒx.west();
      BlockPos â˜ƒxxxxxx = â˜ƒx.east();
      BlockState â˜ƒxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxx);
      BlockState â˜ƒxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx);
      BlockState â˜ƒxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxx);
      BlockState â˜ƒxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxx);
      return this.defaultBlockState()
         .setValue(NORTH, Boolean.valueOf(this.attachsTo(â˜ƒxxxxxxx, â˜ƒxxxxxxx.isFaceSturdy(â˜ƒ, â˜ƒxxx, Direction.SOUTH))))
         .setValue(SOUTH, Boolean.valueOf(this.attachsTo(â˜ƒxxxxxxxx, â˜ƒxxxxxxxx.isFaceSturdy(â˜ƒ, â˜ƒxxxx, Direction.NORTH))))
         .setValue(WEST, Boolean.valueOf(this.attachsTo(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxx.isFaceSturdy(â˜ƒ, â˜ƒxxxxx, Direction.EAST))))
         .setValue(EAST, Boolean.valueOf(this.attachsTo(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxx.isFaceSturdy(â˜ƒ, â˜ƒxxxxxx, Direction.WEST))))
         .setValue(WATERLOGGED, Boolean.valueOf(â˜ƒxx.getType() == Fluids.WATER));
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      return â˜ƒ.getAxis().isHorizontal()
         ? â˜ƒ.setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ), Boolean.valueOf(this.attachsTo(â˜ƒ, â˜ƒ.isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒ.getOpposite()))))
         : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public VoxelShape getVisualShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return Shapes.empty();
   }

   @Override
   public boolean skipRendering(BlockState var1, BlockState var2, Direction var3) {
      if (â˜ƒ.is(this)) {
         if (!â˜ƒ.getAxis().isHorizontal()) {
            return true;
         }

         if (â˜ƒ.getValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ)) && â˜ƒ.getValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ.getOpposite()))) {
            return true;
         }
      }

      return super.skipRendering(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public final boolean attachsTo(BlockState var1, boolean var2) {
      return !isExceptionForConnection(â˜ƒ) && â˜ƒ || â˜ƒ.getBlock() instanceof IronBarsBlock || â˜ƒ.is(BlockTags.WALLS);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
   }
}
