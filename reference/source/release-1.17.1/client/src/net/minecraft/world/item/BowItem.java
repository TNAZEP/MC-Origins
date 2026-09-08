package net.minecraft.world.item;

import java.util.function.Predicate;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class BowItem extends ProjectileWeaponItem implements Vanishable {
   public static final int MAX_DRAW_DURATION = 20;
   public static final int DEFAULT_RANGE = 15;

   public BowItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public void releaseUsing(ItemStack var1, Level var2, LivingEntity var3, int var4) {
      if (â˜ƒ instanceof Player) {
         Player â˜ƒ = (Player)â˜ƒ;
         boolean â˜ƒx = â˜ƒ.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, â˜ƒ) > 0;
         ItemStack â˜ƒxx = â˜ƒ.getProjectile(â˜ƒ);
         if (!â˜ƒxx.isEmpty() || â˜ƒx) {
            if (â˜ƒxx.isEmpty()) {
               â˜ƒxx = new ItemStack(Items.ARROW);
            }

            int â˜ƒxxx = this.getUseDuration(â˜ƒ) - â˜ƒ;
            float â˜ƒxxxx = getPowerForTime(â˜ƒxxx);
            if (!((double)â˜ƒxxxx < 0.1)) {
               boolean â˜ƒxxxxx = â˜ƒx && â˜ƒxx.is(Items.ARROW);
               if (!â˜ƒ.isClientSide) {
                  ArrowItem â˜ƒxxxxxx = (ArrowItem)(â˜ƒxx.getItem() instanceof ArrowItem ? â˜ƒxx.getItem() : Items.ARROW);
                  AbstractArrow â˜ƒxxxxxxx = â˜ƒxxxxxx.createArrow(â˜ƒ, â˜ƒxx, â˜ƒ);
                  â˜ƒxxxxxxx.shootFromRotation(â˜ƒ, â˜ƒ.getXRot(), â˜ƒ.getYRot(), 0.0F, â˜ƒxxxx * 3.0F, 1.0F);
                  if (â˜ƒxxxx == 1.0F) {
                     â˜ƒxxxxxxx.setCritArrow(true);
                  }

                  int â˜ƒxxxxxx = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, â˜ƒ);
                  if (â˜ƒxxxxxx > 0) {
                     â˜ƒxxxxxxx.setBaseDamage(â˜ƒxxxxxxx.getBaseDamage() + (double)â˜ƒxxxxxx * 0.5 + 0.5);
                  }

                  int â˜ƒxxxxxx = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, â˜ƒ);
                  if (â˜ƒxxxxxx > 0) {
                     â˜ƒxxxxxxx.setKnockback(â˜ƒxxxxxx);
                  }

                  if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, â˜ƒ) > 0) {
                     â˜ƒxxxxxxx.setSecondsOnFire(100);
                  }

                  â˜ƒ.hurtAndBreak(1, â˜ƒ, var1x -> var1x.broadcastBreakEvent(â˜ƒ.getUsedItemHand()));
                  if (â˜ƒxxxxx || â˜ƒ.getAbilities().instabuild && (â˜ƒxx.is(Items.SPECTRAL_ARROW) || â˜ƒxx.is(Items.TIPPED_ARROW))) {
                     â˜ƒxxxxxxx.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                  }

                  â˜ƒ.addFreshEntity(â˜ƒxxxxxxx);
               }

               â˜ƒ.playSound(
                  null,
                  â˜ƒ.getX(),
                  â˜ƒ.getY(),
                  â˜ƒ.getZ(),
                  SoundEvents.ARROW_SHOOT,
                  SoundSource.PLAYERS,
                  1.0F,
                  1.0F / (â˜ƒ.getRandom().nextFloat() * 0.4F + 1.2F) + â˜ƒxxxx * 0.5F
               );
               if (!â˜ƒxxxxx && !â˜ƒ.getAbilities().instabuild) {
                  â˜ƒxx.shrink(1);
                  if (â˜ƒxx.isEmpty()) {
                     â˜ƒ.getInventory().removeItem(â˜ƒxx);
                  }
               }

               â˜ƒ.awardStat(Stats.ITEM_USED.get(this));
            }
         }
      }
   }

   public static float getPowerForTime(int var0) {
      float â˜ƒ = (float)â˜ƒ / 20.0F;
      â˜ƒ = (â˜ƒ * â˜ƒ + â˜ƒ * 2.0F) / 3.0F;
      if (â˜ƒ > 1.0F) {
         â˜ƒ = 1.0F;
      }

      return â˜ƒ;
   }

   @Override
   public int getUseDuration(ItemStack var1) {
      return 72000;
   }

   @Override
   public UseAnim getUseAnimation(ItemStack var1) {
      return UseAnim.BOW;
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level var1, Player var2, InteractionHand var3) {
      ItemStack â˜ƒ = â˜ƒ.getItemInHand(â˜ƒ);
      boolean â˜ƒx = !â˜ƒ.getProjectile(â˜ƒ).isEmpty();
      if (!â˜ƒ.getAbilities().instabuild && !â˜ƒx) {
         return InteractionResultHolder.fail(â˜ƒ);
      } else {
         â˜ƒ.startUsingItem(â˜ƒ);
         return InteractionResultHolder.consume(â˜ƒ);
      }
   }

   @Override
   public Predicate<ItemStack> getAllSupportedProjectiles() {
      return ARROW_ONLY;
   }

   @Override
   public int getDefaultProjectileRange() {
      return 15;
   }
}
