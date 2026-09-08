package net.minecraft.world.level.block.piston;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MovingPistonBlock extends BaseEntityBlock {
   public static final DirectionProperty FACING = PistonHeadBlock.FACING;
   public static final EnumProperty<PistonType> TYPE = PistonHeadBlock.TYPE;

   public MovingPistonBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TYPE, PistonType.DEFAULT));
   }

   @Nullable
   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return null;
   }

   public static BlockEntity newMovingBlockEntity(BlockPos var0, BlockState var1, BlockState var2, Direction var3, boolean var4, boolean var5) {
      return new PistonMovingBlockEntity(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Nullable
   @Override
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level var1, BlockState var2, BlockEntityType<T> var3) {
      return createTickerHelper(â˜ƒ, BlockEntityType.PISTON, PistonMovingBlockEntity::tick);
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒ instanceof PistonMovingBlockEntity) {
            ((PistonMovingBlockEntity)â˜ƒ).finalTick();
         }
      }
   }

   @Override
   public void destroy(LevelAccessor var1, BlockPos var2, BlockState var3) {
      BlockPos â˜ƒ = â˜ƒ.relative(((Direction)â˜ƒ.getValue(FACING)).getOpposite());
      BlockState â˜ƒx = â˜ƒ.getBlockState(â˜ƒ);
      if (â˜ƒx.getBlock() instanceof PistonBaseBlock && â˜ƒx.getValue(PistonBaseBlock.EXTENDED)) {
         â˜ƒ.removeBlock(â˜ƒ, false);
      }
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (!â˜ƒ.isClientSide && â˜ƒ.getBlockEntity(â˜ƒ) == null) {
         â˜ƒ.removeBlock(â˜ƒ, false);
         return InteractionResult.CONSUME;
      } else {
         return InteractionResult.PASS;
      }
   }

   @Override
   public List<ItemStack> getDrops(BlockState var1, LootContext.Builder var2) {
      PistonMovingBlockEntity â˜ƒ = this.getBlockEntity(â˜ƒ.getLevel(), new BlockPos(â˜ƒ.getParameter(LootContextParams.ORIGIN)));
      return â˜ƒ == null ? Collections.emptyList() : â˜ƒ.getMovedState().getDrops(â˜ƒ);
   }

   @Override
   public VoxelShape getShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      return Shapes.empty();
   }

   @Override
   public VoxelShape getCollisionShape(BlockState var1, BlockGetter var2, BlockPos var3, CollisionContext var4) {
      PistonMovingBlockEntity â˜ƒ = this.getBlockEntity(â˜ƒ, â˜ƒ);
      return â˜ƒ != null ? â˜ƒ.getCollisionShape(â˜ƒ, â˜ƒ) : Shapes.empty();
   }

   @Nullable
   private PistonMovingBlockEntity getBlockEntity(BlockGetter var1, BlockPos var2) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      return â˜ƒ instanceof PistonMovingBlockEntity ? (PistonMovingBlockEntity)â˜ƒ : null;
   }

   @Override
   public ItemStack getCloneItemStack(BlockGetter var1, BlockPos var2, BlockState var3) {
      return ItemStack.EMPTY;
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
      â˜ƒ.add(FACING, TYPE);
   }

   @Override
   public boolean isPathfindable(BlockState var1, BlockGetter var2, BlockPos var3, PathComputationType var4) {
      return false;
   }
}
