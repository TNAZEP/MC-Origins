package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class NBTTagString implements INBTBase {
   private String field_74751_a;

   public NBTTagString() {
      this("");
   }

   public NBTTagString(String var1) {
      Objects.requireNonNull(☃, "Null string not allowed");
      this.field_74751_a = ☃;
   }

   @Override
   public void func_74734_a(DataOutput var1) throws IOException {
      ☃.writeUTF(this.field_74751_a);
   }

   @Override
   public void func_152446_a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      ☃.func_152450_a(288L);
      this.field_74751_a = ☃.readUTF();
      ☃.func_152450_a((long)(16 * this.field_74751_a.length()));
   }

   @Override
   public byte func_74732_a() {
      return 8;
   }

   @Override
   public String toString() {
      return func_197654_a(this.field_74751_a, true);
   }

   public NBTTagString func_74737_b() {
      return new NBTTagString(this.field_74751_a);
   }

   public boolean equals(Object var1) {
      if (this == ☃) {
         return true;
      } else {
         return ☃ instanceof NBTTagString && Objects.equals(this.field_74751_a, ((NBTTagString)☃).field_74751_a);
      }
   }

   public int hashCode() {
      return this.field_74751_a.hashCode();
   }

   @Override
   public String func_150285_a_() {
      return this.field_74751_a;
   }

   @Override
   public ITextComponent func_199850_a(String var1, int var2) {
      ITextComponent ☃ = new TextComponentString(func_197654_a(this.field_74751_a, false)).func_211708_a(field_197639_c);
      return new TextComponentString("\"").func_150257_a(☃).func_150258_a("\"");
   }

   public static String func_197654_a(String var0, boolean var1) {
      StringBuilder ☃ = new StringBuilder();
      if (☃) {
         ☃.append('"');
      }

      for(int ☃ = 0; ☃ < ☃.length(); ++☃) {
         char ☃x = ☃.charAt(☃);
         if (☃x == '\\' || ☃x == '"') {
            ☃.append('\\');
         }

         ☃.append(☃x);
      }

      if (☃) {
         ☃.append('"');
      }

      return ☃.toString();
   }
}
