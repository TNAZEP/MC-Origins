package net.minecraft.world.item;

public class TieredItem extends Item {
   private final Tier tier;

   public TieredItem(Tier var1, Item.Properties var2) {
      super(â˜ƒ.defaultDurability(â˜ƒ.getUses()));
      this.tier = â˜ƒ;
   }

   public Tier getTier() {
      return this.tier;
   }

   @Override
   public int getEnchantmentValue() {
      return this.tier.getEnchantmentValue();
   }

   @Override
   public boolean isValidRepairItem(ItemStack var1, ItemStack var2) {
      return this.tier.getRepairIngredient().test(â˜ƒ) || super.isValidRepairItem(â˜ƒ, â˜ƒ);
   }
}
