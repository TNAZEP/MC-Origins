package net.minecraft.inventory;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;

public class ContainerPlayer extends ContainerRecipeBook {
   private static final String[] field_200829_h = new String[]{
      "item/empty_armor_slot_boots", "item/empty_armor_slot_leggings", "item/empty_armor_slot_chestplate", "item/empty_armor_slot_helmet"
   };
   private static final EntityEquipmentSlot[] field_185003_h = new EntityEquipmentSlot[]{
      EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET
   };
   public InventoryCrafting field_75181_e = new InventoryCrafting(this, 2, 2);
   public InventoryCraftResult field_75179_f = new InventoryCraftResult();
   public boolean field_75180_g;
   private final EntityPlayer field_82862_h;

   public ContainerPlayer(InventoryPlayer var1, boolean var2, EntityPlayer var3) {
      this.field_75180_g = ☃;
      this.field_82862_h = ☃;
      this.func_75146_a(new SlotCrafting(☃.field_70458_d, this.field_75181_e, this.field_75179_f, 0, 154, 28));

      for(int ☃ = 0; ☃ < 2; ++☃) {
         for(int ☃x = 0; ☃x < 2; ++☃x) {
            this.func_75146_a(new Slot(this.field_75181_e, ☃x + ☃ * 2, 98 + ☃x * 18, 18 + ☃ * 18));
         }
      }

      for(int ☃ = 0; ☃ < 4; ++☃) {
         final EntityEquipmentSlot ☃x = field_185003_h[☃];
         this.func_75146_a(new Slot(☃, 39 - ☃, 8, 8 + ☃ * 18) {
            @Override
            public int func_75219_a() {
               return 1;
            }

            @Override
            public boolean func_75214_a(ItemStack var1) {
               return ☃ == EntityLiving.func_184640_d(☃);
            }

            @Override
            public boolean func_82869_a(EntityPlayer var1) {
               ItemStack ☃ = this.func_75211_c();
               return !☃.func_190926_b() && !☃.func_184812_l_() && EnchantmentHelper.func_190938_b(☃) ? false : super.func_82869_a(☃);
            }
         });
      }

      for(int ☃ = 0; ☃ < 3; ++☃) {
         for(int ☃x = 0; ☃x < 9; ++☃x) {
            this.func_75146_a(new Slot(☃, ☃x + (☃ + 1) * 9, 8 + ☃x * 18, 84 + ☃ * 18));
         }
      }

      for(int ☃ = 0; ☃ < 9; ++☃) {
         this.func_75146_a(new Slot(☃, ☃, 8 + ☃ * 18, 142));
      }

      this.func_75146_a(new Slot(☃, 40, 77, 62) {
      });
   }

   @Override
   public void func_201771_a(RecipeItemHelper var1) {
      this.field_75181_e.func_194018_a(☃);
   }

   @Override
   public void func_201768_e() {
      this.field_75179_f.func_174888_l();
      this.field_75181_e.func_174888_l();
   }

   @Override
   public boolean func_201769_a(IRecipe var1) {
      return ☃.func_77569_a(this.field_75181_e, this.field_82862_h.field_70170_p);
   }

   @Override
   public void func_75130_a(IInventory var1) {
      this.func_192389_a(this.field_82862_h.field_70170_p, this.field_82862_h, this.field_75181_e, this.field_75179_f);
   }

   @Override
   public void func_75134_a(EntityPlayer var1) {
      super.func_75134_a(☃);
      this.field_75179_f.func_174888_l();
      if (!☃.field_70170_p.field_72995_K) {
         this.func_193327_a(☃, ☃.field_70170_p, this.field_75181_e);
      }
   }

   @Override
   public boolean func_75145_c(EntityPlayer var1) {
      return true;
   }

   @Override
   public ItemStack func_82846_b(EntityPlayer var1, int var2) {
      ItemStack ☃ = ItemStack.field_190927_a;
      Slot ☃x = (Slot)this.field_75151_b.get(☃);
      if (☃x != null && ☃x.func_75216_d()) {
         ItemStack ☃xx = ☃x.func_75211_c();
         ☃ = ☃xx.func_77946_l();
         EntityEquipmentSlot ☃xxx = EntityLiving.func_184640_d(☃);
         if (☃ == 0) {
            if (!this.func_75135_a(☃xx, 9, 45, true)) {
               return ItemStack.field_190927_a;
            }

            ☃x.func_75220_a(☃xx, ☃);
         } else if (☃ >= 1 && ☃ < 5) {
            if (!this.func_75135_a(☃xx, 9, 45, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (☃ >= 5 && ☃ < 9) {
            if (!this.func_75135_a(☃xx, 9, 45, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (☃xxx.func_188453_a() == EntityEquipmentSlot.Type.ARMOR && !((Slot)this.field_75151_b.get(8 - ☃xxx.func_188454_b())).func_75216_d()) {
            int ☃xx = 8 - ☃xxx.func_188454_b();
            if (!this.func_75135_a(☃xx, ☃xx, ☃xx + 1, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (☃xxx == EntityEquipmentSlot.OFFHAND && !((Slot)this.field_75151_b.get(45)).func_75216_d()) {
            if (!this.func_75135_a(☃xx, 45, 46, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (☃ >= 9 && ☃ < 36) {
            if (!this.func_75135_a(☃xx, 36, 45, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (☃ >= 36 && ☃ < 45) {
            if (!this.func_75135_a(☃xx, 9, 36, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (!this.func_75135_a(☃xx, 9, 45, false)) {
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

         ItemStack ☃xx = ☃x.func_190901_a(☃, ☃xx);
         if (☃ == 0) {
            ☃.func_71019_a(☃xx, false);
         }
      }

      return ☃;
   }

   @Override
   public boolean func_94530_a(ItemStack var1, Slot var2) {
      return ☃.field_75224_c != this.field_75179_f && super.func_94530_a(☃, ☃);
   }

   @Override
   public int func_201767_f() {
      return 0;
   }

   @Override
   public int func_201770_g() {
      return this.field_75181_e.func_174922_i();
   }

   @Override
   public int func_201772_h() {
      return this.field_75181_e.func_174923_h();
   }
}
