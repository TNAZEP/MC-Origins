package net.minecraft.inventory;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class InventoryCrafting implements IInventory, IRecipeHelperPopulator {
   private final NonNullList<ItemStack> field_70466_a;
   private final int field_70464_b;
   private final int field_174924_c;
   private final Container field_70465_c;

   public InventoryCrafting(Container var1, int var2, int var3) {
      this.field_70466_a = NonNullList.func_191197_a(☃ * ☃, ItemStack.field_190927_a);
      this.field_70465_c = ☃;
      this.field_70464_b = ☃;
      this.field_174924_c = ☃;
   }

   @Override
   public int func_70302_i_() {
      return this.field_70466_a.size();
   }

   @Override
   public boolean func_191420_l() {
      for(ItemStack ☃ : this.field_70466_a) {
         if (!☃.func_190926_b()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack func_70301_a(int var1) {
      return ☃ >= this.func_70302_i_() ? ItemStack.field_190927_a : this.field_70466_a.get(☃);
   }

   @Override
   public ITextComponent func_200200_C_() {
      return new TextComponentTranslation("container.crafting");
   }

   @Override
   public boolean func_145818_k_() {
      return false;
   }

   @Nullable
   @Override
   public ITextComponent func_200201_e() {
      return null;
   }

   @Override
   public ItemStack func_70304_b(int var1) {
      return ItemStackHelper.func_188383_a(this.field_70466_a, ☃);
   }

   @Override
   public ItemStack func_70298_a(int var1, int var2) {
      ItemStack ☃ = ItemStackHelper.func_188382_a(this.field_70466_a, ☃, ☃);
      if (!☃.func_190926_b()) {
         this.field_70465_c.func_75130_a(this);
      }

      return ☃;
   }

   @Override
   public void func_70299_a(int var1, ItemStack var2) {
      this.field_70466_a.set(☃, ☃);
      this.field_70465_c.func_75130_a(this);
   }

   @Override
   public int func_70297_j_() {
      return 64;
   }

   @Override
   public void func_70296_d() {
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
      this.field_70466_a.clear();
   }

   @Override
   public int func_174923_h() {
      return this.field_174924_c;
   }

   @Override
   public int func_174922_i() {
      return this.field_70464_b;
   }

   @Override
   public void func_194018_a(RecipeItemHelper var1) {
      for(ItemStack ☃ : this.field_70466_a) {
         ☃.func_195932_a(☃);
      }
   }
}
