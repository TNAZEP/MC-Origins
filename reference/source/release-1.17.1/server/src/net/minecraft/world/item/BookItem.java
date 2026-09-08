package net.minecraft.world.item;

public class BookItem extends Item {
   public BookItem(Item.Properties var1) {
      super(â˜ƒ);
   }

   @Override
   public boolean isEnchantable(ItemStack var1) {
      return â˜ƒ.getCount() == 1;
   }

   @Override
   public int getEnchantmentValue() {
      return 1;
   }
}
