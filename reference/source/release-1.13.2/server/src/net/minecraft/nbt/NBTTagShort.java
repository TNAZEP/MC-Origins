package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class NBTTagShort extends NBTPrimitive {
   private short field_74752_a;

   public NBTTagShort() {
   }

   public NBTTagShort(short var1) {
      this.field_74752_a = ☃;
   }

   @Override
   public void func_74734_a(DataOutput var1) throws IOException {
      ☃.writeShort(this.field_74752_a);
   }

   @Override
   public void func_152446_a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      ☃.func_152450_a(80L);
      this.field_74752_a = ☃.readShort();
   }

   @Override
   public byte func_74732_a() {
      return 2;
   }

   @Override
   public String toString() {
      return this.field_74752_a + "s";
   }

   public NBTTagShort func_74737_b() {
      return new NBTTagShort(this.field_74752_a);
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else {
         return ☃ instanceof NBTTagShort && this.field_74752_a == ((NBTTagShort)☃).field_74752_a;
      }
   }

   public int hashCode() {
      return this.field_74752_a;
   }

   @Override
   public ITextComponent func_199850_a(String var1, int var2) {
      ITextComponent ☃ = new TextComponentString("s").func_211708_a(field_197641_e);
      return new TextComponentString(String.valueOf(this.field_74752_a)).func_150257_a(☃).func_211708_a(field_197640_d);
   }

   @Override
   public long func_150291_c() {
      return (long)this.field_74752_a;
   }

   @Override
   public int func_150287_d() {
      return this.field_74752_a;
   }

   @Override
   public short func_150289_e() {
      return this.field_74752_a;
   }

   @Override
   public byte func_150290_f() {
      return (byte)(this.field_74752_a & 255);
   }

   @Override
   public double func_150286_g() {
      return (double)this.field_74752_a;
   }

   @Override
   public float func_150288_h() {
      return (float)this.field_74752_a;
   }

   @Override
   public Number func_209908_j() {
      return this.field_74752_a;
   }
}
