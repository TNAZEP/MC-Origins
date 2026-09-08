package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.village.MerchantRecipe;

public class SlotMerchantResult extends Slot {
   private final InventoryMerchant field_75233_a;
   private final EntityPlayer field_75232_b;
   private int field_75231_g;
   private final IMerchant field_75234_h;

   public SlotMerchantResult(EntityPlayer var1, IMerchant var2, InventoryMerchant var3, int var4, int var5, int var6) {
      super(☃, ☃, ☃, ☃);
      this.field_75232_b = ☃;
      this.field_75234_h = ☃;
      this.field_75233_a = ☃;
   }

   @Override
   public boolean func_75214_a(ItemStack var1) {
      return false;
   }

   @Override
   public ItemStack func_75209_a(int var1) {
      if (this.func_75216_d()) {
         this.field_75231_g += Math.min(☃, this.func_75211_c().func_190916_E());
      }

      return super.func_75209_a(☃);
   }

   @Override
   protected void func_75210_a(ItemStack var1, int var2) {
      this.field_75231_g += ☃;
      this.func_75208_c(☃);
   }

   @Override
   protected void func_75208_c(ItemStack var1) {
      ☃.func_77980_a(this.field_75232_b.field_70170_p, this.field_75232_b, this.field_75231_g);
      this.field_75231_g = 0;
   }

   @Override
   public ItemStack func_190901_a(EntityPlayer var1, ItemStack var2) {
      this.func_75208_c(☃);
      MerchantRecipe ☃ = this.field_75233_a.func_70468_h();
      if (☃ != null) {
         ItemStack ☃x = this.field_75233_a.func_70301_a(0);
         ItemStack ☃xx = this.field_75233_a.func_70301_a(1);
         if (this.func_75230_a(☃, ☃x, ☃xx) || this.func_75230_a(☃, ☃xx, ☃x)) {
            this.field_75234_h.func_70933_a(☃);
            ☃.func_195066_a(StatList.field_188075_I);
            this.field_75233_a.func_70299_a(0, ☃x);
            this.field_75233_a.func_70299_a(1, ☃xx);
         }
      }

      return ☃;
   }

   private boolean func_75230_a(MerchantRecipe var1, ItemStack var2, ItemStack var3) {
      ItemStack ☃ = ☃.func_77394_a();
      ItemStack ☃x = ☃.func_77396_b();
      if (☃.func_77973_b() == ☃.func_77973_b() && ☃.func_190916_E() >= ☃.func_190916_E()) {
         if (!☃x.func_190926_b() && !☃.func_190926_b() && ☃x.func_77973_b() == ☃.func_77973_b() && ☃.func_190916_E() >= ☃x.func_190916_E()) {
            ☃.func_190918_g(☃.func_190916_E());
            ☃.func_190918_g(☃x.func_190916_E());
            return true;
         }

         if (☃x.func_190926_b() && ☃.func_190926_b()) {
            ☃.func_190918_g(☃.func_190916_E());
            return true;
         }
      }

      return false;
   }
}
