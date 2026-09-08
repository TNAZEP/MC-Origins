package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.ArrayUtils;

public class NBTTagByteArray extends NBTTagCollection<NBTTagByte> {
   private byte[] field_74754_a;

   NBTTagByteArray() {
   }

   public NBTTagByteArray(byte[] var1) {
      this.field_74754_a = ☃;
   }

   public NBTTagByteArray(List<Byte> var1) {
      this(func_193589_a(☃));
   }

   private static byte[] func_193589_a(List<Byte> var0) {
      byte[] ☃ = new byte[☃.size()];

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         Byte ☃xx = (Byte)☃.get(☃x);
         ☃[☃x] = ☃xx == null ? 0 : ☃xx;
      }

      return ☃;
   }

   @Override
   public void func_74734_a(DataOutput var1) throws IOException {
      ☃.writeInt(this.field_74754_a.length);
      ☃.write(this.field_74754_a);
   }

   @Override
   public void func_152446_a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      ☃.func_152450_a(192L);
      int ☃ = ☃.readInt();
      ☃.func_152450_a((long)(8 * ☃));
      this.field_74754_a = new byte[☃];
      ☃.readFully(this.field_74754_a);
   }

   @Override
   public byte func_74732_a() {
      return 7;
   }

   @Override
   public String toString() {
      StringBuilder ☃ = new StringBuilder("[B;");

      for(int ☃x = 0; ☃x < this.field_74754_a.length; ++☃x) {
         if (☃x != 0) {
            ☃.append(',');
         }

         ☃.append(this.field_74754_a[☃x]).append('B');
      }

      return ☃.append(']').toString();
   }

   @Override
   public INBTBase func_74737_b() {
      byte[] ☃ = new byte[this.field_74754_a.length];
      System.arraycopy(this.field_74754_a, 0, ☃, 0, this.field_74754_a.length);
      return new NBTTagByteArray(☃);
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else {
         return ☃ instanceof NBTTagByteArray && Arrays.equals(this.field_74754_a, ((NBTTagByteArray)☃).field_74754_a);
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.field_74754_a);
   }

   @Override
   public ITextComponent func_199850_a(String var1, int var2) {
      ITextComponent ☃ = new TextComponentString("B").func_211708_a(field_197641_e);
      ITextComponent ☃x = new TextComponentString("[").func_150257_a(☃).func_150258_a(";");

      for(int ☃xx = 0; ☃xx < this.field_74754_a.length; ++☃xx) {
         ITextComponent ☃xxx = new TextComponentString(String.valueOf(this.field_74754_a[☃xx])).func_211708_a(field_197640_d);
         ☃x.func_150258_a(" ").func_150257_a(☃xxx).func_150257_a(☃);
         if (☃xx != this.field_74754_a.length - 1) {
            ☃x.func_150258_a(",");
         }
      }

      ☃x.func_150258_a("]");
      return ☃x;
   }

   public byte[] func_150292_c() {
      return this.field_74754_a;
   }

   @Override
   public int size() {
      return this.field_74754_a.length;
   }

   public NBTTagByte func_197647_c(int var1) {
      return new NBTTagByte(this.field_74754_a[☃]);
   }

   @Override
   public void func_197648_a(int var1, INBTBase var2) {
      this.field_74754_a[☃] = ((NBTPrimitive)☃).func_150290_f();
   }

   @Override
   public void func_197649_b(int var1) {
      this.field_74754_a = ArrayUtils.remove(this.field_74754_a, ☃);
   }
}
