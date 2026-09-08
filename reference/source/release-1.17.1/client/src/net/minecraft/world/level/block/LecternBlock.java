package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LecternBlock extends BaseEntityBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   public static final BooleanProperty HAS_BOOK = BlockStateProperties.HAS_BOOK;
   public static final VoxelShape SHAPE_BASE = Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
   public static final VoxelShape SHAPE_POST = Block.box(4.0, 2.0, 4.0, 12.0, 14.0, 12.0);
   public static final VoxelShape SHAPE_COMMON = Shapes.or(SHAPE_BASE, SHAPE_POST);
   public static final VoxelShape SHAPE_TOP_PLATE = Block.box(0.0, 15.0, 0.0, 16.0, 15.0, 16.0);
   public static final VoxelShape SHAPE_COLLISION = Shapes.or(SHAPE_COMMON, SHAPE_TOP_PLATE);
   public static final VoxelShape SHAPE_WEST = Shapes.or(
      Block.box(1.0, 10.0, 0.0, 5.333333, 14.0, 16.0),
      Block.box(5.333333, 12.0, 0.0, 9.666667, 16.0, 16.0),
      Block.box(9.666667, 14.0, 0.0, 14.0, 18.0, 16.0),
      SHAPE_COMMON
   );
   public static final VoxelShape SHAPE_NORTH = Shapes.or(
      Block.box(0.0, 10.0, 1.0, 16.0, 14.0, 5.333333),
      Block.box(0.0, 12.0, 5.333333, 16.0, 16.0, 9.666667),
      Block.box(0.0, 14.0, 9.666667, 16.0, 18.0, 14.0),
      SHAPE_COMMON
   );
   public static final VoxelShape SHAPE_EAST = Shapes.or(
      Block.box(10.666667, 10.0, 0.0, 15.0, 14.0, 16.0),
      Block.box(6.333333, 12.0, 0.0, 10.666667, 16.0, 16.0),
      Block.box(2.0, 14.0, 0.0, 6.333333, 18.0, 16.0),
      SHAPE_COMMON
   );
   public static final VoxelShape SHAPE_SOUTH = Shapes.or(
      Block.box(0.0, 10.0, 10.666667, 16.0, 14.0, 15.0),
      Block.box(0.0, 12.0, 6.333333, 16.0, 16.0, 10.666667),
      Block.box(0.0, 14.0, 2.0, 16.0, 18.0, 6.333333),
      SHAPE_COMMON
   );
   private static final int PAGE_CHANGE_IMPULSE_TICKS = 2;

   protected LecternBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, Boolean.valueOf(false)).setValue(HAS_BOOK, Boolean.valueOf(false))
      );
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.MODEL;
   }

   @Override
   public VoxelShape getOcclusionShape(BlockState var1, BlockGetter var2, BlockPos var3) {
      return SHAPE_COMMON;
   }

   @Override
   public boolean useShapeForLightOcclusion(BlockState var1) {
      return true;
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      ItemStack â˜ƒx = â˜ƒ.getItemInHand();
      CompoundTag â˜ƒxx = â˜ƒx.getTag();
      Player â˜ƒxxx = â˜ƒ.getPlayer();
      boolean â˜ƒxxxx = false;
      if (!â˜ƒ.isClientSide && â˜ƒxxx != null && â˜ƒxx != null && â˜ƒxxx.canUseGameMasterBlocks() && â˜ƒxx.contains("BlockEntityTag")) {
         CompoundTag â˜ƒxxxxx = â˜ƒxx.getCompound("BlockEntityTag");
         if (â˜ƒxxxxx.contains("Book")) {
            â˜ƒxxxx = true;
         }
      }

      return this.defaultBlockState().setValue(FACING, â˜ƒ.getHorizontalDirection().getOpposite()).setValue(HAS_BOOK, Boolean.valueOf(â˜ƒxxxx));
   }

   @Override
   public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return SHAPE_COLLISION;
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      switch((Direction)â˜ƒ.getValue(FACING)) {
         case NORTH:
            return SHAPE_NORTH;
         case SOUTH:
            return SHAPE_SOUTH;
         case EAST:
            return SHAPE_EAST;
         case WEST:
            return SHAPE_WEST;
         default:
            return SHAPE_COMMON;
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
      â˜ƒ.add(FACING, POWERED, HAS_BOOK);
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new LecternBlockEntity(â˜ƒ, â˜ƒ);
   }

   public static boolean tryPlaceBook(@Nullable Player var0, Level var1, BlockPos var2, BlockState var3, ItemStack var4) {
      if (!â˜ƒ.getValue(HAS_BOOK)) {
         if (!â˜ƒ.isClientSide) {
            placeBook(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
         }

         return true;
      } else {
         return false;
      }
   }

   private static void placeBook(@Nullable Player var0, Level var1, BlockPos var2, BlockState var3, ItemStack var4) {
      BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒx instanceof LecternBlockEntity â˜ƒ) {
         â˜ƒ.setBook(â˜ƒ.split(1));
         resetBookState(â˜ƒ, â˜ƒ, â˜ƒ, true);
         â˜ƒ.playSound(null, â˜ƒ, SoundEvents.BOOK_PUT, SoundSource.BLOCKS, 1.0F, 1.0F);
         â˜ƒ.gameEvent(â˜ƒ, GameEvent.BLOCK_CHANGE, â˜ƒ);
      }
   }

   public static void resetBookState(Level var0, BlockPos var1, BlockState var2, boolean var3) {
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(false)).setValue(HAS_BOOK, Boolean.valueOf(â˜ƒ)), 3);
      updateBelow(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   public static void signalPageChange(Level var0, BlockPos var1, BlockState var2) {
      changePowered(â˜ƒ, â˜ƒ, â˜ƒ, true);
      â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, â˜ƒ.getBlock(), 2);
      â˜ƒ.levelEvent(1043, â˜ƒ, 0);
   }

   private static void changePowered(Level var0, BlockPos var1, BlockState var2, boolean var3) {
      â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(â˜ƒ)), 3);
      updateBelow(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private static void updateBelow(Level var0, BlockPos var1, BlockState var2) {
      â˜ƒ.updateNeighborsAt(â˜ƒ.below(), â˜ƒ.getBlock());
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      changePowered(â˜ƒ, â˜ƒ, â˜ƒ, false);
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         if (â˜ƒ.getValue(HAS_BOOK)) {
            this.popBook(â˜ƒ, â˜ƒ, â˜ƒ);
         }

         if (â˜ƒ.getValue(POWERED)) {
            â˜ƒ.updateNeighborsAt(â˜ƒ.below(), this);
         }

         super.onRemove(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   private void popBook(BlockState var1, Level var2, BlockPos var3) {
      BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒx instanceof LecternBlockEntity â˜ƒ) {
         Direction â˜ƒxx = â˜ƒ.getValue(FACING);
         ItemStack â˜ƒxxx = â˜ƒ.getBook().copy();
         float â˜ƒxxxx = 0.25F * (float)â˜ƒxx.getStepX();
         float â˜ƒxxxxx = 0.25F * (float)â˜ƒxx.getStepZ();
         ItemEntity â˜ƒxxxxxx = new ItemEntity(
            â˜ƒ, (double)â˜ƒ.getX() + 0.5 + (double)â˜ƒxxxx, (double)(â˜ƒ.getY() + 1), (double)â˜ƒ.getZ() + 0.5 + (double)â˜ƒxxxxx, â˜ƒxxx
         );
         â˜ƒxxxxxx.setDefaultPickUpDelay();
         â˜ƒ.addFreshEntity(â˜ƒxxxxxx);
         â˜ƒ.clearContent();
      }
   }

   @Override
   public boolean isSignalSource(BlockState var1) {
      return true;
   }

   @Override
   public int getSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ.getValue(POWERED) ? 15 : 0;
   }

   @Override
   public int getDirectSignal(BlockState var1, BlockGetter var2, BlockPos var3, Direction var4) {
      return â˜ƒ == Direction.UP && â˜ƒ.getValue(POWERED) ? 15 : 0;
   }

   @Override
   public boolean hasAnalogOutputSignal(BlockState var1) {
      return true;
   }

   @Override
   public int getAnalogOutputSignal(BlockState var1, Level var2, BlockPos var3) {
      if (â˜ƒ.getValue(HAS_BOOK)) {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒ instanceof LecternBlockEntity) {
            return ((LecternBlockEntity)â˜ƒ).getRedstoneSignal();
         }
      }

      return 0;
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (â˜ƒ.getValue(HAS_BOOK)) {
         if (!â˜ƒ.isClientSide) {
            this.openScreen(â˜ƒ, â˜ƒ, â˜ƒ);
         }

         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
         return !â˜ƒ.isEmpty() && !â˜ƒ.is(ItemTags.LECTERN_BOOKS) ? InteractionResult.CONSUME : InteractionResult.PASS;
      }
   }

   @Nullable
   @Override
   public MenuProvider getMenuProvider(BlockState var1, Level var2, BlockPos var3) {
      return !â˜ƒ.getValue(HAS_BOOK) ? null : super.getMenuProvider(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   private void openScreen(Level var1, BlockPos var2, Player var3) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒ instanceof LecternBlockEntity) {
         â˜ƒ.openMenu((LecternBlockEntity)â˜ƒ);
         â˜ƒ.awardStat(Stats.INTERACT_WITH_LECTERN);
      }
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }
}
