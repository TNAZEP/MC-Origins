package net.minecraft.world.level.block;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.BlockSourceImpl;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.PositionImpl;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.DropperBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class DispenserBlock extends BaseEntityBlock {
   public static final DirectionProperty FACING = DirectionalBlock.FACING;
   public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
   private static final Map<Item, DispenseItemBehavior> DISPENSER_REGISTRY = Util.make(
      new Object2ObjectOpenHashMap<>(), var0 -> var0.defaultReturnValue(new DefaultDispenseItemBehavior())
   );
   private static final int TRIGGER_DURATION = 4;

   public static void registerBehavior(ItemLike var0, DispenseItemBehavior var1) {
      DISPENSER_REGISTRY.put(â˜ƒ.asItem(), â˜ƒ);
   }

   protected DispenserBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(TRIGGERED, Boolean.valueOf(false)));
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (â˜ƒ.isClientSide) {
         return InteractionResult.SUCCESS;
      } else {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒ instanceof DispenserBlockEntity) {
            â˜ƒ.openMenu((DispenserBlockEntity)â˜ƒ);
            if (â˜ƒ instanceof DropperBlockEntity) {
               â˜ƒ.awardStat(Stats.INSPECT_DROPPER);
            } else {
               â˜ƒ.awardStat(Stats.INSPECT_DISPENSER);
            }
         }

         return InteractionResult.CONSUME;
      }
   }

   protected void dispenseFrom(ServerLevel var1, BlockPos var2) {
      BlockSourceImpl â˜ƒ = new BlockSourceImpl(â˜ƒ, â˜ƒ);
      DispenserBlockEntity â˜ƒx = â˜ƒ.getEntity();
      int â˜ƒxx = â˜ƒx.getRandomSlot();
      if (â˜ƒxx < 0) {
         â˜ƒ.levelEvent(1001, â˜ƒ, 0);
         â˜ƒ.gameEvent(GameEvent.DISPENSE_FAIL, â˜ƒ);
      } else {
         ItemStack â˜ƒ = â˜ƒx.getItem(â˜ƒxx);
         DispenseItemBehavior â˜ƒx = this.getDispenseMethod(â˜ƒ);
         if (â˜ƒx != DispenseItemBehavior.NOOP) {
            â˜ƒx.setItem(â˜ƒxx, â˜ƒx.dispense(â˜ƒ, â˜ƒ));
         }
      }
   }

   protected DispenseItemBehavior getDispenseMethod(ItemStack var1) {
      return (DispenseItemBehavior)DISPENSER_REGISTRY.get(â˜ƒ.getItem());
   }

   @Override
   public void neighborChanged(BlockState var1, Level var2, BlockPos var3, Block var4, BlockPos var5, boolean var6) {
      boolean â˜ƒ = â˜ƒ.hasNeighborSignal(â˜ƒ) || â˜ƒ.hasNeighborSignal(â˜ƒ.above());
      boolean â˜ƒx = â˜ƒ.getValue(TRIGGERED);
      if (â˜ƒ && !â˜ƒx) {
         â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 4);
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(TRIGGERED, Boolean.valueOf(true)), 4);
      } else if (!â˜ƒ && â˜ƒx) {
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(TRIGGERED, Boolean.valueOf(false)), 4);
      }
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      this.dispenseFrom(â˜ƒ, â˜ƒ);
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new DispenserBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext var1) {
      return this.defaultBlockState().setValue(FACING, â˜ƒ.getNearestLookingDirection().getOpposite());
   }

   @Override
   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, LivingEntity var4, ItemStack var5) {
      if (â˜ƒ.hasCustomHoverName()) {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒ instanceof DispenserBlockEntity) {
            ((DispenserBlockEntity)â˜ƒ).setCustomName(â˜ƒ.getHoverName());
         }
      }
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒ instanceof DispenserBlockEntity) {
            Containers.dropContents(â˜ƒ, â˜ƒ, (DispenserBlockEntity)â˜ƒ);
            â˜ƒ.updateNeighbourForOutputSignal(â˜ƒ, this);
         }

         super.onRemove(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   public static Position getDispensePosition(BlockSource var0) {
      Direction â˜ƒ = â˜ƒ.getBlockState().getValue(FACING);
      double â˜ƒx = â˜ƒ.x() + 0.7 * (double)â˜ƒ.getStepX();
      double â˜ƒxx = â˜ƒ.y() + 0.7 * (double)â˜ƒ.getStepY();
      double â˜ƒxxx = â˜ƒ.z() + 0.7 * (double)â˜ƒ.getStepZ();
      return new PositionImpl(â˜ƒx, â˜ƒxx, â˜ƒxxx);
   }

   @Override
   public boolean hasAnalogOutputSignal(BlockState var1) {
      return true;
   }

   @Override
   public int getAnalogOutputSignal(BlockState var1, Level var2, BlockPos var3) {
      return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(â˜ƒ.getBlockEntity(â˜ƒ));
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.MODEL;
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
      â˜ƒ.add(FACING, TRIGGERED);
   }
}
