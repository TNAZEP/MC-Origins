package net.minecraft.tileentity;

import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityBrewingStand extends TileEntityLockable implements ISidedInventory, ITickable {
   private static final int[] field_145941_a = new int[]{3};
   private static final int[] field_184277_f = new int[]{0, 1, 2, 3};
   private static final int[] field_145947_i = new int[]{0, 1, 2, 4};
   private NonNullList<ItemStack> field_145945_j = NonNullList.func_191197_a(5, ItemStack.field_190927_a);
   private int field_145946_k;
   private boolean[] field_145943_l;
   private Item field_145944_m;
   private ITextComponent field_145942_n;
   private int field_184278_m;

   public TileEntityBrewingStand() {
      super(TileEntityType.field_200981_l);
   }

   @Override
   public ITextComponent func_200200_C_() {
      return (ITextComponent)(this.field_145942_n != null ? this.field_145942_n : new TextComponentTranslation("container.brewing"));
   }

   @Override
   public boolean func_145818_k_() {
      return this.field_145942_n != null;
   }

   @Nullable
   @Override
   public ITextComponent func_200201_e() {
      return this.field_145942_n;
   }

   public void func_200224_a(@Nullable ITextComponent var1) {
      this.field_145942_n = ☃;
   }

   @Override
   public int func_70302_i_() {
      return this.field_145945_j.size();
   }

   @Override
   public boolean func_191420_l() {
      for(ItemStack ☃ : this.field_145945_j) {
         if (!☃.func_190926_b()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public void func_73660_a() {
      ItemStack ☃ = this.field_145945_j.get(4);
      if (this.field_184278_m <= 0 && ☃.func_77973_b() == Items.field_151065_br) {
         this.field_184278_m = 20;
         ☃.func_190918_g(1);
         this.func_70296_d();
      }

      boolean ☃ = this.func_145934_k();
      boolean ☃x = this.field_145946_k > 0;
      ItemStack ☃xx = this.field_145945_j.get(3);
      if (☃x) {
         --this.field_145946_k;
         boolean ☃xxx = this.field_145946_k == 0;
         if (☃xxx && ☃) {
            this.func_145940_l();
            this.func_70296_d();
         } else if (!☃) {
            this.field_145946_k = 0;
            this.func_70296_d();
         } else if (this.field_145944_m != ☃xx.func_77973_b()) {
            this.field_145946_k = 0;
            this.func_70296_d();
         }
      } else if (☃ && this.field_184278_m > 0) {
         --this.field_184278_m;
         this.field_145946_k = 400;
         this.field_145944_m = ☃xx.func_77973_b();
         this.func_70296_d();
      }

      if (!this.field_145850_b.field_72995_K) {
         boolean[] ☃ = this.func_174902_m();
         if (!Arrays.equals(☃, this.field_145943_l)) {
            this.field_145943_l = ☃;
            IBlockState ☃x = this.field_145850_b.func_180495_p(this.func_174877_v());
            if (!(☃x.func_177230_c() instanceof BlockBrewingStand)) {
               return;
            }

            for(int ☃x = 0; ☃x < BlockBrewingStand.field_176451_a.length; ++☃x) {
               ☃x = ☃x.func_206870_a(BlockBrewingStand.field_176451_a[☃x], Boolean.valueOf(☃[☃x]));
            }

            this.field_145850_b.func_180501_a(this.field_174879_c, ☃x, 2);
         }
      }
   }

   public boolean[] func_174902_m() {
      boolean[] ☃ = new boolean[3];

      for(int ☃x = 0; ☃x < 3; ++☃x) {
         if (!this.field_145945_j.get(☃x).func_190926_b()) {
            ☃[☃x] = true;
         }
      }

      return ☃;
   }

   private boolean func_145934_k() {
      ItemStack ☃ = this.field_145945_j.get(3);
      if (☃.func_190926_b()) {
         return false;
      } else if (!PotionBrewing.func_185205_a(☃)) {
         return false;
      } else {
         for(int ☃ = 0; ☃ < 3; ++☃) {
            ItemStack ☃x = this.field_145945_j.get(☃);
            if (!☃x.func_190926_b() && PotionBrewing.func_185208_a(☃x, ☃)) {
               return true;
            }
         }

         return false;
      }
   }

   private void func_145940_l() {
      ItemStack ☃ = this.field_145945_j.get(3);

      for(int ☃x = 0; ☃x < 3; ++☃x) {
         this.field_145945_j.set(☃x, PotionBrewing.func_185212_d(☃, this.field_145945_j.get(☃x)));
      }

      ☃.func_190918_g(1);
      BlockPos ☃x = this.func_174877_v();
      if (☃.func_77973_b().func_77634_r()) {
         ItemStack ☃xx = new ItemStack(☃.func_77973_b().func_77668_q());
         if (☃.func_190926_b()) {
            ☃ = ☃xx;
         } else {
            InventoryHelper.func_180173_a(this.field_145850_b, (double)☃x.func_177958_n(), (double)☃x.func_177956_o(), (double)☃x.func_177952_p(), ☃xx);
         }
      }

      this.field_145945_j.set(3, ☃);
      this.field_145850_b.func_175718_b(1035, ☃x, 0);
   }

   @Override
   public void func_145839_a(NBTTagCompound var1) {
      super.func_145839_a(☃);
      this.field_145945_j = NonNullList.func_191197_a(this.func_70302_i_(), ItemStack.field_190927_a);
      ItemStackHelper.func_191283_b(☃, this.field_145945_j);
      this.field_145946_k = ☃.func_74765_d("BrewTime");
      if (☃.func_150297_b("CustomName", 8)) {
         this.field_145942_n = ITextComponent.Serializer.func_150699_a(☃.func_74779_i("CustomName"));
      }

      this.field_184278_m = ☃.func_74771_c("Fuel");
   }

   @Override
   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      super.func_189515_b(☃);
      ☃.func_74777_a("BrewTime", (short)this.field_145946_k);
      ItemStackHelper.func_191282_a(☃, this.field_145945_j);
      if (this.field_145942_n != null) {
         ☃.func_74778_a("CustomName", ITextComponent.Serializer.func_150696_a(this.field_145942_n));
      }

      ☃.func_74774_a("Fuel", (byte)this.field_184278_m);
      return ☃;
   }

   @Override
   public ItemStack func_70301_a(int var1) {
      return ☃ >= 0 && ☃ < this.field_145945_j.size() ? this.field_145945_j.get(☃) : ItemStack.field_190927_a;
   }

   @Override
   public ItemStack func_70298_a(int var1, int var2) {
      return ItemStackHelper.func_188382_a(this.field_145945_j, ☃, ☃);
   }

   @Override
   public ItemStack func_70304_b(int var1) {
      return ItemStackHelper.func_188383_a(this.field_145945_j, ☃);
   }

   @Override
   public void func_70299_a(int var1, ItemStack var2) {
      if (☃ >= 0 && ☃ < this.field_145945_j.size()) {
         this.field_145945_j.set(☃, ☃);
      }
   }

   @Override
   public int func_70297_j_() {
      return 64;
   }

   @Override
   public boolean func_70300_a(EntityPlayer var1) {
      if (this.field_145850_b.func_175625_s(this.field_174879_c) != this) {
         return false;
      } else {
         return !(
            ☃.func_70092_e(
                  (double)this.field_174879_c.func_177958_n() + 0.5,
                  (double)this.field_174879_c.func_177956_o() + 0.5,
                  (double)this.field_174879_c.func_177952_p() + 0.5
               )
               > 64.0
         );
      }
   }

   @Override
   public void func_174889_b(EntityPlayer var1) {
   }

   @Override
   public void func_174886_c(EntityPlayer var1) {
   }

   @Override
   public boolean func_94041_b(int var1, ItemStack var2) {
      if (☃ == 3) {
         return PotionBrewing.func_185205_a(☃);
      } else {
         Item ☃ = ☃.func_77973_b();
         if (☃ == 4) {
            return ☃ == Items.field_151065_br;
         } else {
            return (☃ == Items.field_151068_bn || ☃ == Items.field_185155_bH || ☃ == Items.field_185156_bI || ☃ == Items.field_151069_bo)
               && this.func_70301_a(☃).func_190926_b();
         }
      }
   }

   @Override
   public int[] func_180463_a(EnumFacing var1) {
      if (☃ == EnumFacing.UP) {
         return field_145941_a;
      } else {
         return ☃ == EnumFacing.DOWN ? field_184277_f : field_145947_i;
      }
   }

   @Override
   public boolean func_180462_a(int var1, ItemStack var2, @Nullable EnumFacing var3) {
      return this.func_94041_b(☃, ☃);
   }

   @Override
   public boolean func_180461_b(int var1, ItemStack var2, EnumFacing var3) {
      if (☃ == 3) {
         return ☃.func_77973_b() == Items.field_151069_bo;
      } else {
         return true;
      }
   }

   @Override
   public String func_174875_k() {
      return "minecraft:brewing_stand";
   }

   @Override
   public Container func_174876_a(InventoryPlayer var1, EntityPlayer var2) {
      return new ContainerBrewingStand(☃, this);
   }

   @Override
   public int func_174887_a_(int var1) {
      switch(☃) {
         case 0:
            return this.field_145946_k;
         case 1:
            return this.field_184278_m;
         default:
            return 0;
      }
   }

   @Override
   public void func_174885_b(int var1, int var2) {
      switch(☃) {
         case 0:
            this.field_145946_k = ☃;
            break;
         case 1:
            this.field_184278_m = ☃;
      }
   }

   @Override
   public int func_174890_g() {
      return 2;
   }

   @Override
   public void func_174888_l() {
      this.field_145945_j.clear();
   }
}
