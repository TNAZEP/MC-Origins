package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class NBTTagLong extends NBTPrimitive {
   private long field_74753_a;

   NBTTagLong() {
   }

   public NBTTagLong(long var1) {
      this.field_74753_a = ☃;
   }

   @Override
   public void func_74734_a(DataOutput var1) throws IOException {
      ☃.writeLong(this.field_74753_a);
   }

   @Override
   public void func_152446_a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      ☃.func_152450_a(128L);
      this.field_74753_a = ☃.readLong();
   }

   @Override
   public byte func_74732_a() {
      return 4;
   }

   @Override
   public String toString() {
      return this.field_74753_a + "L";
   }

   public NBTTagLong func_74737_b() {
      return new NBTTagLong(this.field_74753_a);
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else {
         return ☃ instanceof NBTTagLong && this.field_74753_a == ((NBTTagLong)☃).field_74753_a;
      }
   }

   public int hashCode() {
      return (int)(this.field_74753_a ^ this.field_74753_a >>> 32);
   }

   @Override
   public ITextComponent func_199850_a(String var1, int var2) {
      ITextComponent ☃ = new TextComponentString("L").func_211708_a(field_197641_e);
      return new TextComponentString(String.valueOf(this.field_74753_a)).func_150257_a(☃).func_211708_a(field_197640_d);
   }

   @Override
   public long func_150291_c() {
      return this.field_74753_a;
   }

   @Override
   public int func_150287_d() {
      return (int)(this.field_74753_a & -1L);
   }

   @Override
   public short func_150289_e() {
      return (short)((int)(this.field_74753_a & 65535L));
   }

   @Override
   public byte func_150290_f() {
      return (byte)((int)(this.field_74753_a & 255L));
   }

   @Override
   public double func_150286_g() {
      return (double)this.field_74753_a;
   }

   @Override
   public float func_150288_h() {
      return (float)this.field_74753_a;
   }

   @Override
   public Number func_209908_j() {
      return this.field_74753_a;
   }
}
