package net.minecraft.world.item;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class BottleItem extends Item {
   public BottleItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      List<AreaEffectCloud> â˜ƒ = â˜ƒ.getEntitiesOfClass(
         AreaEffectCloud.class, â˜ƒ.getBoundingBox().inflate(2.0), var0 -> var0 != null && var0.isAlive() && var0.getOwner() instanceof EnderDragon
      );
      ItemStack â˜ƒx = â˜ƒ.getItemInHand(â˜ƒ);
      if (!â˜ƒ.isEmpty()) {
         AreaEffectCloud â˜ƒxx = (AreaEffectCloud)â˜ƒ.get(0);
         â˜ƒxx.setRadius(â˜ƒxx.getRadius() - 0.5F);
         â˜ƒ.playSound(null, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0F, 1.0F);
         â˜ƒ.gameEvent(â˜ƒ, GameEvent.FLUID_PICKUP, â˜ƒ.blockPosition());
         return InteractionResultHolder.sidedSuccess(this.turnBottleIntoItem(â˜ƒx, â˜ƒ, new ItemStack(Items.DRAGON_BREATH)), â˜ƒ.isClientSide());
      } else {
         HitResult â˜ƒ = getPlayerPOVHitResult(â˜ƒ, â˜ƒ, ClipContext.Fluid.SOURCE_ONLY);
         if (â˜ƒ.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(â˜ƒx);
         } else {
            if (â˜ƒ.getType() == HitResult.Type.BLOCK) {
               BlockPos â˜ƒ = ((BlockHitResult)â˜ƒ).getBlockPos();
               if (!â˜ƒ.mayInteract(â˜ƒ, â˜ƒ)) {
                  return InteractionResultHolder.pass(â˜ƒx);
               }

               if (â˜ƒ.getFluidState(â˜ƒ).is(FluidTags.WATER)) {
                  â˜ƒ.playSound(â˜ƒ, â˜ƒ.getX(), â˜ƒ.getY(), â˜ƒ.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
                  â˜ƒ.gameEvent(â˜ƒ, GameEvent.FLUID_PICKUP, â˜ƒ);
                  return InteractionResultHolder.sidedSuccess(
                     this.turnBottleIntoItem(â˜ƒx, â˜ƒ, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)), â˜ƒ.isClientSide()
                  );
               }
            }

            return InteractionResultHolder.pass(â˜ƒx);
         }
      }
   }

   protected ItemStack turnBottleIntoItem(ItemStack var1, Player var2, ItemStack var3) {
      â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
      return ItemUtils.createFilledResult(â˜ƒ, â˜ƒ, â˜ƒ);
   }
}
