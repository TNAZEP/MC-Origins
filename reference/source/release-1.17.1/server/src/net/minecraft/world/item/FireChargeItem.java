package net.minecraft.world.item;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;

public class FireChargeItem extends Item {
   public FireChargeItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      boolean â˜ƒxxx = false;
      if (!CampfireBlock.canLight(â˜ƒxx) && !CandleBlock.canLight(â˜ƒxx) && !CandleCakeBlock.canLight(â˜ƒxx)) {
         â˜ƒx = â˜ƒx.relative(â˜ƒ.getClickedFace());
         if (BaseFireBlock.canBePlacedAt(â˜ƒ, â˜ƒx, â˜ƒ.getHorizontalDirection())) {
            this.playSound(â˜ƒ, â˜ƒx);
            â˜ƒ.setBlockAndUpdate(â˜ƒx, BaseFireBlock.getState(â˜ƒ, â˜ƒx));
            â˜ƒ.gameEvent(â˜ƒ.getPlayer(), GameEvent.BLOCK_PLACE, â˜ƒx);
            â˜ƒxxx = true;
         }
      } else {
         this.playSound(â˜ƒ, â˜ƒx);
         â˜ƒ.setBlockAndUpdate(â˜ƒx, â˜ƒxx.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)));
         â˜ƒ.gameEvent(â˜ƒ.getPlayer(), GameEvent.BLOCK_PLACE, â˜ƒx);
         â˜ƒxxx = true;
      }

      if (â˜ƒxxx) {
         â˜ƒ.getItemInHand().shrink(1);
         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         return InteractionResult.FAIL;
      }
   }

   private void playSound(Level var1, BlockPos var2) {
      Random â˜ƒ = â˜ƒ.getRandom();
      â˜ƒ.playSound(null, â˜ƒ, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, (â˜ƒ.nextFloat() - â˜ƒ.nextFloat()) * 0.2F + 1.0F);
   }
}
