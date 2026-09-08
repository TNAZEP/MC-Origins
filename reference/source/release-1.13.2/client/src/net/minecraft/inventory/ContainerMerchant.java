package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerMerchant extends Container {
   private final IMerchant field_75178_e;
   private final InventoryMerchant field_75176_f;
   private final World field_75177_g;

   public ContainerMerchant(InventoryPlayer var1, IMerchant var2, World var3) {
      this.field_75178_e = ☃;
      this.field_75177_g = ☃;
      this.field_75176_f = new InventoryMerchant(☃.field_70458_d, ☃);
      this.func_75146_a(new Slot(this.field_75176_f, 0, 36, 53));
      this.func_75146_a(new Slot(this.field_75176_f, 1, 62, 53));
      this.func_75146_a(new SlotMerchantResult(☃.field_70458_d, ☃, this.field_75176_f, 2, 120, 53));

      for(int ☃ = 0; ☃ < 3; ++☃) {
         for(int ☃x = 0; ☃x < 9; ++☃x) {
            this.func_75146_a(new Slot(☃, ☃x + ☃ * 9 + 9, 8 + ☃x * 18, 84 + ☃ * 18));
         }
      }

      for(int ☃ = 0; ☃ < 9; ++☃) {
         this.func_75146_a(new Slot(☃, ☃, 8 + ☃ * 18, 142));
      }
   }

   public InventoryMerchant func_75174_d() {
      return this.field_75176_f;
   }

   @Override
   public void func_75130_a(IInventory var1) {
      this.field_75176_f.func_70470_g();
      super.func_75130_a(☃);
   }

   public void func_75175_c(int var1) {
      this.field_75176_f.func_70471_c(☃);
   }

   @Override
   public boolean func_75145_c(EntityPlayer var1) {
      return this.field_75178_e.func_70931_l_() == ☃;
   }

   @Override
   public ItemStack func_82846_b(EntityPlayer var1, int var2) {
      ItemStack ☃ = ItemStack.field_190927_a;
      Slot ☃x = (Slot)this.field_75151_b.get(☃);
      if (☃x != null && ☃x.func_75216_d()) {
         ItemStack ☃xx = ☃x.func_75211_c();
         ☃ = ☃xx.func_77946_l();
         if (☃ == 2) {
            if (!this.func_75135_a(☃xx, 3, 39, true)) {
               return ItemStack.field_190927_a;
            }

            ☃x.func_75220_a(☃xx, ☃);
         } else if (☃ != 0 && ☃ != 1) {
            if (☃ >= 3 && ☃ < 30) {
               if (!this.func_75135_a(☃xx, 30, 39, false)) {
                  return ItemStack.field_190927_a;
               }
            } else if (☃ >= 30 && ☃ < 39 && !this.func_75135_a(☃xx, 3, 30, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (!this.func_75135_a(☃xx, 3, 39, false)) {
            return ItemStack.field_190927_a;
         }

         if (☃xx.func_190926_b()) {
            ☃x.func_75215_d(ItemStack.field_190927_a);
         } else {
            ☃x.func_75218_e();
         }

         if (☃xx.func_190916_E() == ☃.func_190916_E()) {
            return ItemStack.field_190927_a;
         }

         ☃x.func_190901_a(☃, ☃xx);
      }

      return ☃;
   }

   @Override
   public void func_75134_a(EntityPlayer var1) {
      super.func_75134_a(☃);
      this.field_75178_e.func_70932_a_(null);
      super.func_75134_a(☃);
      if (!this.field_75177_g.field_72995_K) {
         ItemStack ☃ = this.field_75176_f.func_70304_b(0);
         if (!☃.func_190926_b()) {
            ☃.func_71019_a(☃, false);
         }

         ☃ = this.field_75176_f.func_70304_b(1);
         if (!☃.func_190926_b()) {
            ☃.func_71019_a(☃, false);
         }
      }
   }
}
