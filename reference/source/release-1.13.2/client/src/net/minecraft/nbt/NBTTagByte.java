package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class NBTTagByte extends NBTPrimitive {
   private byte field_74756_a;

   NBTTagByte() {
   }

   public NBTTagByte(byte var1) {
      this.field_74756_a = ☃;
   }

   @Override
   public void func_74734_a(DataOutput var1) throws IOException {
      ☃.writeByte(this.field_74756_a);
   }

   @Override
   public void func_152446_a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      ☃.func_152450_a(72L);
      this.field_74756_a = ☃.readByte();
   }

   @Override
   public byte func_74732_a() {
      return 1;
   }

   @Override
   public String toString() {
      return this.field_74756_a + "b";
   }

   public NBTTagByte func_74737_b() {
      return new NBTTagByte(this.field_74756_a);
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else {
         return ☃ instanceof NBTTagByte && this.field_74756_a == ((NBTTagByte)☃).field_74756_a;
      }
   }

   public int hashCode() {
      return this.field_74756_a;
   }

   @Override
   public ITextComponent func_199850_a(String var1, int var2) {
      ITextComponent ☃ = new TextComponentString("b").func_211708_a(field_197641_e);
      return new TextComponentString(String.valueOf(this.field_74756_a)).func_150257_a(☃).func_211708_a(field_197640_d);
   }

   @Override
   public long func_150291_c() {
      return (long)this.field_74756_a;
   }

   @Override
   public int func_150287_d() {
      return this.field_74756_a;
   }

   @Override
   public short func_150289_e() {
      return (short)this.field_74756_a;
   }

   @Override
   public byte func_150290_f() {
      return this.field_74756_a;
   }

   @Override
   public double func_150286_g() {
      return (double)this.field_74756_a;
   }

   @Override
   public float func_150288_h() {
      return (float)this.field_74756_a;
   }

   @Override
   public Number func_209908_j() {
      return this.field_74756_a;
   }
}
