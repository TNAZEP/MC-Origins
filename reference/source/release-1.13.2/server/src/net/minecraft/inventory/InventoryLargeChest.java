package net.minecraft.inventory;

import javax.annotation.Nullable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public class InventoryLargeChest implements ILockableContainer {
   private final ITextComponent field_70479_a;
   private final ILockableContainer field_70477_b;
   private final ILockableContainer field_70478_c;

   public InventoryLargeChest(ITextComponent var1, ILockableContainer var2, ILockableContainer var3) {
      this.field_70479_a = ☃;
      if (☃ == null) {
         ☃ = ☃;
      }

      if (☃ == null) {
         ☃ = ☃;
      }

      this.field_70477_b = ☃;
      this.field_70478_c = ☃;
      if (☃.func_174893_q_()) {
         ☃.func_174892_a(☃.func_174891_i());
      } else if (☃.func_174893_q_()) {
         ☃.func_174892_a(☃.func_174891_i());
      }
   }

   @Override
   public int func_70302_i_() {
      return this.field_70477_b.func_70302_i_() + this.field_70478_c.func_70302_i_();
   }

   @Override
   public boolean func_191420_l() {
      return this.field_70477_b.func_191420_l() && this.field_70478_c.func_191420_l();
   }

   public boolean func_90010_a(IInventory var1) {
      return this.field_70477_b == ☃ || this.field_70478_c == ☃;
   }

   @Override
   public ITextComponent func_200200_C_() {
      if (this.field_70477_b.func_145818_k_()) {
         return this.field_70477_b.func_200200_C_();
      } else {
         return this.field_70478_c.func_145818_k_() ? this.field_70478_c.func_200200_C_() : this.field_70479_a;
      }
   }

   @Override
   public boolean func_145818_k_() {
      return this.field_70477_b.func_145818_k_() || this.field_70478_c.func_145818_k_();
   }

   @Nullable
   @Override
   public ITextComponent func_200201_e() {
      return this.field_70477_b.func_145818_k_() ? this.field_70477_b.func_200201_e() : this.field_70478_c.func_200201_e();
   }

   @Override
   public ItemStack func_70301_a(int var1) {
      return ☃ >= this.field_70477_b.func_70302_i_()
         ? this.field_70478_c.func_70301_a(☃ - this.field_70477_b.func_70302_i_())
         : this.field_70477_b.func_70301_a(☃);
   }

   @Override
   public ItemStack func_70298_a(int var1, int var2) {
      return ☃ >= this.field_70477_b.func_70302_i_()
         ? this.field_70478_c.func_70298_a(☃ - this.field_70477_b.func_70302_i_(), ☃)
         : this.field_70477_b.func_70298_a(☃, ☃);
   }

   @Override
   public ItemStack func_70304_b(int var1) {
      return ☃ >= this.field_70477_b.func_70302_i_()
         ? this.field_70478_c.func_70304_b(☃ - this.field_70477_b.func_70302_i_())
         : this.field_70477_b.func_70304_b(☃);
   }

   @Override
   public void func_70299_a(int var1, ItemStack var2) {
      if (☃ >= this.field_70477_b.func_70302_i_()) {
         this.field_70478_c.func_70299_a(☃ - this.field_70477_b.func_70302_i_(), ☃);
      } else {
         this.field_70477_b.func_70299_a(☃, ☃);
      }
   }

   @Override
   public int func_70297_j_() {
      return this.field_70477_b.func_70297_j_();
   }

   @Override
   public void func_70296_d() {
      this.field_70477_b.func_70296_d();
      this.field_70478_c.func_70296_d();
   }

   @Override
   public boolean func_70300_a(EntityPlayer var1) {
      return this.field_70477_b.func_70300_a(☃) && this.field_70478_c.func_70300_a(☃);
   }

   @Override
   public void func_174889_b(EntityPlayer var1) {
      this.field_70477_b.func_174889_b(☃);
      this.field_70478_c.func_174889_b(☃);
   }

   @Override
   public void func_174886_c(EntityPlayer var1) {
      this.field_70477_b.func_174886_c(☃);
      this.field_70478_c.func_174886_c(☃);
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
   public boolean func_174893_q_() {
      return this.field_70477_b.func_174893_q_() || this.field_70478_c.func_174893_q_();
   }

   @Override
   public void func_174892_a(LockCode var1) {
      this.field_70477_b.func_174892_a(☃);
      this.field_70478_c.func_174892_a(☃);
   }

   @Override
   public LockCode func_174891_i() {
      return this.field_70477_b.func_174891_i();
   }

   @Override
   public String func_174875_k() {
      return this.field_70477_b.func_174875_k();
   }

   @Override
   public Container func_174876_a(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerChest(☃, this, ☃);
   }

   @Override
   public void func_174888_l() {
      this.field_70477_b.func_174888_l();
      this.field_70478_c.func_174888_l();
   }
}
