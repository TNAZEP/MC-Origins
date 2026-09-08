package net.minecraft.world.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class PotionItem extends Item {
   private static final int DRINK_DURATION = 32;

   public PotionItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public ItemStack getDefaultInstance() {
      return PotionUtils.setPotion(super.getDefaultInstance(), Potions.WATER);
   }

   @Override
   public ItemStack finishUsingItem(ItemStack var1, Level var2, LivingEntity var3) {
      Player â˜ƒ = â˜ƒ instanceof Player ? (Player)â˜ƒ : null;
      if (â˜ƒ instanceof ServerPlayer) {
         CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)â˜ƒ, â˜ƒ);
      }

      if (!â˜ƒ.isClientSide) {
         for(MobEffectInstance â˜ƒ : PotionUtils.getMobEffects(â˜ƒ)) {
            if (â˜ƒ.getEffect().isInstantenous()) {
               â˜ƒ.getEffect().applyInstantenousEffect(â˜ƒ, â˜ƒ, â˜ƒ, â˜ƒ.getAmplifier(), 1.0);
            } else {
               â˜ƒ.addEffect(new MobEffectInstance(â˜ƒ));
            }
         }
      }

      if (â˜ƒ != null) {
         â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
         if (!â˜ƒ.getAbilities().instabuild) {
            â˜ƒ.shrink(1);
         }
      }

      if (â˜ƒ == null || !â˜ƒ.getAbilities().instabuild) {
         if (â˜ƒ.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
         }

         if (â˜ƒ != null) {
            â˜ƒ.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
         }
      }

      â˜ƒ.gameEvent(â˜ƒ, GameEvent.DRINKING_FINISH, â˜ƒ.eyeBlockPosition());
      return â˜ƒ;
   }

   @Override
   public int getUseDuration(ItemStack var1) {
      return 32;
   }

   @Override
   public UseAnim getUseAnimation(ItemStack var1) {
      return UseAnim.DRINK;
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      return ItemUtils.startUsingInstantly(â˜ƒ, â˜ƒ, â˜ƒ);
   }

   @Override
   public String getDescriptionId(ItemStack var1) {
      return PotionUtils.getPotion(â˜ƒ).getName(this.getDescriptionId() + ".effect.");
   }

   @Override
   public void appendHoverText(ItemStack var1, @Nullable Level var2, List<Component> var3, TooltipFlag var4) {
      PotionUtils.addPotionTooltip(â˜ƒ, â˜ƒ, 1.0F);
   }

   @Override
   public boolean isFoil(ItemStack var1) {
      return super.isFoil(â˜ƒ) || !PotionUtils.getMobEffects(â˜ƒ).isEmpty();
   }

   @Override
   public void fillItemCategory(CreativeModeTab var1, NonNullList<ItemStack> var2) {
      if (this.allowdedIn(â˜ƒ)) {
         for(Potion â˜ƒ : Registry.POTION) {
            if (â˜ƒ != Potions.EMPTY) {
               â˜ƒ.add(PotionUtils.setPotion(new ItemStack(this), â˜ƒ));
            }
         }
      }
   }
}
