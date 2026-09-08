package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WallBlock extends Block implements SimpleWaterloggedBlock {
   public static final BooleanProperty UP = BlockStateProperties.UP;
   public static final EnumProperty<WallSide> EAST_WALL = BlockStateProperties.EAST_WALL;
   public static final EnumProperty<WallSide> NORTH_WALL = BlockStateProperties.NORTH_WALL;
   public static final EnumProperty<WallSide> SOUTH_WALL = BlockStateProperties.SOUTH_WALL;
   public static final EnumProperty<WallSide> WEST_WALL = BlockStateProperties.WEST_WALL;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   private final Map<BlockState, VoxelShape> shapeByIndex;
   private final Map<BlockState, VoxelShape> collisionShapeByIndex;
   private static final int WALL_WIDTH = 3;
   private static final int WALL_HEIGHT = 14;
   private static final int POST_WIDTH = 4;
   private static final int POST_COVER_WIDTH = 1;
   private static final int WALL_COVER_START = 7;
   private static final int WALL_COVER_END = 9;
   private static final VoxelShape POST_TEST = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
   private static final VoxelShape NORTH_TEST = Block.box(7.0, 0.0, 0.0, 9.0, 16.0, 9.0);
   private static final VoxelShape SOUTH_TEST = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 16.0);
   private static final VoxelShape WEST_TEST = Block.box(0.0, 0.0, 7.0, 9.0, 16.0, 9.0);
   private static final VoxelShape EAST_TEST = Block.box(7.0, 0.0, 7.0, 16.0, 16.0, 9.0);

   public WallBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(UP, Boolean.valueOf(true))
            .setValue(NORTH_WALL, WallSide.NONE)
            .setValue(EAST_WALL, WallSide.NONE)
            .setValue(SOUTH_WALL, WallSide.NONE)
            .setValue(WEST_WALL, WallSide.NONE)
            .setValue(WATERLOGGED, Boolean.valueOf(false))
      );
      this.shapeByIndex = this.makeShapes(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
      this.collisionShapeByIndex = this.makeShapes(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
   }

   private static VoxelShape applyWallShape(VoxelShape var0, WallSide var1, VoxelShape var2, VoxelShape var3) {
      if (â˜ƒ == WallSide.TALL) {
         return Shapes.or(â˜ƒ, â˜ƒ);
      } else {
         return â˜ƒ == WallSide.LOW ? Shapes.or(â˜ƒ, â˜ƒ) : â˜ƒ;
      }
   }

   private Map<BlockState, VoxelShape> makeShapes(float var1, float var2, float var3, float var4, float var5, float var6) {
      float â˜ƒ = 8.0F - â˜ƒ;
      float â˜ƒx = 8.0F + â˜ƒ;
      float â˜ƒxx = 8.0F - â˜ƒ;
      float â˜ƒxxx = 8.0F + â˜ƒ;
      VoxelShape â˜ƒxxxx = Block.box((double)â˜ƒ, 0.0, (double)â˜ƒ, (double)â˜ƒx, (double)â˜ƒ, (double)â˜ƒx);
      VoxelShape â˜ƒxxxxx = Block.box((double)â˜ƒxx, (double)â˜ƒ, 0.0, (double)â˜ƒxxx, (double)â˜ƒ, (double)â˜ƒxxx);
      VoxelShape â˜ƒxxxxxx = Block.box((double)â˜ƒxx, (double)â˜ƒ, (double)â˜ƒxx, (double)â˜ƒxxx, (double)â˜ƒ, 16.0);
      VoxelShape â˜ƒxxxxxxx = Block.box(0.0, (double)â˜ƒ, (double)â˜ƒxx, (double)â˜ƒxxx, (double)â˜ƒ, (double)â˜ƒxxx);
      VoxelShape â˜ƒxxxxxxxx = Block.box((double)â˜ƒxx, (double)â˜ƒ, (double)â˜ƒxx, 16.0, (double)â˜ƒ, (double)â˜ƒxxx);
      VoxelShape â˜ƒxxxxxxxxx = Block.box((double)â˜ƒxx, (double)â˜ƒ, 0.0, (double)â˜ƒxxx, (double)â˜ƒ, (double)â˜ƒxxx);
      VoxelShape â˜ƒxxxxxxxxxx = Block.box((double)â˜ƒxx, (double)â˜ƒ, (double)â˜ƒxx, (double)â˜ƒxxx, (double)â˜ƒ, 16.0);
      VoxelShape â˜ƒxxxxxxxxxxx = Block.box(0.0, (double)â˜ƒ, (double)â˜ƒxx, (double)â˜ƒxxx, (double)â˜ƒ, (double)â˜ƒxxx);
      VoxelShape â˜ƒxxxxxxxxxxxx = Block.box((double)â˜ƒxx, (double)â˜ƒ, (double)â˜ƒxx, 16.0, (double)â˜ƒ, (double)â˜ƒxxx);
      Builder<BlockState, VoxelShape> â˜ƒxxxxxxxxxxxxx = ImmutableMap.builder();

      for(Boolean â˜ƒxxxxxxxxxxxxxx : UP.getPossibleValues()) {
         for(WallSide â˜ƒxxxxxxxxxxxxxxx : EAST_WALL.getPossibleValues()) {
            for(WallSide â˜ƒxxxxxxxxxxxxxxxx : NORTH_WALL.getPossibleValues()) {
               for(WallSide â˜ƒxxxxxxxxxxxxxxxxx : WEST_WALL.getPossibleValues()) {
                  for(WallSide â˜ƒxxxxxxxxxxxxxxxxxx : SOUTH_WALL.getPossibleValues()) {
                     VoxelShape â˜ƒxxxxxxxxxxxxxxxxxxx = Shapes.empty();
                     â˜ƒxxxxxxxxxxxxxxxxxxx = applyWallShape(â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxx, â˜ƒxxxxxxxxxxxx);
                     â˜ƒxxxxxxxxxxxxxxxxxxx = applyWallShape(â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxx);
                     â˜ƒxxxxxxxxxxxxxxxxxxx = applyWallShape(â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx, â˜ƒxxxxx, â˜ƒxxxxxxxxx);
                     â˜ƒxxxxxxxxxxxxxxxxxxx = applyWallShape(â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxxxxx);
                     if (â˜ƒxxxxxxxxxxxxxx) {
                        â˜ƒxxxxxxxxxxxxxxxxxxx = Shapes.or(â˜ƒxxxxxxxxxxxxxxxxxxx, â˜ƒxxxx);
                     }

                     BlockState â˜ƒxxxxxxxxxxxxxxxxxxx = this.defaultBlockState()
                        .setValue(UP, â˜ƒxxxxxxxxxxxxxx)
                        .setValue(EAST_WALL, â˜ƒxxxxxxxxxxxxxxx)
                        .setValue(WEST_WALL, â˜ƒxxxxxxxxxxxxxxxxx)
                        .setValue(NORTH_WALL, â˜ƒxxxxxxxxxxxxxxxx)
                        .setValue(SOUTH_WALL, â˜ƒxxxxxxxxxxxxxxxxxx);
                     â˜ƒxxxxxxxxxxxxx.put(â˜ƒxxxxxxxxxxxxxxxxxxx.setValue(WATERLOGGED, Boolean.valueOf(false)), â˜ƒxxxxxxxxxxxxxxxxxxx);
                     â˜ƒxxxxxxxxxxxxx.put(â˜ƒxxxxxxxxxxxxxxxxxxx.setValue(WATERLOGGED, Boolean.valueOf(true)), â˜ƒxxxxxxxxxxxxxxxxxxx);
                  }
               }
            }
         }
      }

      return â˜ƒxxxxxxxxxxxxx.build();
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return (VoxelShape)this.shapeByIndex.get(â˜ƒ);
   }

   @Override
   public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return (VoxelShape)this.collisionShapeByIndex.get(â˜ƒ);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }

   private boolean connectsTo(BlockState var1, boolean var2, Direction var3) {
      Block â˜ƒ = â˜ƒ.getBlock();
      boolean â˜ƒx = â˜ƒ instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(â˜ƒ, â˜ƒ);
      return â˜ƒ.is(BlockTags.WALLS) || !isExceptionForConnection(â˜ƒ) && â˜ƒ || â˜ƒ instanceof IronBarsBlock || â˜ƒx;
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      LevelReader â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      FluidState â˜ƒxx = â˜ƒ.getLevel().getFluidState(â˜ƒ.getClickedPos());
      BlockPos â˜ƒxxx = â˜ƒx.north();
      BlockPos â˜ƒxxxx = â˜ƒx.east();
      BlockPos â˜ƒxxxxx = â˜ƒx.south();
      BlockPos â˜ƒxxxxxx = â˜ƒx.west();
      BlockPos â˜ƒxxxxxxx = â˜ƒx.above();
      BlockState â˜ƒxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxx);
      BlockState â˜ƒxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxx);
      BlockState â˜ƒxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxx);
      BlockState â˜ƒxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxx);
      BlockState â˜ƒxxxxxxxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxxxx);
      boolean â˜ƒxxxxxxxxxxxxx = this.connectsTo(â˜ƒxxxxxxxx, â˜ƒxxxxxxxx.isFaceSturdy(â˜ƒ, â˜ƒxxx, Direction.SOUTH), Direction.SOUTH);
      boolean â˜ƒxxxxxxxxxxxxxx = this.connectsTo(â˜ƒxxxxxxxxx, â˜ƒxxxxxxxxx.isFaceSturdy(â˜ƒ, â˜ƒxxxx, Direction.WEST), Direction.WEST);
      boolean â˜ƒxxxxxxxxxxxxxxx = this.connectsTo(â˜ƒxxxxxxxxxx, â˜ƒxxxxxxxxxx.isFaceSturdy(â˜ƒ, â˜ƒxxxxx, Direction.NORTH), Direction.NORTH);
      boolean â˜ƒxxxxxxxxxxxxxxxx = this.connectsTo(â˜ƒxxxxxxxxxxx, â˜ƒxxxxxxxxxxx.isFaceSturdy(â˜ƒ, â˜ƒxxxxxx, Direction.EAST), Direction.EAST);
      BlockState â˜ƒxxxxxxxxxxxxxxxxx = this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(â˜ƒxx.getType() == Fluids.WATER));
      return this.updateShape(
         â˜ƒ, â˜ƒxxxxxxxxxxxxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxx, â˜ƒxxxxxxxxxxxxxxxx
      );
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      if (â˜ƒ == Direction.DOWN) {
         return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         return â˜ƒ == Direction.UP ? this.topUpdate(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) : this.sideUpdate(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private static boolean isConnected(BlockState var0, Property<WallSide> var1) {
      return â˜ƒ.getValue(â˜ƒ) != WallSide.NONE;
   }

   private static boolean isCovered(VoxelShape var0, VoxelShape var1) {
      return !Shapes.joinIsNotEmpty(â˜ƒ, â˜ƒ, BooleanOp.ONLY_FIRST);
   }

   private BlockState topUpdate(LevelReader var1, BlockState var2, BlockPos var3, BlockState var4) {
      boolean â˜ƒ = isConnected(â˜ƒ, NORTH_WALL);
      boolean â˜ƒx = isConnected(â˜ƒ, EAST_WALL);
      boolean â˜ƒxx = isConnected(â˜ƒ, SOUTH_WALL);
      boolean â˜ƒxxx = isConnected(â˜ƒ, WEST_WALL);
      return this.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒxxx);
   }

   private BlockState sideUpdate(LevelReader var1, BlockPos var2, BlockState var3, BlockPos var4, BlockState var5, Direction var6) {
      Direction â˜ƒ = â˜ƒ.getOpposite();
      boolean â˜ƒx = â˜ƒ == Direction.NORTH ? this.connectsTo(â˜ƒ, â˜ƒ.isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ) : isConnected(â˜ƒ, NORTH_WALL);
      boolean â˜ƒxx = â˜ƒ == Direction.EAST ? this.connectsTo(â˜ƒ, â˜ƒ.isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ) : isConnected(â˜ƒ, EAST_WALL);
      boolean â˜ƒxxx = â˜ƒ == Direction.SOUTH ? this.connectsTo(â˜ƒ, â˜ƒ.isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ) : isConnected(â˜ƒ, SOUTH_WALL);
      boolean â˜ƒxxxx = â˜ƒ == Direction.WEST ? this.connectsTo(â˜ƒ, â˜ƒ.isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒ), â˜ƒ) : isConnected(â˜ƒ, WEST_WALL);
      BlockPos â˜ƒxxxxx = â˜ƒ.above();
      BlockState â˜ƒxxxxxx = â˜ƒ.getBlockState(â˜ƒxxxxx);
      return this.updateShape(â˜ƒ, â˜ƒ, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒx, â˜ƒxx, â˜ƒxxx, â˜ƒxxxx);
   }

   private BlockState updateShape(LevelReader var1, BlockState var2, BlockPos var3, BlockState var4, boolean var5, boolean var6, boolean var7, boolean var8) {
      VoxelShape â˜ƒ = â˜ƒ.getCollisionShape(â˜ƒ, â˜ƒ).getFaceShape(Direction.DOWN);
      BlockState â˜ƒx = this.updateSides(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      return â˜ƒx.setValue(UP, Boolean.valueOf(this.shouldRaisePost(â˜ƒx, â˜ƒ, â˜ƒ)));
   }

   private boolean shouldRaisePost(BlockState var1, BlockState var2, VoxelShape var3) {
      boolean â˜ƒ = â˜ƒ.getBlock() instanceof WallBlock && â˜ƒ.getValue(UP);
      if (â˜ƒ) {
         return true;
      } else {
         WallSide â˜ƒ = â˜ƒ.getValue(NORTH_WALL);
         WallSide â˜ƒx = â˜ƒ.getValue(SOUTH_WALL);
         WallSide â˜ƒxx = â˜ƒ.getValue(EAST_WALL);
         WallSide â˜ƒxxx = â˜ƒ.getValue(WEST_WALL);
         boolean â˜ƒxxxx = â˜ƒx == WallSide.NONE;
         boolean â˜ƒxxxxx = â˜ƒxxx == WallSide.NONE;
         boolean â˜ƒxxxxxx = â˜ƒxx == WallSide.NONE;
         boolean â˜ƒxxxxxxx = â˜ƒ == WallSide.NONE;
         boolean â˜ƒxxxxxxxx = â˜ƒxxxxxxx && â˜ƒxxxx && â˜ƒxxxxx && â˜ƒxxxxxx || â˜ƒxxxxxxx != â˜ƒxxxx || â˜ƒxxxxx != â˜ƒxxxxxx;
         if (â˜ƒxxxxxxxx) {
            return true;
         } else {
            boolean â˜ƒ = â˜ƒ == WallSide.TALL && â˜ƒx == WallSide.TALL || â˜ƒxx == WallSide.TALL && â˜ƒxxx == WallSide.TALL;
            if (â˜ƒ) {
               return false;
            } else {
               return â˜ƒ.is(BlockTags.WALL_POST_OVERRIDE) || isCovered(â˜ƒ, POST_TEST);
            }
         }
      }
   }

   private BlockState updateSides(BlockState var1, boolean var2, boolean var3, boolean var4, boolean var5, VoxelShape var6) {
      return â˜ƒ.setValue(NORTH_WALL, this.makeWallState(â˜ƒ, â˜ƒ, NORTH_TEST))
         .setValue(EAST_WALL, this.makeWallState(â˜ƒ, â˜ƒ, EAST_TEST))
         .setValue(SOUTH_WALL, this.makeWallState(â˜ƒ, â˜ƒ, SOUTH_TEST))
         .setValue(WEST_WALL, this.makeWallState(â˜ƒ, â˜ƒ, WEST_TEST));
   }

   private WallSide makeWallState(boolean var1, VoxelShape var2, VoxelShape var3) {
      if (â˜ƒ) {
         return isCovered(â˜ƒ, â˜ƒ) ? WallSide.TALL : WallSide.LOW;
      } else {
         return WallSide.NONE;
      }
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   @Override
   public boolean propagatesSkylightDown(BlockState var1, BlockGetter var2, BlockPos var3) {
      return !â˜ƒ.getValue(WATERLOGGED);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(UP, NORTH_WALL, EAST_WALL, WEST_WALL, SOUTH_WALL, WATERLOGGED);
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      switch(â˜ƒ) {
         case CLOCKWISE_180:
            return â˜ƒ.setValue(NORTH_WALL, (WallSide)â˜ƒ.getValue(SOUTH_WALL))
               .setValue(EAST_WALL, (WallSide)â˜ƒ.getValue(WEST_WALL))
               .setValue(SOUTH_WALL, (WallSide)â˜ƒ.getValue(NORTH_WALL))
               .setValue(WEST_WALL, (WallSide)â˜ƒ.getValue(EAST_WALL));
         case COUNTERCLOCKWISE_90:
            return â˜ƒ.setValue(NORTH_WALL, (WallSide)â˜ƒ.getValue(EAST_WALL))
               .setValue(EAST_WALL, (WallSide)â˜ƒ.getValue(SOUTH_WALL))
               .setValue(SOUTH_WALL, (WallSide)â˜ƒ.getValue(WEST_WALL))
               .setValue(WEST_WALL, (WallSide)â˜ƒ.getValue(NORTH_WALL));
         case CLOCKWISE_90:
            return â˜ƒ.setValue(NORTH_WALL, (WallSide)â˜ƒ.getValue(WEST_WALL))
               .setValue(EAST_WALL, (WallSide)â˜ƒ.getValue(NORTH_WALL))
               .setValue(SOUTH_WALL, (WallSide)â˜ƒ.getValue(EAST_WALL))
               .setValue(WEST_WALL, (WallSide)â˜ƒ.getValue(SOUTH_WALL));
         default:
            return â˜ƒ;
      }
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      switch(â˜ƒ) {
         case LEFT_RIGHT:
            return â˜ƒ.setValue(NORTH_WALL, (WallSide)â˜ƒ.getValue(SOUTH_WALL)).setValue(SOUTH_WALL, (WallSide)â˜ƒ.getValue(NORTH_WALL));
         case FRONT_BACK:
            return â˜ƒ.setValue(EAST_WALL, (WallSide)â˜ƒ.getValue(WEST_WALL)).setValue(WEST_WALL, (WallSide)â˜ƒ.getValue(EAST_WALL));
         default:
            return super.mirror(â˜ƒ, â˜ƒ);
      }
   }
}
