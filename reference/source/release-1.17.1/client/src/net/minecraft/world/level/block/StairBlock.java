package net.minecraft.world.level.block;

import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StairBlock extends Block implements SimpleWaterloggedBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
   public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   protected static final VoxelShape TOP_AABB = SlabBlock.TOP_AABB;
   protected static final VoxelShape BOTTOM_AABB = SlabBlock.BOTTOM_AABB;
   protected static final VoxelShape OCTET_NNN = Block.box(0.0, 0.0, 0.0, 8.0, 8.0, 8.0);
   protected static final VoxelShape OCTET_NNP = Block.box(0.0, 0.0, 8.0, 8.0, 8.0, 16.0);
   protected static final VoxelShape OCTET_NPN = Block.box(0.0, 8.0, 0.0, 8.0, 16.0, 8.0);
   protected static final VoxelShape OCTET_NPP = Block.box(0.0, 8.0, 8.0, 8.0, 16.0, 16.0);
   protected static final VoxelShape OCTET_PNN = Block.box(8.0, 0.0, 0.0, 16.0, 8.0, 8.0);
   protected static final VoxelShape OCTET_PNP = Block.box(8.0, 0.0, 8.0, 16.0, 8.0, 16.0);
   protected static final VoxelShape OCTET_PPN = Block.box(8.0, 8.0, 0.0, 16.0, 16.0, 8.0);
   protected static final VoxelShape OCTET_PPP = Block.box(8.0, 8.0, 8.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape[] TOP_SHAPES = makeShapes(TOP_AABB, OCTET_NNN, OCTET_PNN, OCTET_NNP, OCTET_PNP);
   protected static final VoxelShape[] BOTTOM_SHAPES = makeShapes(BOTTOM_AABB, OCTET_NPN, OCTET_PPN, OCTET_NPP, OCTET_PPP);
   private static final int[] SHAPE_BY_STATE = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
   private final Block base;
   private final BlockState baseState;

   private static VoxelShape[] makeShapes(VoxelShape var0, VoxelShape var1, VoxelShape var2, VoxelShape var3, VoxelShape var4) {
      return (VoxelShape[])IntStream.range(0, 16).mapToObj(var5 -> makeStairShape(var5, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ)).toArray(var0x -> new VoxelShape[var0x]);
   }

   private static VoxelShape makeStairShape(int var0, VoxelShape var1, VoxelShape var2, VoxelShape var3, VoxelShape var4, VoxelShape var5) {
      VoxelShape â˜ƒ = â˜ƒ;
      if ((â˜ƒ & 1) != 0) {
         â˜ƒ = Shapes.or(â˜ƒ, â˜ƒ);
      }

      if ((â˜ƒ & 2) != 0) {
         â˜ƒ = Shapes.or(â˜ƒ, â˜ƒ);
      }

      if ((â˜ƒ & 4) != 0) {
         â˜ƒ = Shapes.or(â˜ƒ, â˜ƒ);
      }

      if ((â˜ƒ & 8) != 0) {
         â˜ƒ = Shapes.or(â˜ƒ, â˜ƒ);
      }

      return â˜ƒ;
   }

   protected StairBlock(BlockState var1, BlockBehaviour.Properties var2) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(FACING, Direction.NORTH)
            .setValue(HALF, Half.BOTTOM)
            .setValue(SHAPE, StairsShape.STRAIGHT)
            .setValue(WATERLOGGED, Boolean.valueOf(false))
      );
      this.base = â˜ƒ.getBlock();
      this.baseState = â˜ƒ;
   }

   @Override
   public boolean useShapeForLightOcclusion(BlockState var1) {
      return true;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return (â˜ƒ.getValue(HALF) == Half.TOP ? TOP_SHAPES : BOTTOM_SHAPES)[SHAPE_BY_STATE[this.getShapeIndex(â˜ƒ)]];
   }

   private int getShapeIndex(BlockState var1) {
      return ((StairsShape)â˜ƒ.getValue(SHAPE)).ordinal() * 4 + ((Direction)â˜ƒ.getValue(FACING)).get2DDataValue();
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      this.base.animateTick(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void attack(BlockState var1, Level var2, BlockPos var3, Player var4) {
      this.baseState.attack(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void destroy(LevelAccessor var1, BlockPos var2, BlockState var3) {
      this.base.destroy(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public float getExplosionResistance() {
      return this.base.getExplosionResistance();
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         this.baseState.neighborChanged(â˜ƒ, â˜ƒ, Blocks.AIR, â˜ƒ, false);
         this.base.onPlace(this.baseState, â˜ƒ, â˜ƒ, â˜ƒ, false);
      }
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         this.baseState.onRemove(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void stepOn(Level var1, BlockPos var2, BlockState var3, Entity var4) {
      this.base.stepOn(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean isRandomlyTicking(BlockState var1) {
      return this.base.isRandomlyTicking(â˜ƒ);
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      this.base.randomTick(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      this.base.tick(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      return this.baseState.use(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public void wasExploded(Level var1, BlockPos var2, Explosion var3) {
      this.base.wasExploded(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      Direction â˜ƒ = â˜ƒ.getClickedFace();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      FluidState â˜ƒxx = â˜ƒ.getLevel().getFluidState(â˜ƒx);
      BlockState â˜ƒxxx = this.defaultBlockState()
         .setValue(FACING, â˜ƒ.getHorizontalDirection())
         .setValue(HALF, â˜ƒ != Direction.DOWN && (â˜ƒ == Direction.UP || !(â˜ƒ.getClickLocation().y - (double)â˜ƒx.getY() > 0.5)) ? Half.BOTTOM : Half.TOP)
         .setValue(WATERLOGGED, Boolean.valueOf(â˜ƒxx.getType() == Fluids.WATER));
      return â˜ƒxxx.setValue(SHAPE, getStairsShape(â˜ƒxxx, â˜ƒ.getLevel(), â˜ƒx));
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      return â˜ƒ.getAxis().isHorizontal() ? â˜ƒ.setValue(SHAPE, getStairsShape(â˜ƒ, â˜ƒ, â˜ƒ)) : super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static StairsShape getStairsShape(BlockState var0, BlockGetter var1, BlockPos var2) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ.relative(â˜ƒ));
      if (isStairs(â˜ƒx) && â˜ƒ.getValue(HALF) == â˜ƒx.getValue(HALF)) {
         Direction â˜ƒxx = â˜ƒx.getValue(FACING);
         if (â˜ƒxx.getAxis() != ((Direction)â˜ƒ.getValue(FACING)).getAxis() && canTakeShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒxx.getOpposite())) {
            if (â˜ƒxx == â˜ƒ.getCounterClockWise()) {
               return StairsShape.OUTER_LEFT;
            }

            return StairsShape.OUTER_RIGHT;
         }
      }

      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.relative(â˜ƒ.getOpposite()));
      if (isStairs(â˜ƒ) && â˜ƒ.getValue(HALF) == â˜ƒ.getValue(HALF)) {
         Direction â˜ƒx = â˜ƒ.getValue(FACING);
         if (â˜ƒx.getAxis() != ((Direction)â˜ƒ.getValue(FACING)).getAxis() && canTakeShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒx)) {
            if (â˜ƒx == â˜ƒ.getCounterClockWise()) {
               return StairsShape.INNER_LEFT;
            }

            return StairsShape.INNER_RIGHT;
         }
      }

      return StairsShape.STRAIGHT;
   }

   private static boolean canTakeShape(BlockState var0, BlockGetter var1, BlockPos var2, Direction var3) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ.relative(â˜ƒ));
      return !isStairs(â˜ƒ) || â˜ƒ.getValue(FACING) != â˜ƒ.getValue(FACING) || â˜ƒ.getValue(HALF) != â˜ƒ.getValue(HALF);
   }

   public static boolean isStairs(BlockState var0) {
      return â˜ƒ.getBlock() instanceof StairBlock;
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      return â˜ƒ.setValue(FACING, â˜ƒ.rotate(â˜ƒ.getValue(FACING)));
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      StairsShape â˜ƒx = â˜ƒ.getValue(SHAPE);
      switch(â˜ƒ) {
         case LEFT_RIGHT:
            if (â˜ƒ.getAxis() == Direction.Axis.Z) {
               switch(â˜ƒx) {
                  case INNER_LEFT:
                     return â˜ƒ.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_RIGHT);
                  case INNER_RIGHT:
                     return â˜ƒ.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_LEFT);
                  case OUTER_LEFT:
                     return â˜ƒ.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_RIGHT);
                  case OUTER_RIGHT:
                     return â˜ƒ.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_LEFT);
                  default:
                     return â˜ƒ.rotate(Rotation.CLOCKWISE_180);
               }
            }
            break;
         case FRONT_BACK:
            if (â˜ƒ.getAxis() == Direction.Axis.X) {
               switch(â˜ƒx) {
                  case INNER_LEFT:
                     return â˜ƒ.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_LEFT);
                  case INNER_RIGHT:
                     return â˜ƒ.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.INNER_RIGHT);
                  case OUTER_LEFT:
                     return â˜ƒ.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_RIGHT);
                  case OUTER_RIGHT:
                     return â˜ƒ.rotate(Rotation.CLOCKWISE_180).setValue(SHAPE, StairsShape.OUTER_LEFT);
                  case STRAIGHT:
                     return â˜ƒ.rotate(Rotation.CLOCKWISE_180);
               }
            }
      }

      return super.mirror(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING, HALF, SHAPE, WATERLOGGED);
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }
}
