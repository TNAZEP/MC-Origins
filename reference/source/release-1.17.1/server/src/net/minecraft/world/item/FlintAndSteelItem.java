package net.minecraft.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;

public class FlintAndSteelItem extends Item {
   public FlintAndSteelItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      Player â˜ƒ = â˜ƒ.getPlayer();
      Level â˜ƒx = â˜ƒ.getLevel();
      BlockPos â˜ƒxx = â˜ƒ.getClickedPos();
      BlockState â˜ƒxxx = â˜ƒx.getBlockState(â˜ƒxx);
      if (!CampfireBlock.canLight(â˜ƒxxx) && !CandleBlock.canLight(â˜ƒxxx) && !CandleCakeBlock.canLight(â˜ƒxxx)) {
         BlockPos â˜ƒxxxx = â˜ƒxx.relative(â˜ƒ.getClickedFace());
         if (BaseFireBlock.canBePlacedAt(â˜ƒx, â˜ƒxxxx, â˜ƒ.getHorizontalDirection())) {
            â˜ƒx.playSound(â˜ƒ, â˜ƒxxxx, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, â˜ƒx.getRandom().nextFloat() * 0.4F + 0.8F);
            BlockState â˜ƒxxxxx = BaseFireBlock.getState(â˜ƒx, â˜ƒxxxx);
            â˜ƒx.setBlock(â˜ƒxxxx, â˜ƒxxxxx, 11);
            â˜ƒx.gameEvent(â˜ƒ, GameEvent.BLOCK_PLACE, â˜ƒxx);
            ItemStack â˜ƒxxxxxx = â˜ƒ.getItemInHand();
            if (â˜ƒ instanceof ServerPlayer) {
               CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)â˜ƒ, â˜ƒxxxx, â˜ƒxxxxxx);
               â˜ƒxxxxxx.hurtAndBreak(1, â˜ƒ, var1x -> var1x.broadcastBreakEvent(â˜ƒ.getHand()));
            }

            return InteractionResult.sidedSuccess(â˜ƒx.isClientSide());
         } else {
            return InteractionResult.FAIL;
         }
      } else {
         â˜ƒx.playSound(â˜ƒ, â˜ƒxx, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, â˜ƒx.getRandom().nextFloat() * 0.4F + 0.8F);
         â˜ƒx.setBlock(â˜ƒxx, â˜ƒxxx.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
         â˜ƒx.gameEvent(â˜ƒ, GameEvent.BLOCK_PLACE, â˜ƒxx);
         if (â˜ƒ != null) {
            â˜ƒ.getItemInHand().hurtAndBreak(1, â˜ƒ, var1x -> var1x.broadcastBreakEvent(â˜ƒ.getHand()));
         }

         return InteractionResult.sidedSuccess(â˜ƒx.isClientSide());
      }
   }
}
