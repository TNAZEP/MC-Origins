package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FenceGateBlock extends HorizontalDirectionalBlock {
   public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   public static final BooleanProperty IN_WALL = BlockStateProperties.IN_WALL;
   protected static final VoxelShape Z_SHAPE = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
   protected static final VoxelShape X_SHAPE = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
   protected static final VoxelShape Z_SHAPE_LOW = Block.box(0.0, 0.0, 6.0, 16.0, 13.0, 10.0);
   protected static final VoxelShape X_SHAPE_LOW = Block.box(6.0, 0.0, 0.0, 10.0, 13.0, 16.0);
   protected static final VoxelShape Z_COLLISION_SHAPE = Block.box(0.0, 0.0, 6.0, 16.0, 24.0, 10.0);
   protected static final VoxelShape X_COLLISION_SHAPE = Block.box(6.0, 0.0, 0.0, 10.0, 24.0, 16.0);
   protected static final VoxelShape Z_OCCLUSION_SHAPE = Shapes.or(Block.box(0.0, 5.0, 7.0, 2.0, 16.0, 9.0), Block.box(14.0, 5.0, 7.0, 16.0, 16.0, 9.0));
   protected static final VoxelShape X_OCCLUSION_SHAPE = Shapes.or(Block.box(7.0, 5.0, 0.0, 9.0, 16.0, 2.0), Block.box(7.0, 5.0, 14.0, 9.0, 16.0, 16.0));
   protected static final VoxelShape Z_OCCLUSION_SHAPE_LOW = Shapes.or(Block.box(0.0, 2.0, 7.0, 2.0, 13.0, 9.0), Block.box(14.0, 2.0, 7.0, 16.0, 13.0, 9.0));
   protected static final VoxelShape X_OCCLUSION_SHAPE_LOW = Shapes.or(Block.box(7.0, 2.0, 0.0, 9.0, 13.0, 2.0), Block.box(7.0, 2.0, 14.0, 9.0, 13.0, 16.0));

   public FenceGateBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition.any().setValue(OPEN, Boolean.valueOf(false)).setValue(POWERED, Boolean.valueOf(false)).setValue(IN_WALL, Boolean.valueOf(false))
      );
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      if (â˜ƒ.getValue(IN_WALL)) {
         return ((Direction)â˜ƒ.getValue(FACING)).getAxis() == Direction.Axis.X ? X_SHAPE_LOW : Z_SHAPE_LOW;
      } else {
         return ((Direction)â˜ƒ.getValue(FACING)).getAxis() == Direction.Axis.X ? X_SHAPE : Z_SHAPE;
      }
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      Direction.Axis â˜ƒ = â˜ƒ.getAxis();
      if (((Direction)â˜ƒ.getValue(FACING)).getClockWise().getAxis() != â˜ƒ) {
         return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         boolean â˜ƒ = this.isWall(â˜ƒ) || this.isWall(â˜ƒ.getBlockState(â˜ƒ.relative(â˜ƒ.getOpposite())));
         return â˜ƒ.setValue(IN_WALL, Boolean.valueOf(â˜ƒ));
      }
   }

   @Override
   public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      if (â˜ƒ.getValue(OPEN)) {
         return Shapes.empty();
      } else {
         return ((Direction)â˜ƒ.getValue(FACING)).getAxis() == Direction.Axis.Z ? Z_COLLISION_SHAPE : X_COLLISION_SHAPE;
      }
   }

   @Override
   public VoxelShape getOcclusionShape(BlockState var1, BlockGetter var2, BlockPos var3) {
      if (â˜ƒ.getValue(IN_WALL)) {
         return ((Direction)â˜ƒ.getValue(FACING)).getAxis() == Direction.Axis.X ? X_OCCLUSION_SHAPE_LOW : Z_OCCLUSION_SHAPE_LOW;
      } else {
         return ((Direction)â˜ƒ.getValue(FACING)).getAxis() == Direction.Axis.X ? X_OCCLUSION_SHAPE : Z_OCCLUSION_SHAPE;
      }
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      switch(â˜ƒ) {
         case LAND:
            return â˜ƒ.getValue(OPEN);
         case WATER:
            return false;
         case AIR:
            return â˜ƒ.getValue(OPEN);
         default:
            return false;
      }
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      boolean â˜ƒxx = â˜ƒ.hasNeighborSignal(â˜ƒx);
      Direction â˜ƒxxx = â˜ƒ.getHorizontalDirection();
      Direction.Axis â˜ƒxxxx = â˜ƒxxx.getAxis();
      boolean â˜ƒxxxxx = â˜ƒxxxx == Direction.Axis.Z && (this.isWall(â˜ƒ.getBlockState(â˜ƒx.west())) || this.isWall(â˜ƒ.getBlockState(â˜ƒx.east())))
         || â˜ƒxxxx == Direction.Axis.X && (this.isWall(â˜ƒ.getBlockState(â˜ƒx.north())) || this.isWall(â˜ƒ.getBlockState(â˜ƒx.south())));
      return this.defaultBlockState()
         .setValue(FACING, â˜ƒxxx)
         .setValue(OPEN, Boolean.valueOf(â˜ƒxx))
         .setValue(POWERED, Boolean.valueOf(â˜ƒxx))
         .setValue(IN_WALL, Boolean.valueOf(â˜ƒxxxxx));
   }

   private boolean isWall(BlockState var1) {
      return â˜ƒ.is(BlockTags.WALLS);
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (â˜ƒ.getValue(OPEN)) {
         â˜ƒ = â˜ƒ.setValue(OPEN, Boolean.valueOf(false));
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 10);
      } else {
         Direction â˜ƒ = â˜ƒ.getDirection();
         if (â˜ƒ.getValue(FACING) == â˜ƒ.getOpposite()) {
            â˜ƒ = â˜ƒ.setValue(FACING, â˜ƒ);
         }

         â˜ƒ = â˜ƒ.setValue(OPEN, Boolean.valueOf(true));
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 10);
      }

      boolean â˜ƒ = â˜ƒ.getValue(OPEN);
      â˜ƒ.levelEvent(â˜ƒ, â˜ƒ ? 1008 : 1014, â˜ƒ, 0);
      â˜ƒ.gameEvent(â˜ƒ, â˜ƒ ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, â˜ƒ);
      return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      if (!â˜ƒ.isClientSide) {
         boolean â˜ƒ = â˜ƒ.hasNeighborSignal(â˜ƒ);
         if (â˜ƒ.getValue(POWERED) != â˜ƒ) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(â˜ƒ)).setValue(OPEN, Boolean.valueOf(â˜ƒ)), 2);
            if (â˜ƒ.getValue(OPEN) != â˜ƒ) {
               â˜ƒ.levelEvent(null, â˜ƒ ? 1008 : 1014, â˜ƒ, 0);
               â˜ƒ.gameEvent(â˜ƒ ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, â˜ƒ);
            }
         }
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING, OPEN, POWERED, IN_WALL);
   }

   public static boolean connectsToDirection(BlockState var0, Direction var1) {
      return ((Direction)â˜ƒ.getValue(FACING)).getAxis() == â˜ƒ.getClockWise().getAxis();
   }
}
