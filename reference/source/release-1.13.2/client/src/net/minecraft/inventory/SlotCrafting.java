package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class SlotCrafting extends Slot {
   private final InventoryCrafting field_75239_a;
   private final EntityPlayer field_75238_b;
   private int field_75237_g;

   public SlotCrafting(EntityPlayer var1, InventoryCrafting var2, IInventory var3, int var4, int var5, int var6) {
      super(☃, ☃, ☃, ☃);
      this.field_75238_b = ☃;
      this.field_75239_a = ☃;
   }

   @Override
   public boolean func_75214_a(ItemStack var1) {
      return false;
   }

   @Override
   public ItemStack func_75209_a(int var1) {
      if (this.func_75216_d()) {
         this.field_75237_g += Math.min(☃, this.func_75211_c().func_190916_E());
      }

      return super.func_75209_a(☃);
   }

   @Override
   protected void func_75210_a(ItemStack var1, int var2) {
      this.field_75237_g += ☃;
      this.func_75208_c(☃);
   }

   @Override
   protected void func_190900_b(int var1) {
      this.field_75237_g += ☃;
   }

   @Override
   protected void func_75208_c(ItemStack var1) {
      if (this.field_75237_g > 0) {
         ☃.func_77980_a(this.field_75238_b.field_70170_p, this.field_75238_b, this.field_75237_g);
      }

      ((IRecipeHolder)this.field_75224_c).func_201560_d(this.field_75238_b);
      this.field_75237_g = 0;
   }

   @Override
   public ItemStack func_190901_a(EntityPlayer var1, ItemStack var2) {
      this.func_75208_c(☃);
      NonNullList<ItemStack> ☃ = ☃.field_70170_p.func_199532_z().func_199513_c(this.field_75239_a, ☃.field_70170_p);

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         ItemStack ☃xx = this.field_75239_a.func_70301_a(☃x);
         ItemStack ☃xxx = ☃.get(☃x);
         if (!☃xx.func_190926_b()) {
            this.field_75239_a.func_70298_a(☃x, 1);
            ☃xx = this.field_75239_a.func_70301_a(☃x);
         }

         if (!☃xxx.func_190926_b()) {
            if (☃xx.func_190926_b()) {
               this.field_75239_a.func_70299_a(☃x, ☃xxx);
            } else if (ItemStack.func_179545_c(☃xx, ☃xxx) && ItemStack.func_77970_a(☃xx, ☃xxx)) {
               ☃xxx.func_190917_f(☃xx.func_190916_E());
               this.field_75239_a.func_70299_a(☃x, ☃xxx);
            } else if (!this.field_75238_b.field_71071_by.func_70441_a(☃xxx)) {
               this.field_75238_b.func_71019_a(☃xxx, false);
            }
         }
      }

      return ☃;
   }
}
