package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class NBTTagFloat extends NBTPrimitive {
   private float field_74750_a;

   NBTTagFloat() {
   }

   public NBTTagFloat(float var1) {
      this.field_74750_a = ☃;
   }

   @Override
   public void func_74734_a(DataOutput var1) throws IOException {
      ☃.writeFloat(this.field_74750_a);
   }

   @Override
   public void func_152446_a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      ☃.func_152450_a(96L);
      this.field_74750_a = ☃.readFloat();
   }

   @Override
   public byte func_74732_a() {
      return 5;
   }

   @Override
   public String toString() {
      return this.field_74750_a + "f";
   }

   public NBTTagFloat func_74737_b() {
      return new NBTTagFloat(this.field_74750_a);
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else {
         return ☃ instanceof NBTTagFloat && this.field_74750_a == ((NBTTagFloat)☃).field_74750_a;
      }
   }

   public int hashCode() {
      return Float.floatToIntBits(this.field_74750_a);
   }

   @Override
   public ITextComponent func_199850_a(String var1, int var2) {
      ITextComponent ☃ = new TextComponentString("f").func_211708_a(field_197641_e);
      return new TextComponentString(String.valueOf(this.field_74750_a)).func_150257_a(☃).func_211708_a(field_197640_d);
   }

   @Override
   public long func_150291_c() {
      return (long)this.field_74750_a;
   }

   @Override
   public int func_150287_d() {
      return MathHelper.func_76141_d(this.field_74750_a);
   }

   @Override
   public short func_150289_e() {
      return (short)(MathHelper.func_76141_d(this.field_74750_a) & 65535);
   }

   @Override
   public byte func_150290_f() {
      return (byte)(MathHelper.func_76141_d(this.field_74750_a) & 0xFF);
   }

   @Override
   public double func_150286_g() {
      return (double)this.field_74750_a;
   }

   @Override
   public float func_150288_h() {
      return this.field_74750_a;
   }

   @Override
   public Number func_209908_j() {
      return this.field_74750_a;
   }
}
