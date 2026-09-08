package net.minecraft.item;

public class ItemTiered extends Item {
   private final IItemTier field_200892_a;

   public ItemTiered(IItemTier var1, Item.Properties var2) {
      super(☃.func_200915_b(☃.func_200926_a()));
      this.field_200892_a = ☃;
   }

   public IItemTier func_200891_e() {
      return this.field_200892_a;
   }

   @Override
   public int func_77619_b() {
      return this.field_200892_a.func_200927_e();
   }

   @Override
   public boolean func_82789_a(ItemStack var1, ItemStack var2) {
      return this.field_200892_a.func_200924_f().test(☃) || super.func_82789_a(☃, ☃);
   }
}
