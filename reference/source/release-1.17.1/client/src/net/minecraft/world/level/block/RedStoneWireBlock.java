package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.math.Vector3f;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RedStoneWireBlock extends Block {
   public static final EnumProperty<RedstoneSide> NORTH = BlockStateProperties.NORTH_REDSTONE;
   public static final EnumProperty<RedstoneSide> EAST = BlockStateProperties.EAST_REDSTONE;
   public static final EnumProperty<RedstoneSide> SOUTH = BlockStateProperties.SOUTH_REDSTONE;
   public static final EnumProperty<RedstoneSide> WEST = BlockStateProperties.WEST_REDSTONE;
   public static final IntegerProperty POWER = BlockStateProperties.POWER;
   public static final Map<Direction, EnumProperty<RedstoneSide>> PROPERTY_BY_DIRECTION = Maps.newEnumMap(
      ImmutableMap.of(Direction.NORTH, NORTH, Direction.EAST, EAST, Direction.SOUTH, SOUTH, Direction.WEST, WEST)
   );
   protected static final int H = 1;
   protected static final int W = 3;
   protected static final int E = 13;
   protected static final int N = 3;
   protected static final int S = 13;
   private static final VoxelShape SHAPE_DOT = Block.box(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);
   private static final Map<Direction, VoxelShape> SHAPES_FLOOR = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         Block.box(3.0, 0.0, 0.0, 13.0, 1.0, 13.0),
         Direction.SOUTH,
         Block.box(3.0, 0.0, 3.0, 13.0, 1.0, 16.0),
         Direction.EAST,
         Block.box(3.0, 0.0, 3.0, 16.0, 1.0, 13.0),
         Direction.WEST,
         Block.box(0.0, 0.0, 3.0, 13.0, 1.0, 13.0)
      )
   );
   private static final Map<Direction, VoxelShape> SHAPES_UP = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         Shapes.or((VoxelShape)SHAPES_FLOOR.get(Direction.NORTH), Block.box(3.0, 0.0, 0.0, 13.0, 16.0, 1.0)),
         Direction.SOUTH,
         Shapes.or((VoxelShape)SHAPES_FLOOR.get(Direction.SOUTH), Block.box(3.0, 0.0, 15.0, 13.0, 16.0, 16.0)),
         Direction.EAST,
         Shapes.or((VoxelShape)SHAPES_FLOOR.get(Direction.EAST), Block.box(15.0, 0.0, 3.0, 16.0, 16.0, 13.0)),
         Direction.WEST,
         Shapes.or((VoxelShape)SHAPES_FLOOR.get(Direction.WEST), Block.box(0.0, 0.0, 3.0, 1.0, 16.0, 13.0))
      )
   );
   private static final Map<BlockState, VoxelShape> SHAPES_CACHE = Maps.<BlockState, VoxelShape>newHashMap();
   private static final Vec3[] COLORS = Util.make(new Vec3[16], var0 -> {
      for(int â˜ƒ = 0; â˜ƒ <= 15; ++â˜ƒ) {
         float â˜ƒx = (float)â˜ƒ / 15.0F;
         float â˜ƒxx = â˜ƒx * 0.6F + (â˜ƒx > 0.0F ? 0.4F : 0.3F);
         float â˜ƒxxx = Mth.clamp(â˜ƒx * â˜ƒx * 0.7F - 0.5F, 0.0F, 1.0F);
         float â˜ƒxxxx = Mth.clamp(â˜ƒx * â˜ƒx * 0.6F - 0.7F, 0.0F, 1.0F);
         var0[â˜ƒ] = new Vec3((double)â˜ƒxx, (double)â˜ƒxxx, (double)â˜ƒxxxx);
      }
   });
   private static final float PARTICLE_DENSITY = 0.2F;
   private final BlockState crossState;
   private boolean shouldSignal = true;

   public RedStoneWireBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(NORTH, RedstoneSide.NONE)
            .setValue(EAST, RedstoneSide.NONE)
            .setValue(SOUTH, RedstoneSide.NONE)
            .setValue(WEST, RedstoneSide.NONE)
            .setValue(POWER, Integer.valueOf(0))
      );
      this.crossState = this.defaultBlockState()
         .setValue(NORTH, RedstoneSide.SIDE)
         .setValue(EAST, RedstoneSide.SIDE)
         .setValue(SOUTH, RedstoneSide.SIDE)
         .setValue(WEST, RedstoneSide.SIDE);

      for(BlockState â˜ƒ : this.getStateDefinition().getPossibleStates()) {
         if (â˜ƒ.getValue(POWER) == 0) {
            SHAPES_CACHE.put(â˜ƒ, this.calculateShape(â˜ƒ));
         }
      }
   }

   private VoxelShape calculateShape(BlockState var1) {
      VoxelShape â˜ƒ = SHAPE_DOT;

      for(Direction â˜ƒx : Direction.Plane.HORIZONTAL) {
         RedstoneSide â˜ƒxx = â˜ƒ.getValue((Property<RedstoneSide>)PROPERTY_BY_DIRECTION.get(â˜ƒx));
         if (â˜ƒxx == RedstoneSide.SIDE) {
            â˜ƒ = Shapes.or(â˜ƒ, (VoxelShape)SHAPES_FLOOR.get(â˜ƒx));
         } else if (â˜ƒxx == RedstoneSide.UP) {
            â˜ƒ = Shapes.or(â˜ƒ, (VoxelShape)SHAPES_UP.get(â˜ƒx));
         }
      }

      return â˜ƒ;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return (VoxelShape)SHAPES_CACHE.get(â˜ƒ.setValue(POWER, Integer.valueOf(0)));
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return this.getConnectionState(â˜ƒ.getLevel(), this.crossState, â˜ƒ.getClickedPos());
   }

   private BlockState getConnectionState(BlockGetter var1, BlockState var2, BlockPos var3) {
      boolean â˜ƒ = isDot(â˜ƒ);
      â˜ƒ = this.getMissingConnections(â˜ƒ, this.defaultBlockState().setValue(POWER, (Integer)â˜ƒ.getValue(POWER)), â˜ƒ);
      if (â˜ƒ && isDot(â˜ƒ)) {
         return â˜ƒ;
      } else {
         boolean â˜ƒ = ((RedstoneSide)â˜ƒ.getValue(NORTH)).isConnected();
         boolean â˜ƒx = ((RedstoneSide)â˜ƒ.getValue(SOUTH)).isConnected();
         boolean â˜ƒxx = ((RedstoneSide)â˜ƒ.getValue(EAST)).isConnected();
         boolean â˜ƒxxx = ((RedstoneSide)â˜ƒ.getValue(WEST)).isConnected();
         boolean â˜ƒxxxx = !â˜ƒ && !â˜ƒx;
         boolean â˜ƒxxxxx = !â˜ƒxx && !â˜ƒxxx;
         if (!â˜ƒxxx && â˜ƒxxxx) {
            â˜ƒ = â˜ƒ.setValue(WEST, RedstoneSide.SIDE);
         }

         if (!â˜ƒxx && â˜ƒxxxx) {
            â˜ƒ = â˜ƒ.setValue(EAST, RedstoneSide.SIDE);
         }

         if (!â˜ƒ && â˜ƒxxxxx) {
            â˜ƒ = â˜ƒ.setValue(NORTH, RedstoneSide.SIDE);
         }

         if (!â˜ƒx && â˜ƒxxxxx) {
            â˜ƒ = â˜ƒ.setValue(SOUTH, RedstoneSide.SIDE);
         }

         return â˜ƒ;
      }
   }

   private BlockState getMissingConnections(BlockGetter var1, BlockState var2, BlockPos var3) {
      boolean â˜ƒ = !â˜ƒ.getBlockState(â˜ƒ.above()).isRedstoneConductor(â˜ƒ, â˜ƒ);

      for(Direction â˜ƒx : Direction.Plane.HORIZONTAL) {
         if (!((RedstoneSide)â˜ƒ.getValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒx))).isConnected()) {
            RedstoneSide â˜ƒxx = this.getConnectingSide(â˜ƒ, â˜ƒ, â˜ƒx, â˜ƒ);
            â˜ƒ = â˜ƒ.setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒx), â˜ƒxx);
         }
      }

      return â˜ƒ;
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ == Direction.DOWN) {
         return â˜ƒ;
      } else if (â˜ƒ == Direction.UP) {
         return this.getConnectionState(â˜ƒ, â˜ƒ, â˜ƒ);
      } else {
         RedstoneSide â˜ƒ = this.getConnectingSide(â˜ƒ, â˜ƒ, â˜ƒ);
         return â˜ƒ.isConnected() == ((RedstoneSide)â˜ƒ.getValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ))).isConnected() && !isCross(â˜ƒ)
            ? â˜ƒ.setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ), â˜ƒ)
            : this.getConnectionState(
               â˜ƒ, this.crossState.setValue(POWER, (Integer)â˜ƒ.getValue(POWER)).setValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ), â˜ƒ), â˜ƒ
            );
      }
   }

   private static boolean isCross(BlockState var0) {
      return ((RedstoneSide)â˜ƒ.getValue(NORTH)).isConnected()
         && ((RedstoneSide)â˜ƒ.getValue(SOUTH)).isConnected()
         && ((RedstoneSide)â˜ƒ.getValue(EAST)).isConnected()
         && ((RedstoneSide)â˜ƒ.getValue(WEST)).isConnected();
   }

   private static boolean isDot(BlockState var0) {
      return !((RedstoneSide)â˜ƒ.getValue(NORTH)).isConnected()
         && !((RedstoneSide)â˜ƒ.getValue(SOUTH)).isConnected()
         && !((RedstoneSide)â˜ƒ.getValue(EAST)).isConnected()
         && !((RedstoneSide)â˜ƒ.getValue(WEST)).isConnected();
   }

   @Override
   public void updateIndirectNeighbourShapes(BlockState var1, LevelAccessor var2, BlockPos var3, int var4, int var5) {
      BlockPos.MutableBlockPos â˜ƒ = new BlockPos.MutableBlockPos();

      for(Direction â˜ƒx : Direction.Plane.HORIZONTAL) {
         RedstoneSide â˜ƒxx = â˜ƒ.getValue((Property<RedstoneSide>)PROPERTY_BY_DIRECTION.get(â˜ƒx));
         if (â˜ƒxx != RedstoneSide.NONE && !â˜ƒ.getBlockState(â˜ƒ.setWithOffset(â˜ƒ, â˜ƒx)).is(this)) {
            â˜ƒ.move(Direction.DOWN);
            BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒ);
            if (!â˜ƒxxx.is(Blocks.OBSERVER)) {
               BlockPos â˜ƒxxxx = â˜ƒ.relative(â˜ƒx.getOpposite());
               BlockState â˜ƒxxxxx = â˜ƒxxx.updateShape(â˜ƒx.getOpposite(), â˜ƒ.getBlockState(â˜ƒxxxx), â˜ƒ, â˜ƒ, â˜ƒxxxx);
               updateOrDestroy(â˜ƒxxx, â˜ƒxxxxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            }

            â˜ƒ.setWithOffset(â˜ƒ, â˜ƒx).move(Direction.UP);
            BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒ);
            if (!â˜ƒxxx.is(Blocks.OBSERVER)) {
               BlockPos â˜ƒxxxx = â˜ƒ.relative(â˜ƒx.getOpposite());
               BlockState â˜ƒxxxxx = â˜ƒxxx.updateShape(â˜ƒx.getOpposite(), â˜ƒ.getBlockState(â˜ƒxxxx), â˜ƒ, â˜ƒ, â˜ƒxxxx);
               updateOrDestroy(â˜ƒxxx, â˜ƒxxxxx, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
            }
         }
      }
   }

   private RedstoneSide getConnectingSide(BlockGetter var1, BlockPos var2, Direction var3) {
      return this.getConnectingSide(â˜ƒ, â˜ƒ, â˜ƒ, !â˜ƒ.getBlockState(â˜ƒ.above()).isRedstoneConductor(â˜ƒ, â˜ƒ));
   }

   private RedstoneSide getConnectingSide(BlockGetter var1, BlockPos var2, Direction var3, boolean var4) {
      BlockPos â˜ƒ = â˜ƒ.relative(â˜ƒ);
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      if (â˜ƒ) {
         boolean â˜ƒxx = this.canSurviveOn(â˜ƒ, â˜ƒ, â˜ƒx);
         if (â˜ƒxx && shouldConnectTo(â˜ƒ.getBlockState(â˜ƒ.above()))) {
            if (â˜ƒx.isFaceSturdy(â˜ƒ, â˜ƒ, â˜ƒ.getOpposite())) {
               return RedstoneSide.UP;
            }

            return RedstoneSide.SIDE;
         }
      }

      return !shouldConnectTo(â˜ƒx, â˜ƒ) && (â˜ƒx.isRedstoneConductor(â˜ƒ, â˜ƒ) || !shouldConnectTo(â˜ƒ.getBlockState(â˜ƒ.below())))
         ? RedstoneSide.NONE
         : RedstoneSide.SIDE;
   }

   @Override
   public boolean canSurvive(BlockState var1, LevelReader var2, BlockPos var3) {
      BlockPos â˜ƒ = â˜ƒ.below();
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      return this.canSurviveOn(â˜ƒ, â˜ƒ, â˜ƒx);
   }

   private boolean canSurviveOn(BlockGetter var1, BlockPos var2, BlockState var3) {
      return â˜ƒ.isFaceSturdy(â˜ƒ, â˜ƒ, Direction.UP) || â˜ƒ.is(Blocks.HOPPER);
   }

   private void updatePowerStrength(Level var1, BlockPos var2, BlockState var3) {
      int â˜ƒ = this.calculateTargetStrength(â˜ƒ, â˜ƒ);
      if (â˜ƒ.getValue(POWER) != â˜ƒ) {
         if (â˜ƒ.getBlockState(â˜ƒ) == â˜ƒ) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWER, Integer.valueOf(â˜ƒ)), 2);
         }

         Set<BlockPos> â˜ƒx = Sets.<BlockPos>newHashSet();
         â˜ƒx.add(â˜ƒ);

         for(Direction â˜ƒxx : Direction.values()) {
            â˜ƒx.add(â˜ƒ.relative(â˜ƒxx));
         }

         for(BlockPos â˜ƒxx : â˜ƒx) {
            â˜ƒ.updateNeighborsAt(â˜ƒxx, this);
         }
      }
   }

   private int calculateTargetStrength(Level var1, BlockPos var2) {
      this.shouldSignal = false;
      int â˜ƒ = â˜ƒ.getBestNeighborSignal(â˜ƒ);
      this.shouldSignal = true;
      int â˜ƒx = 0;
      if (â˜ƒ < 15) {
         for(Direction â˜ƒxx : Direction.Plane.HORIZONTAL) {
            BlockPos â˜ƒxxx = â˜ƒ.relative(â˜ƒxx);
            BlockState â˜ƒxxxx = â˜ƒ.getBlockState(â˜ƒxxx);
            â˜ƒx = Math.max(â˜ƒx, this.getWireSignal(â˜ƒxxxx));
            BlockPos â˜ƒxxxxx = â˜ƒ.above();
            if (â˜ƒxxxx.isRedstoneConductor(â˜ƒ, â˜ƒxxx) && !â˜ƒ.getBlockState(â˜ƒxxxxx).isRedstoneConductor(â˜ƒ, â˜ƒxxxxx)) {
               â˜ƒx = Math.max(â˜ƒx, this.getWireSignal(â˜ƒ.getBlockState(â˜ƒxxx.above())));
            } else if (!â˜ƒxxxx.isRedstoneConductor(â˜ƒ, â˜ƒxxx)) {
               â˜ƒx = Math.max(â˜ƒx, this.getWireSignal(â˜ƒ.getBlockState(â˜ƒxxx.below())));
            }
         }
      }

      return Math.max(â˜ƒ, â˜ƒx - 1);
   }

   private int getWireSignal(BlockState var1) {
      return â˜ƒ.is(this) ? â˜ƒ.getValue(POWER) : 0;
   }

   private void checkCornerChangeAt(Level var1, BlockPos var2) {
      if (â˜ƒ.getBlockState(â˜ƒ).is(this)) {
         â˜ƒ.updateNeighborsAt(â˜ƒ, this);

         for(Direction â˜ƒ : Direction.values()) {
            â˜ƒ.updateNeighborsAt(â˜ƒ.relative(â˜ƒ), this);
         }
      }
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock()) && !â˜ƒ.isClientSide) {
         this.updatePowerStrength(â˜ƒ, â˜ƒ, â˜ƒ);

         for(Direction â˜ƒ : Direction.Plane.VERTICAL) {
            â˜ƒ.updateNeighborsAt(â˜ƒ.relative(â˜ƒ), this);
         }

         this.updateNeighborsOfNeighboringWires(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ && !â˜ƒ.is(â˜ƒ.getBlock())) {
         super.onRemove(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         if (!â˜ƒ.isClientSide) {
            for(Direction â˜ƒ : Direction.values()) {
               â˜ƒ.updateNeighborsAt(â˜ƒ.relative(â˜ƒ), this);
            }

            this.updatePowerStrength(â˜ƒ, â˜ƒ, â˜ƒ);
            this.updateNeighborsOfNeighboringWires(â˜ƒ, â˜ƒ);
         }
      }
   }

   private void updateNeighborsOfNeighboringWires(Level var1, BlockPos var2) {
      for(Direction â˜ƒ : Direction.Plane.HORIZONTAL) {
         this.checkCornerChangeAt(â˜ƒ, â˜ƒ.relative(â˜ƒ));
      }

      for(Direction â˜ƒ : Direction.Plane.HORIZONTAL) {
         BlockPos â˜ƒx = â˜ƒ.relative(â˜ƒ);
         if (â˜ƒ.getBlockState(â˜ƒx).isRedstoneConductor(â˜ƒ, â˜ƒx)) {
            this.checkCornerChangeAt(â˜ƒ, â˜ƒx.above());
         } else {
            this.checkCornerChangeAt(â˜ƒ, â˜ƒx.below());
         }
      }
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      if (!â˜ƒ.isClientSide) {
         if (â˜ƒ.canSurvive(â˜ƒ, â˜ƒ)) {
            this.updatePowerStrength(â˜ƒ, â˜ƒ, â˜ƒ);
         } else {
            dropResources(â˜ƒ, â˜ƒ, â˜ƒ);
            â˜ƒ.removeBlock(â˜ƒ, false);
         }
      }
   }

   @Override
   public int getDirectSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return !this.shouldSignal ? 0 : â˜ƒ.getSignal(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public int getSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      if (this.shouldSignal && â˜ƒ != Direction.DOWN) {
         int â˜ƒ = â˜ƒ.getValue(POWER);
         if (â˜ƒ == 0) {
            return 0;
         } else {
            return â˜ƒ != Direction.UP
                  && !((RedstoneSide)this.getConnectionState(â˜ƒ, â˜ƒ, â˜ƒ).getValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ.getOpposite()))).isConnected()
               ? 0
               : â˜ƒ;
         }
      } else {
         return 0;
      }
   }

   protected static boolean shouldConnectTo(BlockState var0) {
      return shouldConnectTo(â˜ƒ, null);
   }

   protected static boolean shouldConnectTo(BlockState var0, @Nullable Direction var1) {
      if (â˜ƒ.is(Blocks.REDSTONE_WIRE)) {
         return true;
      } else if (â˜ƒ.is(Blocks.REPEATER)) {
         Direction â˜ƒ = â˜ƒ.getValue(RepeaterBlock.FACING);
         return â˜ƒ == â˜ƒ || â˜ƒ.getOpposite() == â˜ƒ;
      } else if (â˜ƒ.is(Blocks.OBSERVER)) {
         return â˜ƒ == â˜ƒ.getValue(ObserverBlock.FACING);
      } else {
         return â˜ƒ.isSignalSource() && â˜ƒ != null;
      }
   }

   @Override
   public boolean isSignalSource(BlockState var1) {
      return this.shouldSignal;
   }

   public static int getColorForPower(int var0) {
      Vec3 â˜ƒ = COLORS[â˜ƒ];
      return Mth.color((float)â˜ƒ.x(), (float)â˜ƒ.y(), (float)â˜ƒ.z());
   }

   private void spawnParticlesAlongLine(Level var1, Random var2, BlockPos var3, Vec3 var4, Direction var5, Direction var6, float var7, float var8) {
      float â˜ƒ = â˜ƒ - â˜ƒ;
      if (!(â˜ƒ.nextFloat() >= 0.2F * â˜ƒ)) {
         float â˜ƒx = 0.4375F;
         float â˜ƒxx = â˜ƒ + â˜ƒ * â˜ƒ.nextFloat();
         double â˜ƒxxx = 0.5 + (double)(0.4375F * (float)â˜ƒ.getStepX()) + (double)(â˜ƒxx * (float)â˜ƒ.getStepX());
         double â˜ƒxxxx = 0.5 + (double)(0.4375F * (float)â˜ƒ.getStepY()) + (double)(â˜ƒxx * (float)â˜ƒ.getStepY());
         double â˜ƒxxxxx = 0.5 + (double)(0.4375F * (float)â˜ƒ.getStepZ()) + (double)(â˜ƒxx * (float)â˜ƒ.getStepZ());
         â˜ƒ.addParticle(
            new DustParticleOptions(new Vector3f(â˜ƒ), 1.0F),
            (double)â˜ƒ.getX() + â˜ƒxxx,
            (double)â˜ƒ.getY() + â˜ƒxxxx,
            (double)â˜ƒ.getZ() + â˜ƒxxxxx,
            0.0,
            0.0,
            0.0
         );
      }
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      int â˜ƒ = â˜ƒ.getValue(POWER);
      if (â˜ƒ != 0) {
         for(Direction â˜ƒx : Direction.Plane.HORIZONTAL) {
            RedstoneSide â˜ƒxx = â˜ƒ.getValue((Property<RedstoneSide>)PROPERTY_BY_DIRECTION.get(â˜ƒx));
            switch(â˜ƒxx) {
               case UP:
                  this.spawnParticlesAlongLine(â˜ƒ, â˜ƒ, â˜ƒ, COLORS[â˜ƒ], â˜ƒx, Direction.UP, -0.5F, 0.5F);
               case SIDE:
                  this.spawnParticlesAlongLine(â˜ƒ, â˜ƒ, â˜ƒ, COLORS[â˜ƒ], Direction.DOWN, â˜ƒx, 0.0F, 0.5F);
                  break;
               case NONE:
               default:
                  this.spawnParticlesAlongLine(â˜ƒ, â˜ƒ, â˜ƒ, COLORS[â˜ƒ], Direction.DOWN, â˜ƒx, 0.0F, 0.3F);
            }
         }
      }
   }

   @Override
   public BlockState rotate(BlockState var1, Rotation var2) {
      switch(â˜ƒ) {
         case CLOCKWISE_180:
            return â˜ƒ.setValue(NORTH, (RedstoneSide)â˜ƒ.getValue(SOUTH))
               .setValue(EAST, (RedstoneSide)â˜ƒ.getValue(WEST))
               .setValue(SOUTH, (RedstoneSide)â˜ƒ.getValue(NORTH))
               .setValue(WEST, (RedstoneSide)â˜ƒ.getValue(EAST));
         case COUNTERCLOCKWISE_90:
            return â˜ƒ.setValue(NORTH, (RedstoneSide)â˜ƒ.getValue(EAST))
               .setValue(EAST, (RedstoneSide)â˜ƒ.getValue(SOUTH))
               .setValue(SOUTH, (RedstoneSide)â˜ƒ.getValue(WEST))
               .setValue(WEST, (RedstoneSide)â˜ƒ.getValue(NORTH));
         case CLOCKWISE_90:
            return â˜ƒ.setValue(NORTH, (RedstoneSide)â˜ƒ.getValue(WEST))
               .setValue(EAST, (RedstoneSide)â˜ƒ.getValue(NORTH))
               .setValue(SOUTH, (RedstoneSide)â˜ƒ.getValue(EAST))
               .setValue(WEST, (RedstoneSide)â˜ƒ.getValue(SOUTH));
         default:
            return â˜ƒ;
      }
   }

   @Override
   public BlockState mirror(BlockState var1, Mirror var2) {
      switch(â˜ƒ) {
         case LEFT_RIGHT:
            return â˜ƒ.setValue(NORTH, (RedstoneSide)â˜ƒ.getValue(SOUTH)).setValue(SOUTH, (RedstoneSide)â˜ƒ.getValue(NORTH));
         case FRONT_BACK:
            return â˜ƒ.setValue(EAST, (RedstoneSide)â˜ƒ.getValue(WEST)).setValue(WEST, (RedstoneSide)â˜ƒ.getValue(EAST));
         default:
            return super.mirror(â˜ƒ, â˜ƒ);
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(NORTH, EAST, SOUTH, WEST, POWER);
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (!â˜ƒ.getAbilities().mayBuild) {
         return InteractionResult.PASS;
      } else {
         if (isCross(â˜ƒ) || isDot(â˜ƒ)) {
            BlockState â˜ƒ = isCross(â˜ƒ) ? this.defaultBlockState() : this.crossState;
            â˜ƒ = â˜ƒ.setValue(POWER, (Integer)â˜ƒ.getValue(POWER));
            â˜ƒ = this.getConnectionState(â˜ƒ, â˜ƒ, â˜ƒ);
            if (â˜ƒ != â˜ƒ) {
               â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 3);
               this.updatesOnShapeChange(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
               return InteractionResult.SUCCESS;
            }
         }

         return InteractionResult.PASS;
      }
   }

   private void updatesOnShapeChange(Level var1, BlockPos var2, BlockState var3, BlockState var4) {
      for(Direction â˜ƒ : Direction.Plane.HORIZONTAL) {
         BlockPos â˜ƒx = â˜ƒ.relative(â˜ƒ);
         if (((RedstoneSide)â˜ƒ.getValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ))).isConnected()
               != ((RedstoneSide)â˜ƒ.getValue((Property)PROPERTY_BY_DIRECTION.get(â˜ƒ))).isConnected()
            && â˜ƒ.getBlockState(â˜ƒx).isRedstoneConductor(â˜ƒ, â˜ƒx)) {
            â˜ƒ.updateNeighborsAtExceptFromFacing(â˜ƒx, â˜ƒ.getBlock(), â˜ƒ.getOpposite());
         }
      }
   }
}
