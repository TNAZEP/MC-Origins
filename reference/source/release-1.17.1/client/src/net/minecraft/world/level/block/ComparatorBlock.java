package net.minecraft.world.level.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.TickPriority;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ComparatorBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ComparatorMode;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

public class ComparatorBlock extends DiodeBlock implements EntityBlock {
   public static final EnumProperty<ComparatorMode> MODE = BlockStateProperties.MODE_COMPARATOR;

   public ComparatorBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(
         this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(POWERED, Boolean.valueOf(false)).setValue(MODE, ComparatorMode.COMPARE)
      );
   }

   @Override
   protected int getDelay(BlockState var1) {
      return 2;
   }

   @Override
   protected int getOutputSignal(BlockGetter var1, BlockPos var2, BlockState var3) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      return â˜ƒ instanceof ComparatorBlockEntity ? ((ComparatorBlockEntity)â˜ƒ).getOutputSignal() : 0;
   }

   private int calculateOutputSignal(Level var1, BlockPos var2, BlockState var3) {
      int â˜ƒ = this.getInputSignal(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ == 0) {
         return 0;
      } else {
         int â˜ƒ = this.getAlternateSignal(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ > â˜ƒ) {
            return 0;
         } else {
            return â˜ƒ.getValue(MODE) == ComparatorMode.SUBTRACT ? â˜ƒ - â˜ƒ : â˜ƒ;
         }
      }
   }

   @Override
   protected boolean shouldTurnOn(Level var1, BlockPos var2, BlockState var3) {
      int â˜ƒ = this.getInputSignal(â˜ƒ, â˜ƒ, â˜ƒ);
      if (â˜ƒ == 0) {
         return false;
      } else {
         int â˜ƒ = this.getAlternateSignal(â˜ƒ, â˜ƒ, â˜ƒ);
         if (â˜ƒ > â˜ƒ) {
            return true;
         } else {
            return â˜ƒ == â˜ƒ && â˜ƒ.getValue(MODE) == ComparatorMode.COMPARE;
         }
      }
   }

   @Override
   protected int getInputSignal(Level var1, BlockPos var2, BlockState var3) {
      int â˜ƒ = super.getInputSignal(â˜ƒ, â˜ƒ, â˜ƒ);
      Direction â˜ƒx = â˜ƒ.getValue(FACING);
      BlockPos â˜ƒxx = â˜ƒ.relative(â˜ƒx);
      BlockState â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒxx);
      if (â˜ƒxxx.hasAnalogOutputSignal()) {
         â˜ƒ = â˜ƒxxx.getAnalogOutputSignal(â˜ƒ, â˜ƒxx);
      } else if (â˜ƒ < 15 && â˜ƒxxx.isRedstoneConductor(â˜ƒ, â˜ƒxx)) {
         â˜ƒxx = â˜ƒxx.relative(â˜ƒx);
         â˜ƒxxx = â˜ƒ.getBlockState(â˜ƒxx);
         ItemFrame â˜ƒ = this.getItemFrame(â˜ƒ, â˜ƒx, â˜ƒxx);
         int â˜ƒx = Math.max(
            â˜ƒ == null ? Integer.MIN_VALUE : â˜ƒ.getAnalogOutput(),
            â˜ƒxxx.hasAnalogOutputSignal() ? â˜ƒxxx.getAnalogOutputSignal(â˜ƒ, â˜ƒxx) : Integer.MIN_VALUE
         );
         if (â˜ƒx != Integer.MIN_VALUE) {
            â˜ƒ = â˜ƒx;
         }
      }

      return â˜ƒ;
   }

   @Nullable
   private ItemFrame getItemFrame(Level var1, Direction var2, BlockPos var3) {
      List<ItemFrame> â˜ƒ = â˜ƒ.getEntitiesOfClass(
         ItemFrame.class,
         new AABB((double)â˜ƒ.getX(), (double)â˜ƒ.getY(), (double)â˜ƒ.getZ(), (double)(â˜ƒ.getX() + 1), (double)(â˜ƒ.getY() + 1), (double)(â˜ƒ.getZ() + 1)),
         var1x -> var1x != null && var1x.getDirection() == â˜ƒ
      );
      return â˜ƒ.size() == 1 ? (ItemFrame)â˜ƒ.get(0) : null;
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (!â˜ƒ.getAbilities().mayBuild) {
         return InteractionResult.PASS;
      } else {
         â˜ƒ = â˜ƒ.cycle(MODE);
         float â˜ƒ = â˜ƒ.getValue(MODE) == ComparatorMode.SUBTRACT ? 0.55F : 0.5F;
         â˜ƒ.playSound(â˜ƒ, â˜ƒ, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, â˜ƒ);
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 2);
         this.refreshOutputState(â˜ƒ, â˜ƒ, â˜ƒ);
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      }
   }

   @Override
   protected void checkTickOnNeighbor(Level var1, BlockPos var2, BlockState var3) {
      if (!â˜ƒ.getBlockTicks().willTickThisTick(â˜ƒ, this)) {
         int â˜ƒ = this.calculateOutputSignal(â˜ƒ, â˜ƒ, â˜ƒ);
         BlockEntity â˜ƒx = â˜ƒ.getBlockEntity(â˜ƒ);
         int â˜ƒxx = â˜ƒx instanceof ComparatorBlockEntity ? ((ComparatorBlockEntity)â˜ƒx).getOutputSignal() : 0;
         if (â˜ƒ != â˜ƒxx || â˜ƒ.getValue(POWERED) != this.shouldTurnOn(â˜ƒ, â˜ƒ, â˜ƒ)) {
            TickPriority â˜ƒxxx = this.shouldPrioritize(â˜ƒ, â˜ƒ, â˜ƒ) ? TickPriority.HIGH : TickPriority.NORMAL;
            â˜ƒ.getBlockTicks().scheduleTick(â˜ƒ, this, 2, â˜ƒxxx);
         }
      }
   }

   private void refreshOutputState(Level var1, BlockPos var2, BlockState var3) {
      int â˜ƒx = this.calculateOutputSignal(â˜ƒ, â˜ƒ, â˜ƒ);
      BlockEntity â˜ƒxx = â˜ƒ.getBlockEntity(â˜ƒ);
      int â˜ƒxxx = 0;
      if (â˜ƒxx instanceof ComparatorBlockEntity â˜ƒ) {
         â˜ƒxxx = â˜ƒ.getOutputSignal();
         â˜ƒ.setOutputSignal(â˜ƒx);
      }

      if (â˜ƒxxx != â˜ƒx || â˜ƒ.getValue(MODE) == ComparatorMode.COMPARE) {
         boolean â˜ƒ = this.shouldTurnOn(â˜ƒ, â˜ƒ, â˜ƒ);
         boolean â˜ƒx = â˜ƒ.getValue(POWERED);
         if (â˜ƒx && !â˜ƒ) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(false)), 2);
         } else if (!â˜ƒx && â˜ƒ) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(POWERED, Boolean.valueOf(true)), 2);
         }

         this.updateNeighborsInFront(â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public void tick(BlockState var1, ServerLevel var2, BlockPos var3, Random var4) {
      this.refreshOutputState(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean triggerEvent(BlockState var1, Level var2, BlockPos var3, int var4, int var5) {
      super.triggerEvent(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      return â˜ƒ != null && â˜ƒ.triggerEvent(â˜ƒ, â˜ƒ);
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new ComparatorBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(FACING, MODE, POWERED);
   }
}
