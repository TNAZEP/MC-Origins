package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BaseCoralWallFanBlock extends BaseCoralFanBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         Block.box(0.0, 4.0, 5.0, 16.0, 12.0, 16.0),
         Direction.SOUTH,
         Block.box(0.0, 4.0, 0.0, 16.0, 12.0, 11.0),
         Direction.WEST,
         Block.box(5.0, 4.0, 0.0, 16.0, 12.0, 16.0),
         Direction.EAST,
         Block.box(0.0, 4.0, 0.0, 11.0, 12.0, 16.0)
      )
   );

   protected BaseCoralWallFanBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(true)));
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return (VoxelShape)SHAPES.get(â˜ƒ.getValue(FACING));
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
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING, WATERLOGGED);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      return â˜ƒ.getOpposite() == â˜ƒ.getValue(FACING) && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ) ? Blocks.AIR.defaultBlockState() : â˜ƒ;
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      BlockPos â˜ƒx = â˜ƒ.relative(â˜ƒ.getOpposite());
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      return â˜ƒxx.isFaceSturdy(â˜ƒ, â˜ƒx, â˜ƒ);
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockState â˜ƒ = super.getStateForPlacement(â˜ƒ);
      LevelReader â˜ƒx = â˜ƒ.getLevel();
      BlockPos â˜ƒxx = â˜ƒ.getClickedPos();
      Direction[] â˜ƒxxx = â˜ƒ.getNearestLookingDirections();

      for(Direction â˜ƒxxxx : â˜ƒxxx) {
         if (â˜ƒxxxx.getAxis().isHorizontal()) {
            â˜ƒ = â˜ƒ.setValue(FACING, â˜ƒxxxx.getOpposite());
            if (â˜ƒ.canSurvive(â˜ƒx, â˜ƒxx)) {
               return â˜ƒ;
            }
         }
      }

      return null;
   }
}
