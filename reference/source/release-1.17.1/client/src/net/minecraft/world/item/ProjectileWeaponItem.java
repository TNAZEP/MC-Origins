package net.minecraft.world.item;

import java.util.function.Predicate;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public abstract class ProjectileWeaponItem extends Item {
   public static final Predicate<ItemStack> ARROW_ONLY = var0 -> var0.is(ItemTags.ARROWS);
   public static final Predicate<ItemStack> ARROW_OR_FIREWORK = ARROW_ONLY.or(var0 -> var0.is(Items.FIREWORK_ROCKET));

   public ProjectileWeaponItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   public Predicate<ItemStack> getSupportedHeldProjectiles() {
      return this.getAllSupportedProjectiles();
   }

   public abstract Predicate<ItemStack> getAllSupportedProjectiles();

   public static ItemStack getHeldProjectile(LivingEntity var0, Predicate<ItemStack> var1) {
      if (â˜ƒ.test(â˜ƒ.getItemInHand(InteractionHand.OFF_HAND))) {
         return â˜ƒ.getItemInHand(InteractionHand.OFF_HAND);
      } else {
         return â˜ƒ.test(â˜ƒ.getItemInHand(InteractionHand.MAIN_HAND)) ? â˜ƒ.getItemInHand(InteractionHand.MAIN_HAND) : ItemStack.EMPTY;
      }
   }

   @Override
   public int getEnchantmentValue() {
      return 1;
   }

   public abstract int getDefaultProjectileRange();
}
