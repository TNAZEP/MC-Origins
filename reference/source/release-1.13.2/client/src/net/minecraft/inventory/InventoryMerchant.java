package net.minecraft.inventory;

import javax.annotation.Nullable;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class InventoryMerchant implements IInventory {
   private final IMerchant field_70476_a;
   private final NonNullList<ItemStack> field_70474_b = NonNullList.func_191197_a(3, ItemStack.field_190927_a);
   private final EntityPlayer field_70475_c;
   private MerchantRecipe field_70472_d;
   private int field_70473_e;

   public InventoryMerchant(EntityPlayer var1, IMerchant var2) {
      this.field_70475_c = ☃;
      this.field_70476_a = ☃;
   }

   @Override
   public int func_70302_i_() {
      return this.field_70474_b.size();
   }

   @Override
   public boolean func_191420_l() {
      for(ItemStack ☃ : this.field_70474_b) {
         if (!☃.func_190926_b()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack func_70301_a(int var1) {
      return this.field_70474_b.get(☃);
   }

   @Override
   public ItemStack func_70298_a(int var1, int var2) {
      ItemStack ☃ = this.field_70474_b.get(☃);
      if (☃ == 2 && !☃.func_190926_b()) {
         return ItemStackHelper.func_188382_a(this.field_70474_b, ☃, ☃.func_190916_E());
      } else {
         ItemStack ☃ = ItemStackHelper.func_188382_a(this.field_70474_b, ☃, ☃);
         if (!☃.func_190926_b() && this.func_70469_d(☃)) {
            this.func_70470_g();
         }

         return ☃;
      }
   }

   private boolean func_70469_d(int var1) {
      return ☃ == 0 || ☃ == 1;
   }

   @Override
   public ItemStack func_70304_b(int var1) {
      return ItemStackHelper.func_188383_a(this.field_70474_b, ☃);
   }

   @Override
   public void func_70299_a(int var1, ItemStack var2) {
      this.field_70474_b.set(☃, ☃);
      if (!☃.func_190926_b() && ☃.func_190916_E() > this.func_70297_j_()) {
         ☃.func_190920_e(this.func_70297_j_());
      }

      if (this.func_70469_d(☃)) {
         this.func_70470_g();
      }
   }

   @Override
   public ITextComponent func_200200_C_() {
      return new TextComponentTranslation("mob.villager");
   }

   @Override
   public boolean func_145818_k_() {
      return false;
   }

   @Nullable
   @Override
   public ITextComponent func_200201_e() {
      return null;
   }

   @Override
   public int func_70297_j_() {
      return 64;
   }

   @Override
   public boolean func_70300_a(EntityPlayer var1) {
      return this.field_70476_a.func_70931_l_() == ☃;
   }

   @Override
   public void func_174889_b(EntityPlayer var1) {
   }

   @Override
   public void func_174886_c(EntityPlayer var1) {
   }

   @Override
   public boolean func_94041_b(int var1, ItemStack var2) {
      return true;
   }

   @Override
   public void func_70296_d() {
      this.func_70470_g();
   }

   public void func_70470_g() {
      this.field_70472_d = null;
      ItemStack ☃ = this.field_70474_b.get(0);
      ItemStack ☃x = this.field_70474_b.get(1);
      if (☃.func_190926_b()) {
         ☃ = ☃x;
         ☃x = ItemStack.field_190927_a;
      }

      if (☃.func_190926_b()) {
         this.func_70299_a(2, ItemStack.field_190927_a);
      } else {
         MerchantRecipeList ☃ = this.field_70476_a.func_70934_b(this.field_70475_c);
         if (☃ != null) {
            MerchantRecipe ☃x = ☃.func_77203_a(☃, ☃x, this.field_70473_e);
            if (☃x != null && !☃x.func_82784_g()) {
               this.field_70472_d = ☃x;
               this.func_70299_a(2, ☃x.func_77397_d().func_77946_l());
            } else if (!☃x.func_190926_b()) {
               ☃x = ☃.func_77203_a(☃x, ☃, this.field_70473_e);
               if (☃x != null && !☃x.func_82784_g()) {
                  this.field_70472_d = ☃x;
                  this.func_70299_a(2, ☃x.func_77397_d().func_77946_l());
               } else {
                  this.func_70299_a(2, ItemStack.field_190927_a);
               }
            } else {
               this.func_70299_a(2, ItemStack.field_190927_a);
            }
         }

         this.field_70476_a.func_110297_a_(this.func_70301_a(2));
      }
   }

   public MerchantRecipe func_70468_h() {
      return this.field_70472_d;
   }

   public void func_70471_c(int var1) {
      this.field_70473_e = ☃;
      this.func_70470_g();
   }

   @Override
   public int func_174887_a_(int var1) {
      return 0;
   }

   @Override
   public void func_174885_b(int var1, int var2) {
   }

   @Override
   public int func_174890_g() {
      return 0;
   }

   @Override
   public void func_174888_l() {
      this.field_70474_b.clear();
   }
}
