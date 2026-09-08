package net.minecraft.data;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NBTToSNBTConverter implements IDataProvider {
   private static final Logger field_200418_a = LogManager.getLogger();
   private final DataGenerator field_200419_b;

   public NBTToSNBTConverter(DataGenerator var1) {
      this.field_200419_b = ☃;
   }

   @Override
   public void func_200398_a(DirectoryCache var1) throws IOException {
      Path ☃ = this.field_200419_b.func_200391_b();

      for(Path ☃x : this.field_200419_b.func_200389_a()) {
         Files.walk(☃x).filter(var0 -> var0.toString().endsWith(".nbt")).forEach(var3 -> this.func_200414_a(var3, this.func_200417_a(☃, var3), ☃));
      }
   }

   @Override
   public String func_200397_b() {
      return "NBT to SNBT";
   }

   private String func_200417_a(Path var1, Path var2) {
      String ☃ = ☃.relativize(☃).toString().replaceAll("\\\\", "/");
      return ☃.substring(0, ☃.length() - ".nbt".length());
   }

   private void func_200414_a(Path var1, String var2, Path var3) {
      try {
         NBTTagCompound ☃ = CompressedStreamTools.func_74796_a(Files.newInputStream(☃));
         ITextComponent ☃x = ☃.func_199850_a("    ", 0);
         String ☃xx = ☃x.getString();
         Path ☃xxx = ☃.resolve(☃ + ".snbt");
         Files.createDirectories(☃xxx.getParent());
         BufferedWriter ☃xxxx = Files.newBufferedWriter(☃xxx);
         Throwable var9 = null;

         try {
            ☃xxxx.write(☃xx);
         } catch (Throwable var19) {
            var9 = var19;
            throw var19;
         } finally {
            if (☃xxxx != null) {
               if (var9 != null) {
                  try {
                     ☃xxxx.close();
                  } catch (Throwable var18) {
                     var9.addSuppressed(var18);
                  }
               } else {
                  ☃xxxx.close();
               }
            }
         }

         field_200418_a.info("Converted {} from NBT to SNBT", ☃);
      } catch (IOException var21) {
         field_200418_a.error("Couldn't convert {} from NBT to SNBT at {}", ☃, ☃, var21);
      }
   }
}
