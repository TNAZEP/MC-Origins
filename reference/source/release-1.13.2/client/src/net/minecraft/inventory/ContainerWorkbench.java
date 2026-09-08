package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerWorkbench extends ContainerRecipeBook {
   public InventoryCrafting field_75162_e = new InventoryCrafting(this, 3, 3);
   public InventoryCraftResult field_75160_f = new InventoryCraftResult();
   private final World field_75161_g;
   private final BlockPos field_178145_h;
   private final EntityPlayer field_192390_i;

   public ContainerWorkbench(InventoryPlayer var1, World var2, BlockPos var3) {
      this.field_75161_g = ☃;
      this.field_178145_h = ☃;
      this.field_192390_i = ☃.field_70458_d;
      this.func_75146_a(new SlotCrafting(☃.field_70458_d, this.field_75162_e, this.field_75160_f, 0, 124, 35));

      for(int ☃ = 0; ☃ < 3; ++☃) {
         for(int ☃x = 0; ☃x < 3; ++☃x) {
            this.func_75146_a(new Slot(this.field_75162_e, ☃x + ☃ * 3, 30 + ☃x * 18, 17 + ☃ * 18));
         }
      }

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
   public void func_75130_a(IInventory var1) {
      this.func_192389_a(this.field_75161_g, this.field_192390_i, this.field_75162_e, this.field_75160_f);
   }

   @Override
   public void func_201771_a(RecipeItemHelper var1) {
      this.field_75162_e.func_194018_a(☃);
   }

   @Override
   public void func_201768_e() {
      this.field_75162_e.func_174888_l();
      this.field_75160_f.func_174888_l();
   }

   @Override
   public boolean func_201769_a(IRecipe var1) {
      return ☃.func_77569_a(this.field_75162_e, this.field_192390_i.field_70170_p);
   }

   @Override
   public void func_75134_a(EntityPlayer var1) {
      super.func_75134_a(☃);
      if (!this.field_75161_g.field_72995_K) {
         this.func_193327_a(☃, this.field_75161_g, this.field_75162_e);
      }
   }

   @Override
   public boolean func_75145_c(EntityPlayer var1) {
      if (this.field_75161_g.func_180495_p(this.field_178145_h).func_177230_c() != Blocks.field_150462_ai) {
         return false;
      } else {
         return ☃.func_70092_e(
               (double)this.field_178145_h.func_177958_n() + 0.5,
               (double)this.field_178145_h.func_177956_o() + 0.5,
               (double)this.field_178145_h.func_177952_p() + 0.5
            )
            <= 64.0;
      }
   }

   @Override
   public ItemStack func_82846_b(EntityPlayer var1, int var2) {
      ItemStack ☃ = ItemStack.field_190927_a;
      Slot ☃x = (Slot)this.field_75151_b.get(☃);
      if (☃x != null && ☃x.func_75216_d()) {
         ItemStack ☃xx = ☃x.func_75211_c();
         ☃ = ☃xx.func_77946_l();
         if (☃ == 0) {
            ☃xx.func_77973_b().func_77622_d(☃xx, this.field_75161_g, ☃);
            if (!this.func_75135_a(☃xx, 10, 46, true)) {
               return ItemStack.field_190927_a;
            }

            ☃x.func_75220_a(☃xx, ☃);
         } else if (☃ >= 10 && ☃ < 37) {
            if (!this.func_75135_a(☃xx, 37, 46, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (☃ >= 37 && ☃ < 46) {
            if (!this.func_75135_a(☃xx, 10, 37, false)) {
               return ItemStack.field_190927_a;
            }
         } else if (!this.func_75135_a(☃xx, 10, 46, false)) {
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
      return ☃.field_75224_c != this.field_75160_f && super.func_94530_a(☃, ☃);
   }

   @Override
   public int func_201767_f() {
      return 0;
   }

   @Override
   public int func_201770_g() {
      return this.field_75162_e.func_174922_i();
   }

   @Override
   public int func_201772_h() {
      return this.field_75162_e.func_174923_h();
   }

   @Override
   public int func_203721_h() {
      return 10;
   }
}
