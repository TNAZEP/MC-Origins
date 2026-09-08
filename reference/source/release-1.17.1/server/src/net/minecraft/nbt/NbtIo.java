package net.minecraft.nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;

public class NbtIo {
   public static CompoundTag readCompressed(File var0) throws IOException {
      InputStream â˜ƒ = new FileInputStream(â˜ƒ);

      CompoundTag var2;
      try {
         var2 = readCompressed(â˜ƒ);
      } catch (Throwable var5) {
         try {
            â˜ƒ.close();
         } catch (Throwable var4) {
            var5.addSuppressed(var4);
         }

         throw var5;
      }

      â˜ƒ.close();
      return var2;
   }

   public static CompoundTag readCompressed(InputStream var0) throws IOException {
      DataInputStream â˜ƒ = new DataInputStream(new BufferedInputStream(new GZIPInputStream(â˜ƒ)));

      CompoundTag var2;
      try {
         var2 = read(â˜ƒ, NbtAccounter.UNLIMITED);
      } catch (Throwable var5) {
         try {
            â˜ƒ.close();
         } catch (Throwable var4) {
            var5.addSuppressed(var4);
         }

         throw var5;
      }

      â˜ƒ.close();
      return var2;
   }

   public static void writeCompressed(CompoundTag var0, File var1) throws IOException {
      OutputStream â˜ƒ = new FileOutputStream(â˜ƒ);

      try {
         writeCompressed(â˜ƒ, â˜ƒ);
      } catch (Throwable var6) {
         try {
            â˜ƒ.close();
         } catch (Throwable var5) {
            var6.addSuppressed(var5);
         }

         throw var6;
      }

      â˜ƒ.close();
   }

   public static void writeCompressed(CompoundTag var0, OutputStream var1) throws IOException {
      DataOutputStream â˜ƒ = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(â˜ƒ)));

      try {
         write(â˜ƒ, â˜ƒ);
      } catch (Throwable var6) {
         try {
            â˜ƒ.close();
         } catch (Throwable var5) {
            var6.addSuppressed(var5);
         }

         throw var6;
      }

      â˜ƒ.close();
   }

   public static void write(CompoundTag var0, File var1) throws IOException {
      FileOutputStream â˜ƒ = new FileOutputStream(â˜ƒ);

      try {
         DataOutputStream â˜ƒx = new DataOutputStream(â˜ƒ);

         try {
            write(â˜ƒ, â˜ƒx);
         } catch (Throwable var8) {
            try {
               â˜ƒx.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
            }

            throw var8;
         }

         â˜ƒx.close();
      } catch (Throwable var9) {
         try {
            â˜ƒ.close();
         } catch (Throwable var6) {
            var9.addSuppressed(var6);
         }

         throw var9;
      }

      â˜ƒ.close();
   }

   @Nullable
   public static CompoundTag read(File var0) throws IOException {
      if (!â˜ƒ.exists()) {
         return null;
      } else {
         FileInputStream â˜ƒ = new FileInputStream(â˜ƒ);

         CompoundTag var3;
         try {
            DataInputStream â˜ƒx = new DataInputStream(â˜ƒ);

            try {
               var3 = read(â˜ƒx, NbtAccounter.UNLIMITED);
            } catch (Throwable var7) {
               try {
                  â˜ƒx.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }

               throw var7;
            }

            â˜ƒx.close();
         } catch (Throwable var8) {
            try {
               â˜ƒ.close();
            } catch (Throwable var5) {
               var8.addSuppressed(var5);
            }

            throw var8;
         }

         â˜ƒ.close();
         return var3;
      }
   }

   public static CompoundTag read(DataInput var0) throws IOException {
      return read(â˜ƒ, NbtAccounter.UNLIMITED);
   }

   public static CompoundTag read(DataInput var0, NbtAccounter var1) throws IOException {
      Tag â˜ƒ = readUnnamedTag(â˜ƒ, 0, â˜ƒ);
      if (â˜ƒ instanceof CompoundTag) {
         return (CompoundTag)â˜ƒ;
      } else {
         throw new IOException("Root tag must be a named compound tag");
      }
   }

   public static void write(CompoundTag var0, DataOutput var1) throws IOException {
      writeUnnamedTag(â˜ƒ, â˜ƒ);
   }

   private static void writeUnnamedTag(Tag var0, DataOutput var1) throws IOException {
      â˜ƒ.writeByte(â˜ƒ.getId());
      if (â˜ƒ.getId() != 0) {
         â˜ƒ.writeUTF("");
         â˜ƒ.write(â˜ƒ);
      }
   }

   private static Tag readUnnamedTag(DataInput var0, int var1, NbtAccounter var2) throws IOException {
      byte â˜ƒ = â˜ƒ.readByte();
      if (â˜ƒ == 0) {
         return EndTag.INSTANCE;
      } else {
         â˜ƒ.readUTF();

         try {
            return TagTypes.getType(â˜ƒ).load(â˜ƒ, â˜ƒ, â˜ƒ);
         } catch (IOException var7) {
            CrashReport â˜ƒ = CrashReport.forThrowable(var7, "Loading NBT data");
            CrashReportCategory â˜ƒx = â˜ƒ.addCategory("NBT Tag");
            â˜ƒx.setDetail("Tag type", â˜ƒ);
            throw new ReportedException(â˜ƒ);
         }
      }
   }
}
