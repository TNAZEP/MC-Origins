package net.minecraft.tileentity;

import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityDispenser extends TileEntityLockableLoot {
   private static final Random field_174913_f = new Random();
   private NonNullList<ItemStack> field_146022_i = NonNullList.func_191197_a(9, ItemStack.field_190927_a);

   protected TileEntityDispenser(TileEntityType<?> var1) {
      super(☃);
   }

   public TileEntityDispenser() {
      this(TileEntityType.field_200976_g);
   }

   @Override
   public int func_70302_i_() {
      return 9;
   }

   @Override
   public boolean func_191420_l() {
      for(ItemStack ☃ : this.field_146022_i) {
         if (!☃.func_190926_b()) {
            return false;
         }
      }

      return true;
   }

   public int func_146017_i() {
      this.func_184281_d(null);
      int ☃ = -1;
      int ☃x = 1;

      for(int ☃xx = 0; ☃xx < this.field_146022_i.size(); ++☃xx) {
         if (!this.field_146022_i.get(☃xx).func_190926_b() && field_174913_f.nextInt(☃x++) == 0) {
            ☃ = ☃xx;
         }
      }

      return ☃;
   }

   public int func_146019_a(ItemStack var1) {
      for(int ☃ = 0; ☃ < this.field_146022_i.size(); ++☃) {
         if (this.field_146022_i.get(☃).func_190926_b()) {
            this.func_70299_a(☃, ☃);
            return ☃;
         }
      }

      return -1;
   }

   @Override
   public ITextComponent func_200200_C_() {
      ITextComponent ☃ = this.func_200201_e();
      return (ITextComponent)(☃ != null ? ☃ : new TextComponentTranslation("container.dispenser"));
   }

   @Override
   public void func_145839_a(NBTTagCompound var1) {
      super.func_145839_a(☃);
      this.field_146022_i = NonNullList.func_191197_a(this.func_70302_i_(), ItemStack.field_190927_a);
      if (!this.func_184283_b(☃)) {
         ItemStackHelper.func_191283_b(☃, this.field_146022_i);
      }

      if (☃.func_150297_b("CustomName", 8)) {
         this.field_190577_o = ITextComponent.Serializer.func_150699_a(☃.func_74779_i("CustomName"));
      }
   }

   @Override
   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      super.func_189515_b(☃);
      if (!this.func_184282_c(☃)) {
         ItemStackHelper.func_191282_a(☃, this.field_146022_i);
      }

      ITextComponent ☃ = this.func_200201_e();
      if (☃ != null) {
         ☃.func_74778_a("CustomName", ITextComponent.Serializer.func_150696_a(☃));
      }

      return ☃;
   }

   @Override
   public int func_70297_j_() {
      return 64;
   }

   @Override
   public String func_174875_k() {
      return "minecraft:dispenser";
   }

   @Override
   public Container func_174876_a(InventoryPlayer var1, EntityPlayer var2) {
      this.func_184281_d(☃);
      return new ContainerDispenser(☃, this);
   }

   @Override
   protected NonNullList<ItemStack> func_190576_q() {
      return this.field_146022_i;
   }

   @Override
   protected void func_199721_a(NonNullList<ItemStack> var1) {
      this.field_146022_i = ☃;
   }
}
