package net.minecraft.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityJukebox extends TileEntity {
   private ItemStack field_195538_a = ItemStack.field_190927_a;

   public TileEntityJukebox() {
      super(TileEntityType.field_200975_f);
   }

   @Override
   public void func_145839_a(NBTTagCompound var1) {
      super.func_145839_a(☃);
      if (☃.func_150297_b("RecordItem", 10)) {
         this.func_195535_a(ItemStack.func_199557_a(☃.func_74775_l("RecordItem")));
      }
   }

   @Override
   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      super.func_189515_b(☃);
      if (!this.func_195537_c().func_190926_b()) {
         ☃.func_74782_a("RecordItem", this.func_195537_c().func_77955_b(new NBTTagCompound()));
      }

      return ☃;
   }

   public ItemStack func_195537_c() {
      return this.field_195538_a;
   }

   public void func_195535_a(ItemStack var1) {
      this.field_195538_a = ☃;
      this.func_70296_d();
   }
}
