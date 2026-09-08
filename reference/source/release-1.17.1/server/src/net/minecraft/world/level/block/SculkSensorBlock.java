package net.minecraft.world.level.block;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SculkSensorBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
   public static final int ACTIVE_TICKS = 40;
   public static final int COOLDOWN_TICKS = 1;
   public static final Object2IntMap<GameEvent> VIBRATION_STRENGTH_FOR_EVENT = Object2IntMaps.unmodifiable(Util.make(new Object2IntOpenHashMap<>(), var0 -> {
      var0.put(GameEvent.STEP, 1);
      var0.put(GameEvent.FLAP, 2);
      var0.put(GameEvent.SWIM, 3);
      var0.put(GameEvent.ELYTRA_FREE_FALL, 4);
      var0.put(GameEvent.HIT_GROUND, 5);
      var0.put(GameEvent.SPLASH, 6);
      var0.put(GameEvent.WOLF_SHAKING, 6);
      var0.put(GameEvent.MINECART_MOVING, 6);
      var0.put(GameEvent.RING_BELL, 6);
      var0.put(GameEvent.BLOCK_CHANGE, 6);
      var0.put(GameEvent.PROJECTILE_SHOOT, 7);
      var0.put(GameEvent.DRINKING_FINISH, 7);
      var0.put(GameEvent.PRIME_FUSE, 7);
      var0.put(GameEvent.PROJECTILE_LAND, 8);
      var0.put(GameEvent.EAT, 8);
      var0.put(GameEvent.MOB_INTERACT, 8);
      var0.put(GameEvent.ENTITY_DAMAGED, 8);
      var0.put(GameEvent.EQUIP, 9);
      var0.put(GameEvent.SHEAR, 9);
      var0.put(GameEvent.RAVAGER_ROAR, 9);
      var0.put(GameEvent.BLOCK_CLOSE, 10);
      var0.put(GameEvent.BLOCK_UNSWITCH, 10);
      var0.put(GameEvent.BLOCK_UNPRESS, 10);
      var0.put(GameEvent.BLOCK_DETACH, 10);
      var0.put(GameEvent.DISPENSE_FAIL, 10);
      var0.put(GameEvent.BLOCK_OPEN, 11);
      var0.put(GameEvent.BLOCK_SWITCH, 11);
      var0.put(GameEvent.BLOCK_PRESS, 11);
      var0.put(GameEvent.BLOCK_ATTACH, 11);
      var0.put(GameEvent.ENTITY_PLACE, 12);
      var0.put(GameEvent.BLOCK_PLACE, 12);
      var0.put(GameEvent.FLUID_PLACE, 12);
      var0.put(GameEvent.ENTITY_KILLED, 13);
      var0.put(GameEvent.BLOCK_DESTROY, 13);
      var0.put(GameEvent.FLUID_PICKUP, 13);
      var0.put(GameEvent.FISHING_ROD_REEL_IN, 14);
      var0.put(GameEvent.CONTAINER_CLOSE, 14);
      var0.put(GameEvent.PISTON_CONTRACT, 14);
      var0.put(GameEvent.SHULKER_CLOSE, 14);
      var0.put(GameEvent.PISTON_EXTEND, 15);
      var0.put(GameEvent.CONTAINER_OPEN, 15);
      var0.put(GameEvent.FISHING_ROD_CAST, 15);
      var0.put(GameEvent.EXPLODE, 15);
      var0.put(GameEvent.LIGHTNING_STRIKE, 15);
      var0.put(GameEvent.SHULKER_OPEN, 15);
   }));
   public static final EnumProperty<SculkSensorPhase> PHASE = BlockStateProperties.SCULK_SENSOR_PHASE;
   public static final IntegerProperty POWER = BlockStateProperties.POWER;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   private final int listenerRange;

   public SculkSensorBlock(BlockBehaviour.Properties var1, int var2) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition
            .any()
            .setValue(PHASE, SculkSensorPhase.INACTIVE)
            .setValue(POWER, Integer.valueOf(0))
            .setValue(WATERLOGGED, Boolean.valueOf(false))
      );
      this.listenerRange = â˜ƒ;
   }

   public int getListenerRange() {
      return this.listenerRange;
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      BlockPos â˜ƒ = â˜ƒ.getClickedPos();
      FluidState â˜ƒx = â˜ƒ.getLevel().getFluidState(â˜ƒ);
      return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(â˜ƒx.getType() == Fluids.WATER));
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      if (getPhase(â˜ƒ) != SculkSensorPhase.ACTIVE) {
         if (getPhase(â˜ƒ) == SculkSensorPhase.COOLDOWN) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(PHASE, SculkSensorPhase.INACTIVE), 3);
         }
      } else {
         deactivate(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void onPlace(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.isClientSide() && !â˜ƒ.is(â˜ƒ.getBlock())) {
         if (â˜ƒ.getValue(POWER) > 0 && !â˜ƒ.getBlockTicks().hasScheduledTick(â˜ƒ, this)) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWER, Integer.valueOf(0)), 18);
         }

         â˜ƒ.getBlockTicks().scheduleTick(new BlockPos(â˜ƒ), â˜ƒ.getBlock(), 1);
      }
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         if (getPhase(â˜ƒ) == SculkSensorPhase.ACTIVE) {
            updateNeighbours(â˜ƒ, â˜ƒ);
         }

         super.onRemove(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static void updateNeighbours(Level var0, BlockPos var1) {
      â˜ƒ.updateNeighborsAt(â˜ƒ, Blocks.SCULK_SENSOR);
      â˜ƒ.updateNeighborsAt(â˜ƒ.relative(Direction.UP.getOpposite()), Blocks.SCULK_SENSOR);
   }

   @Nullable
   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new SculkSensorBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public <T extends BlockEntity> GameEventListener getListener(Level var1, T var2) {
      return â˜ƒ instanceof SculkSensorBlockEntity ? ((SculkSensorBlockEntity)â˜ƒ).getListener() : null;
   }

   @Nullable
   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level var1, BlockState var2, BlockEntityType<T> var3) {
      return !â˜ƒ.isClientSide ? createTickerHelper(â˜ƒ, BlockEntityType.SCULK_SENSOR, (var0, var1x, var2x, var3x) -> var3x.getListener().tick(var0)) : null;
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.MODEL;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   public boolean isSignalSource(BlockState var1) {
      return true;
   }

   @Override
   public int getSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ.getValue(POWER);
   }

   public static SculkSensorPhase getPhase(BlockState var0) {
      return â˜ƒ.getValue(PHASE);
   }

   public static boolean canActivate(BlockState var0) {
      return getPhase(â˜ƒ) == SculkSensorPhase.INACTIVE;
   }

   public static void deactivate(Level var0, BlockPos var1, BlockState var2) {
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(PHASE, SculkSensorPhase.COOLDOWN).setValue(POWER, Integer.valueOf(0)), 3);
      â˜ƒ.getBlockTicks().scheduleTick(new BlockPos(â˜ƒ), â˜ƒ.getBlock(), 1);
      if (!â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.SCULK_CLICKING_STOP, SoundSource.BLOCKS, 1.0F, â˜ƒ.random.nextFloat() * 0.2F + 0.8F);
      }

      updateNeighbours(â˜ƒ, â˜ƒ);
   }

   public static void activate(Level var0, BlockPos var1, BlockState var2, int var3) {
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(PHASE, SculkSensorPhase.ACTIVE).setValue(POWER, Integer.valueOf(â˜ƒ)), 3);
      â˜ƒ.getBlockTicks().scheduleTick(new BlockPos(â˜ƒ), â˜ƒ.getBlock(), 40);
      updateNeighbours(â˜ƒ, â˜ƒ);
      if (!â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.playSound(
            null,
            (double)â˜ƒ.getX() + 0.5,
            (double)â˜ƒ.getY() + 0.5,
            (double)â˜ƒ.getZ() + 0.5,
            SoundEvents.SCULK_CLICKING,
            SoundSource.BLOCKS,
            1.0F,
            â˜ƒ.random.nextFloat() * 0.2F + 0.8F
         );
      }
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      if (getPhase(â˜ƒ) == SculkSensorPhase.ACTIVE) {
         Direction â˜ƒ = Direction.getRandom(â˜ƒ);
         if (â˜ƒ != Direction.UP && â˜ƒ != Direction.DOWN) {
            double â˜ƒx = (double)â˜ƒ.getX() + 0.5 + (â˜ƒ.getStepX() == 0 ? 0.5 - â˜ƒ.nextDouble() : (double)â˜ƒ.getStepX() * 0.6);
            double â˜ƒxx = (double)â˜ƒ.getY() + 0.25;
            double â˜ƒxxx = (double)â˜ƒ.getZ() + 0.5 + (â˜ƒ.getStepZ() == 0 ? 0.5 - â˜ƒ.nextDouble() : (double)â˜ƒ.getStepZ() * 0.6);
            double â˜ƒxxxx = (double)â˜ƒ.nextFloat() * 0.04;
            â˜ƒ.addParticle(DustColorTransitionOptions.SCULK_TO_REDSTONE, â˜ƒx, â˜ƒxx, â˜ƒxxx, 0.0, â˜ƒxxxx, 0.0);
         }
      }
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(PHASE, POWER, WATERLOGGED);
   }

   @Override
   public boolean hasAnalogOutputSignal(BlockState var1) {
      return true;
   }

   @Override
   public int getAnalogOutputSignal(BlockState var1, Level var2, BlockPos var3) {
      BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒx instanceof SculkSensorBlockEntity â˜ƒ) {
         return getPhase(â˜ƒ) == SculkSensorPhase.ACTIVE ? â˜ƒ.getLastVibrationFrequency() : 0;
      } else {
         return 0;
      }
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }

   @Override
   public boolean useShapeForLightOcclusion(BlockState var1) {
      return true;
   }
}
