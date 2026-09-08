package net.minecraft.entity.player;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class InventoryPlayer implements IInventory {
   public final NonNullList<ItemStack> field_70462_a = NonNullList.func_191197_a(36, ItemStack.field_190927_a);
   public final NonNullList<ItemStack> field_70460_b = NonNullList.func_191197_a(4, ItemStack.field_190927_a);
   public final NonNullList<ItemStack> field_184439_c = NonNullList.func_191197_a(1, ItemStack.field_190927_a);
   private final List<NonNullList<ItemStack>> field_184440_g = ImmutableList.of(this.field_70462_a, this.field_70460_b, this.field_184439_c);
   public int field_70461_c;
   public EntityPlayer field_70458_d;
   private ItemStack field_70457_g = ItemStack.field_190927_a;
   private int field_194017_h;

   public InventoryPlayer(EntityPlayer var1) {
      this.field_70458_d = ☃;
   }

   public ItemStack func_70448_g() {
      return func_184435_e(this.field_70461_c) ? this.field_70462_a.get(this.field_70461_c) : ItemStack.field_190927_a;
   }

   public static int func_70451_h() {
      return 9;
   }

   private boolean func_184436_a(ItemStack var1, ItemStack var2) {
      return !☃.func_190926_b()
         && this.func_184431_b(☃, ☃)
         && ☃.func_77985_e()
         && ☃.func_190916_E() < ☃.func_77976_d()
         && ☃.func_190916_E() < this.func_70297_j_();
   }

   private boolean func_184431_b(ItemStack var1, ItemStack var2) {
      return ☃.func_77973_b() == ☃.func_77973_b() && ItemStack.func_77970_a(☃, ☃);
   }

   public int func_70447_i() {
      for(int ☃ = 0; ☃ < this.field_70462_a.size(); ++☃) {
         if (this.field_70462_a.get(☃).func_190926_b()) {
            return ☃;
         }
      }

      return -1;
   }

   public void func_184430_d(int var1) {
      this.field_70461_c = this.func_184433_k();
      ItemStack ☃ = this.field_70462_a.get(this.field_70461_c);
      this.field_70462_a.set(this.field_70461_c, this.field_70462_a.get(☃));
      this.field_70462_a.set(☃, ☃);
   }

   public static boolean func_184435_e(int var0) {
      return ☃ >= 0 && ☃ < 9;
   }

   public int func_194014_c(ItemStack var1) {
      for(int ☃ = 0; ☃ < this.field_70462_a.size(); ++☃) {
         ItemStack ☃x = this.field_70462_a.get(☃);
         if (!this.field_70462_a.get(☃).func_190926_b()
            && this.func_184431_b(☃, this.field_70462_a.get(☃))
            && !this.field_70462_a.get(☃).func_77951_h()
            && !☃x.func_77948_v()
            && !☃x.func_82837_s()) {
            return ☃;
         }
      }

      return -1;
   }

   public int func_184433_k() {
      for(int ☃ = 0; ☃ < 9; ++☃) {
         int ☃x = (this.field_70461_c + ☃) % 9;
         if (this.field_70462_a.get(☃x).func_190926_b()) {
            return ☃x;
         }
      }

      for(int ☃ = 0; ☃ < 9; ++☃) {
         int ☃x = (this.field_70461_c + ☃) % 9;
         if (!this.field_70462_a.get(☃x).func_77948_v()) {
            return ☃x;
         }
      }

      return this.field_70461_c;
   }

   public int func_195408_a(Predicate<ItemStack> var1, int var2) {
      int ☃ = 0;

      for(int ☃x = 0; ☃x < this.func_70302_i_(); ++☃x) {
         ItemStack ☃xx = this.func_70301_a(☃x);
         if (!☃xx.func_190926_b() && ☃.test(☃xx)) {
            int ☃xxx = ☃ <= 0 ? ☃xx.func_190916_E() : Math.min(☃ - ☃, ☃xx.func_190916_E());
            ☃ += ☃xxx;
            if (☃ != 0) {
               ☃xx.func_190918_g(☃xxx);
               if (☃xx.func_190926_b()) {
                  this.func_70299_a(☃x, ItemStack.field_190927_a);
               }

               if (☃ > 0 && ☃ >= ☃) {
                  return ☃;
               }
            }
         }
      }

      if (!this.field_70457_g.func_190926_b() && ☃.test(this.field_70457_g)) {
         int ☃x = ☃ <= 0 ? this.field_70457_g.func_190916_E() : Math.min(☃ - ☃, this.field_70457_g.func_190916_E());
         ☃ += ☃x;
         if (☃ != 0) {
            this.field_70457_g.func_190918_g(☃x);
            if (this.field_70457_g.func_190926_b()) {
               this.field_70457_g = ItemStack.field_190927_a;
            }

            if (☃ > 0 && ☃ >= ☃) {
               return ☃;
            }
         }
      }

      return ☃;
   }

   private int func_70452_e(ItemStack var1) {
      int ☃ = this.func_70432_d(☃);
      if (☃ == -1) {
         ☃ = this.func_70447_i();
      }

      return ☃ == -1 ? ☃.func_190916_E() : this.func_191973_d(☃, ☃);
   }

   private int func_191973_d(int var1, ItemStack var2) {
      Item ☃ = ☃.func_77973_b();
      int ☃x = ☃.func_190916_E();
      ItemStack ☃xx = this.func_70301_a(☃);
      if (☃xx.func_190926_b()) {
         ☃xx = new ItemStack(☃, 0);
         if (☃.func_77942_o()) {
            ☃xx.func_77982_d(☃.func_77978_p().func_74737_b());
         }

         this.func_70299_a(☃, ☃xx);
      }

      int ☃ = ☃x;
      if (☃x > ☃xx.func_77976_d() - ☃xx.func_190916_E()) {
         ☃ = ☃xx.func_77976_d() - ☃xx.func_190916_E();
      }

      if (☃ > this.func_70297_j_() - ☃xx.func_190916_E()) {
         ☃ = this.func_70297_j_() - ☃xx.func_190916_E();
      }

      if (☃ == 0) {
         return ☃x;
      } else {
         ☃x -= ☃;
         ☃xx.func_190917_f(☃);
         ☃xx.func_190915_d(5);
         return ☃x;
      }
   }

   public int func_70432_d(ItemStack var1) {
      if (this.func_184436_a(this.func_70301_a(this.field_70461_c), ☃)) {
         return this.field_70461_c;
      } else if (this.func_184436_a(this.func_70301_a(40), ☃)) {
         return 40;
      } else {
         for(int ☃ = 0; ☃ < this.field_70462_a.size(); ++☃) {
            if (this.func_184436_a(this.field_70462_a.get(☃), ☃)) {
               return ☃;
            }
         }

         return -1;
      }
   }

   public void func_70429_k() {
      for(NonNullList<ItemStack> ☃ : this.field_184440_g) {
         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            if (!☃.get(☃x).func_190926_b()) {
               ☃.get(☃x).func_77945_a(this.field_70458_d.field_70170_p, this.field_70458_d, ☃x, this.field_70461_c == ☃x);
            }
         }
      }
   }

   public boolean func_70441_a(ItemStack var1) {
      return this.func_191971_c(-1, ☃);
   }

   public boolean func_191971_c(int var1, ItemStack var2) {
      if (☃.func_190926_b()) {
         return false;
      } else {
         try {
            if (☃.func_77951_h()) {
               if (☃ == -1) {
                  ☃ = this.func_70447_i();
               }

               if (☃ >= 0) {
                  this.field_70462_a.set(☃, ☃.func_77946_l());
                  this.field_70462_a.get(☃).func_190915_d(5);
                  ☃.func_190920_e(0);
                  return true;
               } else if (this.field_70458_d.field_71075_bZ.field_75098_d) {
                  ☃.func_190920_e(0);
                  return true;
               } else {
                  return false;
               }
            } else {
               int ☃;
               do {
                  ☃ = ☃.func_190916_E();
                  if (☃ == -1) {
                     ☃.func_190920_e(this.func_70452_e(☃));
                  } else {
                     ☃.func_190920_e(this.func_191973_d(☃, ☃));
                  }
               } while(!☃.func_190926_b() && ☃.func_190916_E() < ☃);

               if (☃.func_190916_E() == ☃ && this.field_70458_d.field_71075_bZ.field_75098_d) {
                  ☃.func_190920_e(0);
                  return true;
               } else {
                  return ☃.func_190916_E() < ☃;
               }
            }
         } catch (Throwable var6) {
            CrashReport ☃ = CrashReport.func_85055_a(var6, "Adding item to inventory");
            CrashReportCategory ☃x = ☃.func_85058_a("Item being added");
            ☃x.func_71507_a("Item ID", Item.func_150891_b(☃.func_77973_b()));
            ☃x.func_71507_a("Item data", ☃.func_77952_i());
            ☃x.func_189529_a("Item name", () -> ☃.func_200301_q().getString());
            throw new ReportedException(☃);
         }
      }
   }

   public void func_191975_a(World var1, ItemStack var2) {
      if (!☃.field_72995_K) {
         while(!☃.func_190926_b()) {
            int ☃ = this.func_70432_d(☃);
            if (☃ == -1) {
               ☃ = this.func_70447_i();
            }

            if (☃ == -1) {
               this.field_70458_d.func_71019_a(☃, false);
               break;
            }

            int ☃ = ☃.func_77976_d() - this.func_70301_a(☃).func_190916_E();
            if (this.func_191971_c(☃, ☃.func_77979_a(☃))) {
               ((EntityPlayerMP)this.field_70458_d).field_71135_a.func_147359_a(new SPacketSetSlot(-2, ☃, this.func_70301_a(☃)));
            }
         }
      }
   }

   @Override
   public ItemStack func_70298_a(int var1, int var2) {
      List<ItemStack> ☃ = null;

      for(NonNullList<ItemStack> ☃x : this.field_184440_g) {
         if (☃ < ☃x.size()) {
            ☃ = ☃x;
            break;
         }

         ☃ -= ☃x.size();
      }

      return ☃ != null && !((ItemStack)☃.get(☃)).func_190926_b() ? ItemStackHelper.func_188382_a(☃, ☃, ☃) : ItemStack.field_190927_a;
   }

   public void func_184437_d(ItemStack var1) {
      for(NonNullList<ItemStack> ☃ : this.field_184440_g) {
         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            if (☃.get(☃x) == ☃) {
               ☃.set(☃x, ItemStack.field_190927_a);
               break;
            }
         }
      }
   }

   @Override
   public ItemStack func_70304_b(int var1) {
      NonNullList<ItemStack> ☃ = null;

      for(NonNullList<ItemStack> ☃x : this.field_184440_g) {
         if (☃ < ☃x.size()) {
            ☃ = ☃x;
            break;
         }

         ☃ -= ☃x.size();
      }

      if (☃ != null && !☃.get(☃).func_190926_b()) {
         ItemStack ☃x = ☃.get(☃);
         ☃.set(☃, ItemStack.field_190927_a);
         return ☃x;
      } else {
         return ItemStack.field_190927_a;
      }
   }

   @Override
   public void func_70299_a(int var1, ItemStack var2) {
      NonNullList<ItemStack> ☃ = null;

      for(NonNullList<ItemStack> ☃x : this.field_184440_g) {
         if (☃ < ☃x.size()) {
            ☃ = ☃x;
            break;
         }

         ☃ -= ☃x.size();
      }

      if (☃ != null) {
         ☃.set(☃, ☃);
      }
   }

   public float func_184438_a(IBlockState var1) {
      return this.field_70462_a.get(this.field_70461_c).func_150997_a(☃);
   }

   public NBTTagList func_70442_a(NBTTagList var1) {
      for(int ☃ = 0; ☃ < this.field_70462_a.size(); ++☃) {
         if (!this.field_70462_a.get(☃).func_190926_b()) {
            NBTTagCompound ☃x = new NBTTagCompound();
            ☃x.func_74774_a("Slot", (byte)☃);
            this.field_70462_a.get(☃).func_77955_b(☃x);
            ☃.add((INBTBase)☃x);
         }
      }

      for(int ☃ = 0; ☃ < this.field_70460_b.size(); ++☃) {
         if (!this.field_70460_b.get(☃).func_190926_b()) {
            NBTTagCompound ☃x = new NBTTagCompound();
            ☃x.func_74774_a("Slot", (byte)(☃ + 100));
            this.field_70460_b.get(☃).func_77955_b(☃x);
            ☃.add((INBTBase)☃x);
         }
      }

      for(int ☃ = 0; ☃ < this.field_184439_c.size(); ++☃) {
         if (!this.field_184439_c.get(☃).func_190926_b()) {
            NBTTagCompound ☃x = new NBTTagCompound();
            ☃x.func_74774_a("Slot", (byte)(☃ + 150));
            this.field_184439_c.get(☃).func_77955_b(☃x);
            ☃.add((INBTBase)☃x);
         }
      }

      return ☃;
   }

   public void func_70443_b(NBTTagList var1) {
      this.field_70462_a.clear();
      this.field_70460_b.clear();
      this.field_184439_c.clear();

      for(int ☃ = 0; ☃ < ☃.size(); ++☃) {
         NBTTagCompound ☃x = ☃.func_150305_b(☃);
         int ☃xx = ☃x.func_74771_c("Slot") & 255;
         ItemStack ☃xxx = ItemStack.func_199557_a(☃x);
         if (!☃xxx.func_190926_b()) {
            if (☃xx >= 0 && ☃xx < this.field_70462_a.size()) {
               this.field_70462_a.set(☃xx, ☃xxx);
            } else if (☃xx >= 100 && ☃xx < this.field_70460_b.size() + 100) {
               this.field_70460_b.set(☃xx - 100, ☃xxx);
            } else if (☃xx >= 150 && ☃xx < this.field_184439_c.size() + 150) {
               this.field_184439_c.set(☃xx - 150, ☃xxx);
            }
         }
      }
   }

   @Override
   public int func_70302_i_() {
      return this.field_70462_a.size() + this.field_70460_b.size() + this.field_184439_c.size();
   }

   @Override
   public boolean func_191420_l() {
      for(ItemStack ☃ : this.field_70462_a) {
         if (!☃.func_190926_b()) {
            return false;
         }
      }

      for(ItemStack ☃ : this.field_70460_b) {
         if (!☃.func_190926_b()) {
            return false;
         }
      }

      for(ItemStack ☃ : this.field_184439_c) {
         if (!☃.func_190926_b()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public ItemStack func_70301_a(int var1) {
      List<ItemStack> ☃ = null;

      for(NonNullList<ItemStack> ☃x : this.field_184440_g) {
         if (☃ < ☃x.size()) {
            ☃ = ☃x;
            break;
         }

         ☃ -= ☃x.size();
      }

      return ☃ == null ? ItemStack.field_190927_a : (ItemStack)☃.get(☃);
   }

   @Override
   public ITextComponent func_200200_C_() {
      return new TextComponentTranslation("container.inventory");
   }

   @Nullable
   @Override
   public ITextComponent func_200201_e() {
      return null;
   }

   @Override
   public boolean func_145818_k_() {
      return false;
   }

   @Override
   public int func_70297_j_() {
      return 64;
   }

   public boolean func_184432_b(IBlockState var1) {
      return this.func_70301_a(this.field_70461_c).func_150998_b(☃);
   }

   public void func_70449_g(float var1) {
      if (!(☃ <= 0.0F)) {
         ☃ /= 4.0F;
         if (☃ < 1.0F) {
            ☃ = 1.0F;
         }

         for(int ☃ = 0; ☃ < this.field_70460_b.size(); ++☃) {
            ItemStack ☃x = this.field_70460_b.get(☃);
            if (☃x.func_77973_b() instanceof ItemArmor) {
               ☃x.func_77972_a((int)☃, this.field_70458_d);
            }
         }
      }
   }

   public void func_70436_m() {
      for(List<ItemStack> ☃ : this.field_184440_g) {
         for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
            ItemStack ☃xx = (ItemStack)☃.get(☃x);
            if (!☃xx.func_190926_b()) {
               this.field_70458_d.func_146097_a(☃xx, true, false);
               ☃.set(☃x, ItemStack.field_190927_a);
            }
         }
      }
   }

   @Override
   public void func_70296_d() {
      ++this.field_194017_h;
   }

   public void func_70437_b(ItemStack var1) {
      this.field_70457_g = ☃;
   }

   public ItemStack func_70445_o() {
      return this.field_70457_g;
   }

   @Override
   public boolean func_70300_a(EntityPlayer var1) {
      if (this.field_70458_d.field_70128_L) {
         return false;
      } else {
         return !(☃.func_70068_e(this.field_70458_d) > 64.0);
      }
   }

   public boolean func_70431_c(ItemStack var1) {
      for(List<ItemStack> ☃ : this.field_184440_g) {
         for(ItemStack ☃x : ☃) {
            if (!☃x.func_190926_b() && ☃x.func_77969_a(☃)) {
               return true;
            }
         }
      }

      return false;
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

   public void func_70455_b(InventoryPlayer var1) {
      for(int ☃ = 0; ☃ < this.func_70302_i_(); ++☃) {
         this.func_70299_a(☃, ☃.func_70301_a(☃));
      }

      this.field_70461_c = ☃.field_70461_c;
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
      for(List<ItemStack> ☃ : this.field_184440_g) {
         ☃.clear();
      }
   }

   public void func_201571_a(RecipeItemHelper var1) {
      for(ItemStack ☃ : this.field_70462_a) {
         ☃.func_195932_a(☃);
      }
   }
}
