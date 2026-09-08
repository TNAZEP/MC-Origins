package net.minecraft.item;

public class ItemBook extends Item {
   public ItemBook(Item.Properties var1) {
      super(☃);
   }

   @Override
   public boolean func_77616_k(ItemStack var1) {
      return ☃.func_190916_E() == 1;
   }

   @Override
   public int func_77619_b() {
      return 1;
   }
}
