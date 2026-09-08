package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class NBTTagEnd implements INBTBase {
   @Override
   public void func_152446_a(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      ☃.func_152450_a(64L);
   }

   @Override
   public void func_74734_a(DataOutput var1) throws IOException {
   }

   @Override
   public byte func_74732_a() {
      return 0;
   }

   @Override
   public String toString() {
      return "END";
   }

   public NBTTagEnd func_74737_b() {
      return new NBTTagEnd();
   }

   @Override
   public ITextComponent func_199850_a(String var1, int var2) {
      return new TextComponentString("");
   }

   public boolean equals(Object var1) {
      return ☃ instanceof NBTTagEnd;
   }

   public int hashCode() {
      return this.func_74732_a();
   }
}
