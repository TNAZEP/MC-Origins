package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class JukeboxBlock extends BaseEntityBlock {
   public static final BooleanProperty HAS_RECORD = BlockStateProperties.HAS_RECORD;

   protected JukeboxBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
      this.registerDefaultState(this.stateDefinition.any().setValue(HAS_RECORD, Boolean.valueOf(false)));
   }

   @Override
   public void setPlacedBy(Level var1, BlockPos var2, BlockState var3, @Nullable LivingEntity var4, ItemStack var5) {
      super.setPlacedBy(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      CompoundTag â˜ƒ = â˜ƒ.getOrCreateTag();
      if (â˜ƒ.contains("BlockEntityTag")) {
         CompoundTag â˜ƒx = â˜ƒ.getCompound("BlockEntityTag");
         if (â˜ƒx.contains("RecordItem")) {
            â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(HAS_RECORD, Boolean.valueOf(true)), 2);
         }
      }
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      if (â˜ƒ.getValue(HAS_RECORD)) {
         this.dropRecording(â˜ƒ, â˜ƒ);
         â˜ƒ = â˜ƒ.setValue(HAS_RECORD, Boolean.valueOf(false));
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ, 2);
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }

   public void setRecord(LevelAccessor var1, BlockPos var2, BlockState var3, ItemStack var4) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒ instanceof JukeboxBlockEntity) {
         ((JukeboxBlockEntity)â˜ƒ).setRecord(â˜ƒ.copy());
         â˜ƒ.setBlock(â˜ƒ, â˜ƒ.setValue(HAS_RECORD, Boolean.valueOf(true)), 2);
      }
   }

   private void dropRecording(Level var1, BlockPos var2) {
      if (!â˜ƒ.isClientSide) {
         BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
         if (â˜ƒ instanceof JukeboxBlockEntity) {
            JukeboxBlockEntity â˜ƒx = (JukeboxBlockEntity)â˜ƒ;
            ItemStack â˜ƒxx = â˜ƒx.getRecord();
            if (!â˜ƒxx.isEmpty()) {
               â˜ƒ.levelEvent(1010, â˜ƒ, 0);
               â˜ƒx.clearContent();
               float â˜ƒxxx = 0.7F;
               double â˜ƒxxxx = (double)(â˜ƒ.random.nextFloat() * 0.7F) + 0.15F;
               double â˜ƒxxxxx = (double)(â˜ƒ.random.nextFloat() * 0.7F) + 0.060000002F + 0.6;
               double â˜ƒxxxxxx = (double)(â˜ƒ.random.nextFloat() * 0.7F) + 0.15F;
               ItemStack â˜ƒxxxxxxx = â˜ƒxx.copy();
               ItemEntity â˜ƒxxxxxxxx = new ItemEntity(
                  â˜ƒ, (double)â˜ƒ.getX() + â˜ƒxxxx, (double)â˜ƒ.getY() + â˜ƒxxxxx, (double)â˜ƒ.getZ() + â˜ƒxxxxxx, â˜ƒxxxxxxx
               );
               â˜ƒxxxxxxxx.setDefaultPickUpDelay();
               â˜ƒ.addFreshEntity(â˜ƒxxxxxxxx);
            }
         }
      }
   }

   @Override
   public void onRemove(BlockState var1, Level var2, BlockPos var3, BlockState var4, boolean var5) {
      if (!â˜ƒ.is(â˜ƒ.getBlock())) {
         this.dropRecording(â˜ƒ, â˜ƒ);
         super.onRemove(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public BlockEntity newBlockEntity(BlockPos var1, BlockState var2) {
      return new JukeboxBlockEntity(â˜ƒ, â˜ƒ);
   }

   @Override
   public boolean hasAnalogOutputSignal(BlockState var1) {
      return true;
   }

   @Override
   public int getAnalogOutputSignal(BlockState var1, Level var2, BlockPos var3) {
      BlockEntity â˜ƒ = â˜ƒ.getBlockEntity(â˜ƒ);
      if (â˜ƒ instanceof JukeboxBlockEntity) {
         Item â˜ƒx = ((JukeboxBlockEntity)â˜ƒ).getRecord().getItem();
         if (â˜ƒx instanceof RecordItem) {
            return ((RecordItem)â˜ƒx).getAnalogOutput();
         }
      }

      return 0;
   }

   @Override
   public RenderShape getRenderShape(BlockState var1) {
      return RenderShape.MODEL;
   }

   @Override
   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> var1) {
      â˜ƒ.add(HAS_RECORD);
   }
}
