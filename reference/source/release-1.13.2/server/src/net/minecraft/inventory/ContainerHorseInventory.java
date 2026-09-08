package net.minecraft.inventory;

import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ContainerHorseInventory extends Container {
   private final IInventory field_111243_a;
   private final AbstractHorse field_111242_f;

   public ContainerHorseInventory(IInventory var1, IInventory var2, final AbstractHorse var3, EntityPlayer var4) {
      this.field_111243_a = ☃;
      this.field_111242_f = ☃;
      int ☃ = 3;
      ☃.func_174889_b(☃);
      int ☃x = -18;
      this.func_75146_a(new Slot(☃, 0, 8, 18) {
         @Override
         public boolean func_75214_a(ItemStack var1) {
            return ☃.func_77973_b() == Items.field_151141_av && !this.func_75216_d() && ☃.func_190685_dA();
         }
      });
      this.func_75146_a(new Slot(☃, 1, 8, 36) {
         @Override
         public boolean func_75214_a(ItemStack var1) {
            return ☃.func_190682_f(☃);
         }

         @Override
         public int func_75219_a() {
            return 1;
         }
      });
      if (☃ instanceof AbstractChestHorse && ((AbstractChestHorse)☃).func_190695_dh()) {
         for(int ☃xx = 0; ☃xx < 3; ++☃xx) {
            for(int ☃xxx = 0; ☃xxx < ((AbstractChestHorse)☃).func_190696_dl(); ++☃xxx) {
               this.func_75146_a(new Slot(☃, 2 + ☃xxx + ☃xx * ((AbstractChestHorse)☃).func_190696_dl(), 80 + ☃xxx * 18, 18 + ☃xx * 18));
            }
         }
      }

      for(int ☃ = 0; ☃ < 3; ++☃) {
         for(int ☃x = 0; ☃x < 9; ++☃x) {
            this.func_75146_a(new Slot(☃, ☃x + ☃ * 9 + 9, 8 + ☃x * 18, 102 + ☃ * 18 + -18));
         }
      }

      for(int ☃ = 0; ☃ < 9; ++☃) {
         this.func_75146_a(new Slot(☃, ☃, 8 + ☃ * 18, 142));
      }
   }

   @Override
   public boolean func_75145_c(EntityPlayer var1) {
      return this.field_111243_a.func_70300_a(☃) && this.field_111242_f.func_70089_S() && this.field_111242_f.func_70032_d(☃) < 8.0F;
   }

   @Override
   public ItemStack func_82846_b(EntityPlayer var1, int var2) {
      ItemStack ☃ = ItemStack.field_190927_a;
      Slot ☃x = (Slot)this.field_75151_b.get(☃);
      if (☃x != null && ☃x.func_75216_d()) {
         ItemStack ☃xx = ☃x.func_75211_c();
         ☃ = ☃xx.func_77946_l();
         if (☃ < this.field_111243_a.func_70302_i_()) {
            if (!this.func_75135_a(☃xx, this.field_111243_a.func_70302_i_(), this.field_75151_b.size(), true)) {
               return ItemStack.field_190927_a;
            }
         } else if (this.func_75139_a(1).func_75214_a(☃xx) && !this.func_75139_a(1).func_75216_d()) {
            if (!this.func_75135_a(☃xx, 1, 2, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (this.func_75139_a(0).func_75214_a(☃xx)) {
            if (!this.func_75135_a(☃xx, 0, 1, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (this.field_111243_a.func_70302_i_() <= 2 || !this.func_75135_a(☃xx, 2, this.field_111243_a.func_70302_i_(), false)) {
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
      this.field_111243_a.func_174886_c(☃);
   }
}
