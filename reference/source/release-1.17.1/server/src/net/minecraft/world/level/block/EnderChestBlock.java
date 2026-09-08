package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EnderChestBlock extends AbstractChestBlock<EnderChestBlockEntity> implements SimpleWaterloggedBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
   private static final Component CONTAINER_TITLE = new TranslatableComponent("container.enderchest");

   protected EnderChestBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ, () -> BlockEntityType.ENDER_CHEST);
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)));
   }

   @Override
   public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(BlockState var1, Level var2, BlockPos var3, boolean var4) {
      return DoubleBlockCombiner.Combiner::acceptNone;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE;
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      FluidState â˜ƒ = â˜ƒ.getLevel().getFluidState(â˜ƒ.getClickedPos());
      return this.defaultBlockState()
         .setValue(FACING, â˜ƒ.getHorizontalDirection().getOpposite())
         .setValue(WATERLOGGED, Boolean.valueOf(â˜ƒ.getType() == Fluids.WATER));
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      PlayerEnderChestContainer â˜ƒ = â˜ƒ.getEnderChestInventory();
      BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒ != null && â˜ƒx instanceof EnderChestBlockEntity) {
         BlockPos â˜ƒxx = â˜ƒ.above();
         if (â˜ƒ.getBlockState(â˜ƒxx).isRedstoneConductor(â˜ƒ, â˜ƒxx)) {
            return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
         } else if (â˜ƒ.isClientSide) {
            return InteractionResult.SUCCESS;
         } else {
            EnderChestBlockEntity â˜ƒxx = (EnderChestBlockEntity)â˜ƒx;
            â˜ƒ.setActiveChest(â˜ƒxx);
            â˜ƒ.openMenu(new SimpleMenuProvider((var1x, var2x, var3x) -> ChestMenu.threeRows(var1x, var2x, â˜ƒ), CONTAINER_TITLE));
            â˜ƒ.awardStat(Stats.OPEN_ENDERCHEST);
            PiglinAi.angerNearbyPiglins(â˜ƒ, true);
            return InteractionResult.CONSUME;
         }
      } else {
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      }
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new EnderChestBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level var1, BlockState var2, BlockEntityType<T> var3) {
      return â˜ƒ.isClientSide ? createTickerHelper(â˜ƒ, BlockEntityType.ENDER_CHEST, EnderChestBlockEntity::lidAnimateTick) : null;
   }

   @Override
   public void animateTick(BlockState var1, Level var2, BlockPos var3, Random var4) {
      for(int â˜ƒ = 0; â˜ƒ < 3; ++â˜ƒ) {
         int â˜ƒx = â˜ƒ.nextInt(2) * 2 - 1;
         int â˜ƒxx = â˜ƒ.nextInt(2) * 2 - 1;
         double â˜ƒxxx = (double)â˜ƒ.getX() + 0.5 + 0.25 * (double)â˜ƒx;
         double â˜ƒxxxx = (double)((float)â˜ƒ.getY() + â˜ƒ.nextFloat());
         double â˜ƒxxxxx = (double)â˜ƒ.getZ() + 0.5 + 0.25 * (double)â˜ƒxx;
         double â˜ƒxxxxxx = (double)(â˜ƒ.nextFloat() * (float)â˜ƒx);
         double â˜ƒxxxxxxx = ((double)â˜ƒ.nextFloat() - 0.5) * 0.125;
         double â˜ƒxxxxxxxx = (double)(â˜ƒ.nextFloat() * (float)â˜ƒxx);
         â˜ƒ.addParticle(ParticleTypes.PORTAL, â˜ƒxxx, â˜ƒxxxx, â˜ƒxxxxx, â˜ƒxxxxxx, â˜ƒxxxxxxx, â˜ƒxxxxxxxx);
      }
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

   @Override
   public FluidState getFluidState(BlockState var1) {
      return â˜ƒ.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(â˜ƒ);
   }

   @Override
   public BlockState updateShape(BlockState var1, Direction var2, BlockState var3, LevelAccessor var4, BlockPos var5, BlockPos var6) {
      if (â˜ƒ.getValue(WATERLOGGED)) {
         â˜ƒ.getLiquidTicks().scheduleTick(â˜ƒ, Fluids.WATER, Fluids.WATER.getTickDelay(â˜ƒ));
      }

      return super.updateShape(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒ instanceof EnderChestBlockEntity) {
         ((EnderChestBlockEntity)â˜ƒ).recheckOpen();
      }
   }
}
