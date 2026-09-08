package net.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityComparator extends TileEntity {
   private int field_145997_a;

   public TileEntityComparator() {
      super(TileEntityType.field_200988_s);
   }

   @Override
   public NBTTagCompound func_189515_b(NBTTagCompound var1) {
      super.func_189515_b(☃);
      ☃.func_74768_a("OutputSignal", this.field_145997_a);
      return ☃;
   }

   @Override
   public void func_145839_a(NBTTagCompound var1) {
      super.func_145839_a(☃);
      this.field_145997_a = ☃.func_74762_e("OutputSignal");
   }

   public int func_145996_a() {
      return this.field_145997_a;
   }

   public void func_145995_a(int var1) {
      this.field_145997_a = ☃;
   }
}
