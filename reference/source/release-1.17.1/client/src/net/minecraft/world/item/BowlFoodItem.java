package net.minecraft.world.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BowlFoodItem extends Item {
   public BowlFoodItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public ItemStack finishUsingItem(ItemStack var1, Level var2, LivingEntity var3) {
      ItemStack â˜ƒ = super.finishUsingItem(â˜ƒ, â˜ƒ, â˜ƒ);
      return â˜ƒ instanceof Player && ((Player)â˜ƒ).getAbilities().instabuild ? â˜ƒ : new ItemStack(Items.BOWL);
   }
}
