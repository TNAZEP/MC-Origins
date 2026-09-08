package net.minecraft.world.item.enchantment;

import net.minecraft.util.random.WeightedEntry;

public class EnchantmentInstance extends WeightedEntry.IntrusiveBase {
   public final Enchantment enchantment;
   public final int level;

   public EnchantmentInstance(Enchantment var1, int var2) {
      super(â˜ƒ.getRarity().getWeight());
      this.enchantment = â˜ƒ;
      this.level = â˜ƒ;
   }
}
