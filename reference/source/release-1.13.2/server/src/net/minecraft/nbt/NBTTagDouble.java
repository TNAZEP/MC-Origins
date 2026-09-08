package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class NBTTagDouble extends NBTPrimitive {
   private double field_74755_a;

   NBTTagDouble() {
   }

   public NBTTagDouble(double var1) {
      this.field_74755_a = ☃;
   }

   @Override
   public void func_74734_a(DataOutput var1) throws IOException {
      ☃.writeDouble(this.field_74755_a);
   }

   @Override
   public void func_152446_a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      ☃.func_152450_a(128L);
      this.field_74755_a = ☃.readDouble();
   }

   @Override
   public byte func_74732_a() {
      return 6;
   }

   @Override
   public String toString() {
      return this.field_74755_a + "d";
   }

   public NBTTagDouble func_74737_b() {
      return new NBTTagDouble(this.field_74755_a);
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else {
         return ☃ instanceof NBTTagDouble && this.field_74755_a == ((NBTTagDouble)☃).field_74755_a;
      }
   }

   public int hashCode() {
      long ☃ = Double.doubleToLongBits(this.field_74755_a);
      return (int)(☃ ^ ☃ >>> 32);
   }

   @Override
   public ITextComponent func_199850_a(String var1, int var2) {
      ITextComponent ☃ = new TextComponentString("d").func_211708_a(field_197641_e);
      return new TextComponentString(String.valueOf(this.field_74755_a)).func_150257_a(☃).func_211708_a(field_197640_d);
   }

   @Override
   public long func_150291_c() {
      return (long)Math.floor(this.field_74755_a);
   }

   @Override
   public int func_150287_d() {
      return MathHelper.func_76128_c(this.field_74755_a);
   }

   @Override
   public short func_150289_e() {
      return (short)(MathHelper.func_76128_c(this.field_74755_a) & 65535);
   }

   @Override
   public byte func_150290_f() {
      return (byte)(MathHelper.func_76128_c(this.field_74755_a) & 0xFF);
   }

   @Override
   public double func_150286_g() {
      return this.field_74755_a;
   }

   @Override
   public float func_150288_h() {
      return (float)this.field_74755_a;
   }

   @Override
   public Number func_209908_j() {
      return this.field_74755_a;
   }
}
