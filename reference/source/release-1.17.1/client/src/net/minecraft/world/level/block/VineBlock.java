package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VineBlock extends Block {
   public static final BooleanProperty UP = PipeBlock.UP;
   public static final BooleanProperty NORTH = PipeBlock.NORTH;
   public static final BooleanProperty EAST = PipeBlock.EAST;
   public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
   public static final BooleanProperty WEST = PipeBlock.WEST;
   public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = (Map<Direction, BooleanProperty>)PipeBlock.PROPERTY_BY_DIRECTION
      .entrySet()
      .stream()
      .filter(var0 -> var0.getKey() != Direction.DOWN)
      .collect(Util.toMap());
   protected static final float AABB_OFFSET = 1.0F;
   private static final VoxelShape UP_AABB = Block.box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape WEST_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
   private static final VoxelShape EAST_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
   private static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
   private final Map<BlockState, VoxelShape> shapesCache;

   public VineBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(UP, Boolean.valueOf(false))
            .setValue(NORTH, Boolean.valueOf(false))
            .setValue(EAST, Boolean.valueOf(false))
            .setValue(SOUTH, Boolean.valueOf(false))
            .setValue(WEST, Boolean.valueOf(false))
      );
      this.shapesCache = ImmutableMap.copyOf(
         (Map<? extends BlockState, ? extends VoxelShape>)this.stateDefinition
            .getPossibleStates()
            .stream()
            .collect(Collectors.toMap(Function.identity(), VineBlock::calculateShape))
      );
   }

   private static VoxelShape calculateShape(BlockState var0) {
      VoxelShape â˜ƒ = Shapes.empty();
      if (â˜ƒ.getValue(UP)) {
         â˜ƒ = UP_AABB;
      }

      if (â˜ƒ.getValue(NORTH)) {
         â˜ƒ = Shapes.or(â˜ƒ, NORTH_AABB);
      }

      if (â˜ƒ.getValue(SOUTH)) {
         â˜ƒ = Shapes.or(â˜ƒ, SOUTH_AABB);
      }

      if (â˜ƒ.getValue(EAST)) {
         â˜ƒ = Shapes.or(â˜ƒ, EAST_AABB);
      }

      if (â˜ƒ.getValue(WEST)) {
         â˜ƒ = Shapes.or(â˜ƒ, WEST_AABB);
      }

      return â˜ƒ.isEmpty() ? Shapes.block() : â˜ƒ;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return (VoxelShape)this.shapesCache.get(â˜ƒ);
   }

   @Override
   public boolean propagatesSkylightDown(BlockState var1, BlockGetter var2, BlockPos var3) {
      return true;
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      return this.hasFaces(this.getUpdatedState(â˜ƒ, â˜ƒ, â˜ƒ));
   }

   private boolean hasFaces(BlockState var1) {
      return this.countFaces(â˜ƒ) > 0;
   }

   private int countFaces(BlockState var1) {
      int â˜ƒ = 0;

      for(BooleanProperty â˜ƒx : PROPERTY_BY_DIRECTION.values()) {
         if (â˜ƒ.getValue(â˜ƒx)) {
            ++â˜ƒ;
         }
      }

      return â˜ƒ;
   }

   private boolean canSupportAtFace(BlockGetter var1, BlockPos var2, Direction var3) {
      if (â˜ƒ == Direction.DOWN) {
         return false;
      } else {
         BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ);
         if (isAcceptableNeighbour(â˜ƒ, â˜ƒ, â˜ƒ)) {
            return true;
         } else if (â˜ƒ.getAxis() == Direction.Axis.Y) {
            return false;
         } else {
            BooleanProperty â˜ƒ = (BooleanProperty)PROPERTY_BY_DIRECTION.get(â˜ƒ);
            BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ.above());
            return â˜ƒx.is(this) && â˜ƒx.getValue(â˜ƒ);
         }
      }
   }

   public static boolean isAcceptableNeighbour(BlockGetter var0, BlockPos var1, Direction var2) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      return Block.isFaceFull(â˜ƒ.getCollisionShape(â˜ƒ, â˜ƒ), â˜ƒ.getOpposite());
   }

   private BlockState getUpdatedState(BlockState var1, BlockGetter var2, BlockPos var3) {
      BlockPos â˜ƒ = â˜ƒ.above();
      if (â˜ƒ.getValue(UP)) {
         â˜ƒ = â˜ƒ.setValue(UP, Boolean.valueOf(isAcceptableNeighbour(â˜ƒ, â˜ƒ, Direction.DOWN)));
      }

      BlockState â˜ƒ = null;

      for(Direction â˜ƒx : Direction.Plane.HORIZONTAL) {
         BooleanProperty â˜ƒxx = getPropertyForFace(â˜ƒx);
         if (â˜ƒ.getValue(â˜ƒxx)) {
            boolean â˜ƒxxx = this.canSupportAtFace(â˜ƒ, â˜ƒ, â˜ƒx);
            if (!â˜ƒxxx) {
               if (â˜ƒ == null) {
                  â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
               }

               â˜ƒxxx = â˜ƒ.is(this) && â˜ƒ.getValue(â˜ƒxx);
            }

            â˜ƒ = â˜ƒ.setValue(â˜ƒxx, Boolean.valueOf(â˜ƒxxx));
         }
      }

      return â˜ƒ;
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ == Direction.DOWN) {
         return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         BlockState â˜ƒ = this.getUpdatedState(â˜ƒ, â˜ƒ, â˜ƒ);
         return !this.hasFaces(â˜ƒ) ? Blocks.AIR.defaultBlockState() : â˜ƒ;
      }
   }

   @Override
   public void randomTick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (â˜ƒ.nextInt(4) == 0) {
         Direction â˜ƒ = Direction.getRandom(â˜ƒ);
         BlockPos â˜ƒx = â˜ƒ.above();
         if (â˜ƒ.getAxis().isHorizontal() && !â˜ƒ.getValue(getPropertyForFace(â˜ƒ))) {
            if (this.canSpread(â˜ƒ, â˜ƒ)) {
               BlockPos â˜ƒxx = â˜ƒ.relative(â˜ƒ);
               BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒxx);
               if (â˜ƒxxx.isAir()) {
                  Direction â˜ƒxxxx = â˜ƒ.getClockWise();
                  Direction â˜ƒxxxxx = â˜ƒ.getCounterClockWise();
                  boolean â˜ƒxxxxxx = â˜ƒ.getValue(getPropertyForFace(â˜ƒxxxx));
                  boolean â˜ƒxxxxxxx = â˜ƒ.getValue(getPropertyForFace(â˜ƒxxxxx));
                  BlockPos â˜ƒxxxxxxxx = â˜ƒxx.relative(â˜ƒxxxx);
                  BlockPos â˜ƒxxxxxxxxx = â˜ƒxx.relative(â˜ƒxxxxx);
                  if (â˜ƒxxxxxx && isAcceptableNeighbour(â˜ƒ, â˜ƒxxxxxxxx, â˜ƒxxxx)) {
                     â˜ƒ.setBlock(â˜ƒxx, this.defaultBlockState().setValue(getPropertyForFace(â˜ƒxxxx), Boolean.valueOf(true)), 2);
                  } else if (â˜ƒxxxxxxx && isAcceptableNeighbour(â˜ƒ, â˜ƒxxxxxxxxx, â˜ƒxxxxx)) {
                     â˜ƒ.setBlock(â˜ƒxx, this.defaultBlockState().setValue(getPropertyForFace(â˜ƒxxxxx), Boolean.valueOf(true)), 2);
                  } else {
                     Direction â˜ƒxxxx = â˜ƒ.getOpposite();
                     if (â˜ƒxxxxxx && â˜ƒ.isEmptyBlock(â˜ƒxxxxxxxx) && isAcceptableNeighbour(â˜ƒ, â˜ƒ.relative(â˜ƒxxxx), â˜ƒxxxx)) {
                        â˜ƒ.setBlock(â˜ƒxxxxxxxx, this.defaultBlockState().setValue(getPropertyForFace(â˜ƒxxxx), Boolean.valueOf(true)), 2);
                     } else if (â˜ƒxxxxxxx && â˜ƒ.isEmptyBlock(â˜ƒxxxxxxxxx) && isAcceptableNeighbour(â˜ƒ, â˜ƒ.relative(â˜ƒxxxxx), â˜ƒxxxx)) {
                        â˜ƒ.setBlock(â˜ƒxxxxxxxxx, this.defaultBlockState().setValue(getPropertyForFace(â˜ƒxxxx), Boolean.valueOf(true)), 2);
                     } else if ((double)â˜ƒ.nextFloat() < 0.05 && isAcceptableNeighbour(â˜ƒ, â˜ƒxx.above(), Direction.UP)) {
                        â˜ƒ.setBlock(â˜ƒxx, this.defaultBlockState().setValue(UP, Boolean.valueOf(true)), 2);
                     }
                  }
               } else if (isAcceptableNeighbour(â˜ƒ, â˜ƒxx, â˜ƒ)) {
                  â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(getPropertyForFace(â˜ƒ), Boolean.valueOf(true)), 2);
               }
            }
         } else {
            if (â˜ƒ == Direction.UP && â˜ƒ.getY() < â˜ƒ.getMaxBuildHeight() - 1) {
               if (this.canSupportAtFace(â˜ƒ, â˜ƒ, â˜ƒ)) {
                  â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(UP, Boolean.valueOf(true)), 2);
                  return;
               }

               if (â˜ƒ.isEmptyBlock(â˜ƒx)) {
                  if (!this.canSpread(â˜ƒ, â˜ƒ)) {
                     return;
                  }

                  BlockState â˜ƒ = â˜ƒ;

                  for(Direction â˜ƒx : Direction.Plane.HORIZONTAL) {
                     if (â˜ƒ.nextBoolean() || !isAcceptableNeighbour(â˜ƒ, â˜ƒx.relative(â˜ƒx), â˜ƒx)) {
                        â˜ƒ = â˜ƒ.setValue(getPropertyForFace(â˜ƒx), Boolean.valueOf(false));
                     }
                  }

                  if (this.hasHorizontalConnection(â˜ƒ)) {
                     â˜ƒ.setBlock(â˜ƒx, â˜ƒ, 2);
                  }

                  return;
               }
            }

            if (â˜ƒ.getY() > â˜ƒ.getMinBuildHeight()) {
               BlockPos â˜ƒ = â˜ƒ.below();
               BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
               if (â˜ƒx.isAir() || â˜ƒx.is(this)) {
                  BlockState â˜ƒxx = â˜ƒx.isAir() ? this.defaultBlockState() : â˜ƒx;
                  BlockState â˜ƒxxx = this.copyRandomFaces(â˜ƒ, â˜ƒxx, â˜ƒ);
                  if (â˜ƒxx != â˜ƒxxx && this.hasHorizontalConnection(â˜ƒxxx)) {
                     â˜ƒ.setBlock(â˜ƒ, â˜ƒxxx, 2);
                  }
               }
            }
         }
      }
   }

   private BlockState copyRandomFaces(BlockState var1, BlockState var2, Random var3) {
      for(Direction â˜ƒ : Direction.Plane.HORIZONTAL) {
         if (â˜ƒ.nextBoolean()) {
            BooleanProperty â˜ƒx = getPropertyForFace(â˜ƒ);
            if (â˜ƒ.getValue(â˜ƒx)) {
               â˜ƒ = â˜ƒ.setValue(â˜ƒx, Boolean.valueOf(true));
            }
         }
      }

      return â˜ƒ;
   }

   private boolean hasHorizontalConnection(BlockState var1) {
      return â˜ƒ.getValue(NORTH) || â˜ƒ.getValue(EAST) || â˜ƒ.getValue(SOUTH) || â˜ƒ.getValue(WEST);
   }

   private boolean canSpread(BlockGetter var1, BlockPos var2) {
      int â˜ƒ = 4;
      Iterable<BlockPos> â˜ƒx = BlockPos.betweenClosed(â˜ƒ.getX() - 4, â˜ƒ.getY() - 1, â˜ƒ.getZ() - 4, â˜ƒ.getX() + 4, â˜ƒ.getY() + 1, â˜ƒ.getZ() + 4);
      int â˜ƒxx = 5;

      for(BlockPos â˜ƒxxx : â˜ƒx) {
         if (â˜ƒ.getBlockState(â˜ƒxxx).is(this)) {
            if (--â˜ƒxx <= 0) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public boolean canBeReplaced(BlockState var1, BlockPlaceContext var2) {
      BlockState â˜ƒ = â˜ƒ.getLevel().getBlockState(â˜ƒ.getClickedPos());
      if (â˜ƒ.is(this)) {
         return this.countFaces(â˜ƒ) < PROPERTY_BY_DIRECTION.size();
      } else {
         return super.canBeReplaced(â˜ƒ, â˜ƒ);
      }
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockState â˜ƒ = â˜ƒ.getLevel().getBlockState(â˜ƒ.getClickedPos());
      boolean â˜ƒx = â˜ƒ.is(this);
      BlockState â˜ƒxx = â˜ƒx ? â˜ƒ : this.defaultBlockState();

      for(Direction â˜ƒxxx : â˜ƒ.getNearestLookingDirections()) {
         if (â˜ƒxxx != Direction.DOWN) {
            BooleanProperty â˜ƒxxxx = getPropertyForFace(â˜ƒxxx);
            boolean â˜ƒxxxxx = â˜ƒx && â˜ƒ.getValue(â˜ƒxxxx);
            if (!â˜ƒxxxxx && this.canSupportAtFace(â˜ƒ.getLevel(), â˜ƒ.getClickedPos(), â˜ƒxxx)) {
               return â˜ƒxx.setValue(â˜ƒxxxx, Boolean.valueOf(true));
            }
         }
      }

      return â˜ƒx ? â˜ƒxx : null;
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(UP, NORTH, EAST, SOUTH, WEST);
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      switch(â˜ƒ) {
         case CLOCKWISE_180:
            return â˜ƒ.setValue(NORTH, (Boolean)â˜ƒ.getValue(SOUTH))
               .setValue(EAST, (Boolean)â˜ƒ.getValue(WEST))
               .setValue(SOUTH, (Boolean)â˜ƒ.getValue(NORTH))
               .setValue(WEST, (Boolean)â˜ƒ.getValue(EAST));
         case COUNTERCLOCKWISE_90:
            return â˜ƒ.setValue(NORTH, (Boolean)â˜ƒ.getValue(EAST))
               .setValue(EAST, (Boolean)â˜ƒ.getValue(SOUTH))
               .setValue(SOUTH, (Boolean)â˜ƒ.getValue(WEST))
               .setValue(WEST, (Boolean)â˜ƒ.getValue(NORTH));
         case CLOCKWISE_90:
            return â˜ƒ.setValue(NORTH, (Boolean)â˜ƒ.getValue(WEST))
               .setValue(EAST, (Boolean)â˜ƒ.getValue(NORTH))
               .setValue(SOUTH, (Boolean)â˜ƒ.getValue(EAST))
               .setValue(WEST, (Boolean)â˜ƒ.getValue(SOUTH));
         default:
            return â˜ƒ;
      }
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      switch(â˜ƒ) {
         case LEFT_RIGHT:
            return â˜ƒ.setValue(NORTH, (Boolean)â˜ƒ.getValue(SOUTH)).setValue(SOUTH, (Boolean)â˜ƒ.getValue(NORTH));
         case FRONT_BACK:
            return â˜ƒ.setValue(EAST, (Boolean)â˜ƒ.getValue(WEST)).setValue(WEST, (Boolean)â˜ƒ.getValue(EAST));
         default:
            return super.mirror(â˜ƒ, â˜ƒ);
      }
   }

   public static BooleanProperty getPropertyForFace(Direction var0) {
      return (BooleanProperty)PROPERTY_BY_DIRECTION.get(â˜ƒ);
   }
}
