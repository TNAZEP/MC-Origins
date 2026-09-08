package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class PumpkinBlock extends StemGrownBlock {
   protected PumpkinBlock(BlockBehaviour.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResult use(BlockState var1, Level var2, BlockPos var3, Player var4, InteractionHand var5, BlockHitResult var6) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      if (â˜ƒ.is(Items.SHEARS)) {
         if (!â˜ƒ.isClientSide) {
            Direction â˜ƒx = â˜ƒ.getDirection();
            Direction â˜ƒxx = â˜ƒx.getAxis() == Direction.Axis.Y ? â˜ƒ.getDirection().getOpposite() : â˜ƒx;
            â˜ƒ.playSound(null, â˜ƒ, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
            â˜ƒ.setBlock(â˜ƒ, Blocks.CARVED_PUMPKIN.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, â˜ƒxx), 11);
            ItemEntity â˜ƒxxx = new ItemEntity(
               â˜ƒ,
               (double)â˜ƒ.getX() + 0.5 + (double)â˜ƒxx.getStepX() * 0.65,
               (double)â˜ƒ.getY() + 0.1,
               (double)â˜ƒ.getZ() + 0.5 + (double)â˜ƒxx.getStepZ() * 0.65,
               new ItemStack(Items.PUMPKIN_SEEDS, 4)
            );
            â˜ƒxxx.setDeltaMovement(
               0.05 * (double)â˜ƒxx.getStepX() + â˜ƒ.random.nextDouble() * 0.02, 0.05, 0.05 * (double)â˜ƒxx.getStepZ() + â˜ƒ.random.nextDouble() * 0.02
            );
            â˜ƒ.addFreshEntity(â˜ƒxxx);
            â˜ƒ.hurtAndBreak(1, â˜ƒ, var1x -> var1x.broadcastBreakEvent(â˜ƒ));
            â˜ƒ.gameEvent(â˜ƒ, GameEvent.SHEAR, â˜ƒ);
            â˜ƒ.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
         }

         return InteractionResult.sidedSuccess(â˜ƒ.isClientSide);
      } else {
         return super.use(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ);
      }
   }

   @Override
   public StemBlock getStem() {
      return (StemBlock)Blocks.PUMPKIN_STEM;
   }

   @Override
   public AttachedStemBlock getAttachedStem() {
      return (AttachedStemBlock)Blocks.ATTACHED_PUMPKIN_STEM;
   }
}
