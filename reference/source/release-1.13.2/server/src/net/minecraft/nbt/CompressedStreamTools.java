package net.minecraft.nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;

public class CompressedStreamTools {
   public static NBTTagCompound func_74796_a(InputStream var0) throws IOException {
      DataInputStream ☃ = new DataInputStream(new BufferedInputStream(new GZIPInputStream(☃)));

      NBTTagCompound var2;
      try {
         var2 = func_152456_a(☃, NBTSizeTracker.field_152451_a);
      } finally {
         ☃.close();
      }

      return var2;
   }

   public static void func_74799_a(NBTTagCompound var0, OutputStream var1) throws IOException {
      DataOutputStream ☃ = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(☃)));

      try {
         func_74800_a(☃, ☃);
      } finally {
         ☃.close();
      }
   }

   public static NBTTagCompound func_74794_a(DataInputStream var0) throws IOException {
      return func_152456_a(☃, NBTSizeTracker.field_152451_a);
   }

   public static NBTTagCompound func_152456_a(DataInput var0, NBTSizeTracker var1) throws IOException {
      INBTBase ☃ = func_152455_a(☃, 0, ☃);
      if (☃ instanceof NBTTagCompound) {
         return (NBTTagCompound)☃;
      } else {
         throw new IOException("Root tag must be a named compound tag");
      }
   }

   public static void func_74800_a(NBTTagCompound var0, DataOutput var1) throws IOException {
      func_150663_a(☃, ☃);
   }

   private static void func_150663_a(INBTBase var0, DataOutput var1) throws IOException {
      ☃.writeByte(☃.func_74732_a());
      if (☃.func_74732_a() != 0) {
         ☃.writeUTF("");
         ☃.func_74734_a(☃);
      }
   }

   private static INBTBase func_152455_a(DataInput var0, int var1, NBTSizeTracker var2) throws IOException {
      byte ☃ = ☃.readByte();
      if (☃ == 0) {
         return new NBTTagEnd();
      } else {
         ☃.readUTF();
         INBTBase ☃ = INBTBase.func_150284_a(☃);

         try {
            ☃.func_152446_a(☃, ☃, ☃);
            return ☃;
         } catch (IOException var8) {
            CrashReport ☃x = CrashReport.func_85055_a(var8, "Loading NBT data");
            CrashReportCategory ☃xx = ☃x.func_85058_a("NBT Tag");
            ☃xx.func_71507_a("Tag type", ☃);
            throw new ReportedException(☃x);
         }
      }
   }
}
