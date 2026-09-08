package net.minecraft.inventory;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class InventoryBasic implements IInventory, IRecipeHelperPopulator {
   private final ITextComponent field_70483_a;
   private final int field_70481_b;
   private final NonNullList<ItemStack> field_70482_c;
   private List<IInventoryChangedListener> field_70480_d;
   private ITextComponent field_94051_e;

   public InventoryBasic(ITextComponent var1, int var2) {
      this.field_70483_a = ☃;
      this.field_70481_b = ☃;
      this.field_70482_c = NonNullList.func_191197_a(☃, ItemStack.field_190927_a);
   }

   public void func_110134_a(IInventoryChangedListener var1) {
      if (this.field_70480_d == null) {
         this.field_70480_d = Lists.<IInventoryChangedListener>newArrayList();
      }

      this.field_70480_d.add(☃);
   }

   public void func_110132_b(IInventoryChangedListener var1) {
      this.field_70480_d.remove(☃);
   }

   @Override
   public ItemStack func_70301_a(int var1) {
      return ☃ >= 0 && ☃ < this.field_70482_c.size() ? this.field_70482_c.get(☃) : ItemStack.field_190927_a;
   }

   @Override
   public ItemStack func_70298_a(int var1, int var2) {
      ItemStack ☃ = ItemStackHelper.func_188382_a(this.field_70482_c, ☃, ☃);
      if (!☃.func_190926_b()) {
         this.func_70296_d();
      }

      return ☃;
   }

   public ItemStack func_174894_a(ItemStack var1) {
      ItemStack ☃ = ☃.func_77946_l();

      for(int ☃x = 0; ☃x < this.field_70481_b; ++☃x) {
         ItemStack ☃xx = this.func_70301_a(☃x);
         if (☃xx.func_190926_b()) {
            this.func_70299_a(☃x, ☃);
            this.func_70296_d();
            return ItemStack.field_190927_a;
         }

         if (ItemStack.func_179545_c(☃xx, ☃)) {
            int ☃xx = Math.min(this.func_70297_j_(), ☃xx.func_77976_d());
            int ☃xxx = Math.min(☃.func_190916_E(), ☃xx - ☃xx.func_190916_E());
            if (☃xxx > 0) {
               ☃xx.func_190917_f(☃xxx);
               ☃.func_190918_g(☃xxx);
               if (☃.func_190926_b()) {
                  this.func_70296_d();
                  return ItemStack.field_190927_a;
               }
            }
         }
      }

      if (☃.func_190916_E() != ☃.func_190916_E()) {
         this.func_70296_d();
      }

      return ☃;
   }

   @Override
   public ItemStack func_70304_b(int var1) {
      ItemStack ☃ = this.field_70482_c.get(☃);
      if (☃.func_190926_b()) {
         return ItemStack.field_190927_a;
      } else {
         this.field_70482_c.set(☃, ItemStack.field_190927_a);
         return ☃;
      }
   }

   @Override
   public void func_70299_a(int var1, ItemStack var2) {
      this.field_70482_c.set(☃, ☃);
      if (!☃.func_190926_b() && ☃.func_190916_E() > this.func_70297_j_()) {
         ☃.func_190920_e(this.func_70297_j_());
      }

      this.func_70296_d();
   }

   @Override
   public int func_70302_i_() {
      return this.field_70481_b;
   }

   @Override
   public boolean func_191420_l() {
      for(ItemStack ☃ : this.field_70482_c) {
         if (!☃.func_190926_b()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ITextComponent func_200200_C_() {
      return this.field_94051_e != null ? this.field_94051_e : this.field_70483_a;
   }

   @Nullable
   @Override
   public ITextComponent func_200201_e() {
      return this.field_94051_e;
   }

   @Override
   public boolean func_145818_k_() {
      return this.field_94051_e != null;
   }

   public void func_200228_a(@Nullable ITextComponent var1) {
      this.field_94051_e = ☃;
   }

   @Override
   public int func_70297_j_() {
      return 64;
   }

   @Override
   public void func_70296_d() {
      if (this.field_70480_d != null) {
         for(int ☃ = 0; ☃ < this.field_70480_d.size(); ++☃) {
            ((IInventoryChangedListener)this.field_70480_d.get(☃)).func_76316_a(this);
         }
      }
   }

   @Override
   public boolean func_70300_a(EntityPlayer var1) {
      return true;
   }

   @Override
   public void func_174889_b(EntityPlayer var1) {
   }

   @Override
   public void func_174886_c(EntityPlayer var1) {
   }

   @Override
   public boolean func_94041_b(int var1, ItemStack var2) {
      return true;
   }

   @Override
   public int func_174887_a_(int var1) {
      return 0;
   }

   @Override
   public void func_174885_b(int var1, int var2) {
   }

   @Override
   public int func_174890_g() {
      return 0;
   }

   @Override
   public void func_174888_l() {
      this.field_70482_c.clear();
   }

   @Override
   public void func_194018_a(RecipeItemHelper var1) {
      for(ItemStack ☃ : this.field_70482_c) {
         ☃.func_194112_a(☃);
      }
   }
}
