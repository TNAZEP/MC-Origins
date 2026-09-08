package net.minecraft.world.level.block;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChestBlock extends AbstractChestBlock<ChestBlockEntity> implements SimpleWaterloggedBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final EnumProperty<ChestType> TYPE = BlockStateProperties.CHEST_TYPE;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final int EVENT_SET_OPEN_COUNT = 1;
   protected static final int AABB_OFFSET = 1;
   protected static final int AABB_HEIGHT = 14;
   protected static final VoxelShape NORTH_AABB = Block.box(1.0, 0.0, 0.0, 15.0, 14.0, 15.0);
   protected static final VoxelShape SOUTH_AABB = Block.box(1.0, 0.0, 1.0, 15.0, 14.0, 16.0);
   protected static final VoxelShape WEST_AABB = Block.box(0.0, 0.0, 1.0, 15.0, 14.0, 15.0);
   protected static final VoxelShape EAST_AABB = Block.box(1.0, 0.0, 1.0, 16.0, 14.0, 15.0);
   protected static final VoxelShape AABB = Block.box(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
   private static final DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<Container>> CHEST_COMBINER = new DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<Container>>(
      
   ) {
      public Optional<Container> acceptDouble(ChestBlockEntity var1, ChestBlockEntity var2) {
         return Optional.of(new CompoundContainer(â˜ƒ, â˜ƒ));
      }

      public Optional<Container> acceptSingle(ChestBlockEntity var1) {
         return Optional.of(â˜ƒ);
      }

      public Optional<Container> acceptNone() {
         return Optional.empty();
      }
   };
   private static final DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<MenuProvider>> MENU_PROVIDER_COMBINER = new DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<MenuProvider>>(
      
   ) {
      public Optional<MenuProvider> acceptDouble(final ChestBlockEntity var1, final ChestBlockEntity var2) {
         final Container â˜ƒ = new CompoundContainer(â˜ƒ, â˜ƒ);
         return Optional.of(new MenuProvider() {
            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int var1x, Inventory var2x, Player var3x) {
               if (â˜ƒ.canOpen(â˜ƒ) && â˜ƒ.canOpen(â˜ƒ)) {
                  â˜ƒ.unpackLootTable(â˜ƒ.player);
                  â˜ƒ.unpackLootTable(â˜ƒ.player);
                  return ChestMenu.sixRows(â˜ƒ, â˜ƒ, â˜ƒ);
               } else {
                  return null;
               }
            }

            @Override
            public Component getDisplayName() {
               if (â˜ƒ.hasCustomName()) {
                  return â˜ƒ.getDisplayName();
               } else {
                  return (Component)(â˜ƒ.hasCustomName() ? â˜ƒ.getDisplayName() : new TranslatableComponent("container.chestDouble"));
               }
            }
         });
      }

      public Optional<MenuProvider> acceptSingle(ChestBlockEntity var1) {
         return Optional.of(â˜ƒ);
      }

      public Optional<MenuProvider> acceptNone() {
         return Optional.empty();
      }
   };

   protected ChestBlock(BlockBehaviour.Properties var1, Supplier<BlockEntityType<? extends ChestBlockEntity>> var2) {
      super(â˜ƒ, â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TYPE, ChestType.SINGLE).setValue(WATERLOGGED, Boolean.valueOf(false))
      );
   }

   public static DoubleBlockCombiner.BlockType getBlockType(BlockState var0) {
      ChestType â˜ƒ = â˜ƒ.getValue(TYPE);
      if (â˜ƒ == ChestType.SINGLE) {
         return DoubleBlockCombiner.BlockType.SINGLE;
      } else {
         return â˜ƒ == ChestType.RIGHT ? DoubleBlockCombiner.BlockType.FIRST : DoubleBlockCombiner.BlockType.SECOND;
      }
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      if (â˜ƒ.is(this) && â˜ƒ.getAxis().isHorizontal()) {
         ChestType â˜ƒ = â˜ƒ.getValue(TYPE);
         if (â˜ƒ.getValue(TYPE) == ChestType.SINGLE
            && â˜ƒ != ChestType.SINGLE
            && â˜ƒ.getValue(FACING) == â˜ƒ.getValue(FACING)
            && getConnectedDirection(â˜ƒ) == â˜ƒ.getOpposite()) {
            return â˜ƒ.setValue(TYPE, â˜ƒ.getOpposite());
         }
      } else if (getConnectedDirection(â˜ƒ) == â˜ƒ) {
         return â˜ƒ.setValue(TYPE, ChestType.SINGLE);
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      if (â˜ƒ.getValue(TYPE) == ChestType.SINGLE) {
         return AABB;
      } else {
         switch(getConnectedDirection(â˜ƒ)) {
            case NORTH:
            default:
               return NORTH_AABB;
            case SOUTH:
               return SOUTH_AABB;
            case WEST:
               return WEST_AABB;
            case EAST:
               return EAST_AABB;
         }
      }
   }

   public static Direction getConnectedDirection(BlockState var0) {
      Direction â˜ƒ = â˜ƒ.getValue(FACING);
      return â˜ƒ.getValue(TYPE) == ChestType.LEFT ? â˜ƒ.getClockWise() : â˜ƒ.getCounterClockWise();
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      ChestType â˜ƒ = ChestType.SINGLE;
      Direction â˜ƒx = â˜ƒ.getHorizontalDirection().getOpposite();
      FluidState â˜ƒxx = â˜ƒ.getLevel().getFluidState(â˜ƒ.getClickedPos());
      boolean â˜ƒxxx = â˜ƒ.isSecondaryUseActive();
      Direction â˜ƒxxxx = â˜ƒ.getClickedFace();
      if (â˜ƒxxxx.getAxis().isHorizontal() && â˜ƒxxx) {
         Direction â˜ƒxxxxx = this.candidatePartnerFacing(â˜ƒ, â˜ƒxxxx.getOpposite());
         if (â˜ƒxxxxx != null && â˜ƒxxxxx.getAxis() != â˜ƒxxxx.getAxis()) {
            â˜ƒx = â˜ƒxxxxx;
            â˜ƒ = â˜ƒxxxxx.getCounterClockWise() == â˜ƒxxxx.getOpposite() ? ChestType.RIGHT : ChestType.LEFT;
         }
      }

      if (â˜ƒ == ChestType.SINGLE && !â˜ƒxxx) {
         if (â˜ƒx == this.candidatePartnerFacing(â˜ƒ, â˜ƒx.getClockWise())) {
            â˜ƒ = ChestType.LEFT;
         } else if (â˜ƒx == this.candidatePartnerFacing(â˜ƒ, â˜ƒx.getCounterClockWise())) {
            â˜ƒ = ChestType.RIGHT;
         }
      }

      return this.defaultBlockState().setValue(FACING, â˜ƒx).setValue(TYPE, â˜ƒ).setValue(WATERLOGGED, Boolean.valueOf(â˜ƒxx.getType() == Fluids.WATER));
   }

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   @Nullable
   private Direction candidatePartnerFacing(BlockPlaceContext var1, Direction var2) {
      BlockState â˜ƒ = â˜ƒ.getLevel().getBlockState(â˜ƒ.getClickedPos().relative(â˜ƒ));
      return â˜ƒ.is(this) && â˜ƒ.getValue(TYPE) == ChestType.SINGLE ? â˜ƒ.getValue(FACING) : null;
   }

   @Override
   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, LivingEntity var4, ItemStack var5) {
      if (â˜ƒ.hasCustomHoverName()) {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒ instanceof ChestBlockEntity) {
            ((ChestBlockEntity)â˜ƒ).setCustomName(â˜ƒ.getHoverName());
         }
      }
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒ instanceof Container) {
            Containers.dropContents(â˜ƒ, â˜ƒ, (Container)â˜ƒ);
            â˜ƒ.updateNeighbourForOutputSignal(â˜ƒ, this);
         }

         super.onRemove(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (â˜ƒ.isClientSide) {
         return InteractionResult.SUCCESS;
      } else {
         MenuProvider â˜ƒ = this.getMenuProvider(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ != null) {
            â˜ƒ.openMenu(â˜ƒ);
            â˜ƒ.awardStat(this.getOpenChestStat());
            PiglinAi.angerNearbyPiglins(â˜ƒ, true);
         }

         return InteractionResult.CONSUME;
      }
   }

   protected Stat<ResourceLocation> getOpenChestStat() {
      return Stats.CUSTOM.get(Stats.OPEN_CHEST);
   }

   public BlockEntityType<? extends ChestBlockEntity> blockEntityType() {
      return (BlockEntityType<? extends ChestBlockEntity>)this.blockEntityType.get();
   }

   @Nullable
   public static Container getContainer(ChestBlock var0, BlockState var1, Level var2, BlockPos var3, boolean var4) {
      return (Container)((Optional)â˜ƒ.combine(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ).apply(CHEST_COMBINER)).orElse(null);
   }

   @Override
   public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(BlockState var1, Level var2, BlockPos var3, boolean var4) {
      BiPredicate<LevelAccessor, BlockPos> â˜ƒ;
      if (â˜ƒ) {
         â˜ƒ = (var0, var1x) -> false;
      } else {
         â˜ƒ = ChestBlock::isChestBlockedAt;
      }

      return DoubleBlockCombiner.combineWithNeigbour(
         (BlockEntityType<? extends ChestBlockEntity>)this.blockEntityType.get(),
         ChestBlock::getBlockType,
         ChestBlock::getConnectedDirection,
         FACING,
         â˜ƒ,
         â˜ƒ,
         â˜ƒ,
         â˜ƒ
      );
   }

   @Nullable
   @Override
   public MenuProvider getMenuProvider(BlockState var1, Level var2, BlockPos var3) {
      return (MenuProvider)((Optional)this.combine(â˜ƒ, â˜ƒ, â˜ƒ, false).apply(MENU_PROVIDER_COMBINER)).orElse(null);
   }

   public static DoubleBlockCombiner.Combiner<ChestBlockEntity, Float2FloatFunction> opennessCombiner(final LidBlockEntity var0) {
      return new DoubleBlockCombiner.Combiner<ChestBlockEntity, Float2FloatFunction>() {
         public Float2FloatFunction acceptDouble(ChestBlockEntity var1, ChestBlockEntity var2) {
            return var2x -> Math.max(â˜ƒ.getOpenNess(var2x), â˜ƒ.getOpenNess(var2x));
         }

         public Float2FloatFunction acceptSingle(ChestBlockEntity var1) {
            return â˜ƒ::getOpenNess;
         }

         public Float2FloatFunction acceptNone() {
            return â˜ƒ::getOpenNess;
         }
      };
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new ChestBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level var1, BlockState var2, BlockEntityType<T> var3) {
      return â˜ƒ.isClientSide ? createTickerHelper(â˜ƒ, this.blockEntityType(), ChestBlockEntity::lidAnimateTick) : null;
   }

   public static boolean isChestBlockedAt(LevelAccessor var0, BlockPos var1) {
      return isBlockedChestByBlock(â˜ƒ, â˜ƒ) || isCatSittingOnChest(â˜ƒ, â˜ƒ);
   }

   private static boolean isBlockedChestByBlock(BlockGetter var0, BlockPos var1) {
      BlockPos â˜ƒ = â˜ƒ.above();
      return â˜ƒ.getBlockState(â˜ƒ).isRedstoneConductor(â˜ƒ, â˜ƒ);
   }

   private static boolean isCatSittingOnChest(LevelAccessor var0, BlockPos var1) {
      List<Cat> â˜ƒ = â˜ƒ.getEntitiesOfClass(
         Cat.class,
         new AABB(
            (double)â˜ƒ.getX(), (double)(â˜ƒ.getY() + 1), (double)â˜ƒ.getZ(), (double)(â˜ƒ.getX() + 1), (double)(â˜ƒ.getY() + 2), (double)(â˜ƒ.getZ() + 1)
         )
      );
      if (!â˜ƒ.isEmpty()) {
         for(Cat â˜ƒx : â˜ƒ) {
            if (â˜ƒx.isInSittingPose()) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public boolean hasAnalogOutputSignal(BlockState var1) {
      return true;
   }

   @Override
   public int getAnalogOutputSignal(BlockState var1, Level var2, BlockPos var3) {
      return AbstractContainerMenu.getRedstoneSignalFromContainer(getContainer(this, â˜ƒ, â˜ƒ, â˜ƒ, false));
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
      â˜ƒ.add(FACING, TYPE, WATERLOGGED);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒ instanceof ChestBlockEntity) {
         ((ChestBlockEntity)â˜ƒ).recheckOpen();
      }
   }
}
