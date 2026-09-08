package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerBeacon extends Container {
   private final IInventory field_82866_e;
   private final ContainerBeacon.BeaconSlot field_82864_f;

   public ContainerBeacon(IInventory var1, IInventory var2) {
      this.field_82866_e = ☃;
      this.field_82864_f = new ContainerBeacon.BeaconSlot(☃, 0, 136, 110);
      this.func_75146_a(this.field_82864_f);
      int ☃ = 36;
      int ☃x = 137;

      for(int ☃xx = 0; ☃xx < 3; ++☃xx) {
         for(int ☃xxx = 0; ☃xxx < 9; ++☃xxx) {
            this.func_75146_a(new Slot(☃, ☃xxx + ☃xx * 9 + 9, 36 + ☃xxx * 18, 137 + ☃xx * 18));
         }
      }

      for(int ☃xx = 0; ☃xx < 9; ++☃xx) {
         this.func_75146_a(new Slot(☃, ☃xx, 36 + ☃xx * 18, 195));
      }
   }

   @Override
   public void func_75132_a(IContainerListener var1) {
      super.func_75132_a(☃);
      ☃.func_175173_a(this, this.field_82866_e);
   }

   public IInventory func_180611_e() {
      return this.field_82866_e;
   }

   @Override
   public void func_75134_a(EntityPlayer var1) {
      super.func_75134_a(☃);
      if (!☃.field_70170_p.field_72995_K) {
         ItemStack ☃ = this.field_82864_f.func_75209_a(this.field_82864_f.func_75219_a());
         if (!☃.func_190926_b()) {
            ☃.func_71019_a(☃, false);
         }
      }
   }

   @Override
   public boolean func_75145_c(EntityPlayer var1) {
      return this.field_82866_e.func_70300_a(☃);
   }

   @Override
   public ItemStack func_82846_b(EntityPlayer var1, int var2) {
      ItemStack ☃ = ItemStack.field_190927_a;
      Slot ☃x = (Slot)this.field_75151_b.get(☃);
      if (☃x != null && ☃x.func_75216_d()) {
         ItemStack ☃xx = ☃x.func_75211_c();
         ☃ = ☃xx.func_77946_l();
         if (☃ == 0) {
            if (!this.func_75135_a(☃xx, 1, 37, true)) {
               return ItemStack.field_190927_a;
            }

            ☃x.func_75220_a(☃xx, ☃);
         } else if (!this.field_82864_f.func_75216_d() && this.field_82864_f.func_75214_a(☃xx) && ☃xx.func_190916_E() == 1) {
            if (!this.func_75135_a(☃xx, 0, 1, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (☃ >= 1 && ☃ < 28) {
            if (!this.func_75135_a(☃xx, 28, 37, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (☃ >= 28 && ☃ < 37) {
            if (!this.func_75135_a(☃xx, 1, 28, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (!this.func_75135_a(☃xx, 1, 37, false)) {
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

   class BeaconSlot extends Slot {
      public BeaconSlot(IInventory var2, int var3, int var4, int var5) {
         super(☃, ☃, ☃, ☃);
      }

      @Override
      public boolean func_75214_a(ItemStack var1) {
         Item ☃ = ☃.func_77973_b();
         return ☃ == Items.field_151166_bC || ☃ == Items.field_151045_i || ☃ == Items.field_151043_k || ☃ == Items.field_151042_j;
      }

      @Override
      public int func_75219_a() {
         return 1;
      }
   }
}
