package net.minecraft.nbt;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.ArrayUtils;

public class NBTTagLongArray extends NBTTagCollection<NBTTagLong> {
   private long[] field_193587_b;

   NBTTagLongArray() {
   }

   public NBTTagLongArray(long[] var1) {
      this.field_193587_b = ☃;
   }

   public NBTTagLongArray(LongSet var1) {
      this.field_193587_b = ☃.toLongArray();
   }

   public NBTTagLongArray(List<Long> var1) {
      this(func_193586_a(☃));
   }

   private static long[] func_193586_a(List<Long> var0) {
      long[] ☃ = new long[☃.size()];

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         Long ☃xx = (Long)☃.get(☃x);
         ☃[☃x] = ☃xx == null ? 0L : ☃xx;
      }

      return ☃;
   }

   @Override
   public void func_74734_a(DataOutput var1) throws IOException {
      ☃.writeInt(this.field_193587_b.length);

      for(long ☃ : this.field_193587_b) {
         ☃.writeLong(☃);
      }
   }

   @Override
   public void func_152446_a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      ☃.func_152450_a(192L);
      int ☃ = ☃.readInt();
      ☃.func_152450_a((long)(64 * ☃));
      this.field_193587_b = new long[☃];

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         this.field_193587_b[☃x] = ☃.readLong();
      }
   }

   @Override
   public byte func_74732_a() {
      return 12;
   }

   @Override
   public String toString() {
      StringBuilder ☃ = new StringBuilder("[L;");

      for(int ☃x = 0; ☃x < this.field_193587_b.length; ++☃x) {
         if (☃x != 0) {
            ☃.append(',');
         }

         ☃.append(this.field_193587_b[☃x]).append('L');
      }

      return ☃.append(']').toString();
   }

   public NBTTagLongArray func_74737_b() {
      long[] ☃ = new long[this.field_193587_b.length];
      System.arraycopy(this.field_193587_b, 0, ☃, 0, this.field_193587_b.length);
      return new NBTTagLongArray(☃);
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else {
         return ☃ instanceof NBTTagLongArray && Arrays.equals(this.field_193587_b, ((NBTTagLongArray)☃).field_193587_b);
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.field_193587_b);
   }

   @Override
   public ITextComponent func_199850_a(String var1, int var2) {
      ITextComponent ☃ = new TextComponentString("L").func_211708_a(field_197641_e);
      ITextComponent ☃x = new TextComponentString("[").func_150257_a(☃).func_150258_a(";");

      for(int ☃xx = 0; ☃xx < this.field_193587_b.length; ++☃xx) {
         ITextComponent ☃xxx = new TextComponentString(String.valueOf(this.field_193587_b[☃xx])).func_211708_a(field_197640_d);
         ☃x.func_150258_a(" ").func_150257_a(☃xxx).func_150257_a(☃);
         if (☃xx != this.field_193587_b.length - 1) {
            ☃x.func_150258_a(",");
         }
      }

      ☃x.func_150258_a("]");
      return ☃x;
   }

   public long[] func_197652_h() {
      return this.field_193587_b;
   }

   @Override
   public int size() {
      return this.field_193587_b.length;
   }

   public NBTTagLong func_197647_c(int var1) {
      return new NBTTagLong(this.field_193587_b[☃]);
   }

   @Override
   public void func_197648_a(int var1, INBTBase var2) {
      this.field_193587_b[☃] = ((NBTPrimitive)☃).func_150291_c();
   }

   @Override
   public void func_197649_b(int var1) {
      this.field_193587_b = ArrayUtils.remove(this.field_193587_b, ☃);
   }
}
