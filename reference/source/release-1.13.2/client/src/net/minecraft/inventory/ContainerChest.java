package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ContainerChest extends Container {
   private final IInventory field_75155_e;
   private final int field_75154_f;

   public ContainerChest(IInventory var1, IInventory var2, EntityPlayer var3) {
      this.field_75155_e = ☃;
      this.field_75154_f = ☃.func_70302_i_() / 9;
      ☃.func_174889_b(☃);
      int ☃ = (this.field_75154_f - 4) * 18;

      for(int ☃x = 0; ☃x < this.field_75154_f; ++☃x) {
         for(int ☃xx = 0; ☃xx < 9; ++☃xx) {
            this.func_75146_a(new Slot(☃, ☃xx + ☃x * 9, 8 + ☃xx * 18, 18 + ☃x * 18));
         }
      }

      for(int ☃x = 0; ☃x < 3; ++☃x) {
         for(int ☃xx = 0; ☃xx < 9; ++☃xx) {
            this.func_75146_a(new Slot(☃, ☃xx + ☃x * 9 + 9, 8 + ☃xx * 18, 103 + ☃x * 18 + ☃));
         }
      }

      for(int ☃x = 0; ☃x < 9; ++☃x) {
         this.func_75146_a(new Slot(☃, ☃x, 8 + ☃x * 18, 161 + ☃));
      }
   }

   @Override
   public boolean func_75145_c(EntityPlayer var1) {
      return this.field_75155_e.func_70300_a(☃);
   }

   @Override
   public ItemStack func_82846_b(EntityPlayer var1, int var2) {
      ItemStack ☃ = ItemStack.field_190927_a;
      Slot ☃x = (Slot)this.field_75151_b.get(☃);
      if (☃x != null && ☃x.func_75216_d()) {
         ItemStack ☃xx = ☃x.func_75211_c();
         ☃ = ☃xx.func_77946_l();
         if (☃ < this.field_75154_f * 9) {
            if (!this.func_75135_a(☃xx, this.field_75154_f * 9, this.field_75151_b.size(), true)) {
               return ItemStack.field_190927_a;
            }
         } else if (!this.func_75135_a(☃xx, 0, this.field_75154_f * 9, false)) {
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
      this.field_75155_e.func_174886_c(☃);
   }

   public IInventory func_85151_d() {
      return this.field_75155_e;
   }
}
