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
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallSignBlock extends SignBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   protected static final float AABB_THICKNESS = 2.0F;
   protected static final float AABB_BOTTOM = 4.5F;
   protected static final float AABB_TOP = 12.5F;
   private static final Map<Direction, VoxelShape> AABBS = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         Block.box(0.0, 4.5, 14.0, 16.0, 12.5, 16.0),
         Direction.SOUTH,
         Block.box(0.0, 4.5, 0.0, 16.0, 12.5, 2.0),
         Direction.EAST,
         Block.box(0.0, 4.5, 0.0, 2.0, 12.5, 16.0),
         Direction.WEST,
         Block.box(14.0, 4.5, 0.0, 16.0, 12.5, 16.0)
      )
   );

   public WallSignBlock(BlockBehaviour.Properties var1, WoodType var2) {
      super(â˜ƒ, â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)));
   }

   @Override
   public String getDescriptionId() {
      return this.asItem().getDescriptionId();
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return (VoxelShape)AABBS.get(â˜ƒ.getValue(FACING));
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return â˜ƒ.getBlockState(â˜ƒ.relative(((Direction)â˜ƒ.getValue(FACING)).getOpposite())).getMaterial().isSolid();
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockState â˜ƒ = this.defaultBlockState();
      FluidState â˜ƒx = â˜ƒ.getLevel().getFluidState(â˜ƒ.getClickedPos());
      LevelReader â˜ƒxx = â˜ƒ.getLevel();
      BlockPos â˜ƒxxx = â˜ƒ.getClickedPos();
      Direction[] â˜ƒxxxx = â˜ƒ.getNearestLookingDirections();

      for(Direction â˜ƒxxxxx : â˜ƒxxxx) {
         if (â˜ƒxxxxx.getAxis().isHorizontal()) {
            Direction â˜ƒxxxxxx = â˜ƒxxxxx.getOpposite();
            â˜ƒ = â˜ƒ.setValue(FACING, â˜ƒxxxxxx);
            if (â˜ƒ.canSurvive(â˜ƒxx, â˜ƒxxx)) {
               return â˜ƒ.setValue(WATERLOGGED, Boolean.valueOf(â˜ƒx.getType() == Fluids.WATER));
            }
         }
      }

      return null;
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return â˜ƒ.getOpposite() == â˜ƒ.getValue(FACING) && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
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
}
