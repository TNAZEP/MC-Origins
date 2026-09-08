package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallBannerBlock extends AbstractBannerBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         Block.box(0.0, 0.0, 14.0, 16.0, 12.5, 16.0),
         Direction.SOUTH,
         Block.box(0.0, 0.0, 0.0, 16.0, 12.5, 2.0),
         Direction.WEST,
         Block.box(14.0, 0.0, 0.0, 16.0, 12.5, 16.0),
         Direction.EAST,
         Block.box(0.0, 0.0, 0.0, 2.0, 12.5, 16.0)
      )
   );

   public WallBannerBlock(DyeColor var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ, â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
   }

   @Override
   public String getDescriptionId() {
      return this.asItem().getDescriptionId();
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return â˜ƒ.getBlockState(â˜ƒ.relative(((Direction)â˜ƒ.getValue(FACING)).getOpposite())).getMaterial().isSolid();
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      return â˜ƒ == ((Direction)â˜ƒ.getValue(FACING)).getOpposite() && !â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return (VoxelShape)SHAPES.get(â˜ƒ.getValue(FACING));
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockState â˜ƒ = this.defaultBlockState();
      LevelReader â˜ƒx = â˜ƒ.getLevel();
      BlockPos â˜ƒxx = â˜ƒ.getClickedPos();
      Direction[] â˜ƒxxx = â˜ƒ.getNearestLookingDirections();

      for(Direction â˜ƒxxxx : â˜ƒxxx) {
         if (â˜ƒxxxx.getAxis().isHorizontal()) {
            Direction â˜ƒxxxxx = â˜ƒxxxx.getOpposite();
            â˜ƒ = â˜ƒ.setValue(FACING, â˜ƒxxxxx);
            if (â˜ƒ.canSurvive(â˜ƒx, â˜ƒxx)) {
               return â˜ƒ;
            }
         }
      }

      return null;
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
      â˜ƒ.add(FACING);
   }
}
