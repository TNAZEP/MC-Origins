package net.minecraft.village;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MerchantRecipe {
   private ItemStack field_77403_a = ItemStack.field_190927_a;
   private ItemStack field_77401_b = ItemStack.field_190927_a;
   private ItemStack field_77402_c = ItemStack.field_190927_a;
   private int field_77400_d;
   private int field_82786_e;
   private boolean field_180323_f;

   public MerchantRecipe(NBTTagCompound var1) {
      this.func_77390_a(☃);
   }

   public MerchantRecipe(ItemStack var1, ItemStack var2, ItemStack var3) {
      this(☃, ☃, ☃, 0, 7);
   }

   public MerchantRecipe(ItemStack var1, ItemStack var2, ItemStack var3, int var4, int var5) {
      this.field_77403_a = ☃;
      this.field_77401_b = ☃;
      this.field_77402_c = ☃;
      this.field_77400_d = ☃;
      this.field_82786_e = ☃;
      this.field_180323_f = true;
   }

   public MerchantRecipe(ItemStack var1, ItemStack var2) {
      this(☃, ItemStack.field_190927_a, ☃);
   }

   public MerchantRecipe(ItemStack var1, Item var2) {
      this(☃, new ItemStack(☃));
   }

   public ItemStack func_77394_a() {
      return this.field_77403_a;
   }

   public ItemStack func_77396_b() {
      return this.field_77401_b;
   }

   public boolean func_77398_c() {
      return !this.field_77401_b.func_190926_b();
   }

   public ItemStack func_77397_d() {
      return this.field_77402_c;
   }

   public int func_180321_e() {
      return this.field_77400_d;
   }

   public int func_180320_f() {
      return this.field_82786_e;
   }

   public void func_77399_f() {
      ++this.field_77400_d;
   }

   public void func_82783_a(int var1) {
      this.field_82786_e += ☃;
   }

   public boolean func_82784_g() {
      return this.field_77400_d >= this.field_82786_e;
   }

   public void func_82785_h() {
      this.field_77400_d = this.field_82786_e;
   }

   public boolean func_180322_j() {
      return this.field_180323_f;
   }

   public void func_77390_a(NBTTagCompound var1) {
      NBTTagCompound ☃ = ☃.func_74775_l("buy");
      this.field_77403_a = ItemStack.func_199557_a(☃);
      NBTTagCompound ☃x = ☃.func_74775_l("sell");
      this.field_77402_c = ItemStack.func_199557_a(☃x);
      if (☃.func_150297_b("buyB", 10)) {
         this.field_77401_b = ItemStack.func_199557_a(☃.func_74775_l("buyB"));
      }

      if (☃.func_150297_b("uses", 99)) {
         this.field_77400_d = ☃.func_74762_e("uses");
      }

      if (☃.func_150297_b("maxUses", 99)) {
         this.field_82786_e = ☃.func_74762_e("maxUses");
      } else {
         this.field_82786_e = 7;
      }

      if (☃.func_150297_b("rewardExp", 1)) {
         this.field_180323_f = ☃.func_74767_n("rewardExp");
      } else {
         this.field_180323_f = true;
      }
   }

   public NBTTagCompound func_77395_g() {
      NBTTagCompound ☃ = new NBTTagCompound();
      ☃.func_74782_a("buy", this.field_77403_a.func_77955_b(new NBTTagCompound()));
      ☃.func_74782_a("sell", this.field_77402_c.func_77955_b(new NBTTagCompound()));
      if (!this.field_77401_b.func_190926_b()) {
         ☃.func_74782_a("buyB", this.field_77401_b.func_77955_b(new NBTTagCompound()));
      }

      ☃.func_74768_a("uses", this.field_77400_d);
      ☃.func_74768_a("maxUses", this.field_82786_e);
      ☃.func_74757_a("rewardExp", this.field_180323_f);
      return ☃;
   }
}
