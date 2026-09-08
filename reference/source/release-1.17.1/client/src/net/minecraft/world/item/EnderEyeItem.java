package net.minecraft.world.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.EyeOfEnder;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class EnderEyeItem extends Item {
   public EnderEyeItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResult useOn(UseOnContext var1) {
      Level â˜ƒ = â˜ƒ.getLevel();
      BlockPos â˜ƒx = â˜ƒ.getClickedPos();
      BlockState â˜ƒxx = â˜ƒ.getBlockState(â˜ƒx);
      if (!â˜ƒxx.is(Blocks.END_PORTAL_FRAME) || â˜ƒxx.getValue(EndPortalFrameBlock.HAS_EYE)) {
         return InteractionResult.PASS;
      } else if (â˜ƒ.isClientSide) {
         return InteractionResult.SUCCESS;
      } else {
         BlockState â˜ƒ = â˜ƒxx.setValue(EndPortalFrameBlock.HAS_EYE, Boolean.valueOf(true));
         Block.pushEntitiesUp(â˜ƒxx, â˜ƒ, â˜ƒ, â˜ƒx);
         â˜ƒ.setBlock(â˜ƒx, â˜ƒ, 2);
         â˜ƒ.updateNeighbourForOutputSignal(â˜ƒx, Blocks.END_PORTAL_FRAME);
         â˜ƒ.getItemInHand().shrink(1);
         â˜ƒ.levelEvent(1503, â˜ƒx, 0);
         BlockPattern.BlockPatternMatch â˜ƒx = EndPortalFrameBlock.getOrCreatePortalShape().find(â˜ƒ, â˜ƒx);
         if (â˜ƒx != null) {
            BlockPos â˜ƒxx = â˜ƒx.getFrontTopLeft().offset(-3, 0, -3);

            for(int â˜ƒxxx = 0; â˜ƒxxx < 3; ++â˜ƒxxx) {
               for(int â˜ƒxxxx = 0; â˜ƒxxxx < 3; ++â˜ƒxxxx) {
                  â˜ƒ.setBlock(â˜ƒxx.offset(â˜ƒxxx, 0, â˜ƒxxxx), Blocks.END_PORTAL.defaultBlockState(), 2);
               }
            }

            â˜ƒ.globalLevelEvent(1038, â˜ƒxx.offset(1, 0, 1), 0);
         }

         return InteractionResult.CONSUME;
      }
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      HitResult â˜ƒx = getPlayerPOVHitResult(â˜ƒ, â˜ƒ, ClipContext.Fluid.NONE);
      if (â˜ƒx.getType() == HitResult.Type.BLOCK && â˜ƒ.getBlockState(((BlockHitResult)â˜ƒx).getBlockPos()).is(Blocks.END_PORTAL_FRAME)) {
         return InteractionResultHolder.pass(â˜ƒ);
      } else {
         â˜ƒ.startUsingItem(â˜ƒ);
         if (â˜ƒ instanceof ServerLevel) {
            BlockPos â˜ƒ = ((ServerLevel)â˜ƒ)
               .getChunkSource()
               .getGenerator()
               .findNearestMapFeature((ServerLevel)â˜ƒ, StructureFeature.STRONGHOLD, â˜ƒ.blockPosition(), 100, false);
            if (â˜ƒ != null) {
               EyeOfEnder â˜ƒx = new EyeOfEnder(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(0.5), â˜ƒ.getZ());
               â˜ƒx.setItem(â˜ƒ);
               â˜ƒx.signalTo(â˜ƒ);
               â˜ƒ.addFreshEntity(â˜ƒx);
               if (â˜ƒ instanceof ServerPlayer) {
                  CriteriaTriggers.USED_ENDER_EYE.trigger((ServerPlayer)â˜ƒ, â˜ƒ);
               }

               â˜ƒ.playSound(
                  null,
                  â˜ƒ.getX(),
                  â˜ƒ.getY(),
                  â˜ƒ.getZ(),
                  SoundEvents.ENDER_EYE_LAUNCH,
                  SoundSource.NEUTRAL,
                  0.5F,
                  0.4F / (â˜ƒ.getRandom().nextFloat() * 0.4F + 0.8F)
               );
               â˜ƒ.levelEvent(null, 1003, â˜ƒ.blockPosition(), 0);
               if (!â˜ƒ.getAbilities().instabuild) {
                  â˜ƒ.shrink(1);
               }

               â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
               â˜ƒ.swing(â˜ƒ, true);
               return InteractionResultHolder.success(â˜ƒ);
            }
         }

         return InteractionResultHolder.consume(â˜ƒ);
      }
   }
}
