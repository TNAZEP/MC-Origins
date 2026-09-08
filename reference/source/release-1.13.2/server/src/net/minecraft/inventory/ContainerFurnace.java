package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

public class ContainerFurnace extends ContainerRecipeBook {
   private final IInventory field_75158_e;
   private final World field_201773_f;
   private int field_178152_f;
   private int field_178153_g;
   private int field_178154_h;
   private int field_178155_i;

   public ContainerFurnace(InventoryPlayer var1, IInventory var2) {
      this.field_75158_e = ☃;
      this.field_201773_f = ☃.field_70458_d.field_70170_p;
      this.func_75146_a(new Slot(☃, 0, 56, 17));
      this.func_75146_a(new SlotFurnaceFuel(☃, 1, 56, 53));
      this.func_75146_a(new SlotFurnaceOutput(☃.field_70458_d, ☃, 2, 116, 35));

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
      ☃.func_175173_a(this, this.field_75158_e);
   }

   @Override
   public void func_201771_a(RecipeItemHelper var1) {
      if (this.field_75158_e instanceof IRecipeHelperPopulator) {
         ((IRecipeHelperPopulator)this.field_75158_e).func_194018_a(☃);
      }
   }

   @Override
   public void func_201768_e() {
      this.field_75158_e.func_174888_l();
   }

   @Override
   public boolean func_201769_a(IRecipe var1) {
      return ☃.func_77569_a(this.field_75158_e, this.field_201773_f);
   }

   @Override
   public int func_201767_f() {
      return 2;
   }

   @Override
   public int func_201770_g() {
      return 1;
   }

   @Override
   public int func_201772_h() {
      return 1;
   }

   @Override
   public void func_75142_b() {
      super.func_75142_b();

      for(IContainerListener ☃ : this.field_75149_d) {
         if (this.field_178152_f != this.field_75158_e.func_174887_a_(2)) {
            ☃.func_71112_a(this, 2, this.field_75158_e.func_174887_a_(2));
         }

         if (this.field_178154_h != this.field_75158_e.func_174887_a_(0)) {
            ☃.func_71112_a(this, 0, this.field_75158_e.func_174887_a_(0));
         }

         if (this.field_178155_i != this.field_75158_e.func_174887_a_(1)) {
            ☃.func_71112_a(this, 1, this.field_75158_e.func_174887_a_(1));
         }

         if (this.field_178153_g != this.field_75158_e.func_174887_a_(3)) {
            ☃.func_71112_a(this, 3, this.field_75158_e.func_174887_a_(3));
         }
      }

      this.field_178152_f = this.field_75158_e.func_174887_a_(2);
      this.field_178154_h = this.field_75158_e.func_174887_a_(0);
      this.field_178155_i = this.field_75158_e.func_174887_a_(1);
      this.field_178153_g = this.field_75158_e.func_174887_a_(3);
   }

   @Override
   public boolean func_75145_c(EntityPlayer var1) {
      return this.field_75158_e.func_70300_a(☃);
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
         } else if (☃ != 1 && ☃ != 0) {
            if (this.func_206253_a(☃xx)) {
               if (!this.func_75135_a(☃xx, 0, 1, false)) {
                  return ItemStack.field_190927_a;
               }
            } else if (TileEntityFurnace.func_145954_b(☃xx)) {
               if (!this.func_75135_a(☃xx, 1, 2, false)) {
                  return ItemStack.field_190927_a;
               }
            } else if (☃ >= 3 && ☃ < 30) {
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

   private boolean func_206253_a(ItemStack var1) {
      for(IRecipe ☃ : this.field_201773_f.func_199532_z().func_199510_b()) {
         if (☃ instanceof FurnaceRecipe && ☃.func_192400_c().get(0).test(☃)) {
            return true;
         }
      }

      return false;
   }
}
