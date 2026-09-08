package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MultifaceBlock extends Block {
   private static final float AABB_OFFSET = 1.0F;
   private static final VoxelShape UP_AABB = Block.box(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape DOWN_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
   private static final VoxelShape WEST_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
   private static final VoxelShape EAST_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
   private static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
   private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;
   private static final Map<Direction, VoxelShape> SHAPE_BY_DIRECTION = Util.make(Maps.newEnumMap(Direction.class), var0 -> {
      var0.put(Direction.NORTH, NORTH_AABB);
      var0.put(Direction.EAST, EAST_AABB);
      var0.put(Direction.SOUTH, SOUTH_AABB);
      var0.put(Direction.WEST, WEST_AABB);
      var0.put(Direction.UP, UP_AABB);
      var0.put(Direction.DOWN, DOWN_AABB);
   });
   protected static final Direction[] DIRECTIONS = Direction.values();
   private final ImmutableMap<BlockState, VoxelShape> shapesCache;
   private final boolean canRotate;
   private final boolean canMirrorX;
   private final boolean canMirrorZ;

   public MultifaceBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(getDefaultMultifaceState(this.stateDefinition));
      this.shapesCache = this.getShapeForEachState(MultifaceBlock::calculateMultifaceShape);
      this.canRotate = Direction.Plane.HORIZONTAL.stream().allMatch(this::isFaceSupported);
      this.canMirrorX = Direction.Plane.HORIZONTAL.stream().filter(Direction.Axis.X).filter(this::isFaceSupported).count() % 2L == 0L;
      this.canMirrorZ = Direction.Plane.HORIZONTAL.stream().filter(Direction.Axis.Z).filter(this::isFaceSupported).count() % 2L == 0L;
   }

   protected boolean isFaceSupported(Direction var1) {
      return true;
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      for(Direction â˜ƒ : DIRECTIONS) {
         if (this.isFaceSupported(â˜ƒ)) {
            â˜ƒ.add(getFaceProperty(â˜ƒ));
         }
      }
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (!hasAnyFace(â˜ƒ)) {
         return Blocks.AIR.defaultBlockState();
      } else {
         return hasFace(â˜ƒ, â˜ƒ) && !canAttachTo(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ) ? removeFace(â˜ƒ, getFaceProperty(â˜ƒ)) : â˜ƒ;
      }
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return this.shapesCache.get(â˜ƒ);
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      boolean â˜ƒ = false;

      for(Direction â˜ƒx : DIRECTIONS) {
         if (hasFace(â˜ƒ, â˜ƒx)) {
            BlockPos â˜ƒxx = â˜ƒ.relative(â˜ƒx);
            if (!canAttachTo(â˜ƒ, â˜ƒx, â˜ƒxx, â˜ƒ.getBlockState(â˜ƒxx))) {
               return false;
            }

            â˜ƒ = true;
         }
      }

      return â˜ƒ;
   }

   @Override
   public boolean canBeReplaced(BlockState var1, BlockPlaceContext var2) {
      return hasAnyVacantFace(â˜ƒ);
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      return (BlockState)Arrays.stream(â˜ƒ.getNearestLookingDirections())
         .map(var4x -> this.getStateForPlacement(â˜ƒ, â˜ƒ, â˜ƒ, var4x))
         .filter(Objects::nonNull)
         .findFirst()
         .orElse(null);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      if (!this.isFaceSupported(â˜ƒ)) {
         return null;
      } else {
         BlockState â˜ƒ;
         if (â˜ƒ.is(this)) {
            if (hasFace(â˜ƒ, â˜ƒ)) {
               return null;
            }

            â˜ƒ = â˜ƒ;
         } else if (this.isWaterloggable() && â˜ƒ.getFluidState().isSourceOfType(Fluids.WATER)) {
            â˜ƒ = this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true));
         } else {
            â˜ƒ = this.defaultBlockState();
         }

         BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ);
         return canAttachTo(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getBlockState(â˜ƒ)) ? â˜ƒ.setValue(getFaceProperty(â˜ƒ), Boolean.valueOf(true)) : null;
      }
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      return !this.canRotate ? â˜ƒ : this.mapDirections(â˜ƒ, â˜ƒ::rotate);
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      if (â˜ƒ == Mirror.FRONT_BACK && !this.canMirrorX) {
         return â˜ƒ;
      } else {
         return â˜ƒ == Mirror.LEFT_RIGHT && !this.canMirrorZ ? â˜ƒ : this.mapDirections(â˜ƒ, â˜ƒ::mirror);
      }
   }

   private BlockState mapDirections(BlockState var1, Function<Direction, Direction> var2) {
      BlockState â˜ƒ = â˜ƒ;

      for(Direction â˜ƒx : DIRECTIONS) {
         if (this.isFaceSupported(â˜ƒx)) {
            â˜ƒ = â˜ƒ.setValue(getFaceProperty((Direction)â˜ƒ.apply(â˜ƒx)), (Boolean)â˜ƒ.getValue(getFaceProperty(â˜ƒx)));
         }
      }

      return â˜ƒ;
   }

   public boolean spreadFromRandomFaceTowardRandomDirection(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      List<Direction> â˜ƒ = Lists.<Direction>newArrayList(DIRECTIONS);
      Collections.shuffle(â˜ƒ);
      return â˜ƒ.stream().filter(var1x -> hasFace(â˜ƒ, var1x)).anyMatch(var5x -> this.spreadFromFaceTowardRandomDirection(â˜ƒ, â˜ƒ, â˜ƒ, var5x, â˜ƒ, false));
   }

   public boolean spreadFromFaceTowardRandomDirection(BlockState var1, LevelAccessor var2, BlockPos var3, Direction var4, Random var5, boolean var6) {
      List<Direction> â˜ƒ = Arrays.asList(DIRECTIONS);
      Collections.shuffle(â˜ƒ, â˜ƒ);
      return â˜ƒ.stream().anyMatch(var6x -> this.spreadFromFaceTowardDirection(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, var6x, â˜ƒ));
   }

   public boolean spreadFromFaceTowardDirection(BlockState var1, LevelAccessor var2, BlockPos var3, Direction var4, Direction var5, boolean var6) {
      Optional<Pair<BlockPos, Direction>> â˜ƒ = this.getSpreadFromFaceTowardDirection(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ.isPresent()) {
         Pair<BlockPos, Direction> â˜ƒx = (Pair)â˜ƒ.get();
         return this.spreadToFace(â˜ƒ, â˜ƒx.getFirst(), â˜ƒx.getSecond(), â˜ƒ);
      } else {
         return false;
      }
   }

   protected boolean canSpread(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return Stream.of(DIRECTIONS).anyMatch(var5 -> this.getSpreadFromFaceTowardDirection(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, var5).isPresent());
   }

   private Optional<Pair<BlockPos, Direction>> getSpreadFromFaceTowardDirection(
      BlockState var1, BlockGetter var2, BlockPos var3, Direction var4, Direction var5
   ) {
      if (â˜ƒ.getAxis() == â˜ƒ.getAxis() || !hasFace(â˜ƒ, â˜ƒ) || hasFace(â˜ƒ, â˜ƒ)) {
         return Optional.empty();
      } else if (this.canSpreadToFace(â˜ƒ, â˜ƒ, â˜ƒ)) {
         return Optional.of(Pair.of(â˜ƒ, â˜ƒ));
      } else {
         BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ);
         if (this.canSpreadToFace(â˜ƒ, â˜ƒ, â˜ƒ)) {
            return Optional.of(Pair.of(â˜ƒ, â˜ƒ));
         } else {
            BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ);
            Direction â˜ƒx = â˜ƒ.getOpposite();
            return this.canSpreadToFace(â˜ƒ, â˜ƒ, â˜ƒx) ? Optional.of(Pair.of(â˜ƒ, â˜ƒx)) : Optional.empty();
         }
      }
   }

   private boolean canSpreadToFace(BlockGetter var1, BlockPos var2, Direction var3) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      if (!this.canSpreadInto(â˜ƒ)) {
         return false;
      } else {
         BlockState â˜ƒ = this.getStateForPlacement(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         return â˜ƒ != null;
      }
   }

   private boolean spreadToFace(LevelAccessor var1, BlockPos var2, Direction var3, boolean var4) {
      BlockState â˜ƒ = â˜ƒ.getBlockState(â˜ƒ);
      BlockState â˜ƒx = this.getStateForPlacement(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒx != null) {
         if (â˜ƒ) {
            â˜ƒ.getChunk(â˜ƒ).markPosForPostprocessing(â˜ƒ);
         }

         return â˜ƒ.setBlock(â˜ƒ, â˜ƒx, 2);
      } else {
         return false;
      }
   }

   private boolean canSpreadInto(BlockState var1) {
      return â˜ƒ.isAir() || â˜ƒ.is(this) || â˜ƒ.is(Blocks.WATER) && â˜ƒ.getFluidState().isSource();
   }

   private static boolean hasFace(BlockState var0, Direction var1) {
      BooleanProperty â˜ƒ = getFaceProperty(â˜ƒ);
      return â˜ƒ.hasProperty(â˜ƒ) && â˜ƒ.getValue(â˜ƒ);
   }

   private static boolean canAttachTo(BlockGetter var0, Direction var1, BlockPos var2, BlockState var3) {
      return Block.isFaceFull(â˜ƒ.getCollisionShape(â˜ƒ, â˜ƒ), â˜ƒ.getOpposite());
   }

   private boolean isWaterloggable() {
      return this.stateDefinition.getProperties().contains(BlockStateProperties.WATERLOGGED);
   }

   private static BlockState removeFace(BlockState var0, BooleanProperty var1) {
      BlockState â˜ƒ = â˜ƒ.setValue(â˜ƒ, Boolean.valueOf(false));
      return hasAnyFace(â˜ƒ) ? â˜ƒ : Blocks.AIR.defaultBlockState();
   }

   public static BooleanProperty getFaceProperty(Direction var0) {
      return (BooleanProperty)PROPERTY_BY_DIRECTION.get(â˜ƒ);
   }

   private static BlockState getDefaultMultifaceState(StateDefinition<Block, BlockState> var0) {
      BlockState â˜ƒ = â˜ƒ.any();

      for(BooleanProperty â˜ƒx : PROPERTY_BY_DIRECTION.values()) {
         if (â˜ƒ.hasProperty(â˜ƒx)) {
            â˜ƒ = â˜ƒ.setValue(â˜ƒx, Boolean.valueOf(false));
         }
      }

      return â˜ƒ;
   }

   private static VoxelShape calculateMultifaceShape(BlockState var0) {
      VoxelShape â˜ƒ = Shapes.empty();

      for(Direction â˜ƒx : DIRECTIONS) {
         if (hasFace(â˜ƒ, â˜ƒx)) {
            â˜ƒ = Shapes.or(â˜ƒ, (VoxelShape)SHAPE_BY_DIRECTION.get(â˜ƒx));
         }
      }

      return â˜ƒ.isEmpty() ? Shapes.block() : â˜ƒ;
   }

   protected static boolean hasAnyFace(BlockState var0) {
      return Arrays.stream(DIRECTIONS).anyMatch(var1 -> hasFace(â˜ƒ, var1));
   }

   private static boolean hasAnyVacantFace(BlockState var0) {
      return Arrays.stream(DIRECTIONS).anyMatch(var1 -> !hasFace(â˜ƒ, var1));
   }
}
