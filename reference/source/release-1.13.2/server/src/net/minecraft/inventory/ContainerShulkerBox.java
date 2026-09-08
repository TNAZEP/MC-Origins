package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class ContainerShulkerBox extends Container {
   private final IInventory field_190899_a;

   public ContainerShulkerBox(InventoryPlayer var1, IInventory var2, EntityPlayer var3) {
      this.field_190899_a = ☃;
      ☃.func_174889_b(☃);
      int ☃ = 3;
      int ☃x = 9;

      for(int ☃xx = 0; ☃xx < 3; ++☃xx) {
         for(int ☃xxx = 0; ☃xxx < 9; ++☃xxx) {
            this.func_75146_a(new SlotShulkerBox(☃, ☃xxx + ☃xx * 9, 8 + ☃xxx * 18, 18 + ☃xx * 18));
         }
      }

      for(int ☃xx = 0; ☃xx < 3; ++☃xx) {
         for(int ☃xxx = 0; ☃xxx < 9; ++☃xxx) {
            this.func_75146_a(new Slot(☃, ☃xxx + ☃xx * 9 + 9, 8 + ☃xxx * 18, 84 + ☃xx * 18));
         }
      }

      for(int ☃xx = 0; ☃xx < 9; ++☃xx) {
         this.func_75146_a(new Slot(☃, ☃xx, 8 + ☃xx * 18, 142));
      }
   }

   @Override
   public boolean func_75145_c(EntityPlayer var1) {
      return this.field_190899_a.func_70300_a(☃);
   }

   @Override
   public ItemStack func_82846_b(EntityPlayer var1, int var2) {
      ItemStack ☃ = ItemStack.field_190927_a;
      Slot ☃x = (Slot)this.field_75151_b.get(☃);
      if (☃x != null && ☃x.func_75216_d()) {
         ItemStack ☃xx = ☃x.func_75211_c();
         ☃ = ☃xx.func_77946_l();
         if (☃ < this.field_190899_a.func_70302_i_()) {
            if (!this.func_75135_a(☃xx, this.field_190899_a.func_70302_i_(), this.field_75151_b.size(), true)) {
               return ItemStack.field_190927_a;
            }
         } else if (!this.func_75135_a(☃xx, 0, this.field_190899_a.func_70302_i_(), false)) {
            return ItemStack.field_190927_a;
         }

         if (☃xx.func_190926_b()) {
            ☃x.func_75215_d(ItemStack.field_190927_a);
         } else {
            ☃x.func_75218_e();
         }
      }

      return ☃;
   }

   @Override
   public void func_75134_a(EntityPlayer var1) {
      super.func_75134_a(☃);
      this.field_190899_a.func_174886_c(☃);
   }
}
