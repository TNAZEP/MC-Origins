package net.minecraft.inventory;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;

public class ContainerBrewingStand extends Container {
   private final IInventory field_75188_e;
   private final Slot field_75186_f;
   private int field_184998_g;
   private int field_184999_h;

   public ContainerBrewingStand(InventoryPlayer var1, IInventory var2) {
      this.field_75188_e = ☃;
      this.func_75146_a(new ContainerBrewingStand.Potion(☃, 0, 56, 51));
      this.func_75146_a(new ContainerBrewingStand.Potion(☃, 1, 79, 58));
      this.func_75146_a(new ContainerBrewingStand.Potion(☃, 2, 102, 51));
      this.field_75186_f = this.func_75146_a(new ContainerBrewingStand.Ingredient(☃, 3, 79, 17));
      this.func_75146_a(new ContainerBrewingStand.Fuel(☃, 4, 17, 17));

      for(int ☃ = 0; ☃ < 3; ++☃) {
         for(int ☃x = 0; ☃x < 9; ++☃x) {
            this.func_75146_a(new Slot(☃, ☃x + ☃ * 9 + 9, 8 + ☃x * 18, 84 + ☃ * 18));
         }
      }

      for(int ☃ = 0; ☃ < 9; ++☃) {
         this.func_75146_a(new Slot(☃, ☃, 8 + ☃ * 18, 142));
      }
   }

   @Override
   public void func_75132_a(IContainerListener var1) {
      super.func_75132_a(☃);
      ☃.func_175173_a(this, this.field_75188_e);
   }

   @Override
   public void func_75142_b() {
      super.func_75142_b();

      for(int ☃ = 0; ☃ < this.field_75149_d.size(); ++☃) {
         IContainerListener ☃x = (IContainerListener)this.field_75149_d.get(☃);
         if (this.field_184998_g != this.field_75188_e.func_174887_a_(0)) {
            ☃x.func_71112_a(this, 0, this.field_75188_e.func_174887_a_(0));
         }

         if (this.field_184999_h != this.field_75188_e.func_174887_a_(1)) {
            ☃x.func_71112_a(this, 1, this.field_75188_e.func_174887_a_(1));
         }
      }

      this.field_184998_g = this.field_75188_e.func_174887_a_(0);
      this.field_184999_h = this.field_75188_e.func_174887_a_(1);
   }

   @Override
   public boolean func_75145_c(EntityPlayer var1) {
      return this.field_75188_e.func_70300_a(☃);
   }

   @Override
   public ItemStack func_82846_b(EntityPlayer var1, int var2) {
      ItemStack ☃ = ItemStack.field_190927_a;
      Slot ☃x = (Slot)this.field_75151_b.get(☃);
      if (☃x != null && ☃x.func_75216_d()) {
         ItemStack ☃xx = ☃x.func_75211_c();
         ☃ = ☃xx.func_77946_l();
         if ((☃ < 0 || ☃ > 2) && ☃ != 3 && ☃ != 4) {
            if (this.field_75186_f.func_75214_a(☃xx)) {
               if (!this.func_75135_a(☃xx, 3, 4, false)) {
                  return ItemStack.field_190927_a;
               }
            } else if (ContainerBrewingStand.Potion.func_75243_a_(☃) && ☃.func_190916_E() == 1) {
               if (!this.func_75135_a(☃xx, 0, 3, false)) {
                  return ItemStack.field_190927_a;
               }
            } else if (ContainerBrewingStand.Fuel.func_185004_b_(☃)) {
               if (!this.func_75135_a(☃xx, 4, 5, false)) {
                  return ItemStack.field_190927_a;
               }
            } else if (☃ >= 5 && ☃ < 32) {
               if (!this.func_75135_a(☃xx, 32, 41, false)) {
                  return ItemStack.field_190927_a;
               }
            } else if (☃ >= 32 && ☃ < 41) {
               if (!this.func_75135_a(☃xx, 5, 32, false)) {
                  return ItemStack.field_190927_a;
               }
            } else if (!this.func_75135_a(☃xx, 5, 41, false)) {
               return ItemStack.field_190927_a;
            }
         } else {
            if (!this.func_75135_a(☃xx, 5, 41, true)) {
               return ItemStack.field_190927_a;
            }

            ☃x.func_75220_a(☃xx, ☃);
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

   static class Fuel extends Slot {
      public Fuel(IInventory var1, int var2, int var3, int var4) {
         super(☃, ☃, ☃, ☃);
      }

      @Override
      public boolean func_75214_a(ItemStack var1) {
         return func_185004_b_(☃);
      }

      public static boolean func_185004_b_(ItemStack var0) {
         return ☃.func_77973_b() == Items.field_151065_br;
      }

      @Override
      public int func_75219_a() {
         return 64;
      }
   }

   static class Ingredient extends Slot {
      public Ingredient(IInventory var1, int var2, int var3, int var4) {
         super(☃, ☃, ☃, ☃);
      }

      @Override
      public boolean func_75214_a(ItemStack var1) {
         return PotionBrewing.func_185205_a(☃);
      }

      @Override
      public int func_75219_a() {
         return 64;
      }
   }

   static class Potion extends Slot {
      public Potion(IInventory var1, int var2, int var3, int var4) {
         super(☃, ☃, ☃, ☃);
      }

      @Override
      public boolean func_75214_a(ItemStack var1) {
         return func_75243_a_(☃);
      }

      @Override
      public int func_75219_a() {
         return 1;
      }

      @Override
      public ItemStack func_190901_a(EntityPlayer var1, ItemStack var2) {
         PotionType ☃ = PotionUtils.func_185191_c(☃);
         if (☃ instanceof EntityPlayerMP) {
            CriteriaTriggers.field_192130_j.func_192173_a((EntityPlayerMP)☃, ☃);
         }

         super.func_190901_a(☃, ☃);
         return ☃;
      }

      public static boolean func_75243_a_(ItemStack var0) {
         Item ☃ = ☃.func_77973_b();
         return ☃ == Items.field_151068_bn || ☃ == Items.field_185155_bH || ☃ == Items.field_185156_bI || ☃ == Items.field_151069_bo;
      }
   }
}
