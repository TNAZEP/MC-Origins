package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.ArrayUtils;

public class NBTTagIntArray extends NBTTagCollection<NBTTagInt> {
   private int[] field_74749_a;

   NBTTagIntArray() {
   }

   public NBTTagIntArray(int[] var1) {
      this.field_74749_a = ☃;
   }

   public NBTTagIntArray(List<Integer> var1) {
      this(func_193584_a(☃));
   }

   private static int[] func_193584_a(List<Integer> var0) {
      int[] ☃ = new int[☃.size()];

      for(int ☃x = 0; ☃x < ☃.size(); ++☃x) {
         Integer ☃xx = (Integer)☃.get(☃x);
         ☃[☃x] = ☃xx == null ? 0 : ☃xx;
      }

      return ☃;
   }

   @Override
   public void func_74734_a(DataOutput var1) throws IOException {
      ☃.writeInt(this.field_74749_a.length);

      for(int ☃ : this.field_74749_a) {
         ☃.writeInt(☃);
      }
   }

   @Override
   public void func_152446_a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      ☃.func_152450_a(192L);
      int ☃ = ☃.readInt();
      ☃.func_152450_a((long)(32 * ☃));
      this.field_74749_a = new int[☃];

      for(int ☃x = 0; ☃x < ☃; ++☃x) {
         this.field_74749_a[☃x] = ☃.readInt();
      }
   }

   @Override
   public byte func_74732_a() {
      return 11;
   }

   @Override
   public String toString() {
      StringBuilder ☃ = new StringBuilder("[I;");

      for(int ☃x = 0; ☃x < this.field_74749_a.length; ++☃x) {
         if (☃x != 0) {
            ☃.append(',');
         }

         ☃.append(this.field_74749_a[☃x]);
      }

      return ☃.append(']').toString();
   }

   public NBTTagIntArray func_74737_b() {
      int[] ☃ = new int[this.field_74749_a.length];
      System.arraycopy(this.field_74749_a, 0, ☃, 0, this.field_74749_a.length);
      return new NBTTagIntArray(☃);
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else {
         return ☃ instanceof NBTTagIntArray && Arrays.equals(this.field_74749_a, ((NBTTagIntArray)☃).field_74749_a);
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.field_74749_a);
   }

   public int[] func_150302_c() {
      return this.field_74749_a;
   }

   @Override
   public ITextComponent func_199850_a(String var1, int var2) {
      ITextComponent ☃ = new TextComponentString("I").func_211708_a(field_197641_e);
      ITextComponent ☃x = new TextComponentString("[").func_150257_a(☃).func_150258_a(";");

      for(int ☃xx = 0; ☃xx < this.field_74749_a.length; ++☃xx) {
         ☃x.func_150258_a(" ").func_150257_a(new TextComponentString(String.valueOf(this.field_74749_a[☃xx])).func_211708_a(field_197640_d));
         if (☃xx != this.field_74749_a.length - 1) {
            ☃x.func_150258_a(",");
         }
      }

      ☃x.func_150258_a("]");
      return ☃x;
   }

   @Override
   public int size() {
      return this.field_74749_a.length;
   }

   public NBTTagInt func_197647_c(int var1) {
      return new NBTTagInt(this.field_74749_a[☃]);
   }

   @Override
   public void func_197648_a(int var1, INBTBase var2) {
      this.field_74749_a[☃] = ((NBTPrimitive)☃).func_150287_d();
   }

   @Override
   public void func_197649_b(int var1) {
      this.field_74749_a = ArrayUtils.remove(this.field_74749_a, ☃);
   }
}
